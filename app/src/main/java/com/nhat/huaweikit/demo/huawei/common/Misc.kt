package com.nhat.huaweikit.demo.huawei.common

import android.app.Activity
import android.content.Intent
import android.view.View


fun Boolean.visible() = if (this) View.VISIBLE else View.INVISIBLE

fun Activity.finishWithResult(resultCode: Int) {
    setResult(resultCode)
    finish()
}

fun Activity.finishWithResult(resultCode: Int, intent: Intent) {
    setResult(resultCode, intent)
    finish()
}