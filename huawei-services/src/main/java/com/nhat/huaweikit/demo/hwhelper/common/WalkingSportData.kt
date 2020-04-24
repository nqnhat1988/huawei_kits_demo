package com.nhat.huaweikit.demo.huawei.common

class WalkingSportData(val timestamp: Long, val stepDelta: Long) {

    override fun toString(): String {
        return String.format("timeStamp: %d, value: %d", timestamp, stepDelta)
    }

}