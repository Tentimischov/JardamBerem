package com.baktiyar.android.jardamberem.ui.main.fragment

import com.baktiyar.android.jardamberem.model.AnnouncementsPaginated

interface MainAnnouncementContract {
    interface View {
        fun onAnnouncementSuccess(data: AnnouncementsPaginated)
        fun onAnnouncementError(message: String)
    }
    interface Presenter {
        fun getAnnouncement(limit: Int, offset: Int, isNeeded: Boolean)

    }

}