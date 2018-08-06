package com.baktiyar.android.jardamberem.model

import android.os.Parcel
import android.os.Parcelable

class Urgent (var id: Int, var title: String, var description: String, var date: String, var imgPath: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(date)
        parcel.writeString(imgPath)
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