package com.baktiyar.android.jardamberem.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by admin on 16.03.2018.
 */
class PostProduct(var city: Int, var category: Int,
                  var isNeeded: Boolean, var title: String,
                  var description: String, var number: String,
                  var userImeiCode: String,
                  var imgPath: String?,
                  var imgPath2: String?,
                  var imgPath3: String?)