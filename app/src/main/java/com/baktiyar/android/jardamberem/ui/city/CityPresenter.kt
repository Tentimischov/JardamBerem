package com.baktiyar.android.jardamberem.ui.city

import com.baktiyar.android.jardamberem.StartApplication
import com.baktiyar.android.jardamberem.model.CityPaginated
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CityPresenter(var v: CityContact.View) : CityContact.Presenter {

    override fun getCity() {
        v.showProgress()
        StartApplication.INSTANCE?.service?.getCities()?.enqueue(object : Callback<CityPaginated> {
            override fun onFailure(call: Call<CityPaginated>?, t: Throwable?) {
                v.onError(t.toString())
                v.hideProgress()
            }

            override fun onResponse(call: Call<CityPaginated>?, response: Response<CityPaginated>) {
                if (response.isSuccessful) {
                    v.onSuccess(response.body()!!.results)
                } else {
                    v.onError(response.errorBody().toString())

                }
                v.hideProgress()
            }
        })
    }
}