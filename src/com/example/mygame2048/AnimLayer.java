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
 * 本类用于动画展示，卡片的出现和移动
 * */
public class AnimLayer extends FrameLayout {

    /*
     * 使用ArrayList<Card> cards用于管理临时卡片的创建和回收, 避免每次创建临时卡片时创建新的对象
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
     * 卡片移动动画： 创建一个临时卡片，从卡片from移动到卡片to， 当完成动画之后将临时卡片设为不可见，并使用cards回收该卡片。
     */
    public void creatTranAnimLayer(final Card from, final Card to, int fromX,
	    int toX, int fromY, int toY) {
	// 创建一个临时卡片
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
	 * TranslateAnimation平移动画，参数：
	 *  float fromXDelta 动画开始的点离当前View X坐标上的差值 
	 * float toXDelta 动画结束的点离当前View X坐标上的差值 
	 * float fromYDelta 动画开始的点离当前View Y坐标上的差值
	 * float toYDelta 动画开始的点离当前View Y坐标上的差值
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
     * 创建卡片时，如果cards不为空，则从cards队首取出一张临时卡片。
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
     * 回收卡片将当前卡片设为不可见，并加入到cards中。
     */
    public void recylceCard(Card card) {
	card.setVisibility(View.INVISIBLE);
	card.setAnimation(null);
	cards.add(card);
    }

    /*
     * 卡片出现动画 ScaleAnimation是android4种动画中的一种，尺寸变化动画类 参数列表： fromX：起始X坐标上的伸缩尺寸。
     * toX：结束X坐标上的伸缩尺寸。 fromY：起始Y坐标上的伸缩尺寸。 toY：结束Y坐标上的伸缩尺寸。
     * pivotXType：X轴的伸缩模式，可以取值为ABSOLUTE
     * 、RELATIVE_TO_SELF(以自己为中心缩放)、RELATIVE_TO_PARENT(像是从画面外飞来)。
     * pivotXValue：X坐标的伸缩值。
     * pivotYType：Y轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。
     * pivotYValue：Y坐标的伸缩值。
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
