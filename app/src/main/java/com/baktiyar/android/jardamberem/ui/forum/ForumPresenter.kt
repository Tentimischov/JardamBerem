package com.baktiyar.android.jardamberem.ui.forum

import com.baktiyar.android.jardamberem.ApplicationClass
import com.baktiyar.android.jardamberem.model.Forum
import com.baktiyar.android.jardamberem.model.ForumPaginated
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForumPresenter(var v: ForumContract.View): ForumContract.Presenter {
    private var mCompositeDisposable: CompositeDisposable? = CompositeDisposable()

    override fun getForumFirst(limit: Int, offset: Int) {
        mCompositeDisposable?.add(ApplicationClass.INSTANCE?.service?.getForum(limit, offset)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeOn(Schedulers.io())
                ?.subscribe(this::success, this::error)!!)
    }

    override fun getForumNext(limit: Int, offset: Int) {
        mCompositeDisposable?.add(ApplicationClass.INSTANCE?.service?.getForum(limit, offset)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeOn(Schedulers.io())
                ?.subscribe(this::successNext, this::error)!!)
    }

    private fun error(message: Throwable) {
        v.onError(message.localizedMessage)
    }
    private fun success(data: ForumPaginated) {
        v.onForumFirstSuccess(data)
    }
    private fun successNext(data: ForumPaginated) {
        v.onForumNextSuccess(data)
    }

    override fun sendFroum(data: Forum) {
        ApplicationClass.INSTANCE?.service?.sendForum(data)?.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                v.onError(t?.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                v.onSendSuccess()
            }

        })
    }
}