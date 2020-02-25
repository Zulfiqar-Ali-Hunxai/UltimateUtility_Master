package com.ahmedonics.apps.ultimateutilitymaster.activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ahmedonics.apps.ultimateutilitymaster.R;
import com.ahmedonics.apps.ultimateutilitymaster.fragments.AboutPageAboutFragment;
import com.ahmedonics.apps.ultimateutilitymaster.fragments.AboutPagePrivacyFragment;
import com.ahmedonics.apps.ultimateutilitymaster.fragments.AboutPageTermsFragment;
import com.google.android.material.tabs.TabLayout;

public class AboutActivity extends BaseActivity {

    private Handler handler;
    private ProgressDialog dialog;

    ViewPager simpleViewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setTitle("About Ultimate Utility Manager");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("appSettings", 0);
        boolean check = pref.getBoolean("keep_screen_on_", false);
        if (check) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        simpleViewPager = (ViewPager) findViewById(R.id.simpleViewPager);
        tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);

        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("About Us");
        tabLayout.addTab(firstTab);

        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("Privacy Policy");
        tabLayout.addTab(secondTab);

        TabLayout.Tab thirdTab = tabLayout.newTab();
        thirdTab.setText("Terms & Conditions");
        tabLayout.addTab(thirdTab);

        AboutPageAdapter adapter = new AboutPageAdapter(
                getSupportFragmentManager(), tabLayout.getTabCount()
        );
        simpleViewPager.setAdapter(adapter);

        simpleViewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tabLayout)
        );

        tabLayout.addOnTabSelectedListener(onTabSelectedListener(simpleViewPager));

    }

    private TabLayout.OnTabSelectedListener onTabSelectedListener(final ViewPager pager) {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}

class AboutPageAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public AboutPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                AboutPageAboutFragment tab1 = new AboutPageAboutFragment();
                return tab1;
            case 1:
                AboutPagePrivacyFragment tab2 = new AboutPagePrivacyFragment();
                return tab2;
            case 2:
                AboutPageTermsFragment tab3 = new AboutPageTermsFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
