package ch.heigvd.symlabo3.beacon

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ch.heigvd.symlabo3.R
import ch.heigvd.symlabo3.Utils
import ch.heigvd.symlabo3.databinding.BeaconActivityBinding
import org.altbeacon.beacon.*

/**
 * Beacon activity
 * @author Allemann, Balestrieri, Gomes
 */
class BeaconActivity : AppCompatActivity(), RangeNotifier {
    private lateinit var binding: BeaconActivityBinding
    private lateinit var beaconManager: BeaconManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.beacon_activity)

        // Binding components
        binding = BeaconActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Checking and adding permissions if necessary
        Utils.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, 1, this@BeaconActivity)

        // Beacon parser configuration
        val beaconParser = BeaconParser().setBeaconLayout(LAYOUT)
        beaconManager = BeaconManager.getInstanceForApplication(this)
        beaconManager.addRangeNotifier(this)
        beaconManager.beaconParsers.add(beaconParser)
    }

    override fun onResume() {
        super.onResume()
        beaconManager.startRangingBeacons(REGION)
    }

    override fun onPause() {
        super.onPause()
        beaconManager.stopRangingBeacons(REGION)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        beaconManager.startRangingBeacons(REGION)
    }

    override fun didRangeBeaconsInRegion(beacons: MutableCollection<Beacon>?, region: Region?) {
        if (beacons != null) {
            Log.d(TAG, "Ranged: ${beacons.count()} beacons")
            binding.lstBeacons.adapter =
                ArrayAdapter(this, android.R.layout.simple_list_item_1, beacons.map {
                            "ID : ${it.id1}\nRSSI : ${it.rssi} \nMajor number : ${it.id2}\n" +
                            "Minor number : ${it.id3}\nDistance : ${it.distance}"
                })
        }
    }

    companion object {
        const val TAG = "BeaconActivity"
        const val LAYOUT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"
        val REGION = Region("all-beacons-region", null, null, null)
    }
}