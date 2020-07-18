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

public class AlphabetActivity extends AppCompatActivity {


    public static AlphabetActivity alphabetActivity = null;
    private TextView Score_alpha;
    private TextView maxScore_alpha;
    private AlphabetGameView gameView_alpha;

    public static int score_alpha = 0; //점수

    public AlphabetActivity() {
        alphabetActivity = this;
    }
    public static AlphabetActivity getAlphabetActivity() {
        return alphabetActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet);

        //뷰 설정
        Score_alpha = (TextView) findViewById(R.id.Score_alpha);
        maxScore_alpha = (TextView) findViewById(R.id.maxScore_alpha);
        maxScore_alpha.setText(getSharedPreferences("pMaxScore_alpha", MODE_PRIVATE).getInt("maxScore_alpha", 0)+"");
        gameView_alpha = (AlphabetGameView)findViewById(R.id.gameView_alpha);

        //재시작 버튼 설정
        Button restart = (Button) findViewById(R.id.restart_alpha);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlphabetGameView.startGame();
            }
        });

        //되돌리기 버튼 설정
        Button back = (Button) findViewById(R.id.back_alpha);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameView_alpha.hasTouched) {
                    score_alpha = gameView_alpha.saveScore; //이전 점수를 불러옴
                    renewScore();
                    for(int y=0;y<4;++y) {
                        for(int x=0;x<4;++x) {
                            gameView_alpha.alphabetCells[y][x].setNum(gameView_alpha.saveGrid[y][x]); //이전 그리드를 불러옴
                        }
                    }
                }
            }
        });

        //나가기 버튼 설정
        Button pause = (Button) findViewById(R.id.pause_alpha);
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
        score_alpha = 0;
        renewScore();
    }

    //점수 증가시키기
    public void addScore(int i) {
        score_alpha += i;
        renewScore();
        SharedPreferences pref = getSharedPreferences("pMaxScore_alpha", MODE_PRIVATE);

        //현재 점수가 최고 점수를 초과하면 최고 점수를 갱신한다.
        if (score_alpha > pref.getInt("maxScore_alpha", 0)) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("maxScore_alpha", score_alpha);
            editor.apply();
            maxScore_alpha.setText(pref.getInt("maxScore_alpha", 0)+"");
        }

    }

    //점수 갱신하기
    public void renewScore() {
        Score_alpha.setText(score_alpha+"");
    }

    //뒤로 가기 누르면 Dialog 띄우기
    @Override
    public void onBackPressed() {
        createExitTipDialog();
    }
    private void createExitTipDialog() {
        new AlertDialog.Builder(AlphabetActivity.this)
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
