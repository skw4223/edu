package com.example.edu48.game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.edu48.R;

public class ElementActivity extends AppCompatActivity {


    public static ElementActivity elementActivity = null;
    private TextView Score;
    private TextView maxScore;
    private ElementGameView elementGameView;

    public static int score = 0; //점수

    public ElementActivity() {
        elementActivity = this;
    }
    public static ElementActivity getElementActivity() {
        return elementActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element);

        //뷰 설정
        Score = (TextView) findViewById(R.id.Score);
        maxScore = (TextView) findViewById(R.id.maxScore);
        maxScore.setText(getSharedPreferences("pMaxScore", MODE_PRIVATE).getInt("maxScore", 0)+"");
        elementGameView = (ElementGameView)findViewById(R.id.gameView);

        //재시작 버튼 설정
        Button restart = (Button) findViewById(R.id.restart);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ElementGameView.startGame();
            }
        });

        //되돌리기 버튼 설정
        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (elementGameView.hasTouched) {
                    score = elementGameView.saveScore; //이전 점수를 불러옴
                    renewScore();
                    for(int y=0;y<4;++y) {
                        for(int x=0;x<4;++x) {
                            elementGameView.elementCells[y][x].setNum(elementGameView.saveGrid[y][x]); //이전 그리드를 불러옴
                        }
                    }
                }
            }
        });

        //나가기 버튼 설정
        Button pause = (Button) findViewById(R.id.pause);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
        });

    }

    //점수 초기화
    public void clearScore() {
        score = 0;
        renewScore();
    }

    //점수 증가시키기
    public void addScore(int i) {
        score += i;
        renewScore();
        SharedPreferences pref = getSharedPreferences("pMaxScore", MODE_PRIVATE);

        //현재 점수가 최고 점수를 초과하면 최고 점수를 갱신한다.
        if (score > pref.getInt("maxScore", 0)) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("maxScore", score);
            editor.apply();
            maxScore.setText(pref.getInt("maxScore", 0)+"");
        }

    }

    //점수 갱신하기
    public void renewScore() {
        Score.setText(score+"");
    }

    //뒤로 가기 누르면 Dialog 띄우기
    @Override
    public void onBackPressed() {
        createExitTipDialog();
    }
    private void createExitTipDialog() {
        new AlertDialog.Builder(ElementActivity.this)
                .setMessage("게임을 종료하시겠습니까？")
                .setTitle("게임 종료")
                .setIcon(R.drawable.warning)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

}
