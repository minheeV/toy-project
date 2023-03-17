package com.toy.areadropdownmenu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.toy.areadropdownmenu.ui.theme.AreaDropdownMenuTheme

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: CodeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            viewModel = ViewModelProvider(this)[CodeViewModel::class.java]
            AreaDropdownMenuTheme {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    val code by remember {
                        mutableStateOf("")
                    }
                    /*FirstDropDownMenu(viewModel) {
                        code = it
                    }*/
                    SecondDropDownMenu(viewModel)
                }
            }
        }
    }
}

/**
 * 시/도에 대한 DropDown Menu
 */
@Composable
fun FirstDropDownMenu(
    viewModel: CodeViewModel
) {
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }
    var sidoText by remember { mutableStateOf("도/시") }

    val icon = if (isDropDownMenuExpanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }
    Button(onClick = { isDropDownMenuExpanded = true }) {
        Text(text = sidoText)
    }

    val dropdownSido = stringArrayResource(id = R.array.dropdown_sido)
    val sidoCodeList = stringArrayResource(id = R.array.dropdown_sido_code)
    DropdownMenu(
        expanded = isDropDownMenuExpanded,
        onDismissRequest = { isDropDownMenuExpanded = false },
        modifier = Modifier.requiredSizeIn(maxHeight = 330.dp)

    ) {
        dropdownSido.forEachIndexed { index, value ->
            DropdownMenuItem(onClick = {
                viewModel.setFirstCode(sidoCodeList[index])
                isDropDownMenuExpanded = false
                sidoText = value
            }) {
                Text(value)
                DropdownMenuItem(onClick = { /*TODO*/ }) {

                }
            }
        }
    }
}

@Composable
fun SecondDropDownMenu(viewModel: CodeViewModel) {
    val firstCode by viewModel.firstCode.observeAsState()
    SelectSecond(area = firstCode?.toInt()!!, viewModel)
}

@Composable
fun SelectSecond(
    area: Int,
    viewModel: CodeViewModel
) {
    when (area) {
        11 -> { // 서울특별시
            CodeDropDownItem(
                viewModel,
                dropdownList = stringArrayResource(R.array.dropdown_seoul_gu),
                dropdownCode = stringArrayResource(R.array.dropdown_seoul_code)
            )
        }
        26 -> { // 부산광역시
            CodeDropDownItem(
                viewModel,
                dropdownList = stringArrayResource(R.array.dropdown_busan_gu),
                dropdownCode = stringArrayResource(R.array.dropdown_busan_code)
            )
        }
        27 -> { // 대구광역시
            CodeDropDownItem(
                viewModel,
                dropdownList = stringArrayResource(R.array.dropdown_daegu_gu),
                dropdownCode = stringArrayResource(R.array.dropdown_daegu_code)
            )
        }
        28 -> { // 인천광역시
            CodeDropDownItem(
                viewModel,
                dropdownList = stringArrayResource(R.array.dropdown_incheon_gu),
                dropdownCode = stringArrayResource(R.array.dropdown_incheon_code)
            )
        }
        29 -> { // 광주광역시
            CodeDropDownItem(
                viewModel,
                dropdownList = stringArrayResource(R.array.dropdown_gwangju_gu),
                dropdownCode = stringArrayResource(R.array.dropdown_gwangju_code)
            )
        }
        30 -> { // 대전광역시
            CodeDropDownItem(
                viewModel,
                dropdownList = stringArrayResource(R.array.dropdown_daejeon_gu),
                dropdownCode = stringArrayResource(R.array.dropdown_daejeon_code)
            )
        }
        31 -> { // 울산광역시
            CodeDropDownItem(
                viewModel,
                dropdownList = stringArrayResource(R.array.dropdown_ulsan_gu),
                dropdownCode = stringArrayResource(R.array.dropdown_ulsan_code)
            )
        }
        36 -> { // 세종특별자치시
            CodeDropDownItem(
                viewModel,
                dropdownList = stringArrayResource(R.array.dropdown_sejong_gu),
                dropdownCode = stringArrayResource(R.array.dropdown_sejong_code)
            )
        }
        41 -> { // 경기도
            CodeDropDownItem(
                viewModel,
                dropdownList = stringArrayResource(R.array.dropdown_gg_sigu),
                dropdownCode = stringArrayResource(R.array.dropdown_gg_code)
            )
        }
        42 -> { //강원도
            CodeDropDownItem(
                viewModel,
                dropdownList = stringArrayResource(R.array.dropdown_gw_sigun),
                dropdownCode = stringArrayResource(R.array.dropdown_gw_code)
            )
        }
        43 -> { // 충청북도
            CodeDropDownItem(
                viewModel,
                dropdownList = stringArrayResource(R.array.dropdown_cb_sigu),
                dropdownCode = stringArrayResource(R.array.dropdown_cb_code)
            )
        }
        44 -> { // 충청남도
            CodeDropDownItem(
                viewModel,
                dropdownList = stringArrayResource(R.array.dropdown_cn_sigu),
                dropdownCode = stringArrayResource(R.array.dropdown_cn_code)
            )
        }
        45 -> { // 전라북도
            CodeDropDownItem(
                viewModel,
                dropdownList = stringArrayResource(R.array.dropdown_jb_sigu),
                dropdownCode = stringArrayResource(R.array.dropdown_jb_code)
            )
        }
        46 -> { // 전라남도
            CodeDropDownItem(
                viewModel,
                dropdownList = stringArrayResource(R.array.dropdown_jn_sigu),
                dropdownCode = stringArrayResource(R.array.dropdown_jn_code)
            )
        }
        47 -> { // 경상북도
            CodeDropDownItem(
                viewModel,
                dropdownList = stringArrayResource(R.array.dropdown_gb_sigu),
                dropdownCode = stringArrayResource(R.array.dropdown_gb_code)
            )
        }
        48 -> { // 경상남도
            CodeDropDownItem(
                viewModel,
                dropdownList = stringArrayResource(R.array.dropdown_gn_sigu),
                dropdownCode = stringArrayResource(R.array.dropdown_gn_code)
            )
        }
        50 -> { // 제주특별자치도
            CodeDropDownItem(
                viewModel,
                dropdownList = stringArrayResource(R.array.dropdown_jeju_si),
                dropdownCode = stringArrayResource(R.array.dropdown_jeju_code)
            )
        }
        else -> {
            //시코드가 0 이상아니면 노터치
        }

    }
}

/**
 * 시/구/군에 대한 DropDown Menu
 */

@Composable
fun CodeDropDownItem(
    viewModel: CodeViewModel,
    dropdownList: Array<String>,
    dropdownCode: Array<String>
) {

    var isDropDownMenuExpanded by remember { mutableStateOf(false) }
    var gugunText by remember { mutableStateOf("구/군") }

    Button(onClick = {
        isDropDownMenuExpanded = true
    }) {
        Text(text = gugunText)
    }
    DropdownMenu(
        expanded = isDropDownMenuExpanded,
        onDismissRequest = { isDropDownMenuExpanded = false },
        modifier = Modifier.requiredSizeIn(maxHeight = 330.dp)
    ) {
        dropdownList.forEachIndexed { index, value ->
            DropdownMenuItem(onClick = {
                viewModel.setSecondCode(dropdownCode[index])
                isDropDownMenuExpanded = false
                gugunText = value
            }) {
                Text(value)
            }
        }
    }
}
