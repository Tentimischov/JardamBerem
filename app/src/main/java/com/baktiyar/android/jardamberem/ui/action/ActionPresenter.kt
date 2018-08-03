package com.baktiyar.android.jardamberem.ui.action

import com.baktiyar.android.jardamberem.ApplicationClass
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ActionPresenter(var v: ActionContract.View) : ActionContract.Presenter {
    private var mCompositeDisposable: CompositeDisposable? = CompositeDisposable()
    override fun getActionDataFirst(limit: Int, offset: Int) {
        mCompositeDisposable?.add(ApplicationClass.INSTANCE?.service?.getActionData(limit, offset)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeOn(Schedulers.io())
                ?.subscribe({myData -> v.onSuccessFirst(myData)}, {throwable -> v.onError(throwable.message!!)})!!)
    }

    override fun getActionData(limit: Int, offset: Int) {
        mCompositeDisposable?.add(ApplicationClass.INSTANCE?.service?.getActionData(limit, offset)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeOn(Schedulers.io())
                ?.subscribe({myData -> v.onSuccessNext(myData)}, {throwable -> v.onError(throwable.message!!)})!!)
    }


}