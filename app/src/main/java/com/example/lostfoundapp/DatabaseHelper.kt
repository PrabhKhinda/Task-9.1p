package com.example.lostfoundapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.Cursor

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    fun removeAdvert(advertId: Int) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(advertId.toString()))
        db.close()
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "LostAdsDB"
        private const val TABLE_NAME = "lost_ads"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TYPE = "type"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_LOCATION = "location"
        private const val COLUMN_PHONE = "phone"
        private const val COLUMN_LATITUDE = "latitude"
        private const val COLUMN_LONGITUDE = "longitude"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_TYPE TEXT, $COLUMN_NAME TEXT, $COLUMN_DESCRIPTION TEXT, $COLUMN_DATE TEXT, $COLUMN_LOCATION TEXT, $COLUMN_PHONE TEXT, $COLUMN_LATITUDE REAL, $COLUMN_LONGITUDE REAL)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertAdvert(type: String, name: String, description: String, date: String, location: String, phone: String, latitude: Double, longitude: Double): Long {
        val values = ContentValues()
        values.put(COLUMN_TYPE, type)
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_DESCRIPTION, description)
        values.put(COLUMN_DATE, date)
        values.put(COLUMN_LOCATION, location)
        values.put(COLUMN_PHONE, phone)
        values.put(COLUMN_LATITUDE, latitude)
        values.put(COLUMN_LONGITUDE, longitude)

        val db = this.writableDatabase
        val id = db.insert(TABLE_NAME, null, values)
        db.close()

        return id
    }


    fun getLostAds(): List<Advert> {
        val adsList = mutableListOf<Advert>()
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_TYPE = 'Lost'"
        val db = this.readableDatabase
        val cursor: Cursor? = db.rawQuery(query, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
                    val type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE))
                    val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                    val description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
                    val date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))
                    val location = cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION))
                    val phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE))
                    val latitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUDE))
                    val longitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE))

                    val advert = Advert(id, type, name, description, date, location, phone, latitude, longitude)
                    adsList.add(advert)
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        db.close()

        return adsList
    }

    fun getFoundAds(): List<Advert> {
        val adsList = mutableListOf<Advert>()
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_TYPE = 'Found'"
        val db = this.readableDatabase
        val cursor: Cursor? = db.rawQuery(query, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
                    val type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE))
                    val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                    val description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
                    val date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))
                    val location = cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION))
                    val phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE))
                    val latitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUDE))
                    val longitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE))

                    val advert = Advert(id, type, name, description, date, location, phone, latitude, longitude)
                    adsList.add(advert)
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        db.close()

        return adsList
    }


}
