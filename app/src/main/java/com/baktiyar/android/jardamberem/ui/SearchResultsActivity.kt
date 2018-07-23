package com.baktiyar.android.jardamberem.ui

import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class SearchResultsActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handleIntent(intent)
        Log.d("thi", "++")
    }

    override fun onNewIntent(intent: Intent?) {
        //super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (Intent.ACTION_SEARCH == intent!!.action) {
            var query = intent.getStringExtra(SearchManager.QUERY)
            Toast.makeText(this, query, Toast.LENGTH_SHORT).show()
            Log.d("mm++++", query)
        }
    }
}

