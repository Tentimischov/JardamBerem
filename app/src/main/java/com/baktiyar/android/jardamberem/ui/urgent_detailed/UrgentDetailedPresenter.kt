package com.baktiyar.android.jardamberem.ui.urgent_detailed

import com.baktiyar.android.jardamberem.StartApplication
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UrgentDetailedPresenter(var v: UrgentDetailedContract.View) : UrgentDetailedContract.Presenter {
    override fun deleteUrgent(id: Int) {
        StartApplication.INSTANCE?.service?.deleteUrgent(id)?.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                v.onError(t?.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                if (response?.isSuccessful!!)
                    v.onSuccess(response.message())
            }

        })
    }
}