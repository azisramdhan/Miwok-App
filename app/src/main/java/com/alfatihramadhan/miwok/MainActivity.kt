package com.alfatihramadhan.miwok

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.alfatihramadhan.miwok.fragment.ColorsFragment
import com.alfatihramadhan.miwok.fragment.FamilyMembersFragment
import com.alfatihramadhan.miwok.fragment.NumbersFragment
import com.alfatihramadhan.miwok.fragment.PhrasesFragment
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewPager = findViewById<View>(R.id.viewpager) as ViewPager
        val slidingTab = findViewById<View>(R.id.tab) as TabLayout
        viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            private val tabTitles = arrayOf("Numbers", "Family", "Phrases", "Colors")
            override fun getItem(position: Int): Fragment {
                return if (position == 0) {
                    NumbersFragment()
                } else if (position == 1) {
                    FamilyMembersFragment()
                } else if (position == 2) {
                    PhrasesFragment()
                } else {
                    ColorsFragment()
                }
            }

            override fun getCount(): Int {
                return 4
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return tabTitles[position]
            }
        }
        slidingTab.setupWithViewPager(viewPager)
    }
}