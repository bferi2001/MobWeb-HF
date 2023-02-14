package hu.bme.aut.sportfogadassegito.meccs.fragment

import android.app.Dialog
import android.app.ProgressDialog.show
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.sportfogadassegito.R
import hu.bme.aut.sportfogadassegito.databinding.DialogNewMeccsBinding
import hu.bme.aut.sportfogadassegito.meccs.data.Meccs
import java.text.SimpleDateFormat
import android.widget.*
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import hu.bme.aut.sportfogadassegito.meccs.fragment.DatePicker.DatePickerDialogFragment
import java.util.Calendar


class NewMeccsDialogFragment(var szelvenyId: Long): DialogFragment() {
    val formatter = SimpleDateFormat("yyyy. MM. dd")

    //var willHomeWin: Boolean=true

    interface NewMeccsDialogListener {
        fun onMeccsCreated(newItem: Meccs)
    }

    private lateinit var listener: NewMeccsDialogListener

    private lateinit var binding: DialogNewMeccsBinding



    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewMeccsDialogListener
            ?: throw RuntimeException("Activity must implement the NewShoppingItemDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewMeccsBinding.inflate(LayoutInflater.from(context))

        binding.btnDateSelector.setOnClickListener{
            findNavController().navigate(R.id.action_newMeccsDialogFragment_to_datePickerDialogFragment)
        }

        binding.btnTimeSelector.setOnClickListener{
            findNavController().navigate(R.id.action_newMeccsDialogFragment_to_timePickerDialogFragment)
        }


        /*findNavController()
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<DatePickerDialogFragment.DatePickerResult>(DATE_SELECTED_KEY)
            ?.observe(viewLifecycleOwner) {
                val numHolidays = DataManager.holidays
                if (DataManager.remainingHolidays > 0){
                    DataManager.holidays = numHolidays + 1
                }
                loadHolidays()
            }
        loadHolidays()*/

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_meccs)
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok) { dialogInterface, i ->
                if (isValid()) {
                    listener.onMeccsCreated(getMeccs())
                }
            }
            .setNegativeButton(R.string.button_cancel, null)
            .create()
    }
    private fun isValid() = binding.etHomeTeam.text.toString().isNotEmpty()&&binding.etAwayTeam.text.toString().isNotEmpty()&&binding.etMatchOdds.text.isNotEmpty()

    private fun getMeccs() = Meccs(
        szelvenyId = szelvenyId,
        homeName = binding.etHomeTeam.text.toString(),
        awayName = binding.etAwayTeam.text.toString(),
        homeScore = binding.etHomeScore.text.toString().toIntOrNull() ?:0,
        awayScore = binding.etAwayScore.text.toString().toIntOrNull() ?:0,
        odds = binding.etMatchOdds.text.toString().toDouble(),
        expectedWinner=binding.isHome.isChecked,
        isWin = binding.isWin.isChecked,
        isEnded = binding.isEnded.isChecked,
        date = binding.etDate.text.toString()
    )
    /*public fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.etPickedHome ->
                    if (checked) {
                        willHomeWin = true
                    }
                R.id.etPickedAway ->
                    if (checked) {
                        willHomeWin = false
                    }
            }
        }
    }*/

    companion object {
        const val TAG = "NewMeccsDialogFragment"
        const val DATE_SELECTED_KEY = "date_selected"
        const val TIME_SELECTED_KEY = "time_selected"
    }

}