import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.matteo.mybaby2.R
import java.time.Instant
import java.time.ZoneId


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = null)
    val timePickerState = rememberTimePickerState()
    val isShowingDate = remember{ mutableStateOf(true) }

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                if(isShowingDate.value){
                    isShowingDate.value = false
                } else {
                    val selectedDate = Instant.ofEpochMilli(
                        datePickerState.selectedDateMillis ?: System.currentTimeMillis()
                    )
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                    val dateTime = selectedDate.atTime(
                        timePickerState.hour,
                        timePickerState.minute
                    ) // Set hours and minutes
                    val dateTimeMillis =
                        dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

                    onDateSelected(dateTimeMillis)
                    onDismiss()
                }
            }) {
                Text(stringResource(R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    ) {
        if(isShowingDate.value) {
            DatePicker(state = datePickerState)
        } else {
            TimePicker(state = timePickerState, modifier = Modifier.padding(16.dp))
        }

    }
}