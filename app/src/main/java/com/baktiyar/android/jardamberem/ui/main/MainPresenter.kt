package com.baktiyar.android.jardamberem.ui.main

import com.baktiyar.android.jardamberem.StartApplication
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(var v: MainContract.View) : MainContract.Presenter {

    override fun getCategory(id: Int) {
        StartApplication.INSTANCE.service.getCategory().enqueue(object : Callback<CategoryPaginated> {
            override fun onFailure(call: Call<CategoryPaginated>?, t: Throwable?) {
                v.onError(StartApplication.INSTANCE!!.getString(R.string.fail))
            }

            override fun onResponse(call: Call<CategoryPaginated>?, response: Response<CategoryPaginated>?) {
                if (response?.isSuccessful!!)
                    v.onCategorySuccess(response.body()!!.results)
            }

        })
    }

    override fun getUrgent(limit: Int, offset: Int) {
        StartApplication.INSTANCE.service.getUrgent(limit, offset).enqueue(object : Callback<UrgentPaginated> {
            override fun onFailure(call: Call<UrgentPaginated>?, t: Throwable?) {
                v.onError(StartApplication.INSTANCE!!.getString(R.string.fail))
            }

            override fun onResponse(call: Call<UrgentPaginated>?, response: Response<UrgentPaginated>?) {
                if (response?.isSuccessful!!)
                    v.onUrgentSuccess(response.body()!!.results)
            }
        })
    }
}