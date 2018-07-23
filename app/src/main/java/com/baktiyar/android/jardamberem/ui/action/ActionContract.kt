package com.baktiyar.android.jardamberem.ui.action

import com.baktiyar.android.jardamberem.model.ActionData
import com.baktiyar.android.jardamberem.model.ActionDataPaginated

interface ActionContract {

    interface View {
       // fun onSuccessNext(data: ActionDataPaginated?)
        fun onSuccessFirst(data: ArrayList<ActionData>?)
        fun onError(message: String)
    }
    interface Presenter {
      //  fun getActionData(limit: Int, offset: Int)
        fun getActionDataFirst(limit: Int, offset: Int)
    }

}