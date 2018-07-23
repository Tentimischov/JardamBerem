package com.baktiyar.android.jardamberem.ui.feedback

import com.baktiyar.android.jardamberem.model.Feedback
import com.baktiyar.android.jardamberem.model.Forum
import com.baktiyar.android.jardamberem.utils.IProgressBar

/**
 * Created by admin on 16.03.2018.
 */
class FeedbackContract{
    interface View:IProgressBar{
        fun onSuccess()
        fun onFail(message: String)
        fun onSuccessForum()
    }
    interface Presenter{
        fun sendFeedback(item: Feedback)
        fun sendForum(data: Forum)
    }
}