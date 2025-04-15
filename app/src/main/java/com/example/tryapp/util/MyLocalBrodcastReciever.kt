package com.example.tryapp.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat


class MyLocalBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == BroadcastActions.LOCAL_ACTION) {
            Log.d("MyLocalBroadcastReceiver", "Received local broadcast")
        }
    }
}

@Composable
fun MyLocalBroadcastReceiverComposable() {
    val context = LocalContext.current

    DisposableEffect(Unit) {
        val receiver = MyLocalBroadcastReceiver()
        val intentFilter = IntentFilter(BroadcastActions.LOCAL_ACTION)

        ContextCompat.registerReceiver(
            context,
            receiver,
            intentFilter,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )

        onDispose {
            context.unregisterReceiver(receiver)
        }
    }
}
