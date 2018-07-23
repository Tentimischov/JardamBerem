package com.baktiyar.android.jardamberem.ui.search

import com.baktiyar.android.jardamberem.ApplicationClass
import com.baktiyar.android.jardamberem.model.Announcements
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchPresenter(var v: SearchContract.View): SearchContract.Presenter {
    override fun getSearch(cityId: Int?, categoryId: Int?, cities: Int?, categories: Int?, isNeeded: Int?, title: String?) {
        ApplicationClass.INSTANCE?.service?.getSearch(cityId!!, categoryId!!, cities!!, categories!!, "json", isNeeded!!, title!!)?.enqueue(object : Callback<ArrayList<Announcements>> {
            override fun onFailure(call: Call<ArrayList<Announcements>>?, t: Throwable?) {
                v.onError(t?.message)
            }

            override fun onResponse(call: Call<ArrayList<Announcements>>?, response: Response<ArrayList<Announcements>>?) {
               if (response?.isSuccessful!!) {
                   v.onSuccess(response.body()!!)
               } else {
                   v.onError(response.message())
               }
            }

        })
    }

}