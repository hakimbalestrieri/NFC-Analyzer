package ch.heigvd.symlabo3.codebarre

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ch.heigvd.symlabo3.R
import ch.heigvd.symlabo3.databinding.ActivityCodeBarreBinding
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import java.util.*

import android.widget.ImageView

import android.graphics.Bitmap
import android.graphics.Color
import android.view.View


class CodeBarreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCodeBarreBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_barre)

        // Binding components
        binding = ActivityCodeBarreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermission(Manifest.permission.CAMERA, 1) //TODO : Voir methode de faire

        val formats: Collection<BarcodeFormat> = Arrays.asList(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39)
        binding.videoView.decoderFactory = DefaultDecoderFactory(formats)
        binding.videoView.initializeFromIntent(Intent())

        binding.videoView.decodeContinuous { result ->

            binding.txtCodeDecode.text = result?.result?.text
            binding.imgCode.setImageBitmap(result?.getBitmapWithResultPoints((Color.BLACK)))
            //imageView.setImageBitmap(result.getBitmapWithResultPoints(Color.YELLOW));
        }
        
    }

    override fun onResume() {
        super.onResume()
        binding.videoView.resume()
    }

    //TODO : Il y a une methode supplementaire qui peut être override, voir plus tard si besoin
    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this@CodeBarreActivity, permission) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this@CodeBarreActivity, arrayOf(permission), requestCode)
        } else {
            Toast.makeText(this@CodeBarreActivity, "Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }


}