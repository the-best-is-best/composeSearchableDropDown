package io.github.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.outlined.CheckBoxOutlineBlank
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.compose_searchable_dropdown.multi_selection.MultiSearchableDropDown
import io.github.compose_searchable_dropdown.normal.SearchableDropDown
import io.github.compose_searchable_dropdown.states.rememberDropdownStates
import io.github.compose_searchable_dropdown.states.rememberMultiDropdownState

data class ExampleData(
    val id: Int,
    val name: String
)

@Composable
internal fun App() {


    val data = List(1000) { index ->
        ExampleData(index + 1, "Item ${index + 1}")
    }
    val selectedItemsState = rememberDropdownStates(value = data[2])
    val selectedItems1State = rememberDropdownStates(value = data[3])


    MaterialTheme (
        colorScheme =  lightColorScheme()
    ){
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {

                SearchableDropDown(
                    listOfItems = data,
                    state = selectedItemsState,
                    placeholder = {
                        Text(text = "اختار")
                    },
                    searchPlaceHolder = {
                        Text(text = "بحث")
                    },
                    // idDialog = false,

                    //defaultItem = data[2],
                    onDropDownItemSelected = {
                        println("get v ${it?.name}")
                    },
                    disableSelectItem = data.first { it.id == 10 },

                    dropdownItem = {
                        Text("${it.id} - ${it.name}", fontSize = 20.sp ,
                            color = if (it.id == 10) {
                                Color.Red
                            } else {
                                Color.Black
                            }
                        )
                    },
                    selectedOptionTextDisplay = { it.name },
                    searchIn = {
                        it.name
                    },
                    showClearButton = true,
                    openedIconColor = Color.Cyan,
                    closedIconColor = Color.Magenta,
                )
                Spacer(Modifier.height(20.dp))
                SearchableDropDown(
                    listOfItems = data,
                    state = selectedItems1State,
                    placeholder = {
                        Text(text = "اختار")
                    },

                    // idDialog = false,

                    //defaultItem = data[2],
                    onDropDownItemSelected = {
                        println("get v ${it?.name}")
                    },
                    dropdownItem = {
                        Text("${it.id} - ${it.name}", fontSize = 20.sp)
                    },
                    selectedOptionTextDisplay = { it.name },
                    showClearButton = true,
                    openedIconColor = Color.Cyan,
                    closedIconColor = Color.Magenta,
                )
            }
            item {
                MultiSelectExample()
            }
            item {
                MultiSelectExample(
                    listDisableItem = data.filter { it.id == 10  || it.id == 11}.toSet(),
                )
            }
        }

    }

}



@Composable
fun MultiSelectExample(
    listDisableItem : Set<ExampleData>? = null,
) {

    val data = List(1000) { index ->
        ExampleData(index + 1, "Item ${index + 1}")
    }

    val multiState = rememberMultiDropdownState<ExampleData>()

    MaterialTheme(
        colorScheme = lightColorScheme()
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MultiSearchableDropDown(
                listOfItems = data,
                state = multiState,
                placeholder = { Text("اختر عدة عناصر") },
                searchPlaceHolder = { Text("بحث...") },
                showClearButton = true,
                openedIconColor = Color.Blue,
                closedIconColor = Color.Gray,
                disableSelectItem = listDisableItem,
                onSelectionChanged = {
                    println("Selected Items: ${it.map { it.name }}")
                },
                selectedOptionTextDisplay = { selectedSet ->
                    if (selectedSet.isEmpty()) "لم يتم الاختيار"
                    else selectedSet.joinToString(", ") { it.name }
                },
                dropdownItem = { item, isSelected ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = if (isSelected) Icons.Outlined.CheckBox else Icons.Outlined.CheckBoxOutlineBlank,
                            contentDescription = null,
                            tint = if (isSelected) Color.Green else Color.Gray
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "${item.id} - ${item.name}", fontSize = 18.sp,
                            color =
                                if(listDisableItem?.contains(item) == true ) {
                                    Color.Red
                                } else{
                                    Color.Black
                                }

                        )
                    }
                },
                searchIn = { it.name }
            )
        }
    }
}
