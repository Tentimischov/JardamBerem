package com.baktiyar.android.jardamberem.ui.search

import com.baktiyar.android.jardamberem.StartApplication
import com.baktiyar.android.jardamberem.model.AnnouncementsPaginated
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchPresenter(var v: SearchContract.View): SearchContract.Presenter {
    override fun getSearch(searchItem: String) {
        StartApplication.INSTANCE.service.getSearch(searchItem).enqueue(object : Callback<AnnouncementsPaginated> {
            override fun onFailure(call: Call<AnnouncementsPaginated>?, t: Throwable?) {
                v.onError(t?.message)
            }

            override fun onResponse(call: Call<AnnouncementsPaginated>?, response: Response<AnnouncementsPaginated>?) {
               if (response?.isSuccessful!!) {
                   v.onSuccess(response.body()!!.results)
               } else {
                   v.onError(response.message())
               }
            }

        })
    }

}