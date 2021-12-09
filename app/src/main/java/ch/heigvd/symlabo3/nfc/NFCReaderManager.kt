package ch.heigvd.symlabo3.nfc

import android.R.attr
import android.nfc.Tag
import android.os.Handler
import android.os.Looper
import android.nfc.NdefRecord

import android.nfc.NdefMessage

import android.R.attr.tag

import android.nfc.tech.Ndef
import java.io.UnsupportedEncodingException
import java.util.*


class NFCReaderManager(var communicationEventListener: CommunicationEventListener) {
    companion object {
        private val HANDLER = Handler(Looper.getMainLooper())
    }

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
                            messages.add(String(
                                ndefRecord.payload,
                                3,
                                ndefRecord.payload.size - 3,
                            ))
                        } catch (e: UnsupportedEncodingException) {
//                            Log.e(TAG, "Unsupported Encoding", e)
                        }
                    }
                }
                HANDLER.post { communicationEventListener.handleTagRead(messages) }


            }
        }.start()
    }
}