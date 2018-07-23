package com.baktiyar.android.jardamberem.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by admin on 06.03.2018.
 */
class Product() : Parcelable {

    var id: Int? = null

    var category: Int? = null

    var title: String? = null

    var description: String? = null

    @SerializedName("number")
    @Expose
    var phoneNumber: String? = null
    var date: String? = null
    @SerializedName("imgPath")
    @Expose
    var firstImage: String? = null
    @SerializedName("imgPath2")
    @Expose
    var secondImage: String? = null
    @SerializedName("imgPath3")
    @Expose
    var thirdImage: String? = null

    var userImeiCode: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        category = parcel.readValue(Int::class.java.classLoader) as? Int
        title = parcel.readString()
        description = parcel.readString()
        phoneNumber = parcel.readString()
        date = parcel.readString()
        firstImage = parcel.readString()
        secondImage = parcel.readString()
        thirdImage = parcel.readString()
        userImeiCode = parcel.readString()
    }

    constructor(
            category: Int?,
            title: String?,
            description: String?,
            phoneNumber: String?,
            userImeiCode: String?,
            firstImage: String?,
            secondImage: String?,
            thirdImage: String?) : this() {
        this.category = category
        this.title = title
        this.description = description
        this.phoneNumber = phoneNumber
        this.userImeiCode = userImeiCode
        this.firstImage = firstImage
        this.secondImage = secondImage
        this.thirdImage = thirdImage
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeValue(category)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(phoneNumber)
        parcel.writeString(date)
        parcel.writeString(firstImage)
        parcel.writeString(secondImage)
        parcel.writeString(thirdImage)
        parcel.writeString(userImeiCode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }


}