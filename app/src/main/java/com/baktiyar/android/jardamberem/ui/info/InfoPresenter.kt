package com.baktiyar.android.jardamberem.ui.info

import com.baktiyar.android.jardamberem.StartApplication
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.InfoPaginated
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InfoPresenter(var v: InfoContract.View) : InfoContract.Presenter {
    override fun getInfo(limit: Int?, offset: Int?) {
        StartApplication.INSTANCE.service.getInfo(limit, offset).enqueue(object : Callback<InfoPaginated> {
            override fun onFailure(call: Call<InfoPaginated>?, t: Throwable?) {
                v.onError(StartApplication.INSTANCE?.getString(R.string.fail)!!)
            }
            override fun onResponse(call: Call<InfoPaginated>?, response: Response<InfoPaginated>?) {
               if (response?.isSuccessful!!) v.onSuccessInfo(response.body()!!)
            }

        })
    }

}