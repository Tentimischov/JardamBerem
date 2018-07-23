package com.baktiyar.android.jardamberem.ui.action

import com.baktiyar.android.jardamberem.ApplicationClass
import com.baktiyar.android.jardamberem.model.ActionData
import com.baktiyar.android.jardamberem.model.ActionDataPaginated
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActionPresenter(var v: ActionContract.View) : ActionContract.Presenter {
    override fun getActionDataFirst(limit: Int, offset: Int) {
        ApplicationClass.INSTANCE?.service?.getActionData(limit, offset)?.enqueue(object : Callback<ArrayList<ActionData>> {
            override fun onFailure(call: Call<ArrayList<ActionData>>?, t: Throwable?) {
                v.onError(t.toString())
            }

            override fun onResponse(call: Call<ArrayList<ActionData>>?, response: Response<ArrayList<ActionData>>?) {
                if (response?.isSuccessful!!) v.onSuccessFirst(response.body())
            }

        })
    }

    /*override fun getActionData(limit: Int, offset: Int) {
        ApplicationClass.INSTANCE?.service?.getActionData(limit, offset)?.enqueue(object : Callback<ArrayList<ActionData>> {
            override fun onFailure(call: Call<ArrayList<ActionData>>?, t: Throwable?) {
                v.onError(t.toString())
            }

            override fun onResponse(call: Call<ArrayList<ActionData>>?, response: Response<ArrayList<ActionData>>?) {
                if (response?.isSuccessful!!) v.onSuccessNext(response.body()!!)
            }

        })
    }*/
}