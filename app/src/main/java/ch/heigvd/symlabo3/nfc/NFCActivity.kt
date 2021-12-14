package ch.heigvd.symlabo3.nfc

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.content.IntentFilter.MalformedMimeTypeException
import android.graphics.Color
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ch.heigvd.symlabo3.R
import ch.heigvd.symlabo3.Utils
import ch.heigvd.symlabo3.barcode.BarcodeActivity
import ch.heigvd.symlabo3.databinding.NfcActivityBinding

/**
 * NFC activity
 * @author Allemann, Balestrieri, Gomes
 */
class NFCActivity : AppCompatActivity() {
    private lateinit var binding: NfcActivityBinding
    private lateinit var mNfcAdapter: NfcAdapter
    private var NFCTagRead = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nfc_activity)

        // Binding components
        binding = NfcActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Checking and adding permissions if necessary
        Utils.checkPermission(Manifest.permission.NFC, 1, this@NFCActivity)

        // Getting NFC adapter and check that the device supports NFC
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (mNfcAdapter == null) {
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        // Check that NFC is enabled on the device
        if (mNfcAdapter.isEnabled)
            Toast.makeText(this, "NFC enabled", Toast.LENGTH_LONG).show()
        else
            Toast.makeText(this, "NFC disabled", Toast.LENGTH_LONG).show()

        // Adding listener on login button
        binding.connectButton.setOnClickListener { login() }
    }

    override fun onResume() {
        super.onResume()
        NFCUtils.setupForegroundDispatch(mNfcAdapter, this)
    }

    override fun onPause() {
        super.onPause()
        NFCUtils.stopForegroundDispatch(mNfcAdapter, this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null)
            NFCUtils.handleIntent(intent) {
                if (it.count() > 0 && it[0] == NFCUtils.NFC_MESSAGE) {
                    binding.nfcImage.setBackgroundColor(Color.rgb(0, 153, 0))
                    NFCTagRead = true
                }
            }
    }

    /**
     * Login method
     */
    private fun login() {
        binding.errorMessage.text = ""
        if (binding.username.text.toString() == NFCUtils.USERNAME && binding.password.text.toString() == NFCUtils.PASSWORD)
            if (NFCTagRead)
                startActivity(Intent(this, AuthenticatedNFCActivity::class.java))
            else {
                binding.nfcImage.setBackgroundColor(Color.rgb(255, 102, 102))
                binding.errorMessage.text = "NFC tag was not read"
            }
        else {
            binding.errorMessage.text = "Invalid username / password"
        }
    }

    companion object {
        const val TAG = "NFCActivity"
    }
}