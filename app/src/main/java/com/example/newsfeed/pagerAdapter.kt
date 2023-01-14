package com.example.newsfeed

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class pagerAdapter(fm:FragmentManager):FragmentPagerAdapter(fm) {
    private var fragmentArray = ArrayList<Fragment>()
    private var titleArray = ArrayList<String>()
    override fun getCount(): Int {
        return fragmentArray.size

    }

    override fun getItem(position: Int): Fragment {
        return fragmentArray[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleArray[position]
    }
    fun add(fragment: Fragment,title:String){
        fragmentArray.add(fragment)
        titleArray.add(title)
    }

}