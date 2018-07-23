package com.baktiyar.android.jardamberem.ui.forum

import com.baktiyar.android.jardamberem.ApplicationClass
import com.baktiyar.android.jardamberem.model.Forum
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForumPresenter(var v: ForumContract.View): ForumContract.Presenter {
    /*override fun getForumNext(limit: Int, offset: Int) {
        ApplicationClass.INSTANCE?.service?.getForum()?.enqueue(object : Callback<ArrayList<Forum>> {
            override fun onFailure(call: Call<ArrayList<Forum>>?, t: Throwable?) {
                v.onError(t?.message!!)
            }

            override fun onResponse(call: Call<ArrayList<Forum>>?, response: Response<ArrayList<Forum>>?) {
                if (response?.isSuccessful!!) {
                  //  v.onForumNextSuccess(response.body()!!)
                }
            }

        })
    }*/

    override fun getForumFirst(limit: Int, offset: Int) {
        ApplicationClass.INSTANCE?.service?.getForum()?.enqueue(object : Callback<ArrayList<Forum>> {
            override fun onFailure(call: Call<ArrayList<Forum>>?, t: Throwable?) {
                v.onError(t?.message!!)
            }

            override fun onResponse(call: Call<ArrayList<Forum>>?, response: Response<ArrayList<Forum>>?) {
                if (response?.isSuccessful!!)
                    v.onForumFirstSuccess(response.body()!!)
                else
                    v.onError(response.message())
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