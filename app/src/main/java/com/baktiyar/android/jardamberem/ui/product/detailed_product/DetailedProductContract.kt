package com.baktiyar.android.jardamberem.ui.product.detailed_product

import com.baktiyar.android.jardamberem.utils.IProgressBar

/**
 * Created by admin on 18.03.2018.
 */
interface DetailedProductContract{
    interface View: IProgressBar{
        fun onFail(message: String)
        fun onSuccess()
    }
    interface Presenter{
        fun deleteAnnoun(id: Int)
    }
}