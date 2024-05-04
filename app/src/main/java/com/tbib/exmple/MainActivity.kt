package com.tbib.exmple

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.tbib.composesearchabledropdown.SearchableDropDown
import com.tbib.composesearchabledropdown.states.rememberDropdownStates
import com.tbib.exmple.ui.theme.ExmpleTheme

data class ExampleData(
    val id: Int,
    val name: String
)



class MainActivity : ComponentActivity() {
    private val data = List(1000) { index ->
        ExampleData(index + 1, "Item ${index + 1}")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExmpleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val selectedItemsState = rememberDropdownStates<ExampleData>(value = data[2])

                    Column{

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
                                 Log.d("get v", it.name)
                             },
                             dropdownItem = {
                                 Text("${it.id} - ${it.name}", fontSize = 20.sp)
                             },
                             selectedOptionTextDisplay = {it.name},
//                             searchIn = {
//                                 it.name
//                             }
                         )

                 }
                }
            }
        }
    }
}

