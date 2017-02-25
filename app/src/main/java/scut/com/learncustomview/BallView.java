package scut.com.learncustomview;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by yany on 2017/2/22.
 */

public class BallView extends View {
    //球的半径
    private float radius = 0;
    //球的x,y方向速度
    private int velocityX = 0,velocityY = 0;
    //球的颜色
    private int color = 0x0000ff;
    //球里面的文字
    private String text;
    //文字字体大小
    private int textSize = 32;
    //文字的颜色
    private int textColor = 0xff0000;
    //画笔
    private Paint paintCircle;
    private Paint paintText;
    private Paint paintFill;
    //文字宽高矩形
    Rect bounds = new Rect();
    //屏幕的宽高
    private int screenWidth,screenHeight;
    //状态栏高度
    private  int sbHeight;
    //标题栏高度
    private int titleBarHeight;
    ViewGroup parent;


    public BallView(Context context) {
        super(context);
        init(context);
    }

    public BallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取自定义属性数组
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BallView, 0, 0);
        int n = a.getIndexCount();
        for (int i = 0;i < n;i++){
            int attr = a.getIndex(i);
            switch (attr){
                case R.styleable.BallView_inVelocityX:
                    velocityX = a.getInt(attr,0);
                    System.out.println("velocityX:"+velocityX);
                    break;
                case R.styleable.BallView_inVelocityY:
                    velocityY = a.getInt(attr,0);
                    System.out.println("velocityY:"+velocityY);
                    break;
                case R.styleable.BallView_color:
                    color = a.getColor(attr,Color.BLUE);
                    System.out.println("color:"+color);
                    break;
                case R.styleable.BallView_Text:
                    text = a.getString(attr);
                    System.out.println("text:"+text);
                    break;
                case R.styleable.BallView_TextColor:
                    textColor = a.getColor(attr,Color.RED);
                    System.out.println("textColor:"+textColor);
                    break;

            }
        }
        init(context);

    }

    public BallView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context){
//        paintCircle = new Paint();
//        paintCircle.setStyle(Paint.Style.STROKE);
//        paintCircle.setStrokeWidth(1);

        paintFill = new Paint();
        paintFill.setStyle(Paint.Style.FILL);
        paintFill.setColor(color);

        paintText = new Paint();
        paintText.setStyle(Paint.Style.FILL_AND_STROKE);
        paintText.setTextSize(textSize);
        paintText.setTextAlign(Paint.Align.CENTER);
        paintText.setColor(textColor);

        paintText.getTextBounds(text,0,text.length(),bounds);

        getStatusBarHeight(context);
        getScreenPixel(context);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int wSpeSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSpeSize = MeasureSpec.getSize(heightMeasureSpec);
        int width = wSpeSize ;
        int height = hSpeSize;

//        parent = (ViewGroup) getParent();
//        screenWidth = parent.getLayoutParams().width;
//        screenHeight = parent.getLayoutParams().height;

        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT){
            //在这里实现计算需要wrap_content时需要的宽高
            width = bounds.width();

        }else if(getLayoutParams().width != ViewGroup.LayoutParams.MATCH_PARENT){
            width = getLayoutParams().width;
        }
        if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT){
            //在这里实现计算需要wrap_content时需要的宽高
            height =bounds.height();
        }else if(getLayoutParams().height != ViewGroup.LayoutParams.MATCH_PARENT){
            height = getLayoutParams().height;
        }
        //计算半径
        radius = Math.max(width,height)/2;

        //传入处理后的宽高
        setMeasuredDimension((int) (radius*2+1), (int) (radius*2+1));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(getWidth()/2,getHeight()/2,radius,paintFill);
        //让字体处于球中间
        canvas.drawText(text,getWidth()/2,getHeight()/2+bounds.height()/2,paintText);
        checkCrashScreen();
        offsetLeftAndRight(velocityX);
        offsetTopAndBottom(velocityY);
        postInvalidateDelayed(10);
    }

    //检测碰撞，有碰撞就反弹
    private void checkCrashScreen(){
        if ((getLeft() <= 0 && velocityX < 0)){
            velocityX = -velocityX ;

        }
        if (getRight() >= screenWidth && velocityX > 0){
            velocityX = -velocityX ;
        }
        if ((getTop() <= 0 && velocityY < 0)) {
            velocityY = -velocityY ;

        }
        if (getBottom() >= screenHeight -sbHeight && velocityY > 0){
            velocityY = -velocityY ;
        }
    }

    //获取屏幕宽高分辨率
    public void getScreenPixel(Context context){
        DisplayMetrics dm ;
        dm = context.getResources().getDisplayMetrics();
        float density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
        int densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
        float xdpi = dm.xdpi;
        float ydpi = dm.ydpi;
        System.out.println("density:"+density+",densityDPI:"+densityDPI);
        System.out.println("screen2dp:"+xdpi+","+ ydpi);
        screenWidth = dm.widthPixels; // 屏幕宽，单位为像素
        screenHeight = dm.heightPixels; // 屏幕高,单位为像素
        System.out.println("screen2:"+screenWidth+","+ screenHeight);

    }

    //获取状态栏高度
    private void getStatusBarHeight(Context context){
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            sbHeight = getResources().getDimensionPixelSize(resourceId);
            System.out.println("sbHeight:"+sbHeight);
        }
    }

    //获取标题栏高度
    private void getTitleBarHeight(Context context){
        Rect frame = new Rect();
        getDrawingRect(frame);
//statusBarHeight是上面所求的状态栏的高度
        titleBarHeight = frame.height() - sbHeight;
        System.out.println("titleBarHeight"+titleBarHeight);
    }
}
