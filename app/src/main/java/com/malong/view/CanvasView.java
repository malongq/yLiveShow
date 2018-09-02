package com.malong.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by Malong
 * on 18/8/7.
 */
public class CanvasView extends BaseView {

    public CanvasView(Context context) {
        super(context);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //开始画--X轴
    @Override
    protected void drawXAxis(Canvas canvas, Paint paint) {

        paint.setColor(Color.BLACK);//X轴颜色
        paint.setStrokeWidth(5);//线宽
        canvas.drawLine(originalx, originaly, originalx + width, originaly, paint);//直线
    }

    //开始画--Y轴
    @Override
    protected void drawYAxis(Canvas canvas, Paint paint) {

        paint.setColor(Color.BLUE);//Y轴颜色
        paint.setStrokeWidth(5);//线宽
        canvas.drawLine(originalx, originaly, originalx, originaly - height, paint);//直线
    }

    //开始画--X轴上刻度
    @Override
    protected void drawXAxisScale(Canvas canvas, Paint paint) {

        float cellWidth = width / xSize;
        for (int i = 0; i < xSize - 1; i++) {
            canvas.drawLine(cellWidth * (i + 1) + originalx, originaly, cellWidth * (i + 1) + originalx, originaly - 10, paint);
        }
    }

    //开始画--Y轴上刻度
    @Override
    protected void drawYAxisScale(Canvas canvas, Paint paint) {

        float cellHeight = height / ySize;
        for (int i = 0; i < ySize - 1; i++) {
            canvas.drawLine(originalx, (originaly - cellHeight * (i + 1)), originalx + 10, (originaly - cellHeight * (i + 1)), paint);
        }
    }

    //开始画--X轴上刻度值
    @Override
    protected void drawXAxisScaleValue(Canvas canvas, Paint paint) {
        paint.setColor(Color.GRAY);
        paint.setTextSize(16);
        paint.setFakeBoldText(true);
        float cellwidthValue = width / xSize;
        for (int i = 0; i < xSize; i++) {
            canvas.drawText(String.valueOf(i), cellwidthValue * i + originalx - 35, originaly + 30, paint);
        }
    }

    //开始画--Y轴上刻度值
    @Override
    protected void drawYAxisScaleValue(Canvas canvas, Paint paint) {

        paint.setColor(Color.GRAY);
        paint.setTextSize(16);
        paint.setFakeBoldText(true);
        float cellHeightValue = height / ySize;
        for (int i = 0; i < ySize; i++) {
            canvas.drawText(String.valueOf(i), originalx - 30, originaly - cellHeightValue * i + 10, paint);
        }
    }

    //开始画--中间的条形
    @Override
    protected void drawColumn(Canvas canvas, Paint paint) {

        if (columInfo != null) {
            float cellWidth = width / xSize;
            for (int i = 0; i < columInfo.length - 1; i++) {
                paint.setColor(columInfo[i][1]);
                float leftY = originaly - height * (columInfo[i][0]) / ySize;
                canvas.drawRect(originalx + cellWidth * (i + 1), leftY, originalx + cellWidth * (i + 2), originaly, paint);
            }
        }
    }

}
