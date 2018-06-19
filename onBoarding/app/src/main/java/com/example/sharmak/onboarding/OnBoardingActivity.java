package com.example.sharmak.onboarding;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import com.gc.materialdesign.views.ButtonFlat;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

public class OnBoardingActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private SmartTabLayout mIndicator;
    private ButtonFlat mSkip;
    private ButtonFlat mNext;
    private FragmentStatePagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        mViewPager = findViewById(R.id.viewpager);
        mIndicator = findViewById(R.id.viewpagertab);
        mSkip = findViewById(R.id.btn_skip);
        mNext = findViewById(R.id.btn_next);

        adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new OnboardingFragment1();
                    case 1:
                        return new OnboardingFragment2();
                    case 2:
                        return new OnboardingFragment3();
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };

        mViewPager.setAdapter(adapter);
        mIndicator.setViewPager(mViewPager);

        setOnClickListeners();
        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //Todo change buttons here
                if(position == 2) {
                    mSkip.setVisibility(View.GONE);
                    mNext.setText("Done");
                } else {
                    mSkip.setVisibility(View.VISIBLE);
                    mNext.setText("Next");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setOnClickListeners() {
        mSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishOnboarding();
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mViewPager.getCurrentItem() == 2) {
                    //The last screen
                    finishOnboarding();
                } else {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1, true);
                }
            }
        });
    }

    private void finishOnboarding() {
        SharedPreferences preferences = getSharedPreferences("onBoarding", MODE_PRIVATE);
        preferences.edit().putBoolean("onboarding_complete", true).apply();
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
        finish();
    }
}
