package com.baktiyar.android.jardamberem.ui.main.fragment

import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.StartApplication
import com.baktiyar.android.jardamberem.model.AnnouncementsPaginated
import com.baktiyar.android.jardamberem.utils.Settings.getCityId
import com.baktiyar.android.jardamberem.utils.e
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainAnnouncementPresenter(val v: MainAnnouncementContract.View) : MainAnnouncementContract.Presenter {
    private val errorMessage: String = StartApplication.INSTANCE.getString(R.string.no_internet)
    override fun getAnnouncement(limit: Int, offset: Int, isNeeded: Boolean) {
        StartApplication.INSTANCE.service.getAnnouncement(limit, offset, isNeeded, getCityId()).enqueue(object : Callback<AnnouncementsPaginated> {
            override fun onFailure(call: Call<AnnouncementsPaginated>, t: Throwable) {
                v.onAnnouncementError(errorMessage)
                e(t.localizedMessage)
            }

            override fun onResponse(call: Call<AnnouncementsPaginated>, response: Response<AnnouncementsPaginated>) {
                if (response.isSuccessful && response.body()?.results?.isNotEmpty()!!) {
                    v.onAnnouncementSuccess(response.body()!!)
                } else if (response.isSuccessful && response.body()?.results?.isEmpty()!!) {
                    v.onAnnouncementError(StartApplication.INSTANCE.applicationContext.getString(R.string.no_publication))
                }
            }

        })
    }
}