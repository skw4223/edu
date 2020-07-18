package com.example.edu48.game;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;


//4×4 그리드뷰
public class ElementGameView extends GridLayout {

    public static ElementCell[][] elementCells = new ElementCell[4][4];
    private static List<Point> emptyPoints = new ArrayList<Point>(); //빈 셀 목록 (위치)
    public int saveGrid[][] = new int[4][4]; //되돌리기용 그리드 정보 세이브
    public int saveScore; //되돌리기용 점수 세이브
    public boolean hasTouched = false;
    public static int isComplete; //성공 여부 저장

    public ElementGameView(Context context) {
        super(context);
        initGameView();
    }
    public ElementGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }
    public ElementGameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int cellWidth = (Math.min(w, h)-10)/4;//화면 크기에 따른 셀 너비 설정
        addCells(cellWidth);
        startGame();
    }


    //그리드 초기화
    private void initGameView() {
        setRowCount(4);
        setColumnCount(4);
        setOnTouchListener(new Listener());
    }

    //그리드에 셀 배치
    private void addCells(int cellWidth) {
        this.removeAllViews();
        ElementCell c;
        for(int y=0;y<4;++y) {
            for(int x = 0;x<4;++x) {
                c = new ElementCell(getContext());
                c.setNum(0);
                addView(c, cellWidth, cellWidth);
                elementCells[x][y] = c; //x가 열, y가 행 (좌표계처럼)
            }
        }
    }

    //게임 시작
    public static void startGame() {
        //점수 초기화
        ElementActivity.getElementActivity().clearScore();
        //모든 셀값 초기화
        for(int y=0;y<4;++y) {
            for(int x=0;x<4;++x) {
                elementCells[x][y].setNum(0);
            }
        }
        //성공 여부 초기화
        isComplete = 0;
        //새로운 셀 2개를 추가하며 게임 시작
        addNewCell();
        addNewCell();
    }

    //새로운 셀 추가하기
    private static void addNewCell() {
        emptyPoints.clear();// 빈 셀 목록 초기화
        //빈 셀 찾기
        for (int y = 0; y < 4; ++y) {
            for (int x = 0; x < 4; ++x) {
                if (elementCells[x][y].getNum() == 0) {
                    emptyPoints.add(new Point(x, y));
                }
            }
        }
        // 빈 셀 중 하나를 랜덤으로 골라 2 또는 4 셀을 추가하기(2일 확률 90%)
        Point p = emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
        elementCells[p.x][p.y].setNum(Math.random()>0.1?2:4);
    }

    //왼쪽으로 스와이프 처리
    private void swipeLeft() {
        boolean b = false; //그리드에 변화가 있는지 체크
        // 한 행씩 비교
        for(int y=0;y<4;++y) {
            // 한 열씩(마지막 열은 비교할 필요 없음)
            for(int x=0;x<3;++x) {
                // 기준 셀의 오른쪽에 있는 셀과 비교하기
                for(int xright=x+1;xright<4;++xright) {
                    // 기준 셀의 오른쪽 셀이 존재할 때
                    if (elementCells[xright][y].getNum()>0) {
                        // 해당 셀이 비어있으면 오른쪽 셀을 왼쪽으로 이동
                        if (elementCells[x][y].getNum()==0) {
                            elementCells[x][y].setNum(elementCells[xright][y].getNum());
                            elementCells[xright][y].setNum(0);
                            --x;//기준 셀을 왼쪽으로 한칸 옮김(셀이 맨 왼쪽으로 이동할 때까지 비교를 계속해야 함)
                            b = true;
                        }
                        //해당 셀과 오른쪽 셀이 같을 때
                        else if (elementCells[x][y].equals(elementCells[xright][y])) {
                            //해당 셀로 병합 (숫자 2배)
                            elementCells[x][y].setNum(elementCells[x][y].getNum()*2);
                            elementCells[xright][y].setNum(0);
                            ElementActivity.getElementActivity().addScore(elementCells[x][y].getNum()); //병합 점수 추가
                            b = true;
                        }
                        //오른쪽 셀의 처리를 끝낸 후엔 루프를 빠져나가 재비교
                        break;
                    }
                }
            }
        }
        //그리드에 변동이 생기면 성공 여부를 체크하고 셀을 추가
        if (b) {
            checkMissionComplete(); //게임 성공 체크
            addNewCell();
            checkGameOver(); //셀 추가 후 게임 오버 체크

        }
    }

    //오른쪽으로 스와이프 처리
    private void swipeRight() {
        boolean b = false;
        for(int y=0;y<4;++y) {
            for(int x=3;x>0;--x) {
                //기준 셀의 왼쪽에 있는 셀과 비교
                for(int xleft=x-1;xleft>=0;--xleft) {
                    if (elementCells[xleft][y].getNum()>0) {
                        if (elementCells[x][y].getNum()==0) {
                            elementCells[x][y].setNum(elementCells[xleft][y].getNum());
                            elementCells[xleft][y].setNum(0);
                            ++x;
                            b = true;
                        } else if (elementCells[x][y].equals(elementCells[xleft][y])) {
                            elementCells[x][y].setNum(elementCells[x][y].getNum()*2);
                            elementCells[xleft][y].setNum(0);
                            ElementActivity.getElementActivity().addScore(elementCells[x][y].getNum());
                            b = true;
                        }
                        break;
                    }
                }
            }
        }
        if (b) {
            checkMissionComplete();
            addNewCell();
            checkGameOver();
        }
    }

    //위쪽으로 스와이프 처리
    private void swipeUp() {
        boolean b = false;
        //한 열씩
        for(int x=0;x<4;++x) {
            //한 행씩 밑으로 내려가며 비교
            for(int y=0;y<3;++y) {
                //기준 셀의 아래쪽에 있는 셀과 비교
                for(int y_under=y+1;y_under<4;++y_under) {
                    if (elementCells[x][y_under].getNum()>0) {
                        if (elementCells[x][y].getNum()==0) {
                            elementCells[x][y].setNum(elementCells[x][y_under].getNum());
                            elementCells[x][y_under].setNum(0);
                            --y;
                            b = true;
                        } else if (elementCells[x][y].equals(elementCells[x][y_under])) {
                            elementCells[x][y].setNum(elementCells[x][y].getNum()*2);
                            elementCells[x][y_under].setNum(0);
                            ElementActivity.getElementActivity().addScore(elementCells[x][y].getNum());
                            b = true;
                        }
                        break;
                    }
                }
            }
        }
        if (b) {
            checkMissionComplete();
            addNewCell();
            checkGameOver();
        }
    }

    //아래쪽으로 스와이프 처리
    private void swipeDown() {
        boolean b = false;
        //한 열씩
        for(int x=0;x<4;++x) {
            //한 행씩 위로 올라가며 비교
            for(int y=3;y>0;--y) {
                //기준 셀의 위쪽에 있는 셀과 비교
                for(int y_upper=y-1;y_upper>=0;--y_upper) {
                    if (elementCells[x][y_upper].getNum()>0) {
                        if (elementCells[x][y].getNum()==0) {
                            elementCells[x][y].setNum(elementCells[x][y_upper].getNum());
                            elementCells[x][y_upper].setNum(0);
                            ++y;
                            b = true;
                        } else if (elementCells[x][y].equals(elementCells[x][y_upper])) {
                            elementCells[x][y].setNum(elementCells[x][y].getNum()*2);
                            elementCells[x][y_upper].setNum(0);
                            ElementActivity.getElementActivity().addScore(elementCells[x][y].getNum());
                            b = true;
                        }
                        break;
                    }
                }
            }
        }
        if (b) {
            checkMissionComplete();
            addNewCell();
            checkGameOver();
        }
    }

    //게임 오버 체크
    private void checkGameOver() {
        boolean isOver = true;
        ALL:
        for(int y=0;y<4;++y) {
            for(int x=0;x<4;++x) {
                // 빈 셀이 존재하거나, 좌우나 상하에 같은 값의 셀이 있으면 게임 오버가 아님
                if (elementCells[x][y].getNum()==0||
                        (x<3&& elementCells[x][y].getNum()== elementCells[x+1][y].getNum())||
                        (y<3&& elementCells[x][y].getNum()== elementCells[x][y+1].getNum())) {
                    //끝나지 않고 게임 계속
                    isOver = false;
                    break ALL;
                }
            }
        }
        // 게임 오버시 다이알로그 띄우기
        if (isOver) {
            new AlertDialog.Builder(getContext()).setTitle("Game Over").setMessage("현재 Score "+ ElementActivity.score+", 아쉽네요!").setPositiveButton("재도전하기", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startGame();
                }
            }).show();
        }
    }


    //게임 성공 체크
    private void checkMissionComplete() {
        if (isComplete==-1) return;

        ALL:
        for(int y=0;y<4;++y) {
            for(int x=0;x<4;++x) {
                // 빈 셀이 존재하거나, 좌우나 상하에 같은 값의 셀이 있으면 게임 오버가 아님
                if (elementCells[x][y].getNum()>=2048) {
                    isComplete = 1;
                    break ALL;
                }
            }
        }
        // 게임 성공시 다이알로그 띄우기
        if (isComplete==1) {
            new AlertDialog.Builder(getContext()).setTitle("Mission Complete").setMessage("제 11번 원소, 나트륨에 도달하셨습니다!").setPositiveButton("계속하기", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).setNegativeButton("재도전하기", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startGame();
                }
            }).show();
            isComplete = -1;
        }
    }


    //터치 리스너 설정
    class Listener implements OnTouchListener {

        private float startX, startY, offsetX, offsetY;

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            if (!hasTouched) {
                hasTouched = true;
            }

            //직전 정보 저장하기 (되돌리기)
            saveScore = ElementActivity.score;
            for(int y=0;y<4;++y) {
                for(int x=0;x<4;++x) {
                    saveGrid[y][x] = elementCells[y][x].getNum();
                }
            }

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN: //누른 위치 저장
                    startX = motionEvent.getX();
                    startY = motionEvent.getY();
                    break;
                case MotionEvent.ACTION_UP: //움직인 위치 정보 저장
                    offsetX = motionEvent.getX()-startX;
                    offsetY = motionEvent.getY()-startY;

                    //가로 움직임이 더 많았을 경우
                    if (Math.abs(offsetX)> Math.abs(offsetY)) {
                        if (offsetX<-5) { //유효한 왼쪽으로의 움직임
                            swipeLeft();
                        } else if (offsetX>5) { //유효한 오른쪽으로의 움직임
                            swipeRight();
                        }
                    }
                    else {  //세로 움직임이 더 많았을 경우
                        if (offsetY<-5) {
                            swipeUp();
                        } else if (offsetY>5) {
                            swipeDown();
                        }
                    }
            }
            return true;
        }
    }
}