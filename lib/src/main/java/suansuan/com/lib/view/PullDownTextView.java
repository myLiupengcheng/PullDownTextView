package suansuan.com.lib.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import suansuan.com.lib.R;

/**
 * 用于显示长文本，可以展开的TextView
 * Created by pengchengliu on 2017/9/18.
 */
public class PullDownTextView extends LinearLayout implements View.OnClickListener{

    /** 位置大小相关属性 */
    private int mTextViewPullHeight ;   //textView显示全部也就是下拉状态的高度
    private int mTextViewNotPullHeight ;

    private boolean isPull  ;           //是否处于展开状态。默认为隐藏
    private boolean isReLayout ;        //当前布局是否重新绘制。
    private boolean isAnimator ;        //是否处于动画当中
    private boolean isMaxHeightMeasure;     //是否进行TextView最大行数的测量
    private boolean isMinHeightMeasure;     //是否进行TexView可见行数的测量

    private TextView mTextView ;
    private ImageButton mImageButton;

    /** ImageButton切换的两种图片 */
    private Drawable mPullDownDrawable ;
    private Drawable mUpDownDrawable ;

    private int mTextVisibilityCount = 3;  //隐藏时 TextView可以显示的最大的行数
    private int mAnimatorDuration = 400 ;

    /** TextView展开回调 */
    private OnTextViewPullListener mOnTextViewPullListener ;

    public PullDownTextView(Context context) {
        this(context, null);
    }
    public PullDownTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }
    public PullDownTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /** 初始化PullDownTextView */
    private void initPullDownTextView() {
        mPullDownDrawable = getDrawable(R.drawable.ic_expand_small_holo_light);
        mUpDownDrawable = getDrawable(R.drawable.ic_collapse_small_holo_light);
        setOrientation(LinearLayout.VERTICAL);
        //默认隐藏
        setVisibility(View.GONE);
    }

    /** 当加载完XML布局时回调 */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initPullDownTextView();
        mTextView = (TextView) this.getChildAt(0);
        mImageButton = (ImageButton) this.getChildAt(1);
        mImageButton.setOnClickListener(this);
        mImageButton.setImageDrawable(isPull ? mUpDownDrawable : mPullDownDrawable);
    }

    /** 测量方发，测量自己的宽高，测量孩子的宽高 */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //没有内容的时候，
        if(!isReLayout || getVisibility() == View.GONE){
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return ;
        }

        //有内容，但是内容比较短的时候，正常显示TextView，但是相应的隐藏ImageButton
        if(mTextView.getLineCount() <= mTextVisibilityCount){
            mTextView.setVisibility(View.VISIBLE);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return ;
        }

        //有内容，并且显示的内容比较长的时候，这里我们显示TextView、ImageButton。
        mImageButton.setVisibility(View.VISIBLE);
        if(!isMaxHeightMeasure && mTextViewPullHeight == 0){
            mTextView.setMaxLines(Integer.MAX_VALUE);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            mTextViewPullHeight = mTextView.getMeasuredHeight() ;
            isMaxHeightMeasure = true ;
        }

        if(!isMinHeightMeasure && mTextViewNotPullHeight == 0){
            mTextView.setMaxLines(mTextVisibilityCount);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            mTextViewNotPullHeight = mTextView.getMeasuredHeight();
            isMinHeightMeasure = true ;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /** 设置文字字体的方法 */
    public void setText(@Nullable CharSequence text) {
        isReLayout = true;
        mTextView.setText(text);
        setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
    }

    /** 设置动画时常的方法 */
    public void setDuration(int duration){
        this.mAnimatorDuration = duration ;
    }

    /** 设置TextView */
    public void setTextVisibilityCount(int visibilityCount){
        this.mTextVisibilityCount = visibilityCount ;
    }

    public void setOnTextViewPullListener(OnTextViewPullListener listener){
        this.mOnTextViewPullListener = listener ;
    }

    @Override
    public void setOrientation(int orientation) {
        if(orientation == LinearLayout.HORIZONTAL){
            throw new IllegalArgumentException("参数错误：当前控件，不支持水平");
        }
        super.setOrientation(orientation);
    }

    /**
     * 通过图片的ID获取图片的Drawable、
     * @param id ： 图片的Id
     * @return ：获取到的图片
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Drawable getDrawable(int id){
        Drawable drawable ;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            drawable = getContext().getResources().getDrawable(id, getContext().getTheme()) ;
        } else {
            drawable = getContext().getResources().getDrawable(id) ;
        }
        return drawable ;
    }

    /**
     * 获取TextView的高度。TextViewHeight = TextHeight + TextViewPadding ；
     * @return TextView的高度
     */
    private int getTextViewHeight(){
        int textHeight = mTextView.getLayout().getLineTop(mTextView.getLineCount());
        int padding = mTextView.getCompoundPaddingTop() + mTextView.getCompoundPaddingBottom() ;
        return textHeight + padding ;
    }

    /**
     * imageButton的点击事件
     * @param v 被点击的View
     */
    @Override
    public void onClick(View v) {
        if(isAnimator){
            return ;
        }
        if(isPull){
            startAnimator(mTextView, mTextViewPullHeight, mTextViewNotPullHeight);
        } else {
            startAnimator(mTextView, mTextViewNotPullHeight, mTextViewPullHeight);
        }
        //下拉，或者上拉的时候的回调
        if(this.mOnTextViewPullListener != null){
            this.mOnTextViewPullListener.textViewPull(mTextView, isPull);
        }
        isPull = !isPull ;
        mImageButton.setImageDrawable(isPull ? mUpDownDrawable : mPullDownDrawable);
    }

    /**
     * 开始动画
     */
    private void startAnimator(final TextView view, int startHeight, int endHeight){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(startHeight , endHeight ).setDuration(mAnimatorDuration);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}
            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimator = false ;
            }
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = animatedValue ;
                //这句，让TextView文本的高度随TextView高度进行变化
                view.setMaxHeight(animatedValue);
                view.setLayoutParams(params);
            }
        });
        isAnimator = true ;
        valueAnimator.start();
    }

    /** TextView展开回调 */
    public interface OnTextViewPullListener{
        void textViewPull(TextView textView, boolean isPull) ;
    }

}