package com.baktiyar.android.jardamberem.ui.feedback

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.text.TextUtils
import com.baktiyar.android.jardamberem.ApplicationClass
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Feedback
import com.baktiyar.android.jardamberem.model.Forum
import com.baktiyar.android.jardamberem.ui.BaseActivity
import com.baktiyar.android.jardamberem.ui.forum.ForumActivity
import com.baktiyar.android.jardamberem.ui.main.MainActivity
import com.baktiyar.android.jardamberem.utils.Const.Companion.ACTIVITY_ID
import com.baktiyar.android.jardamberem.utils.Utils.Companion.e
import kotlinx.android.synthetic.main.activity_feedback.*
import org.jetbrains.anko.toast


class FeedbackActivity : BaseActivity(), FeedbackContract.View {

    private var mFeedbackPresenter: FeedbackPresenter? = null
    private var mFeedback: Feedback? = null
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
    }



    fun initLoading() {
        add_feedback.setOnClickListener {
            initFeedBack()
            sendData(mFeedback!!)
        }
    }

    fun getIntentId() {
        actvityId = intent.getIntExtra(ACTIVITY_ID, 1)
    }

    fun initUi() {
        if (actvityId == 2) {
            etFeedbackEmail.hint = getString(R.string.forum_hint)
        }
    }

    private fun initPresenter() {
        val app = this.applicationContext as ApplicationClass
        mFeedbackPresenter = FeedbackPresenter(this, app.service!!, this)
    }

    override fun onSuccessForum() {
        startForumActivity()
    }

    private fun startForumActivity() {
        startActivity(Intent(this, ForumActivity::class.java))
        finish()
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onSuccess() {
        toast("Отзыв принят")
        startMainActivity()
    }

    override fun onFail(message: String) {
        toast(message)
    }

    private fun initFeedBack() {
        val review: String = etFeedbackReview.text.toString()
        val email: String = etFeedbackEmail.text.toString()
        mFeedback = Feedback(review, email)
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
        return Forum(null, etFeedbackEmail.text.toString(), etFeedbackEmail.text.toString(), etFeedbackReview.text.toString(), getAndroidId(), null)
    }

    private fun checkFields(): Boolean {
        return TextUtils.isEmpty(etFeedbackEmail.text.toString()) || TextUtils.isEmpty(etFeedbackReview.text.toString())
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }
    @SuppressLint("HardwareIds")
    private fun getAndroidId(): String {
        return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
    }

}
