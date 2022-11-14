package hu.bme.aut.sportfogadassegito.szelveny

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.sportfogadassegito.databinding.ActivitySzelvenyBinding
import hu.bme.aut.sportfogadassegito.meccs.MeccsActivity
import hu.bme.aut.sportfogadassegito.meccs.adapter.MeccsAdapter
import hu.bme.aut.sportfogadassegito.meccs.data.MeccsDatabase
import hu.bme.aut.sportfogadassegito.szelveny.adapter.SzelvenyAdapter
import hu.bme.aut.sportfogadassegito.szelveny.data.Szelveny
import hu.bme.aut.sportfogadassegito.szelveny.data.SzelvenyDatabase
import hu.bme.aut.sportfogadassegito.szelveny.fragment.NewSzelvenyDialogFragment
import kotlin.concurrent.thread

class SzelvenyActivity: AppCompatActivity(), SzelvenyAdapter.SzelvenyClickListener, NewSzelvenyDialogFragment.NewSzelvenyDialogListener{
    private lateinit var binding: ActivitySzelvenyBinding

    private lateinit var database: SzelvenyDatabase
    private lateinit var adapter: SzelvenyAdapter

    private lateinit var meccsDB: MeccsDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySzelvenyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        database = SzelvenyDatabase.getDatabase(applicationContext)
        meccsDB = MeccsDatabase.getDatabase(applicationContext)

        binding.fab.setOnClickListener {
            NewSzelvenyDialogFragment().show(
                supportFragmentManager,
                NewSzelvenyDialogFragment.TAG
            )
        }

        initRecyclerView()
    }
    private fun initRecyclerView() {
        adapter = SzelvenyAdapter(this)
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.szelvenyDao().getAll()
            runOnUiThread {
                adapter.update(items)
            }
        }
    }
    override fun onItemChanged(item: Szelveny) {
        thread {
            database.szelvenyDao().update(item)
            Log.d("SzelvenyActivity", "Szelveny update was successful")
        }
    }

    override fun onItemDeleted(item: Szelveny) {
        thread {
            database.szelvenyDao().deleteItem(item)
            meccsDB.meccsDao().deleteSelectedSzelveny(item)
            runOnUiThread {
                adapter.deleteItem(item)
            }
        }
    }

    override fun onItemPicked(item: Szelveny) {
        thread {
            val szelvenyId: Long? = item.id
            runOnUiThread {
                if (szelvenyId != null) {
                    val i =  Intent(this, MeccsActivity()::class.java)
                    i.putExtra("SZELVENY_SZAM", szelvenyId)
                    startActivity(i)
                }
            }
        }
    }

    override fun onSzelvenyCreated(newItem: Szelveny) {
        thread {
            val insertId = database.szelvenyDao().insert(newItem)
            newItem.id = insertId
            runOnUiThread {
                adapter.addItem(newItem)
            }
        }
    }
}