package com.malong.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.malong.myliveshow.R;


/**
 * Created by Malong
 * on 18/8/7.
 */
public abstract class BaseView extends View {

    //坐标轴画笔
    private Paint mXYAxisPaint;
    private Paint mGraphTitlePaint;
    private Paint mAxisNamePaint;
    public Paint mPaint;

    private Paint paint;//初始化画笔

    public int width;//视图宽度
    public int height;//视图高度

    public final int originalx = 100;//视图原点X坐标
    public final int originaly = 800;//视图原点Y坐标

    //X坐标轴最大值
    public float maxAxisValueX = 900;
    //X坐标轴刻度线数量
    public int xSize = 9;
    //Y坐标轴最大值
    public float maxAxisValueY = 700;
    //Y坐标轴刻度线数量
    public int ySize = 7;

    //第一个纬度值，第二个未读颜色
    public int columInfo[][];

    private String title;//标题
    private String xAxisName;//X轴坐标文字
    private String yAxisName;//Y轴坐标文字
    private float textSize;//字体大小
    private int textColor;//字体颜色

    public BaseView(Context context) {
        this(context, null);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获取自定义样式
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.CanvasView);

        title = typeArray.getString(R.styleable.CanvasView_title);//标题
        xAxisName = typeArray.getString(R.styleable.CanvasView_xAxisName);//X轴坐标文字
        yAxisName = typeArray.getString(R.styleable.CanvasView_yAxisName);//Y轴坐标文字
        textSize = typeArray.getDimension(R.styleable.CanvasView_textSize, 12);//字体大小
        textColor = typeArray.getColor(R.styleable.CanvasView_textColor, context.getResources().getColor(R.color.black));//字体颜色

        //一定要判断在不为Null的时候，要回收掉
        if (typeArray != null) {
            typeArray.recycle();
        }

        //初始化画笔
        initPaint(context);

    }

    //初始化画笔
    private void initPaint(Context context) {
        if (paint == null) {
            paint = new Paint();
            paint.setDither(true);//防抖动
            paint.setAntiAlias(true);//去锯齿
        }
    }

    //重新onDraw方法
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width = getWidth() - originalx;//屏幕的宽减去起始点的坐标
        height = (originaly > getHeight() ? getHeight() : originaly) - 400;//屏幕的高起始点的坐标

        //开始画： X轴、Y轴、标题、 X轴上刻度、 Y轴上刻度、 X轴上刻度值、 Y轴上刻度值、 X轴上箭头、 Y轴上箭头、 中间的条形
        drawXAxis(canvas, paint);
        drawYAxis(canvas, paint);
        drawTitle(canvas, paint);
        drawXAxisScale(canvas, paint);
        drawYAxisScale(canvas, paint);
        drawXAxisScaleValue(canvas, paint);
        drawYAxisScaleValue(canvas, paint);
        drawXAxisArrow(canvas, paint);
        drawYAxisArrow(canvas, paint);
        drawColumn(canvas, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width_measure = MeasureSpec.getSize(widthMeasureSpec);
        int height_measure = MeasureSpec.getSize(heightMeasureSpec);
        Log.e("马龙--》", "width_measure:  "+width_measure+"  height_measure"+height_measure);
    }

    public void setGrapthTitle(String grapthTitle) {
        title = grapthTitle;
    }

    public void setXAxisName(String XAxisName) {
        xAxisName = XAxisName;
    }

    public void setYAxisName(String YAxisName) {
        yAxisName = YAxisName;
    }

    public void setAxisTextColor(int axisTextColor) {
        textColor = axisTextColor;
    }

    public void setAxisTextSize(float axisTextSize) {
        textSize = axisTextSize;
    }

    /**
     * 传入柱状图数据
     * @param columnInfo
     */
    public void setColumnInfo(int[][] columnInfo) {
        this.columInfo = columnInfo;
    }

    /**
     * 手动设置X轴最大值及等份数
     * @param maxAxisValueX
     * @param dividedSize
     */
    public void setXAxisValue(float maxAxisValueX,int dividedSize) {
        this.maxAxisValueX = maxAxisValueX;
        this.xSize = dividedSize;
    }

    /**
     * 手动设置Y轴最大值及等份数
     * @param maxAxisValueY
     * @param dividedSize
     */
    public void setYAxisValue(float maxAxisValueY,int dividedSize) {
        this.maxAxisValueY = maxAxisValueY;
        this.ySize = dividedSize;
    }


    //开始画--X轴
    protected abstract void drawXAxis(Canvas canvas, Paint paint);

    //开始画--Y轴
    protected abstract void drawYAxis(Canvas canvas, Paint paint);

    //开始画--标题
    private void drawTitle(Canvas canvas, Paint paint) {

        if (title != null) {
            paint.setTextSize(textSize);//设置标题大小
            paint.setColor(textColor);//设置标题颜色
            paint.setFakeBoldText(true);//设置标题字体加粗
            //开始画标题  第一个参数：标题文字
            //开始画标题  第二个参数：标题展示位置X轴
            //开始画标题  第三个参数：标题展示位置Y轴
            //开始画标题  第四个参数：画笔开始画
            canvas.drawText(title, (getWidth() / 2) - (paint.measureText(title)) / 2, originaly + 40, paint);
        }
    }

    //开始画--X轴上刻度
    protected abstract void drawXAxisScale(Canvas canvas, Paint paint);

    //开始画--Y轴上刻度
    protected abstract void drawYAxisScale(Canvas canvas, Paint paint);

    //开始画--X轴上刻度值
    protected abstract void drawXAxisScaleValue(Canvas canvas, Paint paint);

    //开始画--Y轴上刻度值
    protected abstract void drawYAxisScaleValue(Canvas canvas, Paint paint);

    //开始画--X轴上箭头
    private void drawXAxisArrow(Canvas canvas, Paint paint) {

        Path path = new Path();
        path.moveTo(originalx + width + 30, originaly);
        path.lineTo(originalx + width, originaly + 10);//上方线段
        path.lineTo(originalx + width, originaly - 10);//下方线段
        path.close();
        canvas.drawPath(path, paint);
        canvas.drawText(xAxisName, originalx - 50, originaly - height - 30, paint);
    }

    //开始画--Y轴上箭头
    private void drawYAxisArrow(Canvas canvas, Paint paint) {
        Path path = new Path();
        path.moveTo(originalx, originaly - height - 30);
        path.lineTo(originalx - 10, originaly);//左边线段
        path.lineTo(originalx + 10, originalx);//右边线段
        path.close();
        canvas.drawPath(path, paint);
        canvas.drawText(yAxisName, originaly + height, originalx, paint);
    }

    //开始画--中间的条形
    protected abstract void drawColumn(Canvas canvas, Paint paint);

}
