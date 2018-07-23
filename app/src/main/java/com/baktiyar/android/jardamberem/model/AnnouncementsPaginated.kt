package com.baktiyar.android.jardamberem.model

import android.os.Parcel
import android.os.Parcelable


import java.util.ArrayList

class AnnouncementsPaginated : Parcelable {
    var count: Int = 0
    var next: String?
    var previous: String?
    var results: ArrayList<Announcements>


    constructor(count: Int, next: String, previous: String, results: ArrayList<Announcements>) {
        this.count = count
        this.next = next
        this.previous = previous
        this.results = results
    }


    constructor(parcel: Parcel) {
        this.count = parcel.readInt()
        this.next = parcel.readString()
        this.previous = parcel.readString()
        this.results = parcel.readArrayList(null) as ArrayList<Announcements>
    }

    override fun describeContents(): Int {
        return 0
    }

    // Required method to write to Parcel
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(count)
        dest.writeString(next)
        dest.writeString(previous)
        dest.writeList(results)
    }

    companion object {

        // Method to recreate a Question from a Parcel
        var CREATOR: Parcelable.Creator<Announcements> = object : Parcelable.Creator<Announcements> {

            override fun createFromParcel(source: Parcel): Announcements {
                return Announcements(source)
            }

            override fun newArray(size: Int): Array<Announcements?> {
                return arrayOfNulls(size)
            }

        }
    }

}