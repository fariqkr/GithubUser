package com.fariq.githubuser.helper

import android.database.Cursor
import com.fariq.githubuser.db.DatabaseContract
import com.fariq.githubuser.model.User

object MappingHelper {

    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<User> {
        val userList = ArrayList<User>()

        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns._ID))
                val avatar = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.AVATAR))
                val username = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.USERNAME))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.NAME))
                val company = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.COMPANY))
                val location = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.LOCATION))
                val followers = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.FOLLOWERS))
                val following = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.FOLLOWING))
                val repository = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.REPOSITORY))

                userList.add(User(id, avatar, username, name, company, location, followers, following, repository))
            }
        }
        return userList
    }

    fun mapCursorToObject(notesCursor: Cursor?): User {
        var user = User()
        notesCursor?.apply {
            moveToFirst()
            val id = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns._ID))
            val avatar = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.AVATAR))
            val username = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.USERNAME))
            val name = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.NAME))
            val company = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.COMPANY))
            val location = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.LOCATION))
            val followers = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.FOLLOWERS))
            val following = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.FOLLOWING))
            val repository = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.REPOSITORY))

            user = User(id,avatar, username, name, company, location, followers, following, repository)
        }
        return user
    }
}