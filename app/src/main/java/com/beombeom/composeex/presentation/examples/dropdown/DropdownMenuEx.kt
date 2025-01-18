package com.beombeom.composeex.presentation.examples.dropdown

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beombeom.composeex.presentation.MainHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuEx(title : String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        MainHeader(title = title)

        DefaultDropdownMenu()

        Spacer(modifier = Modifier.height(16.dp))

        ExpandableDropdownMenuWithTextField()
    }
}

@Composable
fun DefaultDropdownMenu() {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("Option 1", "Option 2", "Option 3")
    var selectedIndex by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
    ) {
        Text(
            text = items[selectedIndex],
            modifier = Modifier
                .clickable { expanded = !expanded }
                .padding(16.dp)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEachIndexed { index, label ->
                DropdownMenuItem(
                    onClick = {
                        selectedIndex = index
                        expanded = false
                    },
                    text = { Text(text = label) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableDropdownMenuWithTextField() {
    // 드롭다운 상태 관리
    var isDropDownMenuExpanded by rememberSaveable { mutableStateOf(false) }
    val menuItems = listOf("Option 1", "Option 2", "Option 3")
    var selectedOption by rememberSaveable { mutableStateOf(menuItems.first()) } // 기본 선택값

    // 드롭다운 UI
    ExposedDropdownMenuBox(
        modifier = Modifier.padding(horizontal = 16.dp),
        expanded = isDropDownMenuExpanded,
        onExpandedChange = { isDropDownMenuExpanded = !isDropDownMenuExpanded }
    ) {

        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
                .fillMaxWidth(),
            label = { Text("Select an Option") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropDownMenuExpanded)
            }
        )

        ExposedDropdownMenu(
            expanded = isDropDownMenuExpanded,
            onDismissRequest = { isDropDownMenuExpanded = false }
        ) {
            menuItems.forEach { menuItem ->
                DropdownMenuItem(
                    onClick = {
                        selectedOption = menuItem // 선택된 항목 설정
                        isDropDownMenuExpanded = false
                    },
                    text = { Text(text = menuItem) }
                )
            }
        }
    }
}