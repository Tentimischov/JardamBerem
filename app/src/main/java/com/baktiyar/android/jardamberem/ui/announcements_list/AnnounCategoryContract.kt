package com.baktiyar.android.jardamberem.ui.announcements_list

import com.baktiyar.android.jardamberem.model.Announcements
import com.baktiyar.android.jardamberem.model.AnnouncementsPaginated

interface AnnounCategoryContract {
    interface View {
        fun onSuccessFirst(data: ArrayList<Announcements>)
      //  fun onSuccessNext(data: Announcements)
        fun onError(message: String)
    }
    interface Presenter {
        fun getDataFirst(limit: Int, offset: Int)
      //  fun getDataNext(limit: Int, offset: Int)
    }
}