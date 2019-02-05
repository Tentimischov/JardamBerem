package com.baktiyar.android.jardamberem.ui.announcements_list

import android.content.Context
import android.util.Log
import com.baktiyar.android.jardamberem.StartApplication
import com.baktiyar.android.jardamberem.model.AnnouncementsPaginated
import com.baktiyar.android.jardamberem.utils.Settings
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnnounCategoryPresenter(var v: AnnounCategoryContract.View, var context: Context) : AnnounCategoryContract.Presenter {
    override fun getDataFirst(limit: Int, offset: Int) {
        Log.e("__________", Settings.getCityId(context).toString())
        StartApplication.INSTANCE?.service?.getAnnounByCategory(Settings.getCategoryId(context), limit, offset, Settings.getSpinnerItemPosition(context) != 0)?.enqueue(object : Callback<AnnouncementsPaginated> {
            override fun onFailure(call: Call<AnnouncementsPaginated>?, t: Throwable?) {
                v.onError(t?.message!!)
            }

            override fun onResponse(call: Call<AnnouncementsPaginated>?, response: Response<AnnouncementsPaginated>?) {
                if (response?.isSuccessful!!) v.onSuccessFirst(response.body()!!.results)
                else v.onError(response.message())
            }

        })

    }

   /* override fun getDataNext(limit: Int, offset: Int) {
        StartApplication.INSTANCE?.service?.getAnnounByCategory(Settings.getCityId(context), Settings.getCategoryId(context), limit, offset)?.enqueue(object : Callback<Announcements> {
            override fun onFailure(call: Call<Announcements>?, t: Throwable?) {
                v.onError(t?.message!!)
            }

            override fun onResponse(call: Call<Announcements>?, response: Response<Announcements>?) {
                if (response?.isSuccessful!!) v.onSuccessNext(response.body()!!)
                else v.onError(response.message())
            }

        })
    }*/
}