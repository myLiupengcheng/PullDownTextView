package com.pulldowntextview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;

import suansuan.com.lib.view.PullDownTextView;

public class LongTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_text);

        PullDownTextView text1 = (PullDownTextView)findViewById(R.id.expand_text_view);
        text1.setText(getString(R.string.long_text1));

        PullDownTextView text2 = (PullDownTextView)findViewById(R.id.expand1_text_view);
        text2.setText(getString(R.string.long_text1));
        String string = "dasdassad" ;

        Spannable spannable = Spannable.Factory.getInstance().newSpannable(string);

    }
}

