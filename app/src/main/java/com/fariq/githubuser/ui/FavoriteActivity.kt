package com.fariq.githubuser.ui

import android.content.Intent
import android.database.ContentObserver
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.fariq.githubuser.R
import com.fariq.githubuser.adapter.UserFavoriteAdapter
import com.fariq.githubuser.databinding.ActivityFavoriteBinding
import com.fariq.githubuser.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.fariq.githubuser.helper.MappingHelper
import com.fariq.githubuser.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    lateinit var adapter : UserFavoriteAdapter
    private lateinit var binding: ActivityFavoriteBinding
    lateinit var uriWithId : Uri

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = UserFavoriteAdapter()
        adapter.notifyDataSetChanged()
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = adapter

        adapter.setOnItemClickCallback(object : UserFavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(user: User) {
                val intent = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
                intent.putExtra("user", user)
                startActivity(intent)
            }

        })
        adapter.setOnDeleteClickCallback(object : UserFavoriteAdapter.OnDeleteClickCallback {
            override fun onItemClicked(user: User) {
                uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + user.userId)
                contentResolver.delete(uriWithId, null, null)
                Toast.makeText(this@FavoriteActivity, "Berhasil Dihapus", Toast.LENGTH_SHORT).show()
            }

        })

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadNotesAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadNotesAsync()
        } else {
            savedInstanceState.getParcelableArrayList<User>(EXTRA_STATE)?.also { adapter.setData(it) }
        }

    }

    private fun loadNotesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val users = deferredNotes.await()
            if (users.size > 0) {
                adapter.setData(users)
            } else {
                adapter.setData(users)
                Toast.makeText(this@FavoriteActivity, "belum ada user favorit", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
