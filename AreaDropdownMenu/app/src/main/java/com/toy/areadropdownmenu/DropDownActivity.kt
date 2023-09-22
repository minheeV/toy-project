package com.toy.areadropdownmenu

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.toy.areadropdownmenu.ui.theme.AreaDropdownMenuTheme
import me.saket.cascade.CascadeDropdownMenu

class DropDownActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AreaDropdownMenuTheme {
                var code by remember { mutableStateOf("0") }
                Row {
                    FirstDropDownMenu {
                        code = it
                    }
                    if (code != "0")
                        SecondDropDownMenu(code = code)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExposedDropDownMenu() {
    var expanded by remember { mutableStateOf(true) }
    var selectItem by remember { mutableStateOf(" 선택해주세요") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        // text field
        TextField(
            value = selectItem,
            onValueChange = {},
            readOnly = true,
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            AreaCode.values().forEachIndexed { index, areaCode ->
                DropdownMenuItem(onClick = {
                    selectItem = areaCode.firstName
                    //code(areaCode.name)
                }) {
                    Text(text = areaCode.firstName)
                }
            }
        }

    }
}

@Composable
fun FirstDropDownMenu(code: (String) -> Unit) {
    Column {
        var expanded by remember { mutableStateOf(false) }
        var selectItem by remember { mutableStateOf(" 선택해주세요") }
        TextButton(onClick = {
            expanded = !expanded
        }) {
            Text(text = selectItem)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            properties = PopupProperties(
                dismissOnClickOutside = false
            ),
            offset = (DpOffset(0.dp, 20.dp)),
            modifier = Modifier.requiredSizeIn(maxHeight = 400.dp)
        ) {
            AreaCode.values().forEachIndexed { index, areaCode ->
                DropdownMenuItem(onClick = {
                    code(areaCode.name)
                    selectItem = areaCode.firstName
                    expanded = !expanded
                }) {
                    Text(text = areaCode.firstName)
                }
            }
        }
    }


}

@Composable
fun SecondDropDownMenu(code: String) {
    Column {
        var expanded by remember { mutableStateOf(true) }
        var firstCode: String
        var secondCode: String
        var selectItem by remember { mutableStateOf(" 선택해주세요") }

        TextButton(onClick = {
            expanded = !expanded
        }) {
            Text(text = selectItem)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            properties = PopupProperties(
                dismissOnClickOutside = false
            ),
            offset = DpOffset(200.dp, 0.dp),
            modifier = Modifier.requiredSizeIn(maxHeight = 600.dp)

        ) {
            val area = AreaCode.valueOf(code)
            val nameList = stringArrayResource(id = area.secondName)
            val codeList = stringArrayResource(id = area.secondCode)
            nameList.forEachIndexed { index, name ->
                DropdownMenuItem(onClick = {
                    firstCode = area.firstCode
                    secondCode = codeList[index]
                    Log.d(
                        "DropDown", "first : $firstCode," +
                                "second: $secondCode"
                    )
                    expanded = !expanded
                    selectItem = nameList[index]
                }) {
                    Text(text = name)
                }
            }
        }
    }

}


/**
 * CascadeDropdownMenu 사용
 */
@Composable
fun DrawCascadeDropdownMenu() {
    var expanded by remember { mutableStateOf(true) }
    var firstCode: String
    var secondCode: String
    Column(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        CascadeDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            AreaCode.values().forEachIndexed { index, areaCode ->
                DropdownMenuItem(text = { Text(areaCode.firstName) },
                    children = {
                        val nameList = stringArrayResource(id = areaCode.secondName)
                        val codeList = stringArrayResource(id = areaCode.secondCode)
                        nameList.forEachIndexed { index, name ->
                            androidx.compose.material3.DropdownMenuItem(
                                text = { Text(name) },
                                onClick = {
                                    firstCode = areaCode.firstCode
                                    secondCode = codeList[index]
                                    expanded = false
                                    Log.d(
                                        "DropDownActivity",
                                        "firstCode: $firstCode, secondCode: $secondCode"
                                    )
                                })
                        }
                    })
            }
        }
    }
}

@Preview
@Composable
fun PreviewDropDown() {
    AreaDropdownMenuTheme {
    }
}