package com.example.calendar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class MainActivity extends Activity {
	private ViewFlipper vf;
	private float old_x;
	private float new_x;
	boolean flag;
	private int pages = 1;
	private int ViewNumber = 1;
	Animation left_in;
	Animation right_in;
	Animation left_out;
	Animation right_out;
	private ImageView img1;
	private ImageView img2;
	private ImageView img3;
	//固定値
	public static final int LAST_PAGE = 12; //総ページ数

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		vf = (ViewFlipper)this.findViewById(R.id.vf);
		left_in = AnimationUtils.loadAnimation(this, R.anim.left_in);
		right_in = AnimationUtils.loadAnimation(this, R.anim.right_in);
		left_out = AnimationUtils.loadAnimation(this, R.anim.left_out);
		right_out = AnimationUtils.loadAnimation(this, R.anim.right_out);

		img1 = (ImageView)this.findViewById(R.id.imageView1);
		img2 = (ImageView)this.findViewById(R.id.imageView2);
		img3 = (ImageView)this.findViewById(R.id.imageView3);

		//タッチ時の処理を定義
		vf.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event){
				switch (event.getAction()) {
					//指を下した時の座標を記録
					case MotionEvent.ACTION_DOWN:
						old_x = event.getX();
						flag = false;
						break;
					//指を動かした時の判別
					case MotionEvent.ACTION_MOVE:
						new_x = event.getX();
						//左方向に指を移動（次ページ）
						if(old_x-75 > new_x && flag != true){
							flag = true;
							vf.setInAnimation(right_in);
							vf.setOutAnimation(left_out);
							vf.showNext();
							pages++;
							if(pages > LAST_PAGE){
								pages = 1;
							}
							ViewNumber++;
							ViewNumber = ViewNumber % 3;
							if(pages < LAST_PAGE){
								//画像を差し替える
								int viewPage = pages+1;
								int resid = getResources().getIdentifier("m"+viewPage, "drawable", getPackageName()); // リソースID取得
								if(ViewNumber == 1){
									img2.setImageResource(resid);
								}
								if(ViewNumber == 2){
									img3.setImageResource(resid);
								}
								if(ViewNumber == 0){
									img1.setImageResource(resid);
								}
							}else if(pages == LAST_PAGE){//ページ一巡したら
								//画像を差し替える
								int viewPage = pages-11;
								int resid = getResources().getIdentifier("m"+viewPage, "drawable", getPackageName()); // リソースID取得
								if(ViewNumber == 1){
									img2.setImageResource(resid);
								}
								if(ViewNumber == 2){
									img3.setImageResource(resid);
								}
								if(ViewNumber == 0){
									img1.setImageResource(resid);
								}
							}
							Log.d("ViewNumber", ""+ViewNumber);
						//右方向に指を移動（前のページ）
						}else if(old_x+75 < new_x && flag != true){
							flag = true;
							old_x = 0;
							vf.setInAnimation(left_in);
							vf.setOutAnimation(right_out);
							vf.showPrevious();
							pages--;
							if(pages==0){
								pages = LAST_PAGE;
							}
							if(ViewNumber == 0){
								ViewNumber = 3;
							}
							ViewNumber = ViewNumber - 1;
							ViewNumber = ViewNumber % 3;
							if(pages !=1){
								//画像を差し替える
								int viewPage = pages-1;
								int resid = getResources().getIdentifier("m"+viewPage, "drawable", getPackageName()); // リソースID取得
								if(ViewNumber == 1){
									img3.setImageResource(resid);
								}
								if(ViewNumber == 2){
									img1.setImageResource(resid);
								}
								if(ViewNumber == 0){
									img2.setImageResource(resid);
								}
							}else{//最後のページへ
								int viewPage = LAST_PAGE;
								int resid = getResources().getIdentifier("m"+viewPage, "drawable", getPackageName()); // リソースID取得
								if(ViewNumber == 1){
									img3.setImageResource(resid);
								}
								if(ViewNumber == 2){
									img1.setImageResource(resid);
								}
								if(ViewNumber == 0){
									img2.setImageResource(resid);
								}
							}
							//Log.d("ViewNumber", ""+ViewNumber);
						}
						break;
				}
				return true;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
