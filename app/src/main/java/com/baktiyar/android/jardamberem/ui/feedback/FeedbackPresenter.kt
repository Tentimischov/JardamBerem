package com.baktiyar.android.jardamberem.ui.feedback

import android.content.Context
import com.baktiyar.android.jardamberem.StartApplication
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Feedback
import com.baktiyar.android.jardamberem.model.Forum
import com.baktiyar.android.jardamberem.utils.ForumService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by admin on 16.03.2018.
 */

class FeedbackPresenter(var context: Context, var service: ForumService, var mView: FeedbackContract.View) : FeedbackContract.Presenter {
    override fun sendForum(data: Forum) {
        StartApplication.INSTANCE?.service?.sendForum(data)?.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                mView.onFail(StartApplication.INSTANCE?.getString(R.string.fail_send)!!)
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                if (response?.isSuccessful!!) {
                    mView.onSuccessForum()
                } else {
                    mView.onFail(StartApplication.INSTANCE?.getString(R.string.fail)!!)
                }
            }

        })
    }


    override fun sendFeedback(item: Feedback) {
        mView.showProgress()
        service.sendFeedback(item).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                mView.onFail(StartApplication.INSTANCE?.getString(R.string.fail_send)!!)
                mView.hideProgress()
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                if (response!!.isSuccessful) {
                    mView.onSuccess()
                }else{
                    mView.onFail(StartApplication.INSTANCE?.getString(R.string.fail)!!)
                }
                mView.hideProgress()
            }
        })
    }
}