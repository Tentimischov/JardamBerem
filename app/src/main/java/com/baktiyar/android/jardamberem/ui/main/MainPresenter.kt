package com.baktiyar.android.jardamberem.ui.main

import com.baktiyar.android.jardamberem.ApplicationClass
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(var v: MainContract.View) : MainContract.Presenter {


    /*override fun getAnnounFirstIsNeededFalse(limit: Int, offset: Int) {
        ApplicationClass.INSTANCE?.service?.getAnnouncementsIsNeededFalse(limit, offset)?.enqueue(object : Callback<AnnouncementsPaginated> {
            override fun onFailure(call: Call<AnnouncementsPaginated>?, t: Throwable?) {
                v.onError(ApplicationClass.INSTANCE!!.getString(R.string.fail))
            }

            override fun onResponse(call: Call<AnnouncementsPaginated>?, response: Response<AnnouncementsPaginated>?) {
                if (response!!.isSuccessful) {
                   // v.onAnnounFirstSuccess(response.body()!!)
                    v.onAnnounFirstSuccesss(response.body()?.results!!)
                }
            }
        })
    }*/

    override fun getAnnounFirstIsNeededFalses(limit: Int, offset: Int) {
        ApplicationClass.INSTANCE?.service?.getAnnouncementsIsNeededFalses(limit, offset)?.enqueue(object : Callback<ArrayList<Announcements>> {
            override fun onFailure(call: Call<ArrayList<Announcements>>?, t: Throwable?) {
                v.onError(ApplicationClass.INSTANCE!!.getString(R.string.fail))
            }

            override fun onResponse(call: Call<ArrayList<Announcements>>?, response: Response<ArrayList<Announcements>>?) {
                if (response!!.isSuccessful) {
                    // v.onAnnounFirstSuccess(response.body()!!)
                    v.onAnnounFirstSuccesss(response.body()!!)
                }
            }
        })
    }

    override fun getAnnounNextIsNeededFalses(limit: Int, offset: Int) {
        ApplicationClass.INSTANCE?.service?.getAnnouncementsIsNeededFalse(limit, offset)?.enqueue(object : Callback<AnnouncementsPaginated> {
            override fun onFailure(call: Call<AnnouncementsPaginated>?, t: Throwable?) {
                v.onError(ApplicationClass.INSTANCE!!.getString(R.string.fail))
            }

            override fun onResponse(call: Call<AnnouncementsPaginated>?, response: Response<AnnouncementsPaginated>?) {
                if (response!!.isSuccessful) {
                    //  v.onAnnounFirstSuccess(response.body()!!)
                }
            }
        })
    }

    override fun getAnnounNext(limit: Int, offset: Int) {
        ApplicationClass.INSTANCE?.service?.getAnnouncements(limit, offset)?.enqueue(object : Callback<AnnouncementsPaginated> {
            override fun onFailure(call: Call<AnnouncementsPaginated>?, t: Throwable?) {
                v.onError(ApplicationClass.INSTANCE!!.getString(R.string.fail))
            }

            override fun onResponse(call: Call<AnnouncementsPaginated>?, response: Response<AnnouncementsPaginated>?) {
                if (response!!.isSuccessful) {
                    //  v.onAnnounNextSuccess(response.body()!!)
                }
            }

        })
    }

    override fun getAnnounFirst(limit: Int, offset: Int) {
        ApplicationClass.INSTANCE?.service?.getAnnouncementss(limit, offset)?.enqueue(object : Callback<ArrayList<Announcements>> {
            override fun onFailure(call: Call<ArrayList<Announcements>>?, t: Throwable?) {
                v.onError(ApplicationClass.INSTANCE!!.getString(R.string.fail))
            }

            override fun onResponse(call: Call<ArrayList<Announcements>>?, response: Response<ArrayList<Announcements>>?) {
                if (response!!.isSuccessful) {
                    // v.onAnnounFirstSuccess(response.body()!!)
                    if (response.isSuccessful)
                        v.onAnnounIsNeeded(response?.body()!!)
                    else v.onError(response.message())
                }
            }
        })
    }

    override fun getCategory(id: Int) {
        ApplicationClass.INSTANCE?.service?.getCategory(id)?.enqueue(object : Callback<ArrayList<AllCategory>> {
            override fun onFailure(call: Call<ArrayList<AllCategory>>?, t: Throwable?) {
                v.onError(ApplicationClass.INSTANCE!!.getString(R.string.fail))
            }

            override fun onResponse(call: Call<ArrayList<AllCategory>>?, response: Response<ArrayList<AllCategory>>?) {
                if (response?.isSuccessful!!)
                    v.onCategorySuccess(response?.body()!!)
            }

        })
    }

    override fun getUrgent(limit: Int, offset: Int) {
        ApplicationClass.INSTANCE?.service?.getUrgent(limit, offset)?.enqueue(object : Callback<ArrayList<Urgent>> {
            override fun onFailure(call: Call<ArrayList<Urgent>>?, t: Throwable?) {
                v.onError(ApplicationClass.INSTANCE!!.getString(R.string.fail))
            }

            override fun onResponse(call: Call<ArrayList<Urgent>>?, response: Response<ArrayList<Urgent>>?) {
                if (response?.isSuccessful!!)
                    v.onUrgentSuccess(response.body()!!)
            }
        })
    }
}