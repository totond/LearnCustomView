package scut.com.learncustomview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yany on 2017/2/24.
 */

public class TestMeasureView extends View {
    private Paint paint;
    public TestMeasureView(Context context) {
        super(context);
    }

    public TestMeasureView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public TestMeasureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLUE);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int hSpeSize = MeasureSpec.getSize(heightMeasureSpec);
        int hSpeMode = MeasureSpec.getMode(heightMeasureSpec);
        int wSpeSize = MeasureSpec.getSize(widthMeasureSpec);
        int wSpeMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = wSpeSize;
        int height = hSpeSize;
        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT){
            //在这里实现计算需要wrap_content时需要的宽高
            width =200;
        }
        if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT){
            //在这里实现计算需要wrap_content时需要的宽高
            height =200;
        }
//
//        if (wSpeMode == MeasureSpec.AT_MOST){
//            //在这里实现计算需要wrap_content时需要的宽度，这里我直接当作赋值处理了
//            width =200;
//        }
//        if (hSpeMode == MeasureSpec.AT_MOST){
//            //在这里实现计算需要wrap_content时需要的高度，这里我直接当作赋值处理了
//            height = 200;
//        }
        //传入处理后的宽高
        setMeasuredDimension(width,height);
    }


}
