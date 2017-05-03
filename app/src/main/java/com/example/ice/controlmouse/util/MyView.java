package com.example.ice.controlmouse.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.WindowManager;
import android.widget.Switch;

import com.example.ice.controlmouse.MainActivity;
import com.example.ice.controlmouse.constant.Constant;

/**
 * Created by ice on 4/19/17.
 */

public class MyView extends View {
    public float currentX=40;
    public float currentY=50;

    private int width;
    private int height;

    private int pc_width=1366;
    private int pc_heigth=768;

    private float first_x=0;
    private float first_y=0;

    private float last_x=0;
    private float last_y=0;
    Paint p=new Paint();

    private String ip;
    public MyView(Context context) {
        super(context);
        getScreenSize(context);
    }

    public void setIp(String ip){
        this.ip=ip;
    }
    //获得当前设备的宽和高
    private void getScreenSize(Context context){
        WindowManager windowManager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display=windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics=new DisplayMetrics();
        display.getMetrics(displayMetrics);
        width=displayMetrics.widthPixels;
        height=displayMetrics.heightPixels;
    }
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getScreenSize(context);
    }


    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        p.setColor(Color.RED);

        canvas.drawCircle(currentX,currentY,15,p);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){

        //触摸点的 x,y值
        currentX=event.getRawX();
        currentY=event.getRawY();
        //映射到电脑上的xy 值
        float pc_x=0;
        float pc_y=0;
        //行为 时单击 拖动 还是右键
        String action= Constant.moveMouse;
        if(event.getAction()==MotionEvent.ACTION_DOWN)
        {
            first_x=currentX;
            first_y=currentY;
            if(last_x==0&&last_y==0){
                last_x=currentX;
                last_y=currentY;
            }
        }

        pc_x=(currentX-first_x+last_x)*pc_width/width;
        pc_y=(currentY-first_y+last_y)*pc_heigth/height;

        if(event.getAction()==MotionEvent.ACTION_UP){
            last_x=currentX-first_x+last_x;
            last_y=currentY-first_y+last_y;

            //位移的距离为0 表示单击， 如果重新回到出发点 也算是单击
            if(currentX==first_x&&currentY==first_y){
                action=Constant.mouseClick;
            }
            //如果单击点 在屏幕右下角时 判定为右键
            if(action.equals(Constant.mouseClick)){
                if(currentX/width>0.8&&currentY/height>0.8)
                {
                    action=Constant.mouseRight;
                }
            }
        }
        //通知当前组件重绘自己
        invalidate();

        //判定触屏行为 单击 还是 拖动
        new Thread(new SendMouseMessage(action+","+pc_x+","+pc_y,ip)).start();
        //返回true 表明该处理方法已经处理该事件
        return true;
    }


}
