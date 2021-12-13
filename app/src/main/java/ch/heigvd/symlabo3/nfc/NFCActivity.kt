package ch.heigvd.symlabo3.nfc

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.content.IntentFilter.MalformedMimeTypeException
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ch.heigvd.symlabo3.R
import ch.heigvd.symlabo3.Utils
import ch.heigvd.symlabo3.databinding.NfcActivityBinding

/**
 * NFC activity
 * @author Allemann, Balestrieri, Gomes
 */
class NFCActivity : AppCompatActivity() {
    private lateinit var binding: NfcActivityBinding
    private lateinit var mNfcAdapter: NfcAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nfc_activity)

        // Binding components
        binding = NfcActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)

        if (mNfcAdapter == null) {
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        Utils.checkPermission(Manifest.permission.NFC, 1, this@NFCActivity)

        if (mNfcAdapter.isEnabled) {
            Toast.makeText(this, "NFC enabled", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "NFC disabled", Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
        setupForegroundDispatch()
    }

    override fun onPause() {
        super.onPause()
        stopForegroundDispatch()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null)
            handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        val action = intent.action
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == action) {
            val type = intent.type
            if (MIME_TEXT_PLAIN.equals(type)) {
                val tag: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
                if (tag != null)
                    NFCReaderManager(object : CommunicationEventListener {
                        override fun handleTagRead(nfcValue: MutableList<String>) {
                            Toast.makeText(this@NFCActivity, nfcValue[2], Toast.LENGTH_LONG).show()
                        }
                    }).readNFCTag(tag)
            } else {
                Log.d(TAG, "Wrong mime type: $type")
            }
        }
    }

    private fun setupForegroundDispatch() {
        if (mNfcAdapter == null) return
        val intent = Intent(this.applicationContext, this.javaClass)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(this.applicationContext, 0, intent, 0)
        val filters = arrayOfNulls<IntentFilter>(1)
        val techList = arrayOf<Array<String>>()
        filters[0] = IntentFilter()
        filters[0]!!.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED)
        filters[0]!!.addCategory(Intent.CATEGORY_DEFAULT)
        try {
            filters[0]!!.addDataType("text/plain")
        } catch (e: MalformedMimeTypeException) {
            Log.e(TAG, "MalformedMimeTypeException", e)
        }
        mNfcAdapter.enableForegroundDispatch(this, pendingIntent, filters, techList)
    }

    private fun stopForegroundDispatch() {
        if (mNfcAdapter != null) mNfcAdapter.disableForegroundDispatch(this)
    }

    companion object {
        const val TAG = "NFCActivity"
        const val MIME_TEXT_PLAIN = "text/plain"
    }
}