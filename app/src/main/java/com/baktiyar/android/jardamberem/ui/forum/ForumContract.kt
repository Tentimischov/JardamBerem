package com.baktiyar.android.jardamberem.ui.forum

import com.baktiyar.android.jardamberem.model.Forum
import com.baktiyar.android.jardamberem.model.ForumPaginated

interface ForumContract {

    interface View {
        fun onForumFirstSuccess(data: ArrayList<Forum>)
       // fun onForumNextSuccess(data: ForumPaginated)
        fun onError(message: String)
        fun onSendSuccess()
    }
    interface Presenter {
        fun getForumFirst(limit: Int, offset: Int)
      //  fun getForumNext(limit: Int, offset: Int)
        fun sendFroum(data: Forum)
    }

}