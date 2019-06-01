package com.bolivarapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.bolivarapp.Fragments.SectionsPagerAdapter;
import com.bolivarapp.Tutorial.TutorialActivity;
import com.bolivarapp.Util.Data;

public class MainActivity extends AppCompatActivity  {

    private static SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        String conditionInit = Data.getDocInit(getFilesDir());

        if(conditionInit.equals("start")) {
            startActivity(new Intent(MainActivity.this, TutorialActivity.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_exit) {
            finish();
            return true;
        }
        if (id == R.id.action_tutorial) {
            startActivity(new Intent(MainActivity.this, TutorialActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
