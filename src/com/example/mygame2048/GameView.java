package com.example.mygame2048;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

public class GameView extends GridLayout {
	
	private Card[][] cardsMap=new Card[4][4];
	private List<Point>emptyPoint=new ArrayList<Point>();
	
	private static int cardWidth=0;

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initGameView();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initGameView();
	}

	public GameView(Context context) {
		super(context);
		initGameView();
	}
	
	public static int getCardWidth() {
		return cardWidth;
	}

	/*
	 * 初始化这个4*4卡片方阵
	 * 设置屏幕触摸事件
	 * */
	private void initGameView(){
		
		setColumnCount(4);
		setBackgroundColor(0xffbbada0);//4*4卡片方阵的背景色，覆盖掉了之前设置的整个界面的背景色
		
		setOnTouchListener(new View.OnTouchListener() {
			private float startX,startY,offsetX,offsetY;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN://触摸屏幕时刻，==0
					startX=event.getX();//getX() 获得事件发生时,触摸的中间区域在屏幕的X轴.   
					startY=event.getY();//getY() 获得事件发生时,触摸的中间区域在屏幕的X轴.
					break;
				case MotionEvent.ACTION_UP://终止触摸时刻，==1
					offsetX=event.getX()-startX;
					offsetY=event.getY()-startY;
					/*
					 * 根据坐标变化判断手指滑动的方向
					 * 继而控制卡片移动方向
					 * */
					if (Math.abs(offsetX) > Math.abs(offsetY)) {//比较横向位移与纵向位移的大小
						if (offsetX > 5) {//结束时的触摸点X值比开始时大，向右滑
							swipeRight();
						} else if (offsetX < -5) {
							swipeLeft();
						}
					} else {
						if (offsetY > 5) {
							swipeDown();
						} else if (offsetY < -5) {
							swipeUp();
						}
					}
					break;
				default:
					break;
				}
				return true;
			}
		});
	}
	
	/*
	 * 通过下面onSizeChanged方法和addCards方法实现：
	 * 控制4*4方阵的大小，准确说是控制每个卡片的大小，
	 * 因为卡片之间的排列关系已经由Card类确定：由左上角开始，左边和上边留白
	 * 所以设置每个卡片的长宽都是(Math.min(w, h)-10)/4
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
		cardWidth=(Math.min(w, h)-10)/4;
		addCards(cardWidth,cardWidth);
		
		startGame();
	}
    	
    	private void addCards(int cardWith,int cardHeight){
    		Card card=null;
    		for (int y = 0; y < 4; y++){
    			for (int x = 0; x < 4; x++) {
    				card=new Card(getContext());
    				card.setNum(0);
    				addView(card, cardWith, cardHeight);
    					
    				cardsMap[x][y]=card;
    			}
    		}
    	}
	
	//点击按钮，游戏重新开始
	public void startGame(){
		
		MainActivity.getMainActivity().clearScore();
		MainActivity.getMainActivity().showBestScore(MainActivity.getMainActivity().getBestScore());
		
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				cardsMap[x][y].setNum(0);
			}	
		}
		//游戏开始时随机生成的卡片数目为2
		getRandomNum();
		getRandomNum();
		
	}
	
	/*
	 * 此类实现了在随机的位置、随机生成2或4的卡片
	 * 向emptyPoint列表动态添加空白卡片，再从这些空白卡片里随机获取(remove)一个，设置2或4
	 * 出现2的几率应该在90%，出现4为10%
	 * 最后调用creatScaleTo1方法显示出来
	 * */
	private void getRandomNum(){	
		emptyPoint.clear();//坐标列表清空
		
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if(cardsMap[x][y].getNum()<=0){
					emptyPoint.add(new Point(x,y));//坐标列表记录空卡片的位置
				}
			}
		}
		if(emptyPoint.size()>0){
			Point p=emptyPoint.remove((int)(Math.random()*emptyPoint.size()));
			cardsMap[p.x][p.y].setNum(Math.random()>0.1?2:4);
			
			MainActivity.getMainActivity().getAnimLayer().creatScaleTo1(cardsMap[p.x][p.y]);
		}
	}
	
	/*
	 * 该方法实现：判断游戏是否失败
	 * 判断方法：
	 * 1.判断是否还有空卡片 
	 * 2.没有空卡片的话，判断任意卡片的与其相邻卡片(上下左右)是否相同
	 */
	public void checkGameIsEnd(){
		boolean gameIsEnd=true;
		LABEL1:
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if(cardsMap[x][y].getNum()==0||
						(x>0&&cardsMap[x][y].equals(cardsMap[x-1][y]))||
						(x<3&&cardsMap[x][y].equals(cardsMap[x+1][y]))||
						(y>0&&cardsMap[x][y].equals(cardsMap[x][y-1]))||
						(y<3&&cardsMap[x][y].equals(cardsMap[x][y+1]))){
					gameIsEnd=false;
					break LABEL1;
				}
			}
		}
		if(gameIsEnd){
			new AlertDialog.Builder(getContext()).setTitle("提示").setMessage("游戏失败〒_〒，请重新开始 ！").setPositiveButton("好吧", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					startGame();
				}
			}).show();
		}
	}
	
	private void swipeLeft(){
		boolean IsAddCard=false; 
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				for(int x1=x+1; x1<4; x1++){
					if(cardsMap[x1][y].getNum()>0){
						if(cardsMap[x][y].getNum()<=0){
							MainActivity.getMainActivity().getAnimLayer().creatTranAnimLayer(cardsMap[x1][y], cardsMap[x][y], x1, x, y, y);							
							cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
							cardsMap[x1][y].setNum(0);							
							x--;
							IsAddCard=true;
						}else if(cardsMap[x1][y].equals(cardsMap[x][y])){
							MainActivity.getMainActivity().getAnimLayer().creatTranAnimLayer(cardsMap[x1][y], cardsMap[x][y], x1, x, y, y);						
							cardsMap[x][y].setNum(cardsMap[x1][y].getNum() * 2);
							cardsMap[x1][y].setNum(0);
							MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
							IsAddCard = true;
						}
						break;
					}
				}
			}
		}
		if (IsAddCard == true) {
			getRandomNum();
			checkGameIsEnd();
		}
	}
	
	private void swipeRight(){
		boolean IsAddCard=false;
		for (int y = 0; y < 4; y++) {
			for (int x = 3; x >=0; x--) {
				for(int x1=x-1; x1>=0; x1--){
					if(cardsMap[x1][y].getNum()>0){
						if(cardsMap[x][y].getNum()<=0){
							MainActivity.getMainActivity().getAnimLayer().creatTranAnimLayer(cardsMap[x1][y], cardsMap[x][y], x1, x, y, y);
							
							cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
							cardsMap[x1][y].setNum(0);

							x++;
							IsAddCard=true;
						}else if(cardsMap[x1][y].equals(cardsMap[x][y])){
							MainActivity.getMainActivity().getAnimLayer().creatTranAnimLayer(cardsMap[x1][y], cardsMap[x][y], x1, x, y, y);
							
							cardsMap[x][y].setNum(cardsMap[x1][y].getNum() * 2);
							cardsMap[x1][y].setNum(0);
							MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
							IsAddCard = true;
						}									      
						break;
					}
				}
			}
		}
		if (IsAddCard == true) {
			getRandomNum();
			checkGameIsEnd();
		}
	}
	
	private void swipeUp(){
		boolean IsAddCard=false;
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				for(int y1=y+1; y1<4; y1++){
					if(cardsMap[x][y1].getNum()>0){
						if(cardsMap[x][y].getNum()<=0){
							MainActivity.getMainActivity().getAnimLayer().creatTranAnimLayer(cardsMap[x][y1], cardsMap[x][y], x, x, y1, y);
							
							cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
							cardsMap[x][y1].setNum(0);

							y--;
							IsAddCard=true;
						}else if(cardsMap[x][y1].equals(cardsMap[x][y])){
							MainActivity.getMainActivity().getAnimLayer().creatTranAnimLayer(cardsMap[x][y1], cardsMap[x][y], x, x, y1, y);
							
							cardsMap[x][y].setNum(cardsMap[x][y1].getNum() * 2);
							cardsMap[x][y1].setNum(0);
							MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
							IsAddCard = true;
						}
						break;
					}
				}
			}
		}
		if (IsAddCard == true) {
			getRandomNum();
			checkGameIsEnd();
		}
	}
	
	private void swipeDown(){
		boolean IsAddCard=false;
		for (int x = 0; x < 4; x++) {
			for (int y = 3; y >=0; y--) {
				for(int y1=y-1; y1>=0; y1--){
					if(cardsMap[x][y1].getNum()>0){
						if(cardsMap[x][y].getNum()<=0){
							MainActivity.getMainActivity().getAnimLayer().creatTranAnimLayer(cardsMap[x][y1], cardsMap[x][y], x, x, y1, y);
							
							cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
							cardsMap[x][y1].setNum(0);

							y++;
							IsAddCard=true;
						}else if(cardsMap[x][y1].equals(cardsMap[x][y])){
							MainActivity.getMainActivity().getAnimLayer().creatTranAnimLayer(cardsMap[x][y1], cardsMap[x][y], x, x, y1, y);
							
							cardsMap[x][y].setNum(cardsMap[x][y1].getNum() * 2);
							cardsMap[x][y1].setNum(0);
							MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
							IsAddCard = true;
						}
						break;
					}
				}
			}
		}
		if(IsAddCard==true){
			getRandomNum();
			checkGameIsEnd();
		}
	}
}
