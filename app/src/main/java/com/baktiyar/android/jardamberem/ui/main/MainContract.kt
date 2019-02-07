package com.baktiyar.android.jardamberem.ui.main

import com.baktiyar.android.jardamberem.model.AllCategory
import com.baktiyar.android.jardamberem.model.Announcements
import com.baktiyar.android.jardamberem.model.AnnouncementsPaginated
import com.baktiyar.android.jardamberem.model.Urgent
import com.baktiyar.android.jardamberem.utils.IProgressBar

interface MainContract {
    interface View: IProgressBar {
        fun onCategorySuccess(data: ArrayList<AllCategory>)
        fun onUrgentSuccess(data: ArrayList<Urgent>)
        fun onError(message: String)
    }
    interface Presenter{
        fun getCategory(id: Int)
        fun getUrgent(limit: Int, offset: Int)
    }
}