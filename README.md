# PullDownTextView


## 1、TextView实现长文本的分段展示。
长文本：这个没什么好解释的，就是比较长的文本。直接显示就OK，但是我们知道Android当中的屏幕尺寸是有限的，我们要在有限的屏幕内合理的显示很多的内容，当然这个是侧滑菜单栏出现的原因。我们要让TextView通过用户的交互来显示合理的内容，比如在用户并不对该文本关系的前提，显示重要的前几行就OK ，如果用户想看文本内容，用户可以通过点击当前的TextView进行显示其与的内容，根据这个简单的需求，我们来对TextView进行定制。

![这里写图片描述](http://img.blog.csdn.net/20170921225255815?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvTFBDX0pBVkE=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

## 2、TextView显示富文本

### 2.1、颜色相关
颜色相关主要分为一个字体的颜色（ForegroundColorSpan），一个背景的颜色（BackgroundColorSpan）。
![这里写图片描述](http://img.blog.csdn.net/20170923110746483?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvTFBDX0pBVkE=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
```java
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
```
### 2.2、大小位置相关
大小方面主要是RelativeSizeSpan，构造时传入一个数值来说明比较当前字体大小的变化，大于0为变大，小于0为变小
位置方面主要是上移（SuperscriptSpan），下移（SubscriptSpan），移动完成以后大小是不会变化的，上移距离为当前文本高度的一半，下一距离也是当前文本的一半。
![这里写图片描述](http://img.blog.csdn.net/20170923113653597?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvTFBDX0pBVkE=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
```java
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
```
### 2.3、常见样式相关
常见样式有下划线，删除线，textStyle（粗体、斜体）。
![这里写图片描述](http://img.blog.csdn.net/20170923184311417?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvTFBDX0pBVkE=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

```java
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
```
### 2.4、跳转相关
常见的跳转相关有，点击事件，超链接。其实超链接的实现就是点击事件，只不过点击以后由当前手机的默认浏览器去打开。
![这里写图片描述](http://img.blog.csdn.net/20170923190341759?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvTFBDX0pBVkE=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

```java
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
```
**注意：mTextView1.setMovementMethod(LinkMovementMethod.getInstance());必须设置TextView的MovementMethod才有点击效果**
### 2.5、图片相关
在文字当中使用图片，其实这个我们可以联想一下社交软件当中的聊天表情。
![这里写图片描述](http://img.blog.csdn.net/20170923192321643?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvTFBDX0pBVkE=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
在这里我们发现，是图片去替代了我们原有的文字，看客们在这里注意一下。
```java
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
```
## 3 、其他方式实现富文本显示
HTML,也可以实现上述的功能，就是把含有HTML标签的语句直接在TextView当中进行显示，
```java
   public void html(){
        String html = "测 <br/> 试 <br/> 文 <br/> 字 <br/>" ;
        mTextView.setText(Html.fromHtml(html));

        String html1 = "<h1>标题</h1>" ;
        mTextView1.setText(Html.fromHtml(html1));

        String html2 = "<font color ='#FF0000'>测试文字</font>" ;
        mTextView2.setText(Html.fromHtml(html2));
    }
```
![这里写图片描述](http://img.blog.csdn.net/20170923195221885?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvTFBDX0pBVkE=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

但是TextView对HTML的支持不是很全，下面就把TextView对HTML的支持列举一下

> a href="...">  定义链接内容
b>  定义粗体文字   b 是blod的缩写
big>  定义大字体的文字
blockquote>  引用块标签 
属性:
Common  -- 一般属性
cite  -- 被引用内容的URI
br>   定义换行
cite>   表示引用的URI
dfn>   定义标签  dfn 是defining instance的缩写
div align="...">
em>  强调标签  em 是emphasis的缩写
font size="..." color="..." face="...">
h1>
h2>
h3>
h4>
h5>
h6>
i>   定义斜体文字
img src="...">
p>     段落标签,里面可以加入文字,列表,表格等
small>  定义小字体的文字
strike>   定义删除线样式的文字   不符合标准网页设计的理念,不赞成使用.   strike是strikethrough的缩写
strong>   重点强调标签
sub>   下标标签   sub 是subscript的缩写
sup>   上标标签   sup 是superscript的缩写
tt>   定义monospaced字体的文字  不赞成使用.  此标签对中文没意义  tt是teletype or monospaced text style的意思
u>   定义带有下划线的文字  u是underlined text style的意思
