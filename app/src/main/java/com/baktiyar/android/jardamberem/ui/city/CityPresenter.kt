package com.baktiyar.android.jardamberem.ui.city

import com.baktiyar.android.jardamberem.ApplicationClass
import com.baktiyar.android.jardamberem.model.City
import com.baktiyar.android.jardamberem.model.CityPaginated
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CityPresenter(var v: CityContact.View) : CityContact.Presenter {

    override fun getCity() {
        v.showProgress()
        ApplicationClass.INSTANCE?.service?.getCities()?.enqueue(object : Callback<ArrayList<City>> {
            override fun onFailure(call: Call<ArrayList<City>>?, t: Throwable?) {
                v.onError(t.toString())
                v.hideProgress()
            }

            override fun onResponse(call: Call<ArrayList<City>>?, response: Response<ArrayList<City>>) {
                if (response.isSuccessful) {
                    v.onSuccess(response.body()!!)
                } else {
                    v.onError(response.errorBody().toString())

                }
                v.hideProgress()
            }
        })
    }
}