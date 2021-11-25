package ch.heigvd.symlabo3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ch.heigvd.symlabo3.codebarre.CodeBarreActivity
import ch.heigvd.symlabo3.databinding.ActivityMainBinding
import ch.heigvd.symlabo3.ibeacon.iBeaconActivity
import ch.heigvd.symlabo3.nfc.NFCActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Binding components
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        // Adding listeners on buttons to navigate through activities
        binding.btnCodeBarre.setOnClickListener{
            startActivity(Intent(this, CodeBarreActivity::class.java))
        }
        binding.btnBeacon.setOnClickListener {
            startActivity(Intent(this, iBeaconActivity::class.java))
        }
        binding.btnNfc.setOnClickListener {
            startActivity(Intent(this, NFCActivity::class.java))
        }
    }

}