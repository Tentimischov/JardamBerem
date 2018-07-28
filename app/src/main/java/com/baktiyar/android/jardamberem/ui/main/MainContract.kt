package com.baktiyar.android.jardamberem.ui.main

import com.baktiyar.android.jardamberem.model.AllCategory
import com.baktiyar.android.jardamberem.model.Announcements
import com.baktiyar.android.jardamberem.model.AnnouncementsPaginated
import com.baktiyar.android.jardamberem.model.Urgent
import com.baktiyar.android.jardamberem.utils.IProgressBar

interface MainContract {
    interface View: IProgressBar {
        fun onAnnounFirstSuccess(data: AnnouncementsPaginated)
        fun onAnnounNextSuccess(data: AnnouncementsPaginated)


       // fun onAnnounIsNeeded(data: ArrayList<Announcements>)
        fun onCategorySuccess(data: ArrayList<AllCategory>)
        fun onUrgentSuccess(data: ArrayList<Urgent>)
        fun onError(message: String)
    }
    interface Presenter{
        fun getAnnounFirstIsNeededFalse(limit: Int, offset: Int)
        fun getAnnounNextIsNeededFalse(limit: Int, offset: Int)
        fun getAnnounFirst(limit: Int, offset: Int)
        fun getAnnounNext(limit: Int, offset: Int)
        fun getCategory(id: Int)
        fun getUrgent(limit: Int, offset: Int)
    }
}