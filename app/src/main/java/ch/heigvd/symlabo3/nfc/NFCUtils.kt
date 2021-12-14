package ch.heigvd.symlabo3.nfc

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.util.Log

/**
 * NFC activities utils
 * @author Allemann, Balestrieri, Gomes
 */
class NFCUtils {
    companion object {

        /**
         * Start listening for NFC tag
         */
        fun setupForegroundDispatch(nfcAdapter: NfcAdapter, activity: Activity) {
            if (nfcAdapter == null) return

            // NFC Decoder configuration
            val intent = Intent(activity, activity.javaClass)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            val pendingIntent = PendingIntent.getActivity(activity, 0, intent, 0)
            val techList = arrayOf<Array<String>>()
            val filters = arrayOfNulls<IntentFilter>(1)
            filters[0] = IntentFilter()
            filters[0]!!.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED)
            filters[0]!!.addCategory(Intent.CATEGORY_DEFAULT)

            // Filter to read only text plain formatted messages
            try {
                filters[0]!!.addDataType("text/plain")
            } catch (e: IntentFilter.MalformedMimeTypeException) {
                Log.e(NFCActivity.TAG, "MalformedMimeTypeException", e)
            }

            nfcAdapter.enableForegroundDispatch(activity, pendingIntent, filters, techList)
        }

        /**
         * Stop listening for NFC tag
         */
        fun stopForegroundDispatch(nfcAdapter: NfcAdapter, activity: Activity) {
            if (nfcAdapter != null) nfcAdapter.disableForegroundDispatch(activity)
        }

        /**
         * Action done when a NFC tag is read from an intent
         * @param intent that read the NFC tag
         */
        fun handleIntent(intent: Intent, action: (nfcValue: MutableList<String>) -> Unit) {
            if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
                if (MIME_TEXT_PLAIN == intent.type) {
                    val tag: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
                    if (tag != null)
                        NFCReaderManager(object : CommunicationEventListener {
                            override fun handleTagRead(nfcValue: MutableList<String>) {
                                action(nfcValue)
                            }
                        }).readNFCTag(tag)
                } else {
                    Log.d(TAG, "Wrong mime type: $intent.type")
                }
            }
        }

        private const val TAG = "AuthenticatedNFCActivity"
        private const val MIME_TEXT_PLAIN = "text/plain"
        const val USERNAME = "test" // TODO : demander si c'est OK de stocker les credentials ainsi
        const val PASSWORD = "test"
        const val NFC_MESSAGE = "test"
    }
}