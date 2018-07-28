package com.baktiyar.android.jardamberem.ui.info

import com.baktiyar.android.jardamberem.model.Info
import com.baktiyar.android.jardamberem.model.InfoPaginated

interface InfoContract {

    interface View {
        fun onSuccessFirst(data: InfoPaginated)
        fun onSuccessNext(data: InfoPaginated)
        fun onError(message: String)
    }
    interface Presenter {
        fun getInfoFirst(limit: Int?, offset: Int?)
        fun getInfoNext(limit: Int?, offset: Int?)
    }
}