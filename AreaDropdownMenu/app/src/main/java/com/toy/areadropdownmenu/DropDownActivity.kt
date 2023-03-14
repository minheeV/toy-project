package com.toy.areadropdownmenu

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringArrayResource
import com.toy.areadropdownmenu.ui.theme.AreaDropdownMenuTheme

class DropDownActivity : ComponentActivity() {
    private var TAG = "DropDownActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AreaDropdownMenuTheme {
                var firstCode by remember { mutableStateOf("0") }
                FirstDropDown {
                    firstCode = it
                }
                //SecondDropDown(firstCode)
            }
        }
    }
}

@Composable
fun FirstDropDown(code: (String) -> Unit) {
    Column {
        code(drawDropDownMenu(area = AreaCode.All))
    }
}

@Composable
fun SecondDropDown(si: String) {
    var firstCode by remember { mutableStateOf(si) }

    Column() {
        drawDropDownMenu(
            area =
            AreaCode.values().filter {
                it.code.toString() == firstCode
            }[0]
        )
    }
}

@Composable
fun drawDropDownMenu(area: AreaCode): String {
    var expanded by remember { mutableStateOf(true) }
    val nameList = stringArrayResource(id = area.nameList)
    val codeList = stringArrayResource(id = area.codeList)
    var code by remember { mutableStateOf("0") }
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        codeList.forEachIndexed { index, value ->
            DropdownMenuItem(onClick = {
                //expanded = false
                code = value
            }
            ) {
                Text(text = nameList[index])
                //expanded = false
            }
        }
    }
    return code
}