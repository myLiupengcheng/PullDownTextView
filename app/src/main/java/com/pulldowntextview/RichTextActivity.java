package com.pulldowntextview;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 *
 * BackgroundColorSpan,ClickableSpan,ForegroundColorSpan,MaskFilterSpan,MetricAffectingSpan,StrikethroughSpan,SuggestionSpan,UnderlineSpan
 */
public class RichTextActivity extends AppCompatActivity {

    private TextView mTextView, mTextView1, mTextView2;
    private String text = "Spannable测试文字" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rich_text);
        initView();
    }

    /** 初始化View */
    private void initView() {
        mTextView = (TextView) findViewById(R.id.text_view);
        mTextView1 = (TextView) findViewById(R.id.text_view1);
        mTextView2 = (TextView) findViewById(R.id.text_view2);
        html();
    }


    public void html(){
        String html = "测 <br/> 试 <br/> 文 <br/> 字 <br/>" ;
        mTextView.setText(Html.fromHtml(html));

        String html1 = "<h1>标题</h1>" ;
        mTextView1.setText(Html.fromHtml(html1));

        String html2 = "<font color ='#FF0000'>测试文字</font>" ;
        mTextView2.setText(Html.fromHtml(html2));
    }

    /**
     * 图片相关
     * ImageSpan
     */
    public void imageSpan(){
        Spannable spannableImgae = Spannable.Factory.getInstance().newSpannable(text);
        Drawable image = this.getResources().getDrawable(R.mipmap.star);
        image.setBounds(0,0,60,60);

        ImageSpan imageSpan = new ImageSpan(image);
        spannableImgae.setSpan(imageSpan, spannableImgae.length()-2, spannableImgae.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(spannableImgae);

        mTextView1.setVisibility(View.GONE);
        mTextView2.setVisibility(View.GONE);
    }

    /**
     * ClickableSpan : 可点击的文字
     *
     */
    public void clickSpan(){
        Spannable spannable = Spannable.Factory.getInstance().newSpannable(text);
        spannable.setSpan(new ClickableSpan() {
                              @Override
                              public void onClick(View widget) {
                                  Toast.makeText(RichTextActivity.this, "点击测试", Toast.LENGTH_LONG).show();
                              }
                          },
                9, spannable.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());
        mTextView.setText(spannable);


        Spannable spannableUrl = Spannable.Factory.getInstance().newSpannable(text);
        URLSpan urlSpan = new URLSpan("http://blog.csdn.net/lpc_java?viewmode=list");
        spannableUrl.setSpan(urlSpan, 9, spannable.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mTextView1.setText(spannableUrl);
        mTextView1.setMovementMethod(LinkMovementMethod.getInstance());

        mTextView2.setVisibility(View.GONE);
    }

    /***
     * 样式相关：
     * StrikethroughSpan ： 删除线
     * UnderlineSpan ： 下划线
     *
     * StyleSpan ： 一般样式
     */
    public void styleSpan(){
        Spannable spannable = Spannable.Factory.getInstance().newSpannable(text);
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        spannable.setSpan(strikethroughSpan, 0, 9, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mTextView.setText(spannable);

        Spannable underlineSpanAble = Spannable.Factory.getInstance().newSpannable(text);
        UnderlineSpan sizeSpan = new UnderlineSpan();
        underlineSpanAble.setSpan(sizeSpan, 11, spannable.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mTextView1.setText(underlineSpanAble);

        Spannable styleSpan = Spannable.Factory.getInstance().newSpannable(text);
        StyleSpan styleSpan_Bold = new StyleSpan(Typeface.BOLD);
        StyleSpan styleSpan_Italic = new StyleSpan(Typeface.ITALIC);
        styleSpan.setSpan(styleSpan_Bold, 0, 9, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        styleSpan.setSpan(styleSpan_Italic, 11, spannable.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mTextView2.setText(styleSpan);
    }

    /**
     * 大小相关
     * RelativeSizeSpan ：显示大小
     *
     * 位置相关
     * SuperscriptSpan ：上移
     * SubscriptSpan ： 下移
     */
    public void sizeSpan(){
        Spannable spannable = Spannable.Factory.getInstance().newSpannable(text);
        RelativeSizeSpan sizeSpanBig = new RelativeSizeSpan(1.4f);
        RelativeSizeSpan sizeSpanSmall = new RelativeSizeSpan(0.6f);
        spannable.setSpan(sizeSpanSmall, 0, 9, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannable.setSpan(sizeSpanBig, 11, spannable.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mTextView.setText(spannable);

        Spannable SuperscriptSpanAble = Spannable.Factory.getInstance().newSpannable(text);
        SuperscriptSpan sizeSpan = new SuperscriptSpan();
        SuperscriptSpanAble.setSpan(sizeSpan, 12, spannable.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mTextView1.setText(SuperscriptSpanAble);

        Spannable SubscriptSpanAble = Spannable.Factory.getInstance().newSpannable(text);
        SubscriptSpan SubscriptSpan = new SubscriptSpan();
        SubscriptSpanAble.setSpan(SubscriptSpan, 12, spannable.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mTextView2.setText(SubscriptSpanAble);
    }

    /***
     * 颜色相关
     * BackgroundColorSpan ： 背景颜色样式
     * ForegroundColorSpan ： 字体颜色
     */
    public void colorSpan(){
        Spannable spannable = Spannable.Factory.getInstance().newSpannable(text);
        BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(Color.parseColor("#FF0000"));
        spannable.setSpan(backgroundColorSpan, 0, 9, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mTextView.setText(spannable);

        Spannable spannable1 = Spannable.Factory.getInstance().newSpannable(text);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#FF0000"));
        spannable1.setSpan(foregroundColorSpan, 0, 9, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mTextView1.setText(spannable1);
    }


}
