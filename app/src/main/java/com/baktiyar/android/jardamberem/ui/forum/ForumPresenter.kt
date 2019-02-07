package com.baktiyar.android.jardamberem.ui.forum

import com.baktiyar.android.jardamberem.StartApplication
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Forum
import com.baktiyar.android.jardamberem.model.ForumPaginated
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForumPresenter(var v: ForumContract.View) : ForumContract.Presenter {
    private val errorMessage: String = StartApplication.INSTANCE.getString(R.string.no_internet)
    override fun deleteForum(id: Int, position: Int) {
        StartApplication.INSTANCE.service.deleteForum(id).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                v.onDeleteError(StartApplication.INSTANCE?.getString(R.string.fail)!!)
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                if (response?.isSuccessful!!)
                    v.onDeleteSuccess(response.message(), position)
            }

        })
    }

    override fun getForum(limit: Int, offset: Int) {
        StartApplication.INSTANCE.service.getForum(limit, offset)
                .enqueue(object : Callback<ForumPaginated> {
                    override fun onFailure(call: Call<ForumPaginated>?, t: Throwable?) {
                        v.onForumError(errorMessage)
                    }

                    override fun onResponse(call: Call<ForumPaginated>?, response: Response<ForumPaginated>?) {
                        if (response?.isSuccessful!!) {
                            v.onForumSuccess(response.body()!!)
                        }
                    }

                })
    }

    override fun sendForum(data: Forum) {
        StartApplication.INSTANCE.service.sendForum(data).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                v.onForumError(errorMessage)
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                v.onSendSuccess()
            }

        })
    }
}