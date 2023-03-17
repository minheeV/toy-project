package com.toy.areadropdownmenu

enum class AreaCode(
    val firstName: String,
    val firstCode: String,
    val secondName: Int,
    val secondCode: Int
) {
    SEOUL("서울특별시", "11", R.array.dropdown_seoul_gu, R.array.dropdown_seoul_code),
    BUSAN("부산광역시", "26", R.array.dropdown_busan_gu, R.array.dropdown_busan_code),
    DAEGU("대구광역시", "27", R.array.dropdown_daegu_gu, R.array.dropdown_daegu_code),
    INCHEON("인천광역시", "28", R.array.dropdown_incheon_gu, R.array.dropdown_incheon_code),
    GWANGJU("광주광역시", "29", R.array.dropdown_gwangju_gu, R.array.dropdown_gwangju_code),
    DEAJEON("대전광역시", "30", R.array.dropdown_daejeon_gu, R.array.dropdown_daejeon_code),
    ULSAN("울산광역시", "31", R.array.dropdown_ulsan_gu, R.array.dropdown_ulsan_code),
    SEJONG("세종특별자치시", "36", R.array.dropdown_sejong_gu, R.array.dropdown_sejong_code),
    GYEONGGI("경기도", "41", R.array.dropdown_gg_sigu, R.array.dropdown_gg_code),
    GANGWON("강원도", "42", R.array.dropdown_gw_sigun, R.array.dropdown_gw_code),
    CHUNGBOK("충청북도", "43", R.array.dropdown_cb_sigu, R.array.dropdown_cb_code),
    CHUNGNAM("충청남도", "44", R.array.dropdown_cn_sigu, R.array.dropdown_cn_code),
    JEONBOK("전라북도", "45", R.array.dropdown_jb_sigu, R.array.dropdown_jb_code),
    JEONNAM("전라남도", "46", R.array.dropdown_jn_sigu, R.array.dropdown_jn_code),
    GYEONGBOK("경상북도", "47", R.array.dropdown_gb_sigu, R.array.dropdown_gb_code),
    GYEONGNAM("경상남도", "48", R.array.dropdown_gn_sigu, R.array.dropdown_gn_code),
    JEJU("제주특별자치시", "50", R.array.dropdown_jeju_si, R.array.dropdown_jeju_code),
}