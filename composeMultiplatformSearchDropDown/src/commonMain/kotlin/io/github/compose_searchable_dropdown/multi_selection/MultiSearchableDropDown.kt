package io.github.compose_searchable_dropdown.multi_selection

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.github.compose_searchable_dropdown.states.MultiDropdownState

@Composable
fun <T> MultiSearchableDropDown(
    modifier: Modifier = Modifier,
    listOfItems: List<T>,
    enable: Boolean = true,
    readOnly: Boolean = true,
    placeholder: @Composable () -> Unit = { Text(text = "Select Options") },
    searchPlaceHolder: @Composable () -> Unit = { Text(text = "Search") },
    openedIcon: ImageVector = Icons.Outlined.KeyboardArrowUp,
    openedIconColor: Color = LocalContentColor.current,
    closedIcon: ImageVector = Icons.Outlined.KeyboardArrowDown,
    closedIconColor: Color = LocalContentColor.current,
    parentTextFieldCornerRadius: Dp = 12.dp,
    colors: TextFieldColors = TextFieldDefaults.colors(),
    onSelectionChanged: (Set<T>) -> Unit,
    dropdownItem: @Composable (T, Boolean) -> Unit, // العنصر مع حالة الاختيار
    state: MultiDropdownState<T>,
    selectedOptionTextDisplay: (Set<T>) -> String,
    showClearButton: Boolean = false,
    selectedTextStyle: TextStyle? = null,
    dropDownTextStyle: TextStyle? = null,
    idDialog: Boolean = true,
    disableSelectItem: Set<T>? = null,
    searchIn: ((item: T) -> String)? = null,
) {
    val itemHeights = remember { mutableStateMapOf<Int, Int>() }
    val baseHeight = 530.dp
    val density = LocalDensity.current

    val maxHeight = remember(itemHeights.toMap()) {
        if (itemHeights.keys.toSet() != listOfItems.indices.toSet()) {
            return@remember baseHeight
        }
        val baseHeightPx = with(density) { baseHeight.toPx().toInt() }
        var totalHeight = with(density) { DropdownMenuVerticalPadding.toPx().toInt() * 2 }
        for ((_, itemSize) in itemHeights.entries.sortedBy { it.value }) {
            totalHeight += itemSize
            if (totalHeight >= baseHeightPx) {
                return@remember with(density) { (totalHeight - itemSize / 2).toDp() }
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
            value = selectedOptionTextDisplay(state.selectedItems),
            textStyle = selectedTextStyle ?: TextStyle.Default,
            readOnly = readOnly,
            enabled = enable,
            onValueChange = { /* لا تقبل الكتابة */ },
            placeholder = placeholder,
            trailingIcon = {
                Row {
                    if (showClearButton && state.selectedItems.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable {
                                    state.selectedItems = emptySet()
                                    onSelectionChanged(emptySet())
                                },
                        ) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = null,
                                tint = LocalContentColor.current,
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { state.expanded = !state.expanded },
                    ) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = if (state.expanded) openedIcon else closedIcon,
                            contentDescription = null,
                            tint = if (state.expanded) openedIconColor else closedIconColor,
                        )
                    }
                    Spacer(modifier = Modifier.width(30.dp))
                }
            },
            shape = RoundedCornerShape(parentTextFieldCornerRadius),
            isError = false,
            interactionSource = remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
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
                    MultiDisplayDropDown(
                        listOfItems = listOfItems,
                        modifier = modifier,
                        maxHeight = maxHeight,
                        onSelectionChanged = {
                            state.selectedItems = it
                            onSelectionChanged(it)
                        },
                        dropdownItem = dropdownItem,
                        searchPlaceHolder = searchPlaceHolder,
                        searchIn = searchIn,
                        selectedItems = state.selectedItems,
                        disableSelectItem = disableSelectItem
                    )
                }
            } else {
                MultiDisplayDropDown(
                    listOfItems = listOfItems,
                    modifier = modifier,
                    maxHeight = maxHeight,
                    onSelectionChanged = {
                        state.selectedItems = it
                        onSelectionChanged(it)
                    },
                    dropdownItem = dropdownItem,
                    searchPlaceHolder = searchPlaceHolder,
                    searchIn = searchIn,
                    selectedItems = state.selectedItems,
                    disableSelectItem = disableSelectItem

                )
            }
        }
    }
}

private val DropdownMenuVerticalPadding = 8.dp
