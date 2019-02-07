package com.baktiyar.android.jardamberem.ui.main.fragment

import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.StartApplication
import com.baktiyar.android.jardamberem.model.AnnouncementsPaginated
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainAnnouncementPresenter(val v: MainAnnouncementContract.View) : MainAnnouncementContract.Presenter {
    private val errorMessage: String = StartApplication.INSTANCE.getString(R.string.no_internet)
    override fun getAnnouncementIsNeeded(limit: Int, offset: Int) {
        StartApplication.INSTANCE.service.getAnnouncements(limit, offset).enqueue(object : Callback<AnnouncementsPaginated> {
            override fun onFailure(call: Call<AnnouncementsPaginated>, t: Throwable) {
                v.onAnnouncementError(errorMessage)
            }

            override fun onResponse(call: Call<AnnouncementsPaginated>, response: Response<AnnouncementsPaginated>) {
                if (response.isSuccessful && response.body()?.results?.isNotEmpty()!!) {
                    v.onAnnouncementIsNeededSuccess(response.body()!!)
                }
            }

        })
    }

    override fun getAnnouncementIsNeededFalse(limit: Int, offset: Int) {
        StartApplication.INSTANCE.service.getAnnouncementsIsNeededFalse(limit, offset).enqueue(object : Callback<AnnouncementsPaginated> {
            override fun onFailure(call: Call<AnnouncementsPaginated>, t: Throwable) {
                v.onAnnouncementError(errorMessage)
            }

            override fun onResponse(call: Call<AnnouncementsPaginated>, response: Response<AnnouncementsPaginated>) {
                if (response.isSuccessful && response.body()?.results?.isNotEmpty()!!) {
                    v.onAnnouncementIsNeededFalseSuccess(response.body()!!)
                }
            }

        })
    }
}