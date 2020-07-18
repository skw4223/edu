package com.example.edu48.game;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.edu48.R;

public class AlphabetCell extends FrameLayout {

    private int num = 0; //해당 셀의 숫자
    private ImageView imgView;

    public AlphabetCell(Context context) {
        super(context);
        imgView = new ImageView(getContext());
        setNum(0);
        addView(imgView);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {

        Context context = getContext();
        this.num = num; //숫자 설정

        //숫자에 따라 이미지 변경
        switch (num) {
            case 0: imgView.setImageDrawable(context.getDrawable(R.drawable.zero)); break;
            case 2: imgView.setImageDrawable(context.getDrawable(R.drawable.a)); break;
            case 4: imgView.setImageDrawable(context.getDrawable(R.drawable.b)); break;
            case 8: imgView.setImageDrawable(context.getDrawable(R.drawable.c)); break;
            case 16: imgView.setImageDrawable(context.getDrawable(R.drawable.d)); break;
            case 32: imgView.setImageDrawable(context.getDrawable(R.drawable.e)); break;
            case 64: imgView.setImageDrawable(context.getDrawable(R.drawable.f)); break;
            case 128: imgView.setImageDrawable(context.getDrawable(R.drawable.g)); break;
            case 256: imgView.setImageDrawable(context.getDrawable(R.drawable.h)); break;
            case 512: imgView.setImageDrawable(context.getDrawable(R.drawable.i)); break;
            case 1024: imgView.setImageDrawable(context.getDrawable(R.drawable.j)); break;
            case 2048: imgView.setImageDrawable(context.getDrawable(R.drawable.k)); break;
            case 4096: imgView.setImageDrawable(context.getDrawable(R.drawable.l)); break;
            case 8192: imgView.setImageDrawable(context.getDrawable(R.drawable.m)); break;
            case 16384: imgView.setImageDrawable(context.getDrawable(R.drawable.n)); break;
            case 32768: imgView.setImageDrawable(context.getDrawable(R.drawable.o)); break;
            default: break;
        }
    }
    // 두 셀의 수치가 같은지 판단
    public boolean equals(AlphabetCell cell) {
        return getNum()== cell.getNum();
    }
}