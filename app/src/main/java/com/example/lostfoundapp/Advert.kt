package com.example.lostfoundapp

import android.os.Parcel
import android.os.Parcelable


data class Advert(
    val id: Long,
    val name: String,
    val type: String,
    val description: String,
    val date: String,
    val location: String,
    val phone: String,
    val latitude: Double,
    val longitude: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readDouble()

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeString(description)
        parcel.writeString(date)
        parcel.writeString(location)
        parcel.writeString(phone)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Advert> {
        override fun createFromParcel(parcel: Parcel): Advert {
            return Advert(parcel)
        }

        override fun newArray(size: Int): Array<Advert?> {
            return arrayOfNulls(size)
        }
    }
}
