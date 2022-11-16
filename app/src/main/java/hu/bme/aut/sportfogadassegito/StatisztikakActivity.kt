package hu.bme.aut.sportfogadassegito

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.sportfogadassegito.databinding.ActivityStatisztikakBinding
import hu.bme.aut.sportfogadassegito.szelveny.adapter.SzelvenyAdapter
import hu.bme.aut.sportfogadassegito.szelveny.data.Szelveny
import hu.bme.aut.sportfogadassegito.szelveny.data.SzelvenyDatabase
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.concurrent.thread
import kotlin.math.roundToInt

class StatisztikakActivity: AppCompatActivity() {

    private lateinit var binding: ActivityStatisztikakBinding
    private lateinit var database: SzelvenyDatabase
    private lateinit var szelvenyek: List<Szelveny>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStatisztikakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = SzelvenyDatabase.getDatabase(applicationContext)
        initSzelvenyek()



    }

    private fun initSzelvenyek() {
        thread {
            val items = database.szelvenyDao().getAll()
            szelvenyek = items
            binding.idWinrate.text = "${getWinrate()} %"
            binding.idProfit.text = "${getProfit()} HUF"
        }
    }




    private fun getWinrate(): Double{
        var ossz = szelvenyek.size
        var igazakDarab: Int=0
        for (item in szelvenyek){
            if(item.isWin){
                igazakDarab ++
            }
        }
        var winrate: Double = igazakDarab/ossz.toDouble()
        if (winrate.isNaN()) winrate = 0.0
        val roundedWinrate = (winrate * 1000.0).roundToInt() / 10.0

        return roundedWinrate
    }
    private fun getProfit(): Int{

        var profit: Int=0
        for (item in szelvenyek){
            if(item.isWin){
                profit+=item.expectedPrize
            }
            else{
                profit-=item.price
            }
        }

        return profit
    }
}