package com.android.searchingimages.presentation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int) = when(position) {
        0 -> ListFragment()
        1 -> FavoriteFragment()
        else -> throw IllegalStateException("invalid position ${position}")
    }


}