package com.baktiyar.android.jardamberem.ui.info

import com.baktiyar.android.jardamberem.model.Info
import com.baktiyar.android.jardamberem.model.InfoPaginated

interface InfoContract {

    interface View {
        fun onSuccessInfo(data: InfoPaginated)
        fun onError(message: String)
    }
    interface Presenter {
        fun getInfo(limit: Int?, offset: Int?)
    }
}