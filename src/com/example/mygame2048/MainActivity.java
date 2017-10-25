package com.example.mygame2048;

import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * 验证git使用test
 * */
public class MainActivity extends Activity {

    private LinearLayout root = null;
    private TextView tvScore = null;// ��ǰ�÷�
    private TextView tvBestScore = null;// ��ߵ÷�
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
	root.setBackgroundColor(0xfffaf8ef);//������������ı���ɫ��
	//���ں����GameView�������Լ����еı���ɫ,���Կ������ñ���ɫֻ����GameView֮�ϵĲ���

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

    // ���������Ӧ���ǰ���buttonʱ����ǰ��������
    public void clearScore() {
	score = 0;
	showScore();
    }

    // ��ʾ��ǰ����
    public void showScore() {
	tvScore.setText(score + "");
    }

    // �����ݼӵ��㷨
    public void addScore(int s) {
	score += s;
	showScore();

	int maxScore = Math.max(score, getBestScore());
	saveBestScore(maxScore);
	showBestScore(maxScore);
    }

    /*
     * �����ݴ洢��SharedPreference��
     * getPreferences��������һ��SharedPerference���󣬽���һ������ģʽ������Ĭ��ΪMODE_PRIVATE��
     * ��ʾֻ�е�ǰ��Ӧ�ó���ſ��Զ����SharedPerference�ļ�����������MODE_MULTI_PROCESS
     * SharedPerference�����edit������ȡһ��Editor���� putInt����������� commit�����ύ���ݣ���ɴ洢��
     */
    public void saveBestScore(int s) {
	Editor e = getPreferences(MODE_PRIVATE).edit();
	e.putInt(SP_KEY_BEST_SCORE, s);
	e.commit();
    }

    /*
     * ��SharedPerference�����ж�ȡ���ݣ� getInt��������һ�������Ǽ�ֵ���ڶ�����Ĭ��ֵ��
     * ��ʾ������ļ�ֵ�Ҳ�����Ӧ������ʱ������ʲô����Ĭ��ֵ����
     */
    public int getBestScore() {
	return getPreferences(MODE_PRIVATE).getInt(SP_KEY_BEST_SCORE, 0);
    }
    //��ʾ��߷���
    public void showBestScore(int s) {
	tvBestScore.setText(s + "");
    }
}
