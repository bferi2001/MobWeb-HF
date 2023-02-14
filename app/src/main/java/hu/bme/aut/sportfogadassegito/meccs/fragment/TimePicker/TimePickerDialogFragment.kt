package hu.bme.aut.sportfogadassegito.meccs.fragment.TimePicker

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import hu.bme.aut.sportfogadassegito.meccs.fragment.NewMeccsDialogFragment
import java.util.*

class TimePickerDialogFragment: DialogFragment(), TimePickerDialog.OnTimeSetListener{

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR)
        val min = c.get(Calendar.MINUTE)
        return TimePickerDialog(requireContext(), this, hour, min, false )
    }

    override fun onTimeSet(view: TimePicker?, hour: Int, min: Int) {
        val result = TimePickerResult(hour, min)
        findNavController()
            .previousBackStackEntry
            ?.savedStateHandle
            ?.set(NewMeccsDialogFragment.DATE_SELECTED_KEY, result)
    }


}