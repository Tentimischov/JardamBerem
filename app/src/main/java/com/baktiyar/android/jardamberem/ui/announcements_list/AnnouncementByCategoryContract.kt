package com.baktiyar.android.jardamberem.ui.announcements_list

import com.baktiyar.android.jardamberem.model.Announcements
import com.baktiyar.android.jardamberem.model.AnnouncementsPaginated

interface AnnouncementByCategoryContract {
    interface View {
        fun onAnnouncementSuccess(data: AnnouncementsPaginated)
        fun onError(message: String)
    }
    interface Presenter {
        fun getAnnouncementByCategory(limit: Int, offset: Int, isNeeded: Boolean)
    }
}