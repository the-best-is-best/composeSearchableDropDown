package io.github.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.tbib.composesearchabledropdown.SearchableDropDown
import com.tbib.composesearchabledropdown.states.rememberDropdownStates
import io.github.sample.theme.AppTheme


@Composable
internal fun App() {
    data class ExampleData(
        val id: Int,
        val name: String
    )

    val data = List(1000) { index ->
        ExampleData(index + 1, "Item ${index + 1}")
    }
    val selectedItemsState = rememberDropdownStates<ExampleData>(value = data[2])


    MaterialTheme (
        colorScheme =  lightColorScheme()
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

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
                    println("get v ${it.name}")
                },
                dropdownItem = {
                    Text("${it.id} - ${it.name}", fontSize = 20.sp)
                },
                selectedOptionTextDisplay = { it.name },
                searchIn = {
                    it.name
                }
            )

        }
    }
}
