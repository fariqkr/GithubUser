package com.fariq.githubuser.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns._ID
import com.fariq.githubuser.db.DatabaseContract.UserColumns.Companion.TABLE_NAME
import com.fariq.githubuser.db.DatabaseContract.UserColumns.Companion.USERNAME

class UserHelper(context: Context) {

    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: UserHelper? = null

        fun getInstance(context: Context): UserHelper =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: UserHelper(context)
                }
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (dataBaseHelper.writableDatabase.isOpen)
            dataBaseHelper.writableDatabase.close()
    }

    fun queryAll(): Cursor {
        return dataBaseHelper.writableDatabase.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                USERNAME,
                null,
                "$_ID ASC",
                null)
    }

    fun queryById(id: String): Cursor {
        return dataBaseHelper.writableDatabase.query(DATABASE_TABLE, null, "$_ID = ?", arrayOf(id), null, null, null, null)
    }

    fun insert(values: ContentValues?): Long {
        return dataBaseHelper.writableDatabase.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return dataBaseHelper.writableDatabase.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    fun deleteById(id: String): Int {
        return dataBaseHelper.writableDatabase.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }
}