package com.baktiyar.android.jardamberem.ui.urgent_detailed

interface UrgentDetailedContract {
    interface View {
        fun onSuccess(message: String)
        fun onError(message: String)
    }
    interface Presenter {
        fun deleteUrgent(id: Int)
    }
}