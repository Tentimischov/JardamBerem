package com.baktiyar.android.jardamberem.ui.city

import com.baktiyar.android.jardamberem.model.City
import com.baktiyar.android.jardamberem.utils.IProgressBar

interface CityContact {

    interface View : IProgressBar {
        fun onSuccess(data: ArrayList<City>)
        fun onError(error: String)
    }
    interface Presenter {
        fun getCity()
    }

}