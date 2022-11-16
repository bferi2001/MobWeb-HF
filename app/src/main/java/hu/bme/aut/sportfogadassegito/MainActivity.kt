package hu.bme.aut.sportfogadassegito

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.sportfogadassegito.databinding.ActivityMainBinding
import hu.bme.aut.sportfogadassegito.databinding.ActivitySzelvenyBinding
import hu.bme.aut.sportfogadassegito.meccs.MeccsActivity
import hu.bme.aut.sportfogadassegito.szelveny.SzelvenyActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttontoSzelvenyek.setOnClickListener{
            startActivity(Intent(this, SzelvenyActivity::class.java))
        }

        binding.buttontoStatisztika.setOnClickListener{
            startActivity(Intent(this, StatisztikakActivity::class.java))
        }
        binding.buttontoTeamStatisztika.setOnClickListener{
            startActivity(Intent(this, CsapatStatisztikakActivity::class.java))
        }
    }


}