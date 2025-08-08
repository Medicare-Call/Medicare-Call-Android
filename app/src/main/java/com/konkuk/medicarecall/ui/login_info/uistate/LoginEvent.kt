package com.konkuk.medicarecall.ui.login_info.uistate

sealed class LoginEvent {
    object VerificationSuccess : LoginEvent() // 인증번호 확인 성공
    object VerificationFailure : LoginEvent() // 인증번호 확인 실패
    object MemberRegisterSuccess : LoginEvent() // 회원가입 성공
    data class MemberRegisterFailure(val message: String) : LoginEvent() // 회원가입 실패
}