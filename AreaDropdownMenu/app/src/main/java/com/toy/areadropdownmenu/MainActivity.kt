package com.toy.areadropdownmenu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.toy.areadropdownmenu.ui.theme.AreaDropdownMenuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AreaDropdownMenuTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                }
            }
        }
    }
}

var sidoCode: Int = 0
var gugunCode: String = ""
@Composable
fun HomeScreen(){
    Row(modifier = Modifier.fillMaxSize()) {
        SidoDropDownMenu(Modifier.wrapContentSize())
        SelectSugugun(area = sidoCode)
    }
}

@Composable
fun SelectSugugun(
    area: Int
) {
    val code = arrayOf(R.array.dropdown_sido_code)
    when (area) {
        11 -> {
            CodeDropDownItem(
                dropdownList = stringArrayResource(R.array.dropdown_seoul_gu),
                dropdownCode = stringArrayResource(R.array.dropdown_seoul_code)
            )
        }
        26 -> {
            CodeDropDownItem(
                dropdownList = stringArrayResource(R.array.dropdown_busan_gu),
                dropdownCode = stringArrayResource(R.array.dropdown_busan_code)
            )
        }
        27 -> {
            CodeDropDownItem(
                dropdownList = stringArrayResource(R.array.dropdown_daegu_gu),
                dropdownCode = stringArrayResource(R.array.dropdown_daegu_code)
            )
        }
        28 -> {
            CodeDropDownItem(
                dropdownList = stringArrayResource(R.array.dropdown_incheon_gu),
                dropdownCode = stringArrayResource(R.array.dropdown_incheon_code)
            )
        }
        29 -> {
            CodeDropDownItem(
                dropdownList = stringArrayResource(R.array.dropdown_gwangju_gu),
                dropdownCode = stringArrayResource(R.array.dropdown_gwangju_code)
            )
        }
        30 -> {
            CodeDropDownItem(
                dropdownList = stringArrayResource(R.array.dropdown_daejeon_gu),
                dropdownCode = stringArrayResource(R.array.dropdown_daejeon_code)
            )
        }
        31 -> {
            CodeDropDownItem(
                dropdownList = stringArrayResource(R.array.dropdown_ulsan_gu),
                dropdownCode = stringArrayResource(R.array.dropdown_ulsan_code)
            )
        }
        36 -> {
            CodeDropDownItem(
                dropdownList = stringArrayResource(R.array.dropdown_sejong_gu),
                dropdownCode = stringArrayResource(R.array.dropdown_sejong_code)
            )
        }
        41 -> {
            CodeDropDownItem(
                dropdownList = stringArrayResource(R.array.dropdown_gg_sigu),
                dropdownCode = stringArrayResource(R.array.dropdown_gg_code)
            )
        }
        42 -> {
            CodeDropDownItem(
                dropdownList = stringArrayResource(R.array.dropdown_gw_sigun),
                dropdownCode = stringArrayResource(R.array.dropdown_gw_code)
            )
        }
        43 -> {
            CodeDropDownItem(
                dropdownList = stringArrayResource(R.array.dropdown_cb_sigu),
                dropdownCode = stringArrayResource(R.array.dropdown_cb_code)
            )
        }
        44 -> {
            CodeDropDownItem(
                dropdownList = stringArrayResource(R.array.dropdown_cn_sigu),
                dropdownCode = stringArrayResource(R.array.dropdown_cn_code)
            )
        }
        45 -> {
            CodeDropDownItem(
                dropdownList = stringArrayResource(R.array.dropdown_jb_sigu),
                dropdownCode = stringArrayResource(R.array.dropdown_jb_code)
            )
        }
        46 -> {
            CodeDropDownItem(
                dropdownList = stringArrayResource(R.array.dropdown_jn_sigu),
                dropdownCode = stringArrayResource(R.array.dropdown_jn_code)
            )
        }
        47 -> {
            CodeDropDownItem(
                dropdownList = stringArrayResource(R.array.dropdown_gb_sigu),
                dropdownCode = stringArrayResource(R.array.dropdown_gb_code)
            )
        }
        48 -> {
            CodeDropDownItem(
                dropdownList = stringArrayResource(R.array.dropdown_gn_sigu),
                dropdownCode = stringArrayResource(R.array.dropdown_gn_code)
            )
        }
        50 -> {
            CodeDropDownItem(
                dropdownList = stringArrayResource(R.array.dropdown_jeju_si),
                dropdownCode = stringArrayResource(R.array.dropdown_jeju_code)
            )
        }
        else ->{
            //시코드가 0 이상아니면 노터치
        }
        
    }
}

@Composable
fun SidoDropDownMenu(modifier: Modifier = Modifier) {
    var isDropDownMenuExpanded by remember { mutableStateOf(true) }
    val dropdownSido = stringArrayResource(id = R.array.dropdown_sido)
    DropdownMenu(
        expanded = isDropDownMenuExpanded,
        onDismissRequest = { isDropDownMenuExpanded = true }
    ) {
        DropdownMenuItem(onClick = { sidoCode = 11 }) {
            Text(dropdownSido[0])
        }
        DropdownMenuItem(onClick = { sidoCode = 26 }) {
            Text(dropdownSido[1])
        }
        DropdownMenuItem(onClick = { sidoCode = 27 }) {
            Text(dropdownSido[2])
        }
        DropdownMenuItem(onClick = { sidoCode = 28 }) {
            Text(dropdownSido[3])
        }
        DropdownMenuItem(onClick = { sidoCode = 29 }) {
            Text(dropdownSido[4])
        }
        DropdownMenuItem(onClick = { sidoCode = 30 }) {
            Text(dropdownSido[5])
        }
        DropdownMenuItem(onClick = { sidoCode = 31 }) {
            Text(dropdownSido[6])
        }
        DropdownMenuItem(onClick = { sidoCode = 36 }) {
            Text(dropdownSido[7])
        }
        DropdownMenuItem(onClick = { sidoCode = 41 }) {
            Text(dropdownSido[8])
        }
        DropdownMenuItem(onClick = { sidoCode = 42 }) {
            Text(dropdownSido[9])
        }
        DropdownMenuItem(onClick = { sidoCode = 43 }) {
            Text(dropdownSido[10])
        }
        DropdownMenuItem(onClick = { sidoCode = 44 }) {
            Text(dropdownSido[11])
        }
        DropdownMenuItem(onClick = { sidoCode = 45 }) {
            Text(dropdownSido[12])
        }
        DropdownMenuItem(onClick = { sidoCode = 46 }) {
            Text(dropdownSido[13])
        }
        DropdownMenuItem(onClick = { sidoCode = 47 }) {
            Text(dropdownSido[14])
        }
        DropdownMenuItem(onClick = { sidoCode = 48 }) {
            Text(dropdownSido[15])
        }
        DropdownMenuItem(onClick = { sidoCode = 50 }) {
            Text(dropdownSido[16])
        }
    }
}


@Composable
fun CodeDropDownItem(
    dropdownList: Array<String>,
    dropdownCode: Array<String>
) {
    var isDropDownMenuExpanded by remember { mutableStateOf(true) }
    DropdownMenu(
        expanded = isDropDownMenuExpanded,
        onDismissRequest = { isDropDownMenuExpanded = true }
    ) {
        dropdownList.forEachIndexed { index, value ->
            DropdownMenuItem(onClick = { gugunCode = dropdownCode[index] }) {
                Text(value)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AreaDropdownMenuTheme {
        HomeScreen()
    }
}