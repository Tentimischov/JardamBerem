package com.baktiyar.android.jardamberem.ui.feedback

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.View
import com.baktiyar.android.jardamberem.ApplicationClass
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Feedback
import com.baktiyar.android.jardamberem.model.Forum
import com.baktiyar.android.jardamberem.ui.BaseActivity
import com.baktiyar.android.jardamberem.ui.forum.ForumActivity
import com.baktiyar.android.jardamberem.ui.main.MainActivity
import com.baktiyar.android.jardamberem.utils.Const.Companion.ACTIVITY_ID
import com.dd.morphingbutton.MorphingButton
import kotlinx.android.synthetic.main.activity_feedback.*
import org.jetbrains.anko.dimen
import org.jetbrains.anko.toast


class FeedbackActivity : BaseActivity(), FeedbackContract.View, View.OnClickListener {


    private val mHandler = Handler()
    private var mFeedbackPresenter: FeedbackPresenter? = null
    private var mFeedback: Feedback? = null
    private var mProgressBar: ProgressDialog? = null
    private var actvityId: Int? = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        title = getString(R.string.add_feedback)
        init()
    }

    private fun init() {
        initLoading()
        getIntentId()
        initUi()
        initPresenter()
        btAddFeedBack.setOnClickListener(this)
    }

    fun initLoading() {
        val circle = MorphingButton.Params.create()
                .duration(0)
                .cornerRadius(dimen(R.dimen.size_5)) // 56 dp
                .width(dimen(R.dimen.size_220)) // 56 dp
                .height(dimen(R.dimen.size_35)) // 56 dp
                .color(ContextCompat.getColor(this, R.color.addButton)) // normal state color
                .colorPressed(ContextCompat.getColor(this, R.color.addButtonPressed)) // pressed state color
        btnMorph1.morph(circle)

        btnMorph1.setOnClickListener {

            initFeedBack()
            sendData(mFeedback!!)
        }
    }

    override fun onSuccessForum() {
        successLoadingButton()
        mHandler.postDelayed(goForumActivity, 1100)
    }

    fun successLoadingButton() {
        val circle = MorphingButton.Params.create()
                .duration(500)
                .cornerRadius(dimen(R.dimen.size_45))
                .width(dimen(R.dimen.size_45))
                .height(dimen(R.dimen.size_45))
                .color(ContextCompat.getColor(this, android.R.color.holo_green_light)) // normal state color
                .colorPressed(ContextCompat.getColor(this, android.R.color.holo_green_light)) // pressed state color
                .icon(R.drawable.done_loading)
        btnMorph1.morph(circle)
    }


    private val goForumActivity = Runnable {
        startActivity(Intent(this, ForumActivity::class.java))
        finish()
    }
    private val goMainActivity = Runnable {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onFail(message: String) {
        failLoadingButton()
    }

    fun failLoadingButton() {
        val circle = MorphingButton.Params.create()
                .duration(500)
                .cornerRadius(dimen(R.dimen.size_45))
                .width(dimen(R.dimen.size_45))
                .height(dimen(R.dimen.size_45))
                .color(ContextCompat.getColor(this, android.R.color.holo_red_light))
                .colorPressed(ContextCompat.getColor(this, android.R.color.holo_red_dark))
                .icon(R.drawable.error_loading)

        btnMorph1.morph(circle)
    }

    fun initUi() {
        if (actvityId == 2) {
            etFeedbackEmail.hint = getString(R.string.forum_hint)

        }
    }

    fun getIntentId() {
        actvityId = intent.getIntExtra(ACTIVITY_ID, 1)
    }

    private fun initFeedBack() {
        val review: String = etFeedbackReview.text.toString()
        val email: String = etFeedbackEmail.text.toString()
        mFeedback = Feedback(review, email)
    }

    private fun initPresenter() {
        val app = this.applicationContext as ApplicationClass
        mFeedbackPresenter = FeedbackPresenter(this, app.service!!, this)
    }

    private fun sendData(feedback: Feedback) {
        if (checkFields()) {
            toast(R.string.fill_fields)
        } else {
            if (actvityId == 1) {
                initFeedBack()
                mFeedbackPresenter!!.sendFeedback(feedback)
            } else {
                mFeedbackPresenter?.sendForum(initForum())
            }
        }
    }

    fun initForum(): Forum {
        return Forum(etFeedbackEmail.text.toString(), etFeedbackEmail.text.toString(), etFeedbackReview.text.toString(), null)
    }

    private fun checkFields(): Boolean {
        return TextUtils.isEmpty(etFeedbackEmail.text.toString()) || TextUtils.isEmpty(etFeedbackReview.text.toString())
    }

    override fun onClick(v: View?) {
        when (v) {

            btAddFeedBack -> {
                initFeedBack()
                sendData(mFeedback!!)
            }
        }
    }


    override fun showProgress() {
        /*if (mProgressBar == null) {
            mProgressBar = ProgressDialog(this)
            mProgressBar!!.setTitle(R.string.loading)
            mProgressBar!!.show()
        }*/
    }

    override fun hideProgress() {
        /*if (mProgressBar != null && mProgressBar!!.isShowing) mProgressBar!!.dismiss()
        mProgressBar = null*/
    }

    override fun onSuccess() {
        successLoadingButton()
        mHandler.postDelayed(goMainActivity, 1100)
    }


}
