package com.example.mygame2048;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

/*
 * 这里为什么要用FrameLayout呢？
 * 个人理解：因为每一个Card对象上都要覆盖两层时刻都在变化的view，分别是background和label，
 * 类似于图层，因此使用FrameLayout
 * */
public class Card extends FrameLayout {

    private int num = 0;

    private TextView label = null;
    private View background = null;

    public Card(Context context) {
	super(context);
	/*
	 *  LayoutParams相当于一个Layout的信息包，它封装了Layout的位置、高、宽等信息
	 * 可以这样去形容LayoutParams，在象棋的棋盘上，每个棋子都占据一个位置， 也就是每个棋子都有一个位置的信息，如这个棋子在4行4列，
	 * 这里的“4行4列”就是棋子的LayoutParams。
	 * LayoutParams构造函数的参数（-1，-1）与MATCH_PARENT相同，为占满父布局。
	 */
	LayoutParams lp = null;
	lp = new LayoutParams(-1, -1);
	lp.setMargins(10, 10, 0, 0);
	/*
	 * 使用getContext获取的是当前对象所在的Context Context通常翻译成上下文， 通常当成场景来理解。
	 */
	background = new View(getContext());
	background.setBackgroundColor(0x33ffffff);//每个卡片的背景色
	addView(background, lp);

	label = new TextView(getContext());
	label.setTextSize(28);
	label.setGravity(Gravity.CENTER);
	addView(label, lp);

	setNum(0);
    }

    public int getNum() {
	return num;
    }

    public void setNum(int num) {
	this.num = num;

	if (num <= 0) {
	    label.setText("");
	} else {
	    label.setText(num + "");
	}

	switch (num) {
	case 0:
	    label.setBackgroundColor(0x00000000);
	    break;
	case 2:
	    label.setBackgroundColor(0xffeee4da);
	    break;
	case 4:
	    label.setBackgroundColor(0xffede0c8);
	    break;
	case 8:
	    label.setBackgroundColor(0xfff2b179);
	    break;
	case 16:
	    label.setBackgroundColor(0xfff59563);
	    break;
	case 32:
	    label.setBackgroundColor(0xfff67c5f);
	    break;
	case 64:
	    label.setBackgroundColor(0xfff65e3b);
	    break;
	case 128:
	    label.setBackgroundColor(0xffedcf72);
	    break;
	case 256:
	    label.setBackgroundColor(0xffedcc61);
	    break;
	case 512:
	    label.setBackgroundColor(0xffedc850);
	    break;
	case 1024:
	    label.setBackgroundColor(0xffedc53f);
	    break;
	case 2048:
	    label.setBackgroundColor(0xffedc22e);
	    break;
	default:
	    label.setBackgroundColor(0xff3c3a32);
	    break;
	}
    }

    public boolean equals(Card card) {
	return getNum() == card.getNum();
    }

    public TextView getLabel() {
	return label;
    }
}
