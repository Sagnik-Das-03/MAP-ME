package com.example.mapme

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Contract : ActivityResultContract<Intent, String?>() {
    override fun createIntent(context: Context, input: Intent): Intent {
        val intent = Intent(context, CreateMapActivity::class.java)
        intent.putExtra(EXTRA_MAP_TITLE, "new map title :)")
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        return null
    }
}


