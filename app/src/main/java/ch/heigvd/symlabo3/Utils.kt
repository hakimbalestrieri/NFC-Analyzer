package ch.heigvd.symlabo3

import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * Utility class for the laboratory
 * @author Allemann, Balestrieri, Gomes
 */
class Utils {
    companion object {

        /**
         * Check if a permission is already given for an activity and request it if not
         * @param permission to give
         * @param requestCode requested code
         * @param activity to which to check and give permission
         */
        fun checkPermission(
            permission: String,
            requestCode: Int,
            activity: Activity
        ) {
            if (ContextCompat.checkSelfPermission(
                    activity,
                    permission
                ) == PackageManager.PERMISSION_DENIED
            ) {
                ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
            } else {
                Toast.makeText(activity, "Permission already granted", Toast.LENGTH_SHORT).show()
            }
        }
    }
}