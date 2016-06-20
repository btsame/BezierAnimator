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
public class TriangleMultiFunctionDialog extends Dialog implements View.OnClickListener{

    private ImageView mTextIV, mVideoIV, mPicIV, mEnterIV;
    private View mRoot;
    private Activity mContext;

    public TriangleMultiFunctionDialog(Context context) {
        this(context, R.style.dm_alert_dialog);
        mContext = (Activity) context;
    }

    public TriangleMultiFunctionDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = (Activity) context;
    }

    protected TriangleMultiFunctionDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = (Activity) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.triangle_multi_func_enter);
        mTextIV = (ImageView) findViewById(R.id.iv_gif_edit_text);
        mVideoIV = (ImageView) findViewById(R.id.iv_gif_edit_video);
        mPicIV = (ImageView) findViewById(R.id.iv_gif_edit_pic);
        mEnterIV = (ImageView) findViewById(R.id.iv_gif_edit_enter);
        mRoot = findViewById(R.id.rv_gif_edit_root);

        mTextIV.setOnClickListener(this);
        mVideoIV.setOnClickListener(this);
        mPicIV.setOnClickListener(this);
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

        mTextIV.post(new Runnable() {
            @Override
            public void run() {
//                initAnimator();
                initBezierAnimator();
            }
        });
    }


    static final float MOVE_DISTANCE = 400.0f;
    float initX, initY;

    public static final int bezierRadius = 200;
    private void initBezierAnimator(){
        initX = mTextIV.getX();
        initY = mTextIV.getY();

        ObjectAnimator textScaleXAnimator = ObjectAnimator.ofFloat(mTextIV, "scaleX", 0.0f, 1.0f);
        ObjectAnimator textScaleYAnimator = ObjectAnimator.ofFloat(mTextIV, "scaleY", 0.0f, 1.0f);
        ObjectAnimator textAlphaAnimator = ObjectAnimator.ofFloat(mTextIV, "alpha", 0.0f, 1.0f);
        PointF textPointControl = new PointF(initX - MOVE_DISTANCE / 2, initY - bezierRadius);
        ValueAnimator textTransferAnimator = ValueAnimator.ofObject(new QuaBezierEvaluator(textPointControl), new PointF(initX, initY),
                new PointF(initX - MOVE_DISTANCE, initY));
        textTransferAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //这里获取到贝塞尔曲线计算出来的的x y值 赋值给view 这样就能让爱心随着曲线走啦
                PointF pointF = (PointF) animation.getAnimatedValue();
                mTextIV.setX(pointF.x);
                mTextIV.setY(pointF.y);
            }
        });

        ObjectAnimator videoScaleXAnimator = ObjectAnimator.ofFloat(mVideoIV, "scaleX", 0.0f, 1.0f);
        ObjectAnimator videoScaleYAnimator = ObjectAnimator.ofFloat(mVideoIV, "scaleY", 0.0f, 1.0f);
        ObjectAnimator videoAlphaAnimator = ObjectAnimator.ofFloat(mVideoIV, "alpha", 0.0f, 1.0f);
        PointF videoPointControl = new PointF(initX - MOVE_DISTANCE / 2 /1.414f + bezierRadius / 1.414f, initY - MOVE_DISTANCE / 2 / 1.414f - bezierRadius / 1.414f);
        ValueAnimator videoTransferAnimator = ValueAnimator.ofObject(new QuaBezierEvaluator(videoPointControl), new PointF(initX, initY),
                new PointF(initX - MOVE_DISTANCE / 1.414f, initY - MOVE_DISTANCE / 1.414f));
        videoTransferAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //这里获取到贝塞尔曲线计算出来的的x y值 赋值给view 这样就能让爱心随着曲线走啦
                PointF pointF = (PointF) animation.getAnimatedValue();
                mVideoIV.setX(pointF.x);
                mVideoIV.setY(pointF.y);
            }
        });

        ObjectAnimator picScaleXAnimator = ObjectAnimator.ofFloat(mPicIV, "scaleX", 0.0f, 1.0f);
        ObjectAnimator picScaleYAnimator = ObjectAnimator.ofFloat(mPicIV, "scaleY", 0.0f, 1.0f);
        ObjectAnimator picAlphaAnimator = ObjectAnimator.ofFloat(mPicIV, "alpha", 0.0f, 1.0f);
        PointF picPointControl = new PointF(initX + bezierRadius, initY - MOVE_DISTANCE / 2);
        ValueAnimator picTransferAnimator = ValueAnimator.ofObject(new QuaBezierEvaluator(picPointControl), new PointF(initX, initY),
                new PointF(initX, initY - MOVE_DISTANCE));
        picTransferAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //这里获取到贝塞尔曲线计算出来的的x y值 赋值给view 这样就能让爱心随着曲线走啦
                PointF pointF = (PointF) animation.getAnimatedValue();
                mPicIV.setX(pointF.x);
                mPicIV.setY(pointF.y);
            }
        });

        ObjectAnimator enterAlphaAnimator = ObjectAnimator.ofFloat(mEnterIV, "alpha", 0.0f, 1.0f);
        enterAlphaAnimator.setDuration(50);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(textTransferAnimator).with(textScaleXAnimator).with(textScaleYAnimator).with(textAlphaAnimator)
                .with(videoTransferAnimator).with(videoScaleXAnimator).with(videoScaleYAnimator).with(videoAlphaAnimator)
                .with(picTransferAnimator).with(picScaleXAnimator).with(picScaleYAnimator).with(picAlphaAnimator)
                .with(enterAlphaAnimator);
        animatorSet.setDuration(600);
//        animatorSet.setInterpolator(new BounceInterpolator());
        animatorSet.start();
    }

    private void dismissBezierAnimator(){
        ObjectAnimator textScaleXAnimator = ObjectAnimator.ofFloat(mTextIV, "scaleX", 1.0f, 0.0f);
        ObjectAnimator textScaleYAnimator = ObjectAnimator.ofFloat(mTextIV, "scaleY", 1.0f, 0.0f);
        ObjectAnimator textAlphaAnimator = ObjectAnimator.ofFloat(mTextIV, "alpha", 1.0f, 0.0f);
        PointF textPointControl = new PointF(initX - MOVE_DISTANCE / 2, initY - bezierRadius);
        ValueAnimator textTransferAnimator = ValueAnimator.ofObject(new QuaBezierEvaluator(textPointControl),
                new PointF(initX - MOVE_DISTANCE, initY), new PointF(initX, initY));
        textTransferAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //这里获取到贝塞尔曲线计算出来的的x y值 赋值给view 这样就能让爱心随着曲线走啦
                PointF pointF = (PointF) animation.getAnimatedValue();
                mTextIV.setX(pointF.x);
                mTextIV.setY(pointF.y);
            }
        });

        ObjectAnimator videoScaleXAnimator = ObjectAnimator.ofFloat(mVideoIV, "scaleX", 1.0f, 0.0f);
        ObjectAnimator videoScaleYAnimator = ObjectAnimator.ofFloat(mVideoIV, "scaleY", 1.0f, 0.0f);
        ObjectAnimator videoAlphaAnimator = ObjectAnimator.ofFloat(mVideoIV, "alpha", 1.0f, 0.0f);
        PointF videoPointControl = new PointF(initX - MOVE_DISTANCE / 2 /1.414f + bezierRadius / 1.414f, initY - MOVE_DISTANCE / 2 / 1.414f - bezierRadius / 1.414f);
        ValueAnimator videoTransferAnimator = ValueAnimator.ofObject(new QuaBezierEvaluator(videoPointControl),
                new PointF(initX - MOVE_DISTANCE / 1.414f, initY - MOVE_DISTANCE / 1.414f), new PointF(initX, initY));
        videoTransferAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //这里获取到贝塞尔曲线计算出来的的x y值 赋值给view 这样就能让爱心随着曲线走啦
                PointF pointF = (PointF) animation.getAnimatedValue();
                mVideoIV.setX(pointF.x);
                mVideoIV.setY(pointF.y);
            }
        });

        ObjectAnimator picScaleXAnimator = ObjectAnimator.ofFloat(mPicIV, "scaleX", 1.0f, 0.0f);
        ObjectAnimator picScaleYAnimator = ObjectAnimator.ofFloat(mPicIV, "scaleY", 1.0f, 0.0f);
        ObjectAnimator picAlphaAnimator = ObjectAnimator.ofFloat(mPicIV, "alpha", 1.0f, 0.0f);
        PointF picPointControl = new PointF(initX + bezierRadius, initY - MOVE_DISTANCE / 2);
        ValueAnimator picTransferAnimator = ValueAnimator.ofObject(new QuaBezierEvaluator(picPointControl),
                new PointF(initX, initY - MOVE_DISTANCE), new PointF(initX, initY));
        picTransferAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //这里获取到贝塞尔曲线计算出来的的x y值 赋值给view 这样就能让爱心随着曲线走啦
                PointF pointF = (PointF) animation.getAnimatedValue();
                mPicIV.setX(pointF.x);
                mPicIV.setY(pointF.y);
            }
        });

        ObjectAnimator enterAlphaAnimator = ObjectAnimator.ofFloat(mEnterIV, "alpha", 1.0f, 0.0f);
        enterAlphaAnimator.setDuration(50);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(textTransferAnimator).with(textScaleXAnimator).with(textScaleYAnimator).with(textAlphaAnimator)
                .with(videoTransferAnimator).with(videoScaleXAnimator).with(videoScaleYAnimator).with(videoAlphaAnimator)
                .with(picTransferAnimator).with(picScaleXAnimator).with(picScaleYAnimator).with(picAlphaAnimator)
                .with(enterAlphaAnimator);
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
