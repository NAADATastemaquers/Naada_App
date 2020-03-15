package com.example.naada.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.naada.R;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    private LinearLayout mDotLayout;

    private TextView[] mDots;
    private Button nextButton;
    private int mCurrentPage;
    private TextView skipbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        //Check if application is opened for First time
        SharedPreferences preferences=getSharedPreferences
                ("PREFERENCES",MODE_PRIVATE);
        String FirstTime=preferences.getString
                ("FirstTimeInstall","");

        if(FirstTime.equals("Yes")){

            //If  application was opened for the second Time
            Intent intent=new Intent(IntroActivity.this,LoginActivity.class);
            startActivity(intent);
        }else{
            //Else
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("FirstTimeInstall","Yes");
            editor.apply();
        }

        mDotLayout=findViewById(R.id.dotsLayout);
        nextButton=findViewById(R.id.nextBtn);
        skipbutton=findViewById(R.id.skipBtn);

        //fill list screen
        List<ScreenItem> mList=new ArrayList<>();

        mList.add(new ScreenItem("Follow some of the worlds\nnsharp and saavy music and\nnartist explorers",R.drawable.img1));
        mList.add(new ScreenItem("Discover Fresh, New and\nHot Tunes at very\nmoment of your life",R.drawable.img2));
        mList.add(new ScreenItem("Get recognized and\n rewarded for being\nhottest tastemaqer in town",R.drawable.img3));

        //setup viewpager
        screenPager=findViewById(R.id.screen_viewpager);
        introViewPagerAdapter=new IntroViewPagerAdapter(this,mList);
        screenPager.setAdapter(introViewPagerAdapter);

        addDotsIndicator(0);
        screenPager.addOnPageChangeListener(viewListener);

        //OnClickListeners
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenPager.setCurrentItem(mCurrentPage+1);
            }
        });

        skipbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(IntroActivity.this, LoginActivity.class);
                startActivity(mainActivity);
                finish();
            }
        });


    }

    public void addDotsIndicator(int position) {
        mDots=new TextView[3];
        mDotLayout.removeAllViews();
        for(int i=0;i<mDots.length;i++){
            mDots[i]=new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
            mDotLayout.addView(mDots[i]);
        }
        if(mDots.length>0){
            mDots[position].setTextColor(getResources().getColor(R.color.button_text));
        }
    }

    ViewPager.OnPageChangeListener viewListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onPageSelected(final int i) {
            addDotsIndicator(i);
            mCurrentPage=i;
            if(i>=0 && i<2){
                nextButton.setEnabled(true);
                nextButton.setText("Next");
            }else{
                if(i==mDots.length-1){
                    nextButton.setText("Get started");
                    nextButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent mainActivity = new Intent(IntroActivity.this, LoginActivity.class);
                            startActivity(mainActivity);
                            finish();
                        }
                    });
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
