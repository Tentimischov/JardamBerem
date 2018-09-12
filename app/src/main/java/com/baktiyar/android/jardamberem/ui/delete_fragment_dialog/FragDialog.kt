package com.baktiyar.android.jardamberem.ui.delete_fragment_dialog

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.ui.main.MainActivity
import com.baktiyar.android.jardamberem.ui.urgent_detailed.UrgentDetailedContract
import com.baktiyar.android.jardamberem.ui.urgent_detailed.UrgentDetailedPresenter
import kotlinx.android.synthetic.main.fragment_dialog.view.*


class FragDialog : DialogFragment(), UrgentDetailedContract.View {
    override fun onSuccess(message: String) {
        Toast.makeText(context, getString(R.string.success_delete), Toast.LENGTH_SHORT).show()
    }

    override fun onError(message: String) {
        Toast.makeText(context, getString(R.string.error_sending_data), Toast.LENGTH_SHORT).show()
    }

    var pId: Int? = null
    var pTitle: String? = null

    fun newInstance(title: String, id: Int): FragDialog {
        val f = FragDialog()
        val args = Bundle()
        args.putString("title", title)
        args.putInt("id", id)
        f.arguments = args
        return f
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null && dialog.window != null) {
            dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        pTitle = arguments?.getString("title")
        pId = arguments?.getInt("id")
        val v = inflater.inflate(R.layout.fragment_dialog, container, false)
        v.title.text = getString(R.string.check_to_delete, pTitle)
        v.yes.setOnClickListener {
            val presenter = UrgentDetailedPresenter(this)
            presenter.deleteUrgent(pId!!)
            startActivity(Intent(context, MainActivity::class.java))

        }
        v.no.setOnClickListener {
            dismiss()
        }
        return v
    }


}