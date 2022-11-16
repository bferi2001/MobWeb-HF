package hu.bme.aut.sportfogadassegito.meccs


import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.sportfogadassegito.databinding.ActivityMeccsBinding
import hu.bme.aut.sportfogadassegito.meccs.adapter.MeccsAdapter
import hu.bme.aut.sportfogadassegito.meccs.data.Meccs
import hu.bme.aut.sportfogadassegito.meccs.data.MeccsDatabase
import hu.bme.aut.sportfogadassegito.meccs.fragment.NewMeccsDialogFragment
import hu.bme.aut.sportfogadassegito.team.data.TeamDatabase
import kotlin.concurrent.thread
import kotlin.properties.Delegates

class MeccsActivity(): AppCompatActivity(), MeccsAdapter.MeccsClickListener, NewMeccsDialogFragment.NewMeccsDialogListener{
    var szelvenyId by Delegates.notNull<Long>()

    private lateinit var binding: ActivityMeccsBinding

    private lateinit var database: MeccsDatabase
    private lateinit var adapter: MeccsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeccsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)





        val recivedIntent = intent
        szelvenyId = recivedIntent.getLongExtra("SZELVENY_SZAM", 0)

        database = MeccsDatabase.getDatabase(applicationContext)


        binding.fab.setOnClickListener {
            NewMeccsDialogFragment(szelvenyId).show(
                supportFragmentManager,
                NewMeccsDialogFragment.TAG
            )
        }

        initRecyclerView()
    }
    private fun initRecyclerView() {
        adapter = MeccsAdapter(this)
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.meccsDao().getSelected(szelvenyId)
            runOnUiThread {
                adapter.update(items)
            }
        }
    }
    override fun onItemChanged(item: Meccs) {
        thread {
            database.meccsDao().update(item)
            Log.d("MeccsActivity", "Meccs update was successful")
        }
    }

    override fun onItemDeleted(item: Meccs) {
        thread {
            val insertId = database.meccsDao().deleteItem(item)
            runOnUiThread {
                adapter.deleteItem(item)
            }
        }
    }

    override fun onMeccsCreated(newItem: Meccs) {
        thread {
            newItem.szelvenyId=szelvenyId
            val insertId = database.meccsDao().insert(newItem)
            newItem.id = insertId
            runOnUiThread {
                adapter.addItem(newItem)
            }
        }
    }
}