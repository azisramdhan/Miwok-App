package com.alfatihramadhan.miwok;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alfatihramadhan.miwok.Fragment.ColorsFragment;
import com.alfatihramadhan.miwok.Fragment.FamilyMembersFragment;
import com.alfatihramadhan.miwok.Fragment.NumbersFragment;
import com.alfatihramadhan.miwok.Fragment.PhrasesFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
        TabLayout slidingTab = (TabLayout)findViewById(R.id.tab);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            private final String[] tabTitles = {"Numbers","Family","Phrases","Colors"};
            @NonNull
            @Override
            public Fragment getItem(int position) {
                if(position == 0){
                    return new NumbersFragment();
                }else if(position == 1){
                    return new FamilyMembersFragment();
                }else if(position == 2){
                    return new PhrasesFragment();
                }else{
                    return new ColorsFragment();
                }
            }

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabTitles[position];
            }
        });


        slidingTab.setupWithViewPager(viewPager);
    }
}
