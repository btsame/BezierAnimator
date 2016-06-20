package com.cy.yangbo.bezieranimator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/6/7.
 */
public class CircleMultiFunctionDialog extends Dialog implements View.OnClickListener{

    private ImageView mMultiIV, mFunc1, mFunc2, mFunc3, mFunc4, mFunc5, mFunc6, mFunc7, mFunc8;
    private View mRoot;
    private Activity mContext;

    public CircleMultiFunctionDialog(Context context) {
        this(context, R.style.dm_alert_dialog);
        mContext = (Activity) context;
    }

    public CircleMultiFunctionDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = (Activity) context;
    }

    protected CircleMultiFunctionDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = (Activity) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.circle_multi_func_enter);

        mFunc1 = (ImageView) findViewById(R.id.iv_func_1);
        mFunc2 = (ImageView) findViewById(R.id.iv_func_2);
        mFunc3 = (ImageView) findViewById(R.id.iv_func_3);
        mFunc4 = (ImageView) findViewById(R.id.iv_func_4);
        mFunc5 = (ImageView) findViewById(R.id.iv_func_5);
        mFunc6 = (ImageView) findViewById(R.id.iv_func_6);
        mFunc7 = (ImageView) findViewById(R.id.iv_func_7);
        mFunc8 = (ImageView) findViewById(R.id.iv_func_8);
        mRoot = findViewById(R.id.rv_gif_edit_root);

        mFunc1.setOnClickListener(this);
        mFunc2.setOnClickListener(this);
        mFunc3.setOnClickListener(this);
        mFunc4.setOnClickListener(this);
        mFunc5.setOnClickListener(this);
        mFunc6.setOnClickListener(this);
        mFunc7.setOnClickListener(this);
        mFunc8.setOnClickListener(this);
        mRoot.setOnClickListener(this);

        //region set layout
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        WindowManager windowManager = mContext.getWindowManager();
        Display defaultDisplay = windowManager.getDefaultDisplay();

        int dialogWidth = defaultDisplay.getWidth();
        int dialogHeight = defaultDisplay.getHeight();
        params.width = dialogWidth;
        params.height = dialogHeight;
        window.setAttributes(params);
        window.setGravity(Gravity.NO_GRAVITY);
        //endregion

    }

    @Override
    protected void onStart() {
        super.onStart();

        mRoot.post(new Runnable() {
            @Override
            public void run() {
                initBezierAnimator();
            }
        });
    }


    static final float MOVE_DISTANCE = 400.0f;
    float initX, initY;

    public static final int bezierRadius = 300;
    private void initBezierAnimator(){
        initX = mFunc1.getX();
        initY = mFunc1.getY();

        AnimatorSet setFunc1 = calculateindividualAnimator(mFunc1, 0, 8, true);
        AnimatorSet setFunc2 = calculateindividualAnimator(mFunc2, 1, 8, true);
        AnimatorSet setFunc3 = calculateindividualAnimator(mFunc3, 2, 8, true);
        AnimatorSet setFunc4 = calculateindividualAnimator(mFunc4, 3, 8, true);
        AnimatorSet setFunc5 = calculateindividualAnimator(mFunc5, 4, 8, true);
        AnimatorSet setFunc6 = calculateindividualAnimator(mFunc6, 5, 8, true);
        AnimatorSet setFunc7 = calculateindividualAnimator(mFunc7, 6, 8, true);
        AnimatorSet setFunc8 = calculateindividualAnimator(mFunc8, 7, 8, true);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(setFunc1).with(setFunc2).with(setFunc3).with(setFunc4)
                .with(setFunc5).with(setFunc6).with(setFunc7).with(setFunc8);
        animatorSet.setDuration(800);
//        animatorSet.setInterpolator(new BounceInterpolator());
        animatorSet.start();
    }

    /**
     *
     * @param target
     * @param index
     * @param total
     * @param toDisperse
     * @return
     */
    private AnimatorSet calculateindividualAnimator(final View target, int index, int total, boolean toDisperse){
        ObjectAnimator scaleXAnimator = null;
        ObjectAnimator scaleYAnimator = null;
        ObjectAnimator alphaAnimator = null;


        PointF pointBegin = null;
        PointF pointEnd = null;
        PointF pointControl = null;
        double radians = 1.0 * index / total * Math.PI * 2;
        if(toDisperse){
            pointBegin = new PointF(initX, initY);
            pointEnd = new PointF((float)(initX + Math.cos(radians) * MOVE_DISTANCE),
                    (float)(initY - Math.sin(radians) * MOVE_DISTANCE));

            double controlBELineRadians = Math.atan(bezierRadius / (MOVE_DISTANCE / 2));
            double controlRadians = radians - controlBELineRadians;

            pointControl = new PointF((float)(initX + Math.cos(controlRadians) * (bezierRadius / Math.sin(controlBELineRadians))),
                    (float)(initY - Math.sin(controlRadians) * (bezierRadius / Math.sin(controlBELineRadians))));

            scaleXAnimator = ObjectAnimator.ofFloat(target, "scaleX", 0.0f, 0.8f, 1.0f);
            scaleYAnimator = ObjectAnimator.ofFloat(target, "scaleY", 0.0f, 0.8f, 1.0f);
            alphaAnimator = ObjectAnimator.ofFloat(target, "alpha", 0.0f, 1.0f);
        } else {
            pointEnd = new PointF(initX, initY);
            pointBegin = new PointF((float)(initX + Math.cos(radians) * MOVE_DISTANCE),
                    (float)(initY - Math.sin(radians) * MOVE_DISTANCE));

            double controlBELineRadians = Math.atan(bezierRadius / (MOVE_DISTANCE / 2));
            double controlRadians = radians - controlBELineRadians;

            pointControl = new PointF((float)(initX + Math.cos(controlRadians) * (bezierRadius / Math.sin(controlBELineRadians))),
                    (float)(initY - Math.sin(controlRadians) * (bezierRadius / Math.sin(controlBELineRadians))));

            scaleXAnimator = ObjectAnimator.ofFloat(target, "scaleX", 1.0f, 0.8f, 0.0f);
            scaleYAnimator = ObjectAnimator.ofFloat(target, "scaleY", 1.0f, 0.8f, 0.0f);
            alphaAnimator = ObjectAnimator.ofFloat(target, "alpha", 1.0f, 0.0f);
        }
        ValueAnimator transferAnimator = ValueAnimator.ofObject(new QuaBezierEvaluator(pointControl),
                pointBegin, pointEnd);
        transferAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //这里获取到贝塞尔曲线计算出来的的x y值 赋值给view 这样就能让爱心随着曲线走啦
                PointF pointF = (PointF) animation.getAnimatedValue();
                target.setX(pointF.x);
                target.setY(pointF.y);
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(transferAnimator).with(scaleXAnimator).with(scaleYAnimator)
                .with(alphaAnimator);

        return animatorSet;
    }

    private void dismissBezierAnimator(){
        AnimatorSet setFunc1 = calculateindividualAnimator(mFunc1, 0, 8, false);
        AnimatorSet setFunc2 = calculateindividualAnimator(mFunc2, 1, 8, false);
        AnimatorSet setFunc3 = calculateindividualAnimator(mFunc3, 2, 8, false);
        AnimatorSet setFunc4 = calculateindividualAnimator(mFunc4, 3, 8, false);
        AnimatorSet setFunc5 = calculateindividualAnimator(mFunc5, 4, 8, false);
        AnimatorSet setFunc6 = calculateindividualAnimator(mFunc6, 5, 8, false);
        AnimatorSet setFunc7 = calculateindividualAnimator(mFunc7, 6, 8, false);
        AnimatorSet setFunc8 = calculateindividualAnimator(mFunc8, 7, 8, false);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(setFunc1).with(setFunc2).with(setFunc3).with(setFunc4)
                .with(setFunc5).with(setFunc6).with(setFunc7).with(setFunc8);
        animatorSet.setDuration(600);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rv_gif_edit_root:
                dismissBezierAnimator();
                break;
            case R.id.iv_gif_edit_text:
                Toast.makeText(mContext, "text", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            case R.id.iv_gif_edit_video:
                Toast.makeText(mContext, "video", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            case R.id.iv_gif_edit_pic:
                Toast.makeText(mContext, "pic", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            default:
        }
    }
}
