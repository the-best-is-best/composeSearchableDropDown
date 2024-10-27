package io.github.compose_searchable_dropdown

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun <T> DisplayDropDown(
    maxHeight: Dp,
    modifier: Modifier = Modifier,
    listOfItems: List<T>,
    onDropDownItemSelected: (item: T) -> Unit,
    dropdownItem: @Composable (item: T) -> Unit,
    searchPlaceHolder: @Composable () -> Unit,
    // keyboardController: SoftwareKeyboardController?,
    onChange: (value: T) -> Unit,
    searchIn: ((item: T) -> String)? = null,
    dropDownTextStyle: TextStyle?
) {
    var searchedOption by rememberSaveable { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    var filteredItems = mutableListOf<T>()

    Surface(
        modifier = Modifier.requiredSizeIn(maxHeight = maxHeight),
        shape = RoundedCornerShape(25.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            if (searchIn != null) {
                OutlinedTextField(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .focusRequester(focusRequester),
                    value = searchedOption,
                    textStyle = dropDownTextStyle ?: TextStyle.Default,
                    onValueChange = { selectedSport ->
                        searchedOption = selectedSport
                        filteredItems = listOfItems.filter {
                            searchIn(it).contains(
                                searchedOption,
                                ignoreCase = true,
                            )
                        }.toMutableList()
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
                    },
                    placeholder = {
                        searchPlaceHolder()
                    },
                    interactionSource = remember { MutableInteractionSource() }
                        .also { interactionSource ->
                            LaunchedEffect(interactionSource) {
                                interactionSource.interactions.collect { interaction ->
                                    if (interaction is PressInteraction.Release) {
                                        // keyboardController?.show() // Show keyboard on user interaction
                                    }
                                }
                            }
                        },
                )
            }
            val items = if (filteredItems.isEmpty() && searchedOption.isEmpty()) {
                listOfItems
            } else {
                filteredItems
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.padding(18.dp)
            ) {
                itemsIndexed(items) { _, item ->
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            //keyboardController?.hide()
                            onDropDownItemSelected(item)
                            searchedOption = ""
                            onChange(item)
                        }) {
                        dropdownItem(item)
                    }
                }
            }
        }
    }
}
