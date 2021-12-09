package ch.heigvd.symlabo3.ibeacon

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import ch.heigvd.symlabo3.R
import ch.heigvd.symlabo3.databinding.ActivityIbeaconBinding
import org.altbeacon.beacon.*

class iBeaconActivity : AppCompatActivity(), RangeNotifier {

    companion object {
        val TAG = "iBeaconActivity"
    }

    private lateinit var binding: ActivityIbeaconBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ibeacon)

        // Binding components
        binding = ActivityIbeaconBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, 1)

        val beaconParser =
            BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24")
        val beaconManager = BeaconManager.getInstanceForApplication(this)
        beaconManager.addRangeNotifier(this)
        beaconManager.beaconParsers.add(beaconParser)
        val region = Region("all-beacons-region", null, null, null)
        // Set up a Live Data observer so this Activity can get monitoring callbacks
        // observer will be called each time the monitored regionState changes (inside vs. outside region)
//        beaconManager.getRegionViewModel(region).rangedBeacons.observeForever(rangingObserver)
        beaconManager.startRangingBeacons(region)
    }

    val rangingObserver = Observer<Collection<Beacon>> { beacons ->
        Log.d(TAG, "Ranged: ${beacons.count()} beacons")
        for (beacon: Beacon in beacons) {
            Log.d(TAG, "$beacon about ${beacon.distance} meters away")
            Toast.makeText(this, "$beacon about ${beacon.distance} meters away", Toast.LENGTH_LONG)
                .show()
        }
    }

    private val monitoringObserver = Observer<Int> { state ->
        if (state == MonitorNotifier.INSIDE) {
            Log.d(TAG, "Detected beacons(s)")
            Toast.makeText(this, "Beacon detected", Toast.LENGTH_LONG).show()
        } else {
            Log.d(TAG, "Stopped detecteing beacons")
        }
    }

    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                this@iBeaconActivity,
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            // Requesting the permission
            ActivityCompat.requestPermissions(
                this@iBeaconActivity,
                arrayOf(permission),
                requestCode
            )
        } else {
            Toast.makeText(this@iBeaconActivity, "Permission already granted", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun didRangeBeaconsInRegion(beacons: MutableCollection<Beacon>?, region: Region?) {
        Log.d(TAG, "Test beacons")
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
}