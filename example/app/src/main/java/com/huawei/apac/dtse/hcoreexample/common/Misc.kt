package com.huawei.apac.dtse.hcoreexample.common

import android.app.Activity
import android.content.Intent
import android.view.View
import timber.log.Timber
import java.net.NetworkInterface
import java.util.*


fun Boolean.visible() = if (this) View.VISIBLE else View.INVISIBLE

fun Activity.finishWithResult(resultCode: Int) {
    setResult(resultCode)
    finish()
}

fun Activity.finishWithResult(resultCode: Int, intent: Intent) {
    setResult(resultCode, intent)
    finish()
}

fun getMacAddress(networkName: String?): String? {
    try {
        val allNetworkInterface: List<NetworkInterface> =
            Collections.list(NetworkInterface.getNetworkInterfaces())
        Timber.d(allNetworkInterface.toString())
        for (nif in allNetworkInterface) {
            if (!nif.name.equals(networkName, true)) {
                continue
            }
            val macBytes: ByteArray = nif.hardwareAddress ?: return ""
            val res1 = StringBuilder()
            for (b in macBytes) {
                res1.append(String.format("%02X:", b))
            }
            if (res1.isNotEmpty()) {
                res1.deleteCharAt(res1.length - 1)
            }
            return res1.toString().trim { it <= ' ' }
        }
    } catch (ex: Exception) {
        Timber.e(ex)
    }
    return ""
}
