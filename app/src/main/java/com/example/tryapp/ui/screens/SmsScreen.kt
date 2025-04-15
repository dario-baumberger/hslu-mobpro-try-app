package com.example.tryapp.ui.screens

import android.Manifest
import android.app.Activity
import android.content.IntentFilter
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tryapp.business.sms.SmsReceiver
import com.example.tryapp.business.sms.SmsUpdateHandler
import com.example.tryapp.business.sms.hasSmsPermission
import com.example.tryapp.business.sms.readSms

@Composable
fun SmsScreen(
    navController: NavController,

    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activity = context as? Activity
    var smsMessages by remember { mutableStateOf<List<String>>(emptyList()) }

    val smsPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted && activity != null) {
            smsMessages = readSms(context)
        }
    }

    LaunchedEffect(Unit) {
        

        if (hasSmsPermission(context)) {
            smsMessages = readSms(context)
        } else {
            smsPermissionLauncher.launch(Manifest.permission.READ_SMS)
        }
    }

    DisposableEffect(Unit) {
        val receiver = SmsReceiver()
        val filter = IntentFilter("android.provider.Telephony.SMS_RECEIVED")
        context.registerReceiver(receiver, filter)

        SmsUpdateHandler.setOnSmsUpdateListener {
            smsMessages = readSms(it)
        }

        onDispose {
            context.unregisterReceiver(receiver)
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            smsMessages.take(5).forEach {
                Text(text = it)
            }
        }

        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { navController.popBackStack() }) {
                    Text("Go Back")
                }
            }
        }
    }
}
