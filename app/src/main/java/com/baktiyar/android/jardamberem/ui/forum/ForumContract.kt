package com.baktiyar.android.jardamberem.ui.forum

import com.baktiyar.android.jardamberem.model.Forum
import com.baktiyar.android.jardamberem.model.ForumPaginated

interface ForumContract {

    interface View {
        fun onForumSuccess(data: ForumPaginated)
        fun onForumError(message: String)
        fun onSendSuccess()
        fun onDeleteSuccess(message: String, position: Int)
        fun onDeleteError(message: String)
    }
    interface Presenter {
        fun deleteForum(id: Int, position: Int)
        fun getForum(limit: Int, offset: Int)
        fun sendForum(data: Forum)
    }

}