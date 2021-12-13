package ch.heigvd.symlabo3.nfc

import android.nfc.NdefRecord
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Handler
import android.os.Looper
import android.util.Log
import java.io.UnsupportedEncodingException
import java.util.*

/**
 * NFC tag reader manager
 * @author Allemann, Balestrieri, Gomes
 */
class NFCReaderManager(var communicationEventListener: CommunicationEventListener) {

    /**
     * Read a given NFC tag tnf well know messages
     * @param tag who contains tnf well know messages
     */
    fun readNFCTag(tag: Tag) {
        Thread {
            var messages = mutableListOf<String>()
            val ndef = Ndef.get(tag)
            if (ndef != null) {
                val ndefMessage = ndef!!.cachedNdefMessage
                val records = ndefMessage.records
                for (ndefRecord in records) {
                    if (ndefRecord.tnf == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(
                            ndefRecord.type,
                            NdefRecord.RTD_TEXT
                        )
                    ) {
                        try {
                            messages.add(
                                String(
                                    ndefRecord.payload,
                                    OFFSET_LENGTH,
                                    ndefRecord.payload.size - OFFSET_LENGTH,
                                )
                            )
                        } catch (e: UnsupportedEncodingException) {
                            Log.e(TAG, "Unsupported Encoding", e)
                        }
                    }
                }
                HANDLER.post { communicationEventListener.handleTagRead(messages) }
            }
        }.start()
    }

    companion object {
        private val HANDLER = Handler(Looper.getMainLooper())
        const val TAG = "NFCReaderManager"
        const val OFFSET_LENGTH = 3
    }
}