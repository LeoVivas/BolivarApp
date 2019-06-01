package com.bolivarapp.Tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bolivarapp.MainActivity;
import com.bolivarapp.R;
import com.bolivarapp.Util.Data;

public class TutorialActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;

    private SliderAdapter sliderAdapter;
    private TextView[] mDots;
    private Button btn_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayouts);
        btn_finish = (Button) findViewById(R.id.btn_finish);
        btn_finish.setVisibility(View.INVISIBLE);
        sliderAdapter = new SliderAdapter(this);

        mSlideViewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        btn_finish.setOnTouchListener(new View.OnTouchListener() {

            public boolean isOnView(float evX, int vWidth, float evY, int vHeight){
                return (evX > vWidth || evY > vHeight || evX < 0 || evY < 0);
            }

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (isOnView(event.getX(), v.getWidth(), event.getY(), v.getHeight()))
                        btn_finish.setBackgroundResource(android.R.color.transparent);
                    else
                        btn_finish.setBackgroundResource(android.R.drawable.screen_background_dark_transparent);
                }

                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    btn_finish.setBackgroundResource(android.R.drawable.screen_background_dark_transparent);

                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(!isOnView(event.getX(), v.getWidth(), event.getY(), v.getHeight()))
                        finishTutorial();
                    btn_finish.setBackgroundResource(android.R.color.transparent);
                }

                return false;
            }
        });
    }

    public void finishTutorial(){
        Data.makeXMLinit("started",getFilesDir());
        startActivity(new Intent(TutorialActivity.this, MainActivity.class));
        finish();
    }

    public void addDotsIndicator(int position){
        mDots = new TextView[5];

        for(int i = 0; i < mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
            mDotLayout.addView(mDots[i]);
        }

        if(mDots.length > 0)
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
    }

    public void setColorDotSelected(int position){
        for(int i = 0; i < mDots.length; i++) {
            if(i==position)
                mDots[i].setTextColor(getResources().getColor(R.color.colorWhite));
            else
                mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
        }
    }


    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {}

        @Override
        public void onPageSelected(int i) {
            setColorDotSelected(i);
            if(i == 4)
                btn_finish.setVisibility(View.VISIBLE);
            else
                btn_finish.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onPageScrollStateChanged(int i) {}
    };

}
