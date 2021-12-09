package ch.heigvd.symlabo3.nfc

import android.nfc.NfcAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ch.heigvd.symlabo3.R
import ch.heigvd.symlabo3.databinding.ActivityNfcactivityBinding

class AuthenticatedNFCActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNfcactivityBinding
    private lateinit var mNfcAdapter: NfcAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfcactivity)

        // Binding components
        binding = ActivityNfcactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)

        if (mNfcAdapter == null) {
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        if (mNfcAdapter.isEnabled()) {
            Toast.makeText(this, "NFC enabled", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "NFC disabled", Toast.LENGTH_LONG).show()
        }
    }
}