package ch.heigvd.symlabo3.nfc

/**
 * Communication listener to handle server response
 */
interface CommunicationEventListener {

    /**
     * Handle tag read
     * @param nfcValue read
     */
    fun handleTagRead(nfcValue: MutableList<String>)
}