package com.baktiyar.android.jardamberem.ui.all_urgents

interface AllUrgentContract {
    interface View {
        fun onSuccess()
        fun onError()
    }
    interface Presenter {
        fun getAllUrgents()
    }
}