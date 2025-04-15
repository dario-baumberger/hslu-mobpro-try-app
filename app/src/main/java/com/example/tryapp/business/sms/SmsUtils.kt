package com.example.tryapp.business.sms

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.provider.Telephony
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

private const val SMS_PERMISSION_REQUEST_CODE = 123


object SmsUpdateHandler {
    private var listener: ((Context) -> Unit)? = null


    fun setOnSmsUpdateListener(l: (Context) -> Unit) {
        listener = l
    }

    fun notifyListeners(context: Context) {
        listener?.invoke(context)
    }
}

fun readSms(context: Context): List<String> {
    val smsList = mutableListOf<String>()

    context.contentResolver.query(
        Telephony.Sms.Inbox.CONTENT_URI,
        arrayOf(Telephony.Sms._ID, Telephony.Sms.BODY),
        null,
        null,
        "date DESC"
    )?.use { cursor ->
        val bodyIndex = cursor.getColumnIndex(Telephony.Sms.BODY)
        if (bodyIndex != -1) {
            while (cursor.moveToNext()) {
                val body = cursor.getString(bodyIndex)
                smsList.add(body)
            }
        }
    }

    return smsList
}

fun hasSmsPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        android.Manifest.permission.READ_SMS
    ) == PackageManager.PERMISSION_GRANTED
}

fun requestSmsPermission(activity: Activity) {
    ActivityCompat.requestPermissions(
        activity,
        arrayOf(android.Manifest.permission.READ_SMS),
        SMS_PERMISSION_REQUEST_CODE
    )
}