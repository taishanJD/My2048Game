package com.example.mygame2048;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

/*
 * �������ڶ���չʾ����Ƭ�ĳ��ֺ��ƶ�
 * */
public class AnimLayer extends FrameLayout {

    /*
     * ʹ��ArrayList<Card> cards���ڹ�����ʱ��Ƭ�Ĵ����ͻ���, ����ÿ�δ�����ʱ��Ƭʱ�����µĶ���
     */
    private List<Card> cards = new ArrayList<Card>();

    public AnimLayer(Context context, AttributeSet attrs, int defStyle) {
	super(context, attrs, defStyle);
    }

    public AnimLayer(Context context, AttributeSet attrs) {
	super(context, attrs);
    }

    public AnimLayer(Context context) {
	super(context);
    }

    /*
     * ��Ƭ�ƶ������� ����һ����ʱ��Ƭ���ӿ�Ƭfrom�ƶ�����Ƭto�� ����ɶ���֮����ʱ��Ƭ��Ϊ���ɼ�����ʹ��cards���ոÿ�Ƭ��
     */
    public void creatTranAnimLayer(final Card from, final Card to, int fromX,
	    int toX, int fromY, int toY) {
	// ����һ����ʱ��Ƭ
	final Card card = getcard(from.getNum());

	int cardWidth = GameView.getCardWidth();
	LayoutParams lp = new LayoutParams(cardWidth, cardWidth);
	lp.leftMargin = fromX * cardWidth;
	lp.topMargin = fromY * cardWidth;
	card.setLayoutParams(lp);

	if (to.getNum() <= 0) {
	    to.getLabel().setVisibility(View.INVISIBLE);
	}

	/*
	 * TranslateAnimationƽ�ƶ�����������
	 *  float fromXDelta ������ʼ�ĵ��뵱ǰView X�����ϵĲ�ֵ 
	 * float toXDelta ���������ĵ��뵱ǰView X�����ϵĲ�ֵ 
	 * float fromYDelta ������ʼ�ĵ��뵱ǰView Y�����ϵĲ�ֵ
	 * float toYDelta ������ʼ�ĵ��뵱ǰView Y�����ϵĲ�ֵ
	 */
	TranslateAnimation ta = new TranslateAnimation(0, cardWidth
		* (toX - fromX), 0, cardWidth * (toY - fromY));
	ta.setDuration(100);
	ta.setAnimationListener(new Animation.AnimationListener() {

	    @Override
	    public void onAnimationStart(Animation arg0) {

	    }

	    @Override
	    public void onAnimationRepeat(Animation arg0) {

	    }

	    @Override
	    public void onAnimationEnd(Animation arg0) {
		to.getLabel().setVisibility(View.VISIBLE);
		recylceCard(card);
	    }
	});
	card.startAnimation(ta);
    }

    /*
     * ������Ƭʱ�����cards��Ϊ�գ����cards����ȡ��һ����ʱ��Ƭ��
     */
    public Card getcard(int num) {
	Card card = null;
	if (cards.size() <= 0) {
	    card = new Card(getContext());
	    addView(card);
	} else {
	    card = cards.remove(0);
	}
	card.setVisibility(View.VISIBLE);
	card.setNum(num);
	return card;
    }

    /*
     * ���տ�Ƭ����ǰ��Ƭ��Ϊ���ɼ��������뵽cards�С�
     */
    public void recylceCard(Card card) {
	card.setVisibility(View.INVISIBLE);
	card.setAnimation(null);
	cards.add(card);
    }

    /*
     * ��Ƭ���ֶ��� ScaleAnimation��android4�ֶ����е�һ�֣��ߴ�仯������ �����б� fromX����ʼX�����ϵ������ߴ硣
     * toX������X�����ϵ������ߴ硣 fromY����ʼY�����ϵ������ߴ硣 toY������Y�����ϵ������ߴ硣
     * pivotXType��X�������ģʽ������ȡֵΪABSOLUTE
     * ��RELATIVE_TO_SELF(���Լ�Ϊ��������)��RELATIVE_TO_PARENT(���Ǵӻ��������)��
     * pivotXValue��X���������ֵ��
     * pivotYType��Y�������ģʽ������ȡֵΪABSOLUTE��RELATIVE_TO_SELF��RELATIVE_TO_PARENT��
     * pivotYValue��Y���������ֵ��
     */
    public void creatScaleTo1(Card card) {
	ScaleAnimation sa = new ScaleAnimation(0.1f, 1, 0.1f, 1,
		Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
		0.5f);
	sa.setDuration(100);
	card.setAnimation(null);
	card.startAnimation(sa);
    }
}
