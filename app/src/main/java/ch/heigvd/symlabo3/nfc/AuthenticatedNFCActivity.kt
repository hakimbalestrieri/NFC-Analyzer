package ch.heigvd.symlabo3.nfc

import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ch.heigvd.symlabo3.R
import ch.heigvd.symlabo3.databinding.NfcAuthenticatedActivityBinding
import java.util.*

/**
 * Authenticated NFC activity
 * @author Allemann, Balestrieri, Gomes
 */
class AuthenticatedNFCActivity : AppCompatActivity() {
    private lateinit var binding: NfcAuthenticatedActivityBinding
    private lateinit var mNfcAdapter: NfcAdapter
    private var lastNFCTagReadDateTime = System.currentTimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nfc_authenticated_activity)

        // Binding components
        binding = NfcAuthenticatedActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        // Adding listeners on buttons to show security level
        binding.maxSecurityButton.setOnClickListener{
            checkSecurityLevel(AUTHENTICATE_MAX_TIME)
        }
        binding.mediumSecurityButton.setOnClickListener{
            checkSecurityLevel(AUTHENTICATE_MEDIUM_TIME)
        }
        binding.minSecurityButton.setOnClickListener{
            checkSecurityLevel(AUTHENTICATE_MIN_TIME)
        }
    }

    override fun onResume() {
        super.onResume()
        NFCUtils.setupForegroundDispatch(mNfcAdapter, this)
    }

    override fun onPause() {
        super.onPause()
        NFCUtils.stopForegroundDispatch(mNfcAdapter,this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null)
            NFCUtils.handleIntent(intent) {
                if (it.count() > 0 && it[0] == NFC_MESSAGE) {
                    Toast.makeText(this@AuthenticatedNFCActivity, "NFC tag read", Toast.LENGTH_SHORT)
                        .show()
                    lastNFCTagReadDateTime = System.currentTimeMillis()
                }
            }
    }

    /**
     * Check if the security level is high enough
     * @param maxTimeInSeconds maximum time in seconds since the last NFC tag was read
     */
    private fun checkSecurityLevel(maxTimeInSeconds : Int) {
        if(System.currentTimeMillis() - lastNFCTagReadDateTime < maxTimeInSeconds * 1000)
            sufficientLevel()
        else
            insufficientLevel()
    }

    /**
     * Function called when the security level is sufficient
     */
    private fun sufficientLevel() {
        Toast.makeText(this, "Sufficient level", Toast.LENGTH_SHORT).show()
    }

    /**
     * Function called when the security level is insufficient
     */
    private fun insufficientLevel() {
        Toast.makeText(this, "Insufficient level", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val NFC_MESSAGE = "test"
        const val AUTHENTICATE_MIN_TIME = 45
        const val AUTHENTICATE_MEDIUM_TIME = 30
        const val AUTHENTICATE_MAX_TIME = 15
    }
}