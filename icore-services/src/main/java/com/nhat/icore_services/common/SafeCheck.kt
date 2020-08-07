package com.nhat.icore_services.common

interface SafeCheck {
    val mobileServicesEnum: MobileServicesEnum
}

enum class MobileServicesEnum {
    HMS, GMS
}