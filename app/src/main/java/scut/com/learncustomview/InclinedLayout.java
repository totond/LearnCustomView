package scut.com.learncustomview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yany on 2017/2/25.
 */

public class InclinedLayout extends ViewGroup {
    public InclinedLayout(Context context) {
        super(context);
    }

    public InclinedLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InclinedLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);


        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int cCount = getChildCount();
        int width = 0;
        int height = 0;
        //处理WRAP_CONTENT情况，把所有子View的宽高加起来作为自己的宽高
        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT){
            for (int i = 0; i < cCount; i++){
                View childView = getChildAt(i);
                width += childView.getMeasuredWidth();
            }
        }else {
            width = sizeWidth;
        }
        if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT){
            for (int i = 0; i < cCount; i++){
                View childView = getChildAt(i);
                height += childView.getMeasuredHeight();
            }
        }else {
            height =sizeHeight;
        }
        //传入处理后的宽高
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int cCount = getChildCount();
        int sPointX = 0;
        int sPointY = 0;
        int cWidth = 0;
        int cHeight = 0;
        //遍历子View，根据它们的宽高定位
        for (int i = 0; i < cCount; i++){
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            //定位
            childView.layout(sPointX,sPointY,sPointX + cWidth,sPointY + cHeight);
            sPointX += cWidth;
            sPointY += cHeight;
        }
    }
}
