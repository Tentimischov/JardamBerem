package com.baktiyar.android.jardamberem.ui.product.post_product


import android.content.Context
import android.util.Log
import okhttp3.ResponseBody
import retrofit2.Call
import com.baktiyar.android.jardamberem.utils.ApiService
import retrofit2.Callback
import retrofit2.Response
import com.baktiyar.android.jardamberem.model.PostProduct
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


/**
 * Created by admin on 09.03.2018.
 */
class NewProductPresenter(var context: Context,
                          var mService: ApiService?,
                          var mView: NewProductContract.View?) : NewProductContract.Presenter {

    override fun sendProduct(categoryId: Int, city: Int, item: PostProduct, paths: ArrayList<String>?) {
        mView!!.showProgress()
        val bodyBuilder = MultipartBody.Builder()
        bodyBuilder.setType(MultipartBody.FORM)

        bodyBuilder.addFormDataPart("city", item.city.toString())
        bodyBuilder.addFormDataPart("category", item.category.toString())
        bodyBuilder.addFormDataPart("isNeeded", item.isNeeded.toString())
        bodyBuilder.addFormDataPart("title", item.title)
        bodyBuilder.addFormDataPart("description", item.description)
        bodyBuilder.addFormDataPart("number", item.number)
        bodyBuilder.addFormDataPart("userImeiCode", item.userImeiCode)


        if (paths==null) {
        } else if (paths.size > 0) {

            var file = File(paths[0])
            bodyBuilder.addFormDataPart("imgPath", file.name,
                    RequestBody.create(MediaType.parse("multipart/form-data"), file))
            if (paths.size > 1) {
                file = File(paths[1])
                bodyBuilder.addFormDataPart("imgPath2", file.name,
                        RequestBody.create(MediaType.parse("multipart/form-data"), file))
            }
            if (paths.size > 2) {
                file = File(paths[2])
                bodyBuilder.addFormDataPart("imgPath3", file.name,
                        RequestBody.create(MediaType.parse("multipart/form-data"), file))
            }
        }

        val requestBody = bodyBuilder.build()

        mService!!.sendProduct(item.city, item.category, requestBody).enqueue(
                object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        mView!!.onFail(t!!.message.toString())
                        Log.e("_____", t.message)
                        mView!!.hideProgress()
                    }

                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        if (response!!.isSuccessful) {
                            mView!!.onSuccess()
                        } else {
                            mView!!.onFail(response.message())
                        }
                        mView!!.hideProgress()
                    }
                })
    }


}