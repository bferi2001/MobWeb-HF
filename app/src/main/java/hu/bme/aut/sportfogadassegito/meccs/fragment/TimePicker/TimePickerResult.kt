package hu.bme.aut.sportfogadassegito.meccs.fragment.TimePicker

import java.io.Serializable

data class TimePickerResult(
    val hour: Int,
    val min: Int,
) : Serializable