package com.baktiyar.android.jardamberem.ui.forum

import com.baktiyar.android.jardamberem.ApplicationClass
import com.baktiyar.android.jardamberem.model.Forum
import com.baktiyar.android.jardamberem.model.ForumPaginated
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForumPresenter(var v: ForumContract.View) : ForumContract.Presenter {
    override fun deleteForum(id: Int, position: Int) {
        ApplicationClass.INSTANCE?.service?.deletForum(id)?.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                v.onDeleteError(t?.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                if (response?.isSuccessful!!)
                    v.onDeleteSuccess(response.message(), position)
            }

        })
    }

    override fun getForumFirst(limit: Int, offset: Int) {
        ApplicationClass.INSTANCE?.service?.getForum(limit, offset)
                ?.enqueue(object : Callback<ForumPaginated> {
                    override fun onFailure(call: Call<ForumPaginated>?, t: Throwable?) {
                        v.onError(t?.message!!)
                    }

                    override fun onResponse(call: Call<ForumPaginated>?, response: Response<ForumPaginated>?) {
                        if (response?.isSuccessful!!) {
                            v.onForumFirstSuccess(response.body()!!)
                        }
                    }

                })

    }

    override fun getForumNext(limit: Int, offset: Int) {
        ApplicationClass.INSTANCE?.service?.getForum(limit, offset)
                ?.enqueue(object : Callback<ForumPaginated> {
                    override fun onFailure(call: Call<ForumPaginated>?, t: Throwable?) {
                        v.onError(t?.message!!)
                    }

                    override fun onResponse(call: Call<ForumPaginated>?, response: Response<ForumPaginated>?) {
                        if (response?.isSuccessful!!) {
                            v.onForumNextSuccess(response.body()!!)
                        }
                    }

                })
    }

    override fun sendFroum(data: Forum) {
        ApplicationClass.INSTANCE?.service?.sendForum(data)?.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                v.onError(t?.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                v.onSendSuccess()
            }

        })
    }
}