package com.vntoeic.bkteam.vntoeicpro.Utils;

import android.graphics.Typeface;
import android.text.Layout;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.StyleSpan;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by giang on 5/30/17.
 */

public class Tag {

    private SpannableString span=null;
    private String s;

    public static final String ACTAG ="<ac>";
    public static final String ALTAG = "<al>";
    public static final String ARTAG = "<ar>";

    public static final String ITAG = "<i>";
    public static final String BTAG = "<b>";
    public static final String BITAG = "<bi>";

    public static final String HITAG = "<hi>";
    public static final String HTAG = "<h>";
    public static final String HBTAG = "<hb>";
    public static final String HBITAG = "<hbi>";

    public static final String LITAG = "<li>";
    public static final String LTAG = "<l>";
    public static final String LBTAG = "<lb>";
    public static final String LBITAG = "<lbi>";

    public static final String LSTAG = "<ls>";
    public static final String HSTAG = "<ar>";

    public Tag(String s ){
        s = "<end>" + s + "<end>";
        span = new SpannableString(s);
        this.s = s;
    }


    public void parse() {
        String newTag;
        int start =0;
        Stack<String> stringStack =new Stack<>();
        Pattern pattern = Pattern.compile("<[a-z]*>");
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) stringStack.push(matcher.group(0));

        while (matcher.find()){
            newTag = matcher.group(0);
            if (newTag.equals(stringStack.peek())) {
                stringStack.pop();
                formatStr(start, matcher.start(), newTag);
            } else {
                stringStack.push(newTag);
            }
            start=matcher.end();
        }
    }
    public SpannableString getSpan() {

        Pattern pattern = Pattern.compile("<[a-z]*>");
        Matcher matcher = pattern.matcher(s);
        SpannableString newSpan = new SpannableString("");
        int lastTag = 0;
        while (matcher.find()) {
            newSpan = new SpannableString(
                    TextUtils.concat(
                            newSpan,
                            span.subSequence(lastTag, matcher.start())
                    )
            );
            lastTag = matcher.end();
        }
        span = newSpan;
        return span;
    }

    private void formatStr(int start, int end, String tag) {
        SpannableString span1 = new SpannableString(span.subSequence(0, start));
        SpannableString span2 = new SpannableString(span.subSequence(start, end));
        SpannableString span3 = new SpannableString(span.subSequence(end, span.length()));
        switch (tag) {
            case ITAG :
                span2 = addI(span2, tag);
                break;
            case BTAG :
                span2 = addB(span2, tag);
                break;
            case BITAG :
                span2 = addBI(span2, tag);
                break;

            case HBTAG :
                span2 = addHB(span2, tag);
                break;
            case HITAG :
                span2 = addHI(span2, tag);
                break;
            case HTAG :
                span2 = addH(span2, tag);
                break;
            case HBITAG :
                span2 = addH(span2, tag);
                break;

            case LBTAG :
                span2 = addHB(span2, tag);
                break;
            case LITAG :
                span2 = addHI(span2, tag);
                break;
            case LTAG :
                span2 = addH(span2, tag);
                break;
            case LBITAG :
                span2 = addH(span2, tag);
                break;
            default: span2 = adddefault(span2, tag);
        }

        span = new SpannableString(TextUtils.concat(span1, span2, span3));

    }

    private SpannableString adddefault(SpannableString span1, String type){
        if(type.equals(ACTAG)){
            span1.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),0,span1.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        }else if(type.equals(ALTAG)){
            span1.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),0,span1.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        }else if(type.equals(ARTAG)){
            span1.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),0,span1.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return span1;
    }

    private SpannableString addI(SpannableString span1, String type){
        span1.setSpan(new StyleSpan(Typeface.ITALIC),0,span1.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span1;
    }

    private SpannableString addB(SpannableString span1, String type){
        span1.setSpan(new StyleSpan(Typeface.BOLD),0,span1.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        return span1;
    }

    private SpannableString addBI(SpannableString span1 , String type){
        span1.setSpan(new StyleSpan(Typeface.ITALIC),0,span1.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        span1.setSpan(new StyleSpan(Typeface.BOLD),0,span1.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        return span1;
    }



    private SpannableString addHB(SpannableString span1 , String type){
        span1.setSpan(new AbsoluteSizeSpan(60),0,span1.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        span1.setSpan(new StyleSpan(Typeface.BOLD),0,span1.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        return span1;
    }

    private SpannableString addHI(SpannableString span1 , String type){
        span1.setSpan(new AbsoluteSizeSpan(60),0,span1.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        span1.setSpan(new StyleSpan(Typeface.ITALIC),0,span1.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        return span1;
    }

    private SpannableString addH(SpannableString span1 , String type){
        span1.setSpan(new AbsoluteSizeSpan(60),0,span1.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        return span1;
    }

    private SpannableString addHBI(SpannableString span1 , String type){
        span1.setSpan(new AbsoluteSizeSpan(60),0,span1.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        span1.setSpan(new StyleSpan(Typeface.BOLD),0,span1.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        span1.setSpan(new StyleSpan(Typeface.ITALIC),0,span1.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        return span1;
    }



    private SpannableString addLB(SpannableString span1 , String type){
        span1.setSpan(new AbsoluteSizeSpan(40),0,span1.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        span1.setSpan(new StyleSpan(Typeface.BOLD),0,span1.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        return span1;
    }

    private SpannableString addLI(SpannableString span1, String type){
        span1.setSpan(new AbsoluteSizeSpan(40),0,span1.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        span1.setSpan(new StyleSpan(Typeface.ITALIC),0,span1.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        return span1;
    }

    private SpannableString addL(SpannableString span1 , String type){
        span1.setSpan(new AbsoluteSizeSpan(40),0,span1.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        return span1;
    }

    private SpannableString addLBI(SpannableString span1 , String type){
        span1.setSpan(new AbsoluteSizeSpan(40),0,span1.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        span1.setSpan(new StyleSpan(Typeface.BOLD),0,span1.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        span1.setSpan(new StyleSpan(Typeface.ITALIC),0,span1.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        return span1;
    }
}
