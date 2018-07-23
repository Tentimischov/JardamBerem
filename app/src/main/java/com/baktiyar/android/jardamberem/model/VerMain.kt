package com.baktiyar.android.jardamberem.model

import android.os.Parcel
import android.os.Parcelable

class VerMain(var title: String, var desc: String, var im: Int): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(desc)
        parcel.writeInt(im)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VerMain> {
        override fun createFromParcel(parcel: Parcel): VerMain {
            return VerMain(parcel)
        }

        override fun newArray(size: Int): Array<VerMain?> {
            return arrayOfNulls(size)
        }
    }
}