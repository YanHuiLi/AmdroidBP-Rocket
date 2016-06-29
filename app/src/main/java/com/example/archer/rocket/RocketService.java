package com.example.archer.rocket;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Outline;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by Archer on 2016/6/26.
 */
public class RocketService extends Service{

    private WindowManager mWM;
    private View view;
    private int winwidth;
    private int winheight;

    private int startX,startY;
    private WindowManager.LayoutParams mParams;
    private Message message;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mWM = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);

        winwidth = mWM.getDefaultDisplay().getWidth();
        winheight = mWM.getDefaultDisplay().getHeight();
        mParams = new WindowManager.LayoutParams();

        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.format = PixelFormat.TRANSLUCENT;

        mParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        mParams.setTitle("Toast");
        mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mParams.gravity= Gravity.LEFT+Gravity.TOP;//将中心位置设置成左上方，默认是居中


        view = View.inflate(this, R.layout.rocket,null);
        //初始化火箭帧动画
        ImageView ivRocket= (ImageView) view.findViewById(R.id.iv_rocket);
        ivRocket.setBackgroundResource(R.drawable.animation_list);
        AnimationDrawable  anim = (AnimationDrawable) ivRocket.getBackground();
        anim.start();
        mWM.addView(view, mParams);

        view.setOnTouchListener(new View.OnTouchListener() {



            @Override
            public boolean onTouch(View v, MotionEvent event) {

//                System.out.println("OnTouch");

                switch (event.getAction()){

                    case MotionEvent.ACTION_DOWN:
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;

                    case  MotionEvent.ACTION_MOVE:
                        int endX=(int) event.getRawX();
                        int endY= (int) event.getRawY();

                        int dX=endX-startX;
                        int dY=endY-startY;//计算移动偏移量

                        //更新浮窗位置
                        mParams.x+=dX;
                        mParams.y+=dY;

                        //防止坐标偏离屏幕
                        if (mParams.x<0){
                            mParams.x=0;
                        }

                        if (mParams.y<0){
                            mParams.y=0;
                        }
                        if (mParams.x>winwidth-view.getWidth()) {
                            mParams.x=winwidth-view.getWidth();

                        }
                        if (mParams.y>winheight-view.getHeight()){
                            mParams.y=winheight-view.getHeight();
                        }
                        mWM.updateViewLayout(view, mParams);

                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;

                    case MotionEvent.ACTION_UP:
                       if (mParams.x>400&&mParams.x<600){//华为为准
                           System.out.println(winheight);
                           System.out.println(winwidth);
                           System.out.println("火箭发射 ");
                           sendRocket();

                           Intent intent=new Intent(RocketService.this,BackgroundActivity.class);
                           intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//启动一个盏存放activity

                           startActivity(intent);

                       }
                        break;

                }
                return true;
            }
        });

    }

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mParams.y= msg.arg1;
            mWM.updateViewLayout(view,mParams);
        }
    };
    /**
     * 发射火箭
     */

    private void sendRocket() {

        //设置火箭居中

        mParams.x=winwidth/2-view.getWidth()/2;
        mWM.updateViewLayout(view,mParams);

        //异步加载
        new Thread(){
            @Override
            public void run() {
                int pos=1920;
                for (int i=0;i<=10;i++){

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    int y=pos-192*i;


                    message = Message.obtain();
                    message.arg1=y;
                    mHandler.sendMessage(message);


                }
            }
        }.start();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        mWM.removeView(view);
        view=null;
    }
}
