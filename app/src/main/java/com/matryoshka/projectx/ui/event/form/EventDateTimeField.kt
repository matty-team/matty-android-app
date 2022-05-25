package com.matryoshka.projectx.ui.event.form

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.matryoshka.projectx.R
import com.matryoshka.projectx.ui.common.FieldState
import com.matryoshka.projectx.ui.common.ListItem
import com.matryoshka.projectx.ui.common.pickers.DatePickerDialog
import com.matryoshka.projectx.ui.common.pickers.TimePickerDialog
import com.matryoshka.projectx.ui.theme.ProjectxTheme
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun EventDateTimeField(
    dateField: FieldState<LocalDate>,
    timeField: FieldState<LocalTime>
) {
    val formattedDate = remember(dateField.value) {
        dateField.value.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
    }
    val formattedTime = remember(timeField.value) {
        timeField.value.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
    }
    var displayDatePickerDialog by remember {
        mutableStateOf(false)
    }
    var displayTimePickerDialog by remember {
        mutableStateOf(false)
    }

    Column {
        ListItem(
            icon = {
                Icon(
                    Icons.Outlined.Schedule,
                    contentDescription = stringResource(R.string.date_time),
                    tint = MaterialTheme.colors.primary
                )
            },
            trailing = {
                Text(
                    text = formattedTime,
                    modifier = Modifier.clickable { displayTimePickerDialog = true },
                )
            }
        ) {
            Text(
                text = formattedDate,
                modifier = Modifier.clickable { displayDatePickerDialog = true },
            )
        }
    }

    DatePickerDialog(
        display = displayDatePickerDialog,
        date = dateField.value,
        onCancel = { displayDatePickerDialog = false },
        onSubmit = { selectedDate ->
            dateField.onChange(selectedDate)
            displayDatePickerDialog = false
        }
    )

    TimePickerDialog(
        display = displayTimePickerDialog,
        time = timeField.value,
        onCancel = { displayTimePickerDialog = false },
        onSubmit = { selectedTime ->
            timeField.onChange(selectedTime)
            displayTimePickerDialog = false
        }
    )
}

@Preview(showBackground = true)
@Composable
fun EventDateTimeItemPreview() {
    ProjectxTheme {
        EventDateTimeField(
            timeField = FieldState(LocalTime.now()),
            dateField = FieldState(LocalDate.now())
        )
    }
}