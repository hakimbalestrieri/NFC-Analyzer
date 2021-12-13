package ch.heigvd.symlabo3.nfc

/**
 * Communication listener to handle server response
 */
interface CommunicationEventListener {

    /**
     * Handle tag read
     * @param nfcValues values read
     */
    fun handleTagRead(nfcValues: MutableList<String>)
}