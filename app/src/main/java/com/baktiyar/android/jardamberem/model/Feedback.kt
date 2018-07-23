package com.baktiyar.android.jardamberem.model

/**
 * Created by admin on 16.03.2018.
 */
class Feedback{
    var reviewText: String? = null
    var email: String? = null

    constructor(reviewText: String?, email: String?) {
        this.reviewText = reviewText
        this.email = email
    }
}