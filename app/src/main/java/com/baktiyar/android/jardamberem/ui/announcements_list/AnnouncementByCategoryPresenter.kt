package com.baktiyar.android.jardamberem.ui.announcements_list

import com.baktiyar.android.jardamberem.StartApplication
import com.baktiyar.android.jardamberem.model.AnnouncementsPaginated
import com.baktiyar.android.jardamberem.utils.Settings
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnnouncementByCategoryPresenter(var v: AnnouncementByCategoryContract.View) : AnnouncementByCategoryContract.Presenter {
    override fun getAnnouncementByCategory(limit: Int, offset: Int, isNeeded: Boolean) {
        StartApplication.INSTANCE.service.getAnnouncementByCategory(Settings.getCategoryId(),
                limit,
                offset,
                isNeeded,
                Settings.getCityId()).enqueue(object : Callback<AnnouncementsPaginated> {
            override fun onFailure(call: Call<AnnouncementsPaginated>?, t: Throwable?) {
                v.onError(t?.message!!)
            }

            override fun onResponse(call: Call<AnnouncementsPaginated>?, response: Response<AnnouncementsPaginated>?) {
                if (response?.isSuccessful!!) v.onAnnouncementSuccess(response.body()!!)
                else v.onError(response.message())
            }

        })

    }
}