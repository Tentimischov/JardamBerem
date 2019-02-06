package com.baktiyar.android.jardamberem.ui.main.fragment

import com.baktiyar.android.jardamberem.model.AnnouncementsPaginated

interface MainAnnouncementContract {
    interface View {
        fun onAnnouncementIsNeededSuccess(data: AnnouncementsPaginated)
        fun onAnnouncementIsNeededFalseSuccess(data: AnnouncementsPaginated)
        fun onAnnouncementError(message: String)
    }
    interface Presenter {
        fun getAnnouncementIsNeeded(limit: Int, offset: Int)
        fun getAnnouncementIsNeededFalse(limit: Int, offset: Int)

    }

}