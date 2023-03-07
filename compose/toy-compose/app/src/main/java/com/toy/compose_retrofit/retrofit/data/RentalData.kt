package com.toy.compose_retrofit.retrofit.data

import com.google.gson.annotations.SerializedName

data class RentalData(
    //페이지 당 데이터 개수
    @SerializedName("numOfRows") val pageNum:String?,
    //페이지 번호
    @SerializedName("pageNo") val pageNo:Int?,
    //전체 결과 수
    @SerializedName("totalCount")val totalCnt:Int?,
    //단지 식별자
    @SerializedName("hsmpSn")val hsmpSn:Int?,
    //기관 명
    @SerializedName("insttNm")val insttNm:String?,
    //광역시도 코드
    @SerializedName("brtcCode")val brtcCode:String?,
    //광역시도 명
    @SerializedName("brtcNm")val brtcNm:String?,
    //시군구 코드
    @SerializedName("signguCode")val signguCode:String?,
    //시군구 명
    @SerializedName("signguNm")val signguNm:String?,
    //단지 명
    @SerializedName("hsmpNm")val hsmpNm:String?,
    //도로명 주소
    @SerializedName("rnAdres")val rnAdres:String?,
    //pnu
    @SerializedName("pnu")val pnu:String?,
    //준공일자
    @SerializedName("competDe")val competDe:Any?,
    //세대 수
    @SerializedName("hshldCo")val hshldCo:Int?,
    //공급 유형 명
    @SerializedName("suplyTyNm")val suplyTyNm:String?,
    //형 명
    @SerializedName("styleNm")val styleNm:String?,
    //공급 전용 면적
    @SerializedName("suplyPrvuseAr")val suplyPrvuseAr:Float?,
    //공급 공용 면적
    @SerializedName("suplyCmnuseAr")val suplyCmnuseAr:Float?,
    //주택 유형 명
    @SerializedName("houseTyNm")val houseTyNm:Any?,
    //난방 방식
    @SerializedName("heatMthdDetailNm")val heatMthdDetailNm:Any?,
    //건물 형태
    @SerializedName("buldStleNm")val buldStleNm:Any?,
    //승강기 설치여부
    @SerializedName("elvtrInstlAtNm")val elvtrInstlAtNm:Any?,
    //주차수
    @SerializedName("parkngCo")val parkngCo:Int?,
    //기본 임대보증금
    @SerializedName("bassRentGtn")val bassRentGtn:Int?,
    //기본 월 임대료
    @SerializedName("bassMtRntchrg")val bassMtRntchrg:Int?,
    //기본 전환보증금
    @SerializedName("bassCnvrsGtnLmt")val bassCnvrsGtnLmt:Int?
)
