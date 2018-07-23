package com.baktiyar.android.jardamberem.ui.announcements_list

import android.content.Context
import android.util.Log
import com.baktiyar.android.jardamberem.ApplicationClass
import com.baktiyar.android.jardamberem.model.Announcements
import com.baktiyar.android.jardamberem.model.AnnouncementsPaginated
import com.baktiyar.android.jardamberem.utils.Settings
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnnounCategoryPresenter(var v: AnnounCategoryContract.View, var context: Context) : AnnounCategoryContract.Presenter {
    override fun getDataFirst(limit: Int, offset: Int) {
        Log.e("__________", Settings.getCityId(context).toString())
        ApplicationClass.INSTANCE?.service?.getAnnounByCategory(Settings.getCityId(context), Settings.getCategoryId(context), limit, offset)?.enqueue(object : Callback<ArrayList<Announcements>> {
            override fun onFailure(call: Call<ArrayList<Announcements>>?, t: Throwable?) {
                v.onError(t?.message!!)
            }

            override fun onResponse(call: Call<ArrayList<Announcements>>?, response: Response<ArrayList<Announcements>>?) {
                if (response?.isSuccessful!!) v.onSuccessFirst(response.body()!!)
                else v.onError(response.message())
            }

        })

    }

   /* override fun getDataNext(limit: Int, offset: Int) {
        ApplicationClass.INSTANCE?.service?.getAnnounByCategory(Settings.getCityId(context), Settings.getCategoryId(context), limit, offset)?.enqueue(object : Callback<Announcements> {
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