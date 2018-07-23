package com.baktiyar.android.jardamberem.ui.main

import com.baktiyar.android.jardamberem.model.AllCategory
import com.baktiyar.android.jardamberem.model.Announcements
import com.baktiyar.android.jardamberem.model.AnnouncementsPaginated
import com.baktiyar.android.jardamberem.model.Urgent
import com.baktiyar.android.jardamberem.utils.IProgressBar

interface MainContract {
    interface View: IProgressBar {
        /*
        fun onAnnounFirstSuccess(data: AnnouncementsPaginated)
        fun onAnnounNextSuccess(data: AnnouncementsPaginated)*/

        fun onAnnounFirstSuccesss(data: ArrayList<Announcements>)
      //  fun onAnnounNextSuccesss(data: ArrayList<Announcements>)
        fun onAnnounIsNeeded(data: ArrayList<Announcements>)
        fun onCategorySuccess(data: ArrayList<AllCategory>)
        fun onUrgentSuccess(data: ArrayList<Urgent>)
        fun onError(message: String)
    }
    interface Presenter{
        fun getAnnounFirstIsNeededFalses(limit: Int, offset: Int)
        fun getAnnounNextIsNeededFalses(limit: Int, offset: Int)
        fun getAnnounFirst(limit: Int, offset: Int)
        fun getAnnounNext(limit: Int, offset: Int)
        fun getCategory(id: Int)
        fun getUrgent(limit: Int, offset: Int)
    }
}