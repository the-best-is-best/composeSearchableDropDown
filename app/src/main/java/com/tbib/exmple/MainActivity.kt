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
import com.tbib.composesearchabledropdown.SearchableDropDown
import com.tbib.exmple.ui.theme.ExmpleTheme

data class ExampleData(
    val id: Int,
    val name: String
)



class MainActivity : ComponentActivity() {
    private val data = listOf(
        ExampleData(1, "First"),
        ExampleData(2, "Second"),
        ExampleData(3, "Third"),
        ExampleData(4, "Fourth"),
        ExampleData(5, "Fifth"),
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExmpleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                 Column{

                         SearchableDropDown(
                             listOfItems = data,
                              placeholder = {
                                    Text(text = "اختار")
                              },
                             searchPlaceHolder = {
                                 Text(text = "بحث")
                             },

                             //defaultItem = data[2],
                             onDropDownItemSelected = {
                                 Log.d("get v", it.name)
                             },
                             dropdownItem = {
                                 Text("${it.id} - ${it.name}")
                             },
                             selectedOptionTextDisplay = {it.name}
                         )

                 }
                }
            }
        }
    }
}

