package com.baktiyar.android.jardamberem.ui.forum

import com.baktiyar.android.jardamberem.model.Forum
import com.baktiyar.android.jardamberem.model.ForumPaginated

interface ForumContract {

    interface View {
        fun onForumFirstSuccess(data: ForumPaginated)
        fun onForumNextSuccess(data: ForumPaginated)
        fun onError(message: String)
        fun onSendSuccess()
        fun onDeleteSuccess(message: String, position: Int)
        fun onDeleteError(message: String)
    }
    interface Presenter {
        fun deleteForum(id: Int, position: Int)
        fun getForumFirst(limit: Int, offset: Int)
        fun getForumNext(limit: Int, offset: Int)
        fun sendFroum(data: Forum)
    }

}