package com.example.mygame2048;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

/*
 * ����ΪʲôҪ��FrameLayout�أ�
 * �������⣺��Ϊÿһ��Card�����϶�Ҫ��������ʱ�̶��ڱ仯��view���ֱ���background��label��
 * ������ͼ�㣬���ʹ��FrameLayout
 * */
public class Card extends FrameLayout {

    private int num = 0;

    private TextView label = null;
    private View background = null;

    public Card(Context context) {
	super(context);
	/*
	 *  LayoutParams�൱��һ��Layout����Ϣ��������װ��Layout��λ�á��ߡ�������Ϣ
	 * ��������ȥ����LayoutParams��������������ϣ�ÿ�����Ӷ�ռ��һ��λ�ã� Ҳ����ÿ�����Ӷ���һ��λ�õ���Ϣ�������������4��4�У�
	 * ����ġ�4��4�С��������ӵ�LayoutParams��
	 * LayoutParams���캯���Ĳ�����-1��-1����MATCH_PARENT��ͬ��Ϊռ�������֡�
	 */
	LayoutParams lp = null;
	lp = new LayoutParams(-1, -1);
	lp.setMargins(10, 10, 0, 0);
	/*
	 * ʹ��getContext��ȡ���ǵ�ǰ�������ڵ�Context Contextͨ������������ģ� ͨ�����ɳ��������⡣
	 */
	background = new View(getContext());
	background.setBackgroundColor(0x33ffffff);//ÿ����Ƭ�ı���ɫ
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