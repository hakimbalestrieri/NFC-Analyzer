package ch.heigvd.symlabo3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ch.heigvd.symlabo3.barcode.BarcodeActivity
import ch.heigvd.symlabo3.beacon.BeaconActivity
import ch.heigvd.symlabo3.databinding.MainActivityBinding
import ch.heigvd.symlabo3.nfc.NFCActivity

/**
 * Main activity
 * @author Allemann, Balestrieri, Gomes
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Binding components
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Adding listeners on buttons to navigate through activities
        binding.btnCodeBarre.setOnClickListener{
            startActivity(Intent(this, BarcodeActivity::class.java))
        }
        binding.btnBeacon.setOnClickListener {
            startActivity(Intent(this, BeaconActivity::class.java))
        }
        binding.btnNfc.setOnClickListener {
            startActivity(Intent(this, NFCActivity::class.java))
        }
    }
}