package com.baktiyar.android.jardamberem.ui.info

import com.baktiyar.android.jardamberem.ApplicationClass
import com.baktiyar.android.jardamberem.model.Info
import com.baktiyar.android.jardamberem.model.InfoPaginated
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InfoPresenter(var v: InfoContract.View) : InfoContract.Presenter {
 /*   override fun getInfoNext(limit: Int?, offset: Int?) {
        ApplicationClass.INSTANCE?.service?.getInfo(limit, offset)?.enqueue(object : Callback<InfoPaginated> {
            override fun onFailure(call: Call<InfoPaginated>?, t: Throwable?) {
                v.onError(t?.message.toString())
            }

            override fun onResponse(call: Call<InfoPaginated>?, response: Response<InfoPaginated>?) {
                v.onSuccessNext(response?.body()!!)
            }

        })
    }*/

    override fun getInfoFirst(limit: Int?, offset: Int?) {
        ApplicationClass.INSTANCE?.service?.getInfo(limit, offset)?.enqueue(object : Callback<ArrayList<Info>> {
            override fun onFailure(call: Call<ArrayList<Info>>?, t: Throwable?) {
                v.onError(t?.message.toString())
            }

            override fun onResponse(call: Call<ArrayList<Info>>?, response: Response<ArrayList<Info>>?) {
               if (response?.isSuccessful!!) v.onSuccessFirst(response.body()!!)
            }

        })
    }

}