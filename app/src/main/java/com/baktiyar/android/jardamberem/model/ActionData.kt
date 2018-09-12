package com.baktiyar.android.jardamberem.model

import android.os.Parcel
import android.os.Parcelable

class ActionData(var id: Int?,
                 var title: String?,
                 var description: String?,
                 var date: String?,
                 var imgPath: String?,
                 var imgPath2: String?,
                 var imgPath3: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(date)
        parcel.writeString(imgPath)
        parcel.writeString(imgPath2)
        parcel.writeString(imgPath3)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ActionData> {
        override fun createFromParcel(parcel: Parcel): ActionData {
            return ActionData(parcel)
        }

        override fun newArray(size: Int): Array<ActionData?> {
            return arrayOfNulls(size)
        }
    }
}