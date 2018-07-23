package com.baktiyar.android.jardamberem.ui.all_urgents

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Urgent
import com.baktiyar.android.jardamberem.ui.urgent_detailed.UrgentDetailed
import com.baktiyar.android.jardamberem.utils.Const.Companion.ACTION_URGENT
import com.baktiyar.android.jardamberem.utils.Const.Companion.URGENTS
import kotlinx.android.synthetic.main.activity_all_urgent.*
import kotlinx.android.synthetic.main.toolbar.*
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import android.support.v4.app.ActivityCompat


class AllUrgentsActivity : AppCompatActivity(), AllUrgentsAdapter.OnItemClickListener {
    private var allUrgent: ArrayList<Urgent>? = null

    private var adapter: AllUrgentsAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_urgent)
        title = getString(R.string.urgent_help)
        init()

    }

    fun init() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        allUrgent = intent.getParcelableArrayListExtra(URGENTS)
        if (allUrgent != null)
            adapter = AllUrgentsAdapter(allUrgent!!, this)
        else return
        all_rec.layoutManager = LinearLayoutManager(this)
        all_rec.adapter = adapter
    }

    override fun onUrgentDetClick(main: Urgent, position: Int) {
        /*val intent = Intent(this, UrgentDetailed::class.java)
        intent.putExtra(ACTION_URGENT, main)
        startActivity(intent)*/
        animateIntent(main)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun animateIntent(main: Urgent) {

        // Ordinary Intent for launching a new activity
        val intent = Intent(this, UrgentDetailed::class.java)
        intent.putExtra(ACTION_URGENT, main)
        // Get the transition name from the string
        val transitionName = getString(R.string.transition_name)

        // Define the view that the animation will start from

        val options =

                ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                        begin_anim, // Starting view
                        transitionName    // The String
                )
        //Start the Intent
        ActivityCompat.startActivity(this, intent, options.toBundle())

    }


}
