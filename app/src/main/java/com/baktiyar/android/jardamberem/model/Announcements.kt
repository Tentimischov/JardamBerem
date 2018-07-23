package com.baktiyar.android.jardamberem.model

import android.os.Parcel
import android.os.Parcelable

class Announcements(var id: Int?, var city: Int?,
                    var category: Int?, var isNeeded: Int?,
                    var title: String?, var description: String?,
                    var number: String?, var userImeiCode: String?,
                    var imgPath: String?,
                    var imgPath_height: String?, var imgPath_width: String?,
                    var imgPath2: String?,
                    var imgPath_height2: String?, var imgPath_width2: String?,
                    var imgPath3: String?,
                    var imgPath_height3: String?, var imgPath_width3: String?,
                    var date: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
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
        parcel.writeInt(id!!)
        parcel.writeInt(city!!)
        parcel.writeInt(category!!)
        parcel.writeInt(isNeeded!!)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(number)
        parcel.writeString(userImeiCode)
        parcel.writeString(imgPath)
        parcel.writeString(imgPath_height)
        parcel.writeString(imgPath_height2)
        parcel.writeString(imgPath_height3)
        parcel.writeString(imgPath_width)
        parcel.writeString(imgPath_width2)
        parcel.writeString(imgPath_width3)
        parcel.writeString(imgPath2)
        parcel.writeString(imgPath3)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Announcements> {
        override fun createFromParcel(parcel: Parcel): Announcements {
            return Announcements(parcel)
        }

        override fun newArray(size: Int): Array<Announcements?> {
            return arrayOfNulls(size)
        }
    }
}
