package com.nhat.huaweikit.demo.nd_services

interface SafeCheck {
    fun getMobileServices(): MobileServicesEnum
}

enum class MobileServicesEnum {
    HMS, GMS
}