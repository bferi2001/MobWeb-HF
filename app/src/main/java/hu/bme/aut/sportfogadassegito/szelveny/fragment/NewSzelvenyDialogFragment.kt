package hu.bme.aut.sportfogadassegito.szelveny.fragment


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import hu.bme.aut.sportfogadassegito.R
import hu.bme.aut.sportfogadassegito.databinding.DialogNewSzelvenyBinding
import hu.bme.aut.sportfogadassegito.szelveny.data.Szelveny
import java.text.SimpleDateFormat
import java.util.*

class NewSzelvenyDialogFragment: DialogFragment() {
    val formatter = SimpleDateFormat("yyyy. MM. dd")
    val date = Date()
    val current = formatter.format(date)
    interface NewSzelvenyDialogListener {
        fun onSzelvenyCreated(newItem: Szelveny)
    }

    private lateinit var listener: NewSzelvenyDialogListener

    private lateinit var binding: DialogNewSzelvenyBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewSzelvenyDialogListener
            ?: throw RuntimeException("Activity must implement the NewSzelvenyDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewSzelvenyBinding.inflate(LayoutInflater.from(context))


        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_szelveny)
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok) { dialogInterface, i ->
                if (isValid()) {
                    listener.onSzelvenyCreated(getSzelveny())
                }
            }
            .setNegativeButton(R.string.button_cancel, null)
            .create()
    }
    private fun isValid() = binding.etPrice.text.isNotEmpty()&&binding.etexpectedPrize.text.isNotEmpty()

    private fun getSzelveny() = Szelveny(
        discription = binding.etDescription.text.toString(),
        date = current.toString(),
        price = binding.etPrice.text.toString().toInt(),
        expectedPrize = binding.etexpectedPrize.text.toString().toInt(),
        isWin = binding.isWin.isChecked,
        isEnded = binding.isEnded.isChecked
    )
    companion object {
        const val TAG = "NewSzelvenyDialogFragment"
    }
}
