package com.baktiyar.android.jardamberem.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class AnnounP implements Parcelable {
    public int count;
    public String next;
    public String previous;
    public ArrayList<Announcements> results;


    /**
     * Constructs a Question from values
     */
    public AnnounP (int count, String next, String previous, ArrayList<Announcements> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    /**
     * Constructs a Question from a Parcel
     * @param parcel Source Parcel
     */
    public AnnounP (Parcel parcel) {
        this.count = parcel.readInt();
        this.next = parcel.readString();
        this.previous = parcel.readString();
        this.results = parcel.readArrayList(null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Required method to write to Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(count);
        dest.writeString(next);
        dest.writeString(previous);
        dest.writeList(results);
    }

    // Method to recreate a Question from a Parcel
    public static Creator<Announcements> CREATOR = new Creator<Announcements>() {

        @Override
        public Announcements createFromParcel(Parcel source) {
            return new Announcements(source);
        }

        @Override
        public Announcements[] newArray(int size) {
            return new Announcements[size];
        }

    };
}