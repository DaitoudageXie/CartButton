package com.xyb.mylibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by xieyubin on 2017/10/16 0016.
 */

public class CartNumView extends View {

    protected CartNumClickListener clickListener;

    protected int mLeft;
    protected int mTop;
    protected int mWidth;
    protected int mHeight;
    //加按钮
    protected Paint mAddPaint;//
    protected boolean isAddFillMode;//加按钮是否填充
    protected int mAddEnableBgColor;//加按钮背景色
    protected int mAddEnableFgColor;//加按钮前景色
    protected int mAddDisableBgColor;//
    protected int mAddDisableFgColor;

    //减按钮
    protected Paint mDelPaint;//
    protected boolean isDelFillMode;
    protected int mDelEnableBgColor;
    protected int mDelEnableFgColor;
    protected int mDelDisableBgColor;
    protected int mDelDisableFgColor;

    //最大数量和当前数量
    protected int mMaxCount;
    protected int mCount;



    //圆形半径
    protected float mRadius;
    //圆圈的宽度
    protected float mCircleWidth;
    //线的宽度
    protected float mLineWidth;

    //间距
    protected float mGapBetweenCircle;

    //数量
    protected Paint mTextPaint;
    protected float mTextSize;
    protected Paint.FontMetrics mFontMetrics;

    protected Path mDelPath;
    protected Path mAddPath;
    protected Region mDelRegion;
    protected Region mAddRegion;

    public CartNumView(Context context) {
        this(context, null);
    }

    public CartNumView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CartNumView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * 初始化数据信息
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        initDefalutValue(context);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CartNumView, defStyleAttr, 0);
        int indexCount = ta.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = ta.getIndex(i);
            if (index == R.styleable.CartNumView_isAddFillMode) {
                isAddFillMode = ta.getBoolean(index, isAddFillMode);
            } else if (index == R.styleable.CartNumView_gapBetweenCircle) {
                mGapBetweenCircle = ta.getDimension(index, mGapBetweenCircle);
            } else if (index == R.styleable.CartNumView_addEnableBgColor) {
                mAddEnableBgColor = ta.getColor(index, mAddEnableBgColor);
            } else if (index == R.styleable.CartNumView_addEnableFgColor) {
                mAddEnableFgColor = ta.getColor(index, mAddEnableFgColor);
            } else if (index == R.styleable.CartNumView_addDisableBgColor) {
                mAddDisableBgColor = ta.getColor(index, mAddDisableBgColor);
            } else if (index == R.styleable.CartNumView_addDisableFgColor) {
                mAddDisableFgColor = ta.getColor(index, mAddDisableFgColor);
            } else if (index == R.styleable.CartNumView_isDelFillMode) {
                isDelFillMode = ta.getBoolean(index, isDelFillMode);
            } else if (index == R.styleable.CartNumView_delEnableBgColor) {
                mDelEnableBgColor = ta.getColor(index, mDelEnableBgColor);
            } else if (index == R.styleable.CartNumView_delEnableFgColor) {
                mDelDisableFgColor = ta.getColor(index, mDelDisableFgColor);
            } else if (index == R.styleable.CartNumView_delDisableBgColor) {
                mDelDisableBgColor = ta.getColor(index, mDelDisableBgColor);
            } else if (index == R.styleable.CartNumView_delDisableFgColor) {
                mDelDisableFgColor = ta.getColor(index, mDelDisableFgColor);
            } else if (index == R.styleable.CartNumView_maxCount) {
                mMaxCount = ta.getInteger(index, mMaxCount);
            } else if (index == R.styleable.CartNumView_count) {
                mCount = ta.getInteger(index, mCount);
            } else if (index == R.styleable.CartNumView_radius) {
                mRadius = ta.getDimension(index, mRadius);
            } else if (index == R.styleable.CartNumView_circleStokeWidth) {
                mCircleWidth = ta.getDimension(index, mCircleWidth);
            } else if (index == R.styleable.CartNumView_lineWidth) {
                mLineWidth = ta.getDimension(index, mLineWidth);
            } else if (index == R.styleable.CartNumView_numTextSize) {
                mTextSize = ta.getDimension(index, mTextSize);
            }

        }

        ta.recycle();

        mAddRegion=new Region();
        mDelRegion=new Region();
        mAddPath=new Path();
        mDelPath=new Path();


        mAddPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (isAddFillMode) {
            mAddPaint.setStyle(Paint.Style.FILL);
        } else {
            mAddPaint.setStyle(Paint.Style.STROKE);
        }
        mDelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (isDelFillMode) {
            mDelPaint.setStyle(Paint.Style.FILL);
        } else {
            mDelPaint.setStyle(Paint.Style.STROKE);
        }

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mTextSize);
        mFontMetrics = mTextPaint.getFontMetrics();

    }

    private void initDefalutValue(Context context) {

        mGapBetweenCircle = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 34, context.getResources().getDisplayMetrics());

        isAddFillMode = true;
        /*mAddEnableBgColor = 0xFFFFDC5B;*/
        mAddEnableBgColor = 0xff22AC38;
        mAddEnableFgColor = Color.WHITE;
        mAddDisableBgColor = 0xff979797;
        mAddDisableFgColor = Color.BLACK;

        isDelFillMode = false;
        mDelEnableBgColor = 0xff979797;
        mDelEnableFgColor = 0xff979797;
        mDelDisableBgColor = 0xff979797;
        mDelDisableFgColor = 0xff979797;

        mMaxCount = 1000;
        mCount = 1;

        mRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12.5f, getResources().getDisplayMetrics());
        mCircleWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, getResources().getDisplayMetrics());
        mLineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2f, getResources().getDisplayMetrics());
        mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14.5f, getResources().getDisplayMetrics());

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (wMode) {
            case MeasureSpec.EXACTLY:
                //match_parent
                break;
            case MeasureSpec.AT_MOST:
                //wrap_content
                int computeSize = (int) (getPaddingLeft() + getPaddingRight() + mRadius * 4 + mCircleWidth * 4 + mGapBetweenCircle);
                wSize = computeSize > wSize ? wSize : computeSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                computeSize = (int) (getPaddingLeft() + getPaddingRight() + mRadius * 4 + mCircleWidth * 4 + mGapBetweenCircle);
                wSize = computeSize;
                break;
        }
        switch (hMode) {
            case MeasureSpec.EXACTLY:
                //
                break;
            case MeasureSpec.AT_MOST:
                //
                int computeSize = (int) (getPaddingTop() + getPaddingBottom() + mRadius * 2 + mCircleWidth * 2);
                hSize = computeSize > hSize ? hSize : computeSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                computeSize = (int) (getPaddingTop() + getPaddingBottom() + mRadius * 2 + mCircleWidth * 2);
                hSize = computeSize;
                break;
        }

        setMeasuredDimension(wSize, hSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mLeft = (int) (getPaddingLeft() + mCircleWidth/2);
        mTop = (int) (getPaddingTop() + mCircleWidth/2);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画
        //减号 背景
        if (mCount > 0) {
            mDelPaint.setColor(mDelEnableBgColor);
        } else {
            mDelPaint.setColor(mDelDisableBgColor);
        }
        mDelPaint.setStrokeWidth(mCircleWidth);

        mDelPath.reset();
        mDelPath.addCircle(mLeft + mRadius, mTop + mRadius, mRadius, Path.Direction.CW);
        mDelRegion.setPath(mDelPath, new Region(mLeft, mTop, mWidth - getPaddingRight(), mHeight - getPaddingBottom()));
        //
        canvas.drawPath(mDelPath, mDelPaint);

        //减号 前景
        if (mCount > 0) {
            mDelPaint.setColor(mDelEnableFgColor);
        } else {
            mDelPaint.setColor(mDelDisableFgColor);
        }
        mDelPaint.setStrokeWidth(mLineWidth);
        canvas.save();
        canvas.translate(mLeft + mRadius, mTop + mRadius);
        canvas.drawLine(-mRadius / 2, 0, +mRadius / 2, 0, mDelPaint);
        //
        canvas.restore();

        //画数字
        canvas.save();
        float textWidth=mDelPaint.measureText(mCount+"")/2;
        canvas.translate(mLeft + mRadius * 2 + mCircleWidth/2+(mGapBetweenCircle/2 - textWidth) ,
                mTop + mRadius - (mFontMetrics.top + mFontMetrics.bottom) / 2);
        canvas.drawText(mCount + "", 0, 0, mTextPaint);
        canvas.restore();
        //右边
        // 背景圆
        if (mCount < mMaxCount) {
            mAddPaint.setColor(mAddEnableBgColor);
        } else {
            mAddPaint.setColor(mAddDisableBgColor);
        }

        mAddPaint.setStrokeWidth(mCircleWidth);
        float left = mLeft + mRadius * 2 +mCircleWidth/2+ mGapBetweenCircle;

        if (isAddFillMode){
            mAddPath.addCircle(left + mRadius + mCircleWidth, mTop + mRadius, mRadius+mCircleWidth/2, Path.Direction.CW);
        }else{
            mAddPath.addCircle(left + mRadius + mCircleWidth, mTop + mRadius, mRadius, Path.Direction.CW);
        }
        mAddRegion.setPath(mAddPath, new Region(mLeft, mTop, mWidth - getPaddingRight(), mHeight - getPaddingBottom()));
        canvas.drawPath(mAddPath, mAddPaint);
        //前景
        canvas.save();
        canvas.translate(left + mCircleWidth + mRadius, mTop + mRadius);
        if (mCount < mMaxCount) {
            mAddPaint.setColor(mAddEnableFgColor);
        } else {
            mAddPaint.setColor(mAddDisableFgColor);
        }
        mAddPaint.setStrokeWidth(mLineWidth);
        canvas.drawLine(-mRadius / 2, 0, mRadius / 2, 0, mAddPaint);
        canvas.drawLine(0, -mRadius / 2, 0, mRadius / 2, mAddPaint);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (mAddRegion.contains((int) event.getX(),(int) event.getY())){

                    onAddClick();
                }else if (mDelRegion.contains((int) event.getX(),(int) event.getY())){

                    onDelClick();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }


        return super.onTouchEvent(event);
    }

    private void onDelClick() {
        if (mCount==1){
            if (null!=clickListener){
                clickListener.onEmety();
            }

        }
        if (mCount>1){
            if (null!=clickListener){
                //返回变化以后的数量，但是mCount此时还没有改变，如果有数据库操作，
                // 网络请求操作，成功后调用increaseCount
                clickListener.onDel(mCount-1);
            }
        }

    }

    private void onAddClick() {
        if (mCount<mMaxCount){
            if (clickListener!=null){
                clickListener.onAdd(mCount+1);//
            }
        }
    }

    public CartNumClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(CartNumClickListener clickListener) {
        this.clickListener = clickListener;
    }


    public int getmCount() {
        return mCount;
    }

    /**
     * 设置数量
     * @param mCount
     */
    public void setmCount(int mCount) {
        this.mCount = mCount;
        invalidate();
    }

    /**
     * 增加数量
     */
    public void increaseCount(){
        if (mCount<mMaxCount){
            mCount++;
            invalidate();
        }
    }

    /**
     *减少数量
     */
    public void reduceCount(){
        if (mCount>1){
            mCount--;
            invalidate();
        }
    }

}
