package com.baktiyar.android.jardamberem.model

import android.os.Parcel
import android.os.Parcelable

class Urgent (var id: Int,
              var title: String,
              var description: String,
              var phoneNumber: String,
              var userImeiCode: String,
              var date: String,
              var imgPath: String?,
              var imgPath2: String?,
              var imgPath3: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(phoneNumber)
        parcel.writeString(userImeiCode)
        parcel.writeString(date)
        parcel.writeString(imgPath)
        parcel.writeString(imgPath2)
        parcel.writeString(imgPath3)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Urgent> {
        override fun createFromParcel(parcel: Parcel): Urgent {
            return Urgent(parcel)
        }

        override fun newArray(size: Int): Array<Urgent?> {
            return arrayOfNulls(size)
        }
    }
}