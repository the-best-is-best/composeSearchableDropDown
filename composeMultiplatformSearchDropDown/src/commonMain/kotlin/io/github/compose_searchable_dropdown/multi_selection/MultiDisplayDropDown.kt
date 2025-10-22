package io.github.compose_searchable_dropdown.multi_selection

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
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
internal fun <T> MultiDisplayDropDown(
    maxHeight: Dp,
    modifier: Modifier = Modifier,
    listOfItems: List<T>,
    onSelectionChanged: (Set<T>) -> Unit,
    dropdownItem: @Composable (item: T, isSelected: Boolean) -> Unit,
    searchPlaceHolder: @Composable () -> Unit,
    searchIn: ((item: T) -> String)? = null,
    selectedItems: Set<T>,
    disableSelectItem: Set<T>?,
    ) {
    var searchedOption by rememberSaveable { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val filteredItems = remember(searchedOption, listOfItems) {
        if (searchIn != null && searchedOption.isNotEmpty()) {
            listOfItems.filter {
                searchIn(it).contains(searchedOption, ignoreCase = true)
            }
        } else {
            listOfItems
        }
    }

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
                    onValueChange = {
                        selectedSport -> searchedOption = selectedSport
                                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
                    },
                    placeholder = {
                        searchPlaceHolder()
                    },
                    interactionSource = remember { MutableInteractionSource() }
                )
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.padding(18.dp)
            ) {
                items(filteredItems) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if(disableSelectItem?.contains(item) == true) return@clickable
                                val newSelection = selectedItems.toMutableSet()
                                if (selectedItems.contains(item)) {
                                    newSelection.remove(item)
                                } else {
                                    newSelection.add(item)
                                }
                                onSelectionChanged(newSelection)
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        dropdownItem(item, selectedItems.contains(item))
                    }
                }
            }
        }
    }
}
