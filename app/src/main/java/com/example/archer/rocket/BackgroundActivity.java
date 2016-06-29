package com.example.archer.rocket;

import android.app.Activity;
import android.os.Bundle;

import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

/**
 * 烟雾背景
 * Created by Archer on 2016/6/29.
 */
public class BackgroundActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_bg);

        ImageView ivTop = (ImageView) findViewById(R.id.iv_top);
        ImageView ivBottom = (ImageView) findViewById(R.id.iv_bottom);

        AlphaAnimation anim=new AlphaAnimation(0,1);

        anim.setDuration(1000);
        anim.setFillAfter(true);//保持状态

        //启动动画
        ivTop.startAnimation(anim);
        ivBottom.startAnimation(anim);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
         finish();
            }
        }, 2000);//延时一面后结束Activity
    }
}
