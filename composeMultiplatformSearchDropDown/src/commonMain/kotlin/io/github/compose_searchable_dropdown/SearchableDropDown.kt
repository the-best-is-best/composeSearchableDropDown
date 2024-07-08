package com.tbib.composesearchabledropdown

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.tbib.composesearchabledropdown.states.DropdownState

@Composable
fun <T> SearchableDropDown(
    modifier: Modifier = Modifier,
    listOfItems: List<T>,
    enable: Boolean = true,
    readOnly: Boolean = true,
    placeholder: @Composable (() -> Unit) = { Text(text = "Select Option") },
    searchPlaceHolder: @Composable (() -> Unit) = { Text(text = "Search") },
    openedIcon: ImageVector = Icons.Outlined.KeyboardArrowUp,
    closedIcon: ImageVector = Icons.Outlined.KeyboardArrowDown,
    parentTextFieldCornerRadius: Dp = 12.dp,
    colors: TextFieldColors = TextFieldDefaults.colors(),
    onDropDownItemSelected: (T) -> Unit,
    dropdownItem: @Composable (T) -> Unit,
    isError: Boolean = false,
    idDialog: Boolean = true,
    searchIn: ((item: T) -> String)? = null,
    state: DropdownState<T>,
    selectedOptionTextDisplay: (T) -> String,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val itemHeights = remember { mutableStateMapOf<Int, Int>() }
    val baseHeight = 530.dp
    val density = LocalDensity.current

    val maxHeight = remember(itemHeights.toMap()) {
        if (itemHeights.keys.toSet() != listOfItems.indices.toSet()) {
            return@remember baseHeight
        }
        val baseHeightInt = with(density) { baseHeight.toPx().toInt() }
        var sum = with(density) { DropdownMenuVerticalPadding.toPx().toInt() } * 2
        for ((_, itemSize) in itemHeights.entries.sortedBy { it.value }.associate { it.toPair() }) {
            sum += itemSize
            if (sum >= baseHeightInt) {
                return@remember with(density) { (sum - itemSize / 2).toDp() }
            }
        }
        baseHeight
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
    ) {
        OutlinedTextField(
            modifier = modifier,
            colors = colors,
            value = if (state.selectedOptionText.isEmpty()) "" else selectedOptionTextDisplay(listOfItems.first { it.toString() == state.selectedOptionText }),
            readOnly = readOnly,
            enabled = enable,
            onValueChange = { state.selectedOptionText = it },
            placeholder = placeholder,
            trailingIcon = {
                IconToggleButton(
                    checked = state.expanded,
                    onCheckedChange = { state.expanded = it },
                ) {
                    Icon(
                        imageVector = if (state.expanded) openedIcon else closedIcon,
                        contentDescription = null,
                    )
                }
            },
            shape = RoundedCornerShape(parentTextFieldCornerRadius),
            isError = isError,
            interactionSource = remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        keyboardController?.show()
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Release) {
                                state.expanded = !state.expanded
                            }
                        }
                    }
                },
        )

        if (state.expanded) {
            if (idDialog) {
                Dialog(onDismissRequest = { state.expanded = false }) {
                    DisplayDropDown(
                        listOfItems = listOfItems,
                        modifier = modifier,
                        maxHeight = maxHeight,
                        onDropDownItemSelected = { item ->
                            state.selectedOptionText = item.toString()
                            state.expanded = false
                            onDropDownItemSelected(item)
                        },
                        dropdownItem = dropdownItem,
                        searchPlaceHolder = searchPlaceHolder,
                        keyboardController = keyboardController,
                        onChange = {
                            state.selectedOptionText = it.toString()
                            state.expanded = false
                        },
                        searchIn = searchIn
                    )
                }
            } else {
                DisplayDropDown(
                    listOfItems = listOfItems,
                    modifier = modifier,
                    maxHeight = maxHeight,
                    onDropDownItemSelected = { item ->
                        state.selectedOptionText = item.toString()
                        state.expanded = false
                        onDropDownItemSelected(item)
                    },
                    dropdownItem = dropdownItem,
                    searchPlaceHolder = searchPlaceHolder,
                    keyboardController = keyboardController,
                    onChange = {
                        state.selectedOptionText = it.toString()
                        state.expanded = false
                    },
                    searchIn = searchIn
                )
            }
        }
    }
}

private val DropdownMenuVerticalPadding = 8.dp
