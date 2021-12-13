package ch.heigvd.symlabo3.barcode

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ch.heigvd.symlabo3.R
import ch.heigvd.symlabo3.Utils
import ch.heigvd.symlabo3.databinding.BarcodeActivityBinding
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import java.util.*

/**
 * Barcode activity
 * @author Allemann, Balestrieri, Gomes
 */
class BarcodeActivity : AppCompatActivity() {
    private lateinit var binding: BarcodeActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.barcode_activity)

        // Binding components
        binding = BarcodeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Checking and adding permissions if necessary
        Utils.checkPermission(Manifest.permission.CAMERA, 1, this@BarcodeActivity)

        // Decoder configuration
        val formats: Collection<BarcodeFormat> =
            Arrays.asList(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39)
        binding.videoView.decoderFactory = DefaultDecoderFactory(formats)
        binding.videoView.initializeFromIntent(Intent())

        // Action performed when a barcode is detected
        binding.videoView.decodeContinuous { result ->
            binding.txtCodeDecode.text = result?.result?.text
            binding.imgCode.setImageBitmap(result?.getBitmapWithResultPoints((Color.BLACK)))
        }
    }

    override fun onResume() {
        super.onResume()
        binding.videoView.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.videoView.pause()
    }
}