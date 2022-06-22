package com.matryoshka.projectx.ui.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.matryoshka.projectx.ui.validator.Validator

typealias TextFieldState = FieldState<String>

fun textFieldState(
    initialValue: String = "",
    validators: List<Validator<String>> = emptyList(),
    onChange: ((prevValue: String, newValue: String, sourceType: SourceType) -> Boolean)? = null,
) = FieldState(initialValue, validators, onChange)

fun switchState(
    checked: Boolean,
    onChange: ((prevValue: Boolean?, newValue: Boolean, sourceType: SourceType) -> Boolean)? = null
) = FieldState(checked, onChange = onChange)

fun numberFieldState(
    initialValue: Int? = null,
    validators: List<Validator<String?>> = emptyList()
) = FieldState(initialValue?.toString(), validators)

val FieldState<String?>.numValue
    get() = value?.toIntOrNull()

class FieldState<T>(
    initialValue: T,
    private val validators: List<Validator<T>> = emptyList(),
    private val onChange: ((prevValue: T, newValue: T, sourceType: SourceType) -> Boolean)? = null,
) {
    var value by mutableStateOf(initialValue)
        private set

    var error by mutableStateOf<FieldError?>(null)
        private set

    val hasError: Boolean
        get() = error != null

    fun onChange(newValue: T, sourceType: SourceType = SourceType.USER) {
        val shouldUpdate = onChange?.invoke(value, newValue, sourceType) ?: true
        if (shouldUpdate) {
            error = null
            this.value = newValue
        }
    }

    suspend fun validate(): Boolean {
        return if (hasError) {
            false
        } else {
            validators.firstOrNull {
                val error = it.validate(value)
                if (error != null) {
                    this.error = error
                    true
                } else {
                    false
                }
            } == null
        }
    }
}

enum class SourceType {
    USER,
    APPLICATION
}