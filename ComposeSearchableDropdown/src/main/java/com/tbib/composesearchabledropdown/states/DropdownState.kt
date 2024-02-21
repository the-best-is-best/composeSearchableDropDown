package com.tbib.composesearchabledropdown.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun<T> rememberDropdownStates( value: T? = null ): DropdownState<T> {
    return rememberSaveable(saver = DropdownState.Saver()  ) {
        DropdownState(value)
    }
}
class DropdownState<T> internal constructor(
    value: T?
) {
    var selectedOptionText by mutableStateOf("")

    var expanded by mutableStateOf(false)

    init {
        if (value != null) {
            selectedOptionText = selectedOptionText.ifEmpty { value.toString() }
        }
    }
    fun clear(defaultValue:T? = null) {
        if (defaultValue != null) {
            selectedOptionText = selectedOptionText.ifEmpty { defaultValue.toString() }
        }

    }
    companion object {
        fun <T> Saver(): Saver<DropdownState<T>, *> = listSaver(

            save = {
                listOf(
                    it.selectedOptionText,
                    it.expanded
                )
            },
            restore = {
                val data = DropdownState<T>(null)
                data.selectedOptionText = it[0] as String? ?: ""
                data.expanded = it[1] as Boolean? ?: false
                data
            }
        )

    }
}


