package com.fariq.githubuser.ui

import android.annotation.SuppressLint
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fariq.githubuser.R
import com.fariq.githubuser.adapter.SectionsPagerAdapter
import com.fariq.githubuser.databinding.ActivityDetailUserBinding
import com.fariq.githubuser.db.DatabaseContract.UserColumns.Companion.AVATAR
import com.fariq.githubuser.db.DatabaseContract.UserColumns.Companion.COMPANY
import com.fariq.githubuser.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.fariq.githubuser.db.DatabaseContract.UserColumns.Companion.FOLLOWERS
import com.fariq.githubuser.db.DatabaseContract.UserColumns.Companion.FOLLOWING
import com.fariq.githubuser.db.DatabaseContract.UserColumns.Companion.LOCATION
import com.fariq.githubuser.db.DatabaseContract.UserColumns.Companion.NAME
import com.fariq.githubuser.db.DatabaseContract.UserColumns.Companion.REPOSITORY
import com.fariq.githubuser.db.DatabaseContract.UserColumns.Companion.USERNAME
import com.fariq.githubuser.model.User
import com.fariq.githubuser.viewModel.DetailUserViewModel

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var user : User
    private lateinit var detailUserViewModel: DetailUserViewModel

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.fabFavorite.setOnClickListener(this)

        user = intent.getParcelableExtra<User>("user") as User
        detailUserViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailUserViewModel::class.java)
        detailUserViewModel.setUserDetail(user.username!!)
        showLoading(true)
        detailUserViewModel.getUserDetail().observe(this, { userDetail ->
            user = userDetail
            showLoading(false)
            Glide.with(applicationContext)
                    .load(user.avatar)
                    .apply(RequestOptions().override(125, 125))
                    .into(binding.avatar)
            binding.username.text = "${user.username}"
            binding.followers.text = "${user.followers} " + resources.getString(R.string.followers)
            binding.following.text = "${user.following} " + resources.getString(R.string.following)
            binding.repository.text = "${user.repository} " + resources.getString(R.string.repository)
            if (user.company.equals("null")) user.company = "-"
            binding.company.text = "${user.company}"
            if (user.location.equals("null")) user.location = "-"
            binding.location.text = "${user.location}"

        })

        val sectionsPagerAdapter = SectionsPagerAdapter(this, user.username!!)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.title = resources.getString(R.string.user_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onClick(v: View) {
        if (v.id == R.id.fab_favorite){
            val values = ContentValues()
            values.put(AVATAR, user.avatar)
            values.put(USERNAME, user.username)
            values.put(NAME, user.name)
            values.put(COMPANY, user.company)
            values.put(LOCATION, user.location)
            values.put(FOLLOWING, user.following)
            values.put(FOLLOWERS, user.followers)
            values.put(REPOSITORY, user.repository)

            contentResolver.insert(CONTENT_URI, values)
            Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show()

        }
    }


}