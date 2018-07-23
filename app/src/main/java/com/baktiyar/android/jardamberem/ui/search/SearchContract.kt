package com.baktiyar.android.jardamberem.ui.search

import com.baktiyar.android.jardamberem.model.Announcements

interface SearchContract {
    interface View {
        fun onSuccess(data: ArrayList<Announcements>)
        fun onError(message: String?)
    }

    interface Presenter {
        fun getSearch(cityId: Int?, categoryId: Int?, cities: Int?, categories: Int?, isNeeded: Int?, title: String?)
    }

}