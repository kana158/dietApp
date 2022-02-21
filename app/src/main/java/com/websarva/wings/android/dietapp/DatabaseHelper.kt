package com.websarva.wings.android.dietapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context, databaseName: String, factory: SQLiteDatabase.CursorFactory?, version: Int):
    SQLiteOpenHelper(
    context,
    databaseName,
    factory,
    version) {
    override fun onCreate(database: SQLiteDatabase?){
        database?.execSQL("create table if not exists DietTable(id Integer primary key AUTOINCREMENT,weight float,bmi float,fat float,moisture float,muscle float,bone float,meta float,visceral float)")
    }
    override fun onUpgrade(database: SQLiteDatabase?,oldVersion: Int,newVersion: Int){
        if(oldVersion < newVersion){
            database?.execSQL("alter table DietTable add column deleteFlag integer default 0")
        }
    }

}