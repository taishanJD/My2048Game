package com.example.mygame2048;

import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    private LinearLayout root = null;
    private TextView tvScore = null;// 当前得分
    private TextView tvBestScore = null;// 最高得分
    private static MainActivity mainActivity = null;
    private AnimLayer animlayer = null;
    private Button btnRestartGame = null;
    private GameView gameView = null;

    private int score = 0;

    public static final String SP_KEY_BEST_SCORE = "bestScore";

    public MainActivity() {
	mainActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
//	requestWindowFeature(Window.FEATURE_NO_TITLE);
	root = (LinearLayout) findViewById(R.id.container);
	root.setBackgroundColor(0xfffaf8ef);//这是整个界面的背景色，
	//由于后面的GameView设置了自己特有的背景色,所以看起来该背景色只包括GameView之上的部分

	tvScore = (TextView) findViewById(R.id.tvScore);
	tvBestScore = (TextView) findViewById(R.id.tvBestScore);

	gameView = (GameView) findViewById(R.id.gameView);

	animlayer = (AnimLayer) findViewById(R.id.animlayer);

	btnRestartGame = (Button) findViewById(R.id.btnNewGame);
	btnRestartGame.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View arg0) {
		gameView.startGame();
	    }
	});
    }

    public static MainActivity getMainActivity() {
	return mainActivity;
    }

    public AnimLayer getAnimLayer() {
	return animlayer;
    }

    // 清除分数，应该是按下button时，当前分数清零
    public void clearScore() {
	score = 0;
	showScore();
    }

    // 显示当前分数
    public void showScore() {
	tvScore.setText(score + "");
    }

    // 分数递加的算法
    public void addScore(int s) {
	score += s;
	showScore();

	int maxScore = Math.max(score, getBestScore());
	saveBestScore(maxScore);
	showBestScore(maxScore);
    }

    /*
     * 将数据存储到SharedPreference中
     * getPreferences方法产生一个SharedPerference对象，接受一个操作模式参数，默认为MODE_PRIVATE，
     * 表示只有当前的应用程序才可以对这个SharedPerference文件操作，另有MODE_MULTI_PROCESS
     * SharedPerference对象的edit方法获取一个Editor对象 putInt方法添加数据 commit方法提交数据，完成存储。
     */
    public void saveBestScore(int s) {
	Editor e = getPreferences(MODE_PRIVATE).edit();
	e.putInt(SP_KEY_BEST_SCORE, s);
	e.commit();
    }

    /*
     * 从SharedPerference对象中读取数据， getInt方法，第一个参数是键值，第二个是默认值，
     * 表示当传入的键值找不到对应的数据时，会以什么样的默认值返回
     */
    public int getBestScore() {
	return getPreferences(MODE_PRIVATE).getInt(SP_KEY_BEST_SCORE, 0);
    }
    //显示最高分数
    public void showBestScore(int s) {
	tvBestScore.setText(s + "");
    }
}
