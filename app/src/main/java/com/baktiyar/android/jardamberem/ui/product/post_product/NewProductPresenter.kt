package com.baktiyar.android.jardamberem.ui.product.post_product


import android.content.Context
import android.util.Log
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.StartApplication
import com.baktiyar.android.jardamberem.model.CategoryPaginated
import com.baktiyar.android.jardamberem.model.PostProduct
import com.baktiyar.android.jardamberem.model.PostUrgentProduct
import com.baktiyar.android.jardamberem.utils.e
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


/**
 * Created by admin on 09.03.2018.
 */
class NewProductPresenter(var context: Context,
                          var mView: NewProductContract.View?) : NewProductContract.Presenter {

    private val noInternetError = StartApplication.INSTANCE.getString(R.string.no_internet)

    override fun getCategory() {
        StartApplication.INSTANCE.service.getCategory().enqueue(object : Callback<CategoryPaginated> {
            override fun onFailure(call: Call<CategoryPaginated>, t: Throwable) {
                mView?.onCategoryError(noInternetError)
            }

            override fun onResponse(call: Call<CategoryPaginated>, response: Response<CategoryPaginated>) {
                if (response.isSuccessful && response.body()?.results?.isNotEmpty()!!) {
                    mView?.onCategorySuccess(response.body()?.results!!)
                } else {
                    mView?.onCategoryError(noInternetError)
                }
            }

        })
    }

    override fun sendUrgentProduct(item: PostUrgentProduct, paths: ArrayList<String>?) {
        mView!!.showProgress()
        val bodyBuilder = MultipartBody.Builder()
        bodyBuilder.setType(MultipartBody.FORM)

        bodyBuilder.addFormDataPart("title", item.title)
        bodyBuilder.addFormDataPart("description", item.description)
        bodyBuilder.addFormDataPart("phoneNumber", item.phoneNumber)
        bodyBuilder.addFormDataPart("userImeiCode", item.userImeiCode)


        if (paths == null) {
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

        StartApplication.INSTANCE.service.sendUrgentProduct(requestBody).enqueue(
                object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        mView!!.onPostProductError(t!!.message.toString())
                        Log.e("_____", t.message)
                        mView!!.hideProgress()
                    }

                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        if (response!!.isSuccessful) {
                            mView!!.onPostProductSuccess()
                        } else {
                            mView!!.onPostProductError(response.message())
                        }
                        mView!!.hideProgress()
                    }
                })
    }

    override fun sendProduct(categoryId: Int, city: Int, item: PostProduct, paths: ArrayList<String>?) {
        mView!!.showProgress()
        val bodyBuilder = MultipartBody.Builder()
        bodyBuilder.setType(MultipartBody.FORM)

        e(city.toString() + " " + item.category.toString())
        bodyBuilder.addFormDataPart("city", city.toString())
        bodyBuilder.addFormDataPart("category", categoryId.toString())
        bodyBuilder.addFormDataPart("isNeeded", item.isNeeded.toString())
        bodyBuilder.addFormDataPart("title", item.title)
        bodyBuilder.addFormDataPart("description", item.description)
        bodyBuilder.addFormDataPart("number", item.number)
        bodyBuilder.addFormDataPart("userImeiCode", item.userImeiCode)


        if (paths == null) {
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

        StartApplication.INSTANCE.service.sendProduct(item.category, requestBody).enqueue(
                object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        mView!!.onPostProductError(t!!.message.toString())
                        mView!!.hideProgress()
                    }

                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        if (response!!.isSuccessful) {
                            mView!!.onPostProductSuccess()
                        } else {
                            mView!!.onPostProductError(response.message())
                        }
                        mView!!.hideProgress()
                    }
                })
    }


}