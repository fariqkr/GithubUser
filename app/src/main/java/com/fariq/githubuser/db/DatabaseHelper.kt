package com.fariq.githubuser.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.fariq.githubuser.db.DatabaseContract.UserColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_NAME = "dbnoteapp"

        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_NOTE = "CREATE TABLE $TABLE_NAME" +
                " (${DatabaseContract.UserColumns._ID} INTEGER PRIMARY KEY," +
                " ${DatabaseContract.UserColumns.AVATAR} TEXT NOT NULL," +
                " ${DatabaseContract.UserColumns.USERNAME} TEXT NOT NULL," +
                " ${DatabaseContract.UserColumns.NAME} TEXT NOT NULL," +
                " ${DatabaseContract.UserColumns.COMPANY} TEXT NOT NULL," +
                " ${DatabaseContract.UserColumns.LOCATION} TEXT NOT NULL," +
                " ${DatabaseContract.UserColumns.FOLLOWERS} INTEGER NOT NULL," +
                " ${DatabaseContract.UserColumns.FOLLOWING} INTEGER NOT NULL," +
                " ${DatabaseContract.UserColumns.REPOSITORY} INTEGER NOT NULL)"

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_NOTE)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}
