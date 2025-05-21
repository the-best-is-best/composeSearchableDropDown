package io.github.compose_searchable_dropdown.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

class MultiDropdownState<T> internal constructor(
    selectedItems: Set<T> = emptySet(),
) {
    var selectedItems by mutableStateOf(selectedItems)
    var expanded by mutableStateOf(false)

    fun clear() {
        selectedItems = emptySet()
    }

    companion object {
        fun <T> Saver(): Saver<MultiDropdownState<T>, *> = listSaver(
            save = {
                val selectedStrings = it.selectedItems.map { it.toString() }
                listOf(selectedStrings, it.expanded)
            },
            restore = {
                val restoredItems = (it[0] as? List<String>)?.toSet() ?: emptySet()
                val state = MultiDropdownState<T>()
                state.selectedItems = restoredItems as Set<T>
                state.expanded = it[1] as Boolean
                state
            }
        )
    }
}

@Composable
fun <T> rememberMultiDropdownState(initialSelected: Set<T> = emptySet()): MultiDropdownState<T> {
    return rememberSaveable(saver = MultiDropdownState.Saver()) {
        MultiDropdownState(initialSelected)
    }
}
