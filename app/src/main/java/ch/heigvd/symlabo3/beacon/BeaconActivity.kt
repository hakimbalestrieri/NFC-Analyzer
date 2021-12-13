package ch.heigvd.symlabo3.beacon

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ch.heigvd.symlabo3.R
import ch.heigvd.symlabo3.Utils
import ch.heigvd.symlabo3.databinding.BeaconActivityBinding
import org.altbeacon.beacon.*

/**
 * iBeacon activity
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
        beaconManager.startRangingBeacons(REGION)
    }

    override fun onResume() {
        super.onResume()
        beaconManager.stopRangingBeacons(REGION)
    }

    override fun onPause() {
        super.onPause()
        beaconManager.startRangingBeacons(REGION)
    }

    override fun didRangeBeaconsInRegion(beacons: MutableCollection<Beacon>?, region: Region?) {
        if (beacons != null) {
            Log.d(TAG, "Ranged: ${beacons.count()} beacons")
            for (beacon: Beacon in beacons) {
                Log.d(TAG, "$beacon about ${beacon.distance} meters away")
                Toast.makeText(
                    this,
                    "$beacon about ${beacon.distance} meters away",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    companion object {
        const val TAG = "BeaconActivity"
        const val LAYOUT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"
        val REGION = Region("all-beacons-region", null, null, null)
    }
}