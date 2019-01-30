package com.baktiyar.android.jardamberem.ui.action

import com.baktiyar.android.jardamberem.ApplicationClass
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.ActionDataPaginated
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActionPresenter(var v: ActionContract.View) : ActionContract.Presenter {
    override fun getActionDataFirst(limit: Int, offset: Int) {
        ApplicationClass.INSTANCE?.service?.getActionData(limit, offset)?.enqueue(object : Callback<ActionDataPaginated> {
            override fun onFailure(call: Call<ActionDataPaginated>?, t: Throwable?) {
                    v.onError(ApplicationClass.INSTANCE?.getString(R.string.fail)!!)
            }

            override fun onResponse(call: Call<ActionDataPaginated>?, response: Response<ActionDataPaginated>?) {
                if (response?.isSuccessful!!) {
                    v.onSuccessFirst(response.body())
                }
            }

        })
    }

    override fun getActionData(limit: Int, offset: Int) {
        ApplicationClass.INSTANCE?.service?.getActionData(limit, offset)?.enqueue(object : Callback<ActionDataPaginated> {
            override fun onFailure(call: Call<ActionDataPaginated>?, t: Throwable?) {
                v.onError(ApplicationClass.INSTANCE?.getString(R.string.fail)!!)
            }

            override fun onResponse(call: Call<ActionDataPaginated>?, response: Response<ActionDataPaginated>?) {
                if (response?.isSuccessful!!) {
                    v.onSuccessNext(response.body())
                }
            }

        })
    }


}