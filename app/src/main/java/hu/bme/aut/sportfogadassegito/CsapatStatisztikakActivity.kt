package hu.bme.aut.sportfogadassegito

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.sportfogadassegito.databinding.ActivityStatisztikakCsapatBinding
import hu.bme.aut.sportfogadassegito.meccs.data.Meccs
import hu.bme.aut.sportfogadassegito.meccs.data.MeccsDatabase
import kotlin.concurrent.thread
import kotlin.math.roundToInt

class CsapatStatisztikakActivity: AppCompatActivity() {
    private lateinit var binding: ActivityStatisztikakCsapatBinding
    private lateinit var database: MeccsDatabase
    private lateinit var meccsek: List<Meccs>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStatisztikakCsapatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = MeccsDatabase.getDatabase(applicationContext)

        binding.searchButton.setOnClickListener{getTeam()}



    }

    private fun initMeccsek(teamName: String) {
        thread {
            val items = database.meccsDao().getMeccsekForTeam(teamName)
            meccsek = items
            val winrate = getTeamWinrate(teamName, meccsek)
            val predictionWinrate = getPredictionWinrate(meccsek)
            runOnUiThread{
                binding.idWinrate.text = "${convertProcenttoStringFormat(winrate)}"
                binding.idProfit.text = "${convertProcenttoStringFormat(predictionWinrate)}"
            }

        }
    }
    private fun getTeam(){
        val team = binding.idMelyikCsapat.text.toString()
        initMeccsek(team)
    }

    private fun getTeamWinrate(teamName: String, list: List<Meccs>): Double{
        var ossz = list.size
        var igazakDarab: Int=0

        for (item in list){
            if(item.isWin){
                if(item.expectedWinner && item.homeName==teamName){
                    igazakDarab ++
                }
                else if(!item.expectedWinner && item.awayName==teamName){
                    igazakDarab ++
                }
            }
            else {
                if(item.expectedWinner && item.awayName==teamName){
                    igazakDarab ++
                }
                else if(!item.expectedWinner && item.homeName==teamName){
                    igazakDarab ++
                }
            }

        }
        var winrate: Double = igazakDarab/ossz.toDouble()

        return winrate
    }
    private fun getPredictionWinrate(list: List<Meccs>): Double{
        var ossz = list.size
        var igazakDarab: Int=0
        for (item in list){
            if(item.isWin){
                igazakDarab ++
            }
        }
        var winrate: Double = igazakDarab/ossz.toDouble()


        return winrate
    }
    private fun convertProcenttoStringFormat(winrate: Double): String{
        val stringWinrate =
        if (winrate.isNaN()) "Vegyél fel meccseket"
        else{
            val roundedWinrate = (winrate * 1000.0).roundToInt() / 10.0
            "$roundedWinrate %"
        }


        return stringWinrate
    }
}




