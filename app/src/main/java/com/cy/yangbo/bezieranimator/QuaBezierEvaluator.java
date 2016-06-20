package com.cy.yangbo.bezieranimator;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by Administrator on 2016/6/14.
 */
public class QuaBezierEvaluator implements TypeEvaluator<PointF>{

    private PointF point1;

    public QuaBezierEvaluator(PointF pointControl) {
        this.point1 = pointControl;
    }

    @Override
    public PointF evaluate(float time, PointF startValue, PointF endValue) {
        float timeLeft = 1.0f - time;
        PointF point = new PointF();//结果

        PointF point0 = (PointF)startValue;//起点

        PointF point2 = (PointF)endValue;//终点
        //代入公式
        point.x = timeLeft * timeLeft * (point0.x)
                + 2 * timeLeft * time * (point1.x)
                + time * time * (point2.x);

        point.y = timeLeft * timeLeft * (point0.y)
                + 2 * timeLeft * time * (point1.y)
                + time * time * (point2.y);
        return point;
    }
}
