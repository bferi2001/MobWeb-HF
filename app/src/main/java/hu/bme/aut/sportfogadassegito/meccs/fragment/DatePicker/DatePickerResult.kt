package hu.bme.aut.sportfogadassegito.meccs.fragment.DatePicker

import java.io.Serializable

data class DatePickerResult(
    val year: Int,
    val month: Int,
    val dayOfMonth: Int,
) : Serializable