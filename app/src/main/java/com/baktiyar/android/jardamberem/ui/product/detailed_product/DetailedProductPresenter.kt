package com.baktiyar.android.jardamberem.ui.product.detailed_product

import com.baktiyar.android.jardamberem.ApplicationClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by admin on 18.03.2018.
 */
class DetailedProductPresenter(var mView: DetailedProductContract.View) : DetailedProductContract.Presenter {


    override fun deleteAnnoun(id: Int) {
        mView.showProgress()
        ApplicationClass.INSTANCE?.service?.deleteAnnouncement(id)?.enqueue(object : Callback<ResponseBody> {

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                mView.onFail(t!!.message.toString())
                t.printStackTrace()
                mView.hideProgress()
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                if (response!!.isSuccessful) {
                    mView.onSuccess()
                }else{
                    mView.onFail(response.message())
                }
                mView.hideProgress()
            }
        })
    }
}