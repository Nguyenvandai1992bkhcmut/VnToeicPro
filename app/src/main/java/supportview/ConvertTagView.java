package supportview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.AlignmentSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Convert;

/**
 * Created by dainguyen on 6/16/17.
 */

public class ConvertTagView {
    private Context context;
    private String s;
    private String sRoot;
    private SpannableString spannableString;
    public ConvertTagView(Context context,String sRoot){
        this.context = context;
        this.sRoot = sRoot;
        s =stringNotTag(sRoot);
        spannableString = new SpannableString(s);
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/VietAPk.vn-MyriadPro_SBI.ttf");
        spannableString.setSpan(new CustomTypefaceSpan("",font),0,s.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        divStringTag();
    }

    public void divStringTag(){
        Pattern pattern = Pattern.compile("<[a-z]*>");
        Matcher matcher = pattern.matcher(sRoot);
        Stack<ChildTag>stack = new Stack<>();
        int count = 0;
        while (matcher.find()) {
            String tag = matcher.group(0); // tab
            int index = matcher.start() - count;
            count += tag.length();
            if (stack.empty()) {
                stack.push(new ChildTag(tag, index));
            } else {
                if (tag.equals(stack.peek().tag)) {
                    int k = stack.peek().index;
                    setSpanTag(tag,k,index);
                    stack.pop();
                } else {
                    stack.push(new ChildTag(tag, index));
                }
            }
        }
    }

    public void setSpanTag(String tag, int begin, int end){
        switch (tag){
            case ConstantTag.ACTAG:
                spannableString.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), begin, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case ConstantTag.ALTAG:
                spannableString.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL), begin, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case ConstantTag.ARTAG:
                spannableString.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE), begin, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case ConstantTag.BITAG:
                spannableString.setSpan(new StyleSpan(Typeface.ITALIC),begin,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new StyleSpan(Typeface.BOLD),begin,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case ConstantTag.BTAG:
                spannableString.setSpan(new StyleSpan(Typeface.BOLD),begin,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case ConstantTag.HBITAG:
                spannableString.setSpan(new RelativeSizeSpan(2f),begin,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC),begin,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case ConstantTag.HTAG:
                spannableString.setSpan(new RelativeSizeSpan(2f),begin,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case ConstantTag.HBTAG:
                spannableString.setSpan(new RelativeSizeSpan(2f),begin,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new StyleSpan(Typeface.BOLD),begin,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case ConstantTag.HSITAG:
                Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/fontls.ttf");
                spannableString.setSpan(new CustomTypefaceSpan("",font),begin,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new RelativeSizeSpan(2f),begin,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new StyleSpan(Typeface.BOLD),begin,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case ConstantTag.ITAG:
                spannableString.setSpan(new StyleSpan(Typeface.ITALIC),begin,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case ConstantTag.LITAG:
                spannableString.setSpan(new StyleSpan(Typeface.ITALIC),begin,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new RelativeSizeSpan(1.5f),begin,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case ConstantTag.LTAG:
                spannableString.setSpan(new RelativeSizeSpan(1.f),begin,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case ConstantTag.LBTAG:
                spannableString.setSpan(new RelativeSizeSpan(1.5f),begin,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new StyleSpan(Typeface.BOLD),begin,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case ConstantTag.LBITAG:
                spannableString.setSpan(new RelativeSizeSpan(1.5f),begin,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC),begin,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case ConstantTag.HITAG:
                spannableString.setSpan(new RelativeSizeSpan(2f),begin,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new StyleSpan(Typeface.ITALIC),begin,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case ConstantTag.LSITAG:
                Typeface font1 = Typeface.createFromAsset(context.getAssets(), "fonts/fontls.ttf");
                spannableString.setSpan(new CustomTypefaceSpan("",font1),begin,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new RelativeSizeSpan(2f),begin,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new StyleSpan(Typeface.BOLD),begin,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case ConstantTag.UTAG:
                spannableString.setSpan(new StyleSpan(Typeface.ITALIC),begin,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case ConstantTag.CRITAG:
                spannableString.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), begin, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new StyleSpan(Typeface.ITALIC),begin,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(Color.RED),begin,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            default:

        }
    }

    public String stringNotTag(String s){
        return s.replaceAll("<[a-z]*>","");
    }

    public SpannableString getSpannableString() {
        return spannableString;
    }

    public void setSpannableString(SpannableString spannableString) {
        this.spannableString = spannableString;
    }

    public String getsRoot() {
        return sRoot;
    }

    public void setsRoot(String sRoot) {
        this.sRoot = sRoot;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public class ChildTag{
        public String tag;
        public int index;
        public ChildTag(String tag , int index){
            this.tag = tag;
            this.index = index;
        }
    }

    public class ConstantTag{

        public static  final String UTAG ="<u>";
        public static  final String ACTAG="<ac>";
        public static  final String ALTAG="<al>";

        public static  final String ARTAG="<ar>";

        public static  final String ITAG="<i>";

        public static  final String BTAG="<b>";

        public static  final String BITAG="<bi>";

        public static  final String LITAG="<li>";

        public static  final String LTAG="<l>";

        public static  final String LBTAG="<lb>";

        public static  final String LBITAG="<lbi>";

        public static  final String HITAG="<hi>";

        public static  final String HTAG="<h>";

        public static  final String HBTAG="<hb>";

        public static  final String HBITAG="<hbi>";

        public static  final String LSITAG="<ls>";

        public static  final String HSITAG="<hs>";

        public static  final String CRITAG="<cr>";


    }


    class CustomTypefaceSpan extends TypefaceSpan {

        private final Typeface newType;

        public CustomTypefaceSpan(String family, Typeface type) {
            super(family);
            newType = type;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            applyCustomTypeFace(ds, newType);
        }

        @Override
        public void updateMeasureState(TextPaint paint) {
            applyCustomTypeFace(paint, newType);
        }

        private  void applyCustomTypeFace(Paint paint, Typeface tf) {
            int oldStyle;
            Typeface old = paint.getTypeface();
            if (old == null) {
                oldStyle = 0;
            } else {
                oldStyle = old.getStyle();
            }

            int fake = oldStyle & ~tf.getStyle();
            if ((fake & Typeface.BOLD) != 0) {
                paint.setFakeBoldText(true);
            }

            if ((fake & Typeface.ITALIC) != 0) {
                paint.setTextSkewX(-0.25f);
            }

            paint.setTypeface(tf);
        }
    }
}
