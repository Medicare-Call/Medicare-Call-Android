package com.konkuk.medicarecall.ui.homedetail.glucoselevel

object GlucoseStatusUtil {

    /**
     * 혈당 값에 따라 상태 판별
     * 90 미만 -> "낮음"
     * 90 이상 130 미만 -> "정상"
     * 130 이상 200 이하 -> "높음"
     * 200 초과 -> "위험"
     */
    fun getStatus(value: Int): String {
        return when {
            value < 90 -> "낮음"
            value < 130 -> "정상"
            value <= 200 -> "높음"
            else -> ""
        }
    }

}

