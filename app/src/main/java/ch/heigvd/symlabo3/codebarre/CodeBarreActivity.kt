package ch.heigvd.symlabo3.codebarre

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ch.heigvd.symlabo3.R
import ch.heigvd.symlabo3.databinding.ActivityCodeBarreBinding


class CodeBarreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCodeBarreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_barre)

        // Binding components
        binding = ActivityCodeBarreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
    }
}