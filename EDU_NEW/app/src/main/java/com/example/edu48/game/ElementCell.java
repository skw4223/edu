package com.example.edu48.game;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.edu48.R;

public class ElementCell extends FrameLayout {

    private int num = 0; //해당 셀의 숫자
    private ImageView imgView;

    public ElementCell(Context context) {
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
            case 2: imgView.setImageDrawable(context.getDrawable(R.drawable.eliment1)); break;
            case 4: imgView.setImageDrawable(context.getDrawable(R.drawable.eliment2)); break;
            case 8: imgView.setImageDrawable(context.getDrawable(R.drawable.eliment3)); break;
            case 16: imgView.setImageDrawable(context.getDrawable(R.drawable.eliment4)); break;
            case 32: imgView.setImageDrawable(context.getDrawable(R.drawable.eliment5)); break;
            case 64: imgView.setImageDrawable(context.getDrawable(R.drawable.eliment6)); break;
            case 128: imgView.setImageDrawable(context.getDrawable(R.drawable.eliment7)); break;
            case 256: imgView.setImageDrawable(context.getDrawable(R.drawable.eliment8)); break;
            case 512: imgView.setImageDrawable(context.getDrawable(R.drawable.eliment9)); break;
            case 1024: imgView.setImageDrawable(context.getDrawable(R.drawable.eliment10)); break;
            case 2048: imgView.setImageDrawable(context.getDrawable(R.drawable.eliment11)); break;
            case 4096: imgView.setImageDrawable(context.getDrawable(R.drawable.eliment12)); break;
            case 8192: imgView.setImageDrawable(context.getDrawable(R.drawable.eliment13)); break;
            case 16384: imgView.setImageDrawable(context.getDrawable(R.drawable.eliment14)); break;
            case 32768: imgView.setImageDrawable(context.getDrawable(R.drawable.eliment15)); break;
            default: break;
        }
    }
    // 두 셀의 수치가 같은지 판단
    public boolean equals(ElementCell elementCell) {
        return getNum()== elementCell.getNum();
    }
}