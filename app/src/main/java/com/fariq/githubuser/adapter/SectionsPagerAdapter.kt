package com.fariq.githubuser.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fariq.githubuser.ui.TabsFragment

class SectionsPagerAdapter(activity: AppCompatActivity, username : String) : FragmentStateAdapter(activity) {
    private val mUsername = username
    override fun createFragment(position: Int): Fragment {
        return TabsFragment.newInstance(position, mUsername)
    }

    override fun getItemCount(): Int {
        return 2
    }
}
