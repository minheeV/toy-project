package com.toy.areadropdownmenu

enum class AreaCode(val nameList: Int, val codeList: Int, val code: Int) {
    All(R.array.dropdown_sido, R.array.dropdown_sido_code,0),
    SEOUL(R.array.dropdown_seoul_gu, R.array.dropdown_seoul_code, 11),
    BUSAN(R.array.dropdown_busan_gu, R.array.dropdown_busan_code,26),
    DAEGU(R.array.dropdown_daegu_gu, R.array.dropdown_daegu_code, 27),
    INCHEON(R.array.dropdown_incheon_gu, R.array.dropdown_incheon_code, 28),
    GWANGJU(R.array.dropdown_gwangju_gu, R.array.dropdown_gwangju_code,29)
}