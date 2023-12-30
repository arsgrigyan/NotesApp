package com.southernsunrise.notesapp.utils.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabsViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private var fragmentList: List<Fragment> = listOf()

    fun submitFragmentList(fragmentList: List<Fragment>) {
        this.fragmentList = fragmentList
    }


    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}