package raf.example.calllogapp.ui

import android.content.pm.PackageManager
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog
import android.util.Log
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import raf.example.calllogapp.R
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                Array<String>(1) { android.Manifest.permission.READ_CALL_LOG }, 1)
        }

        setContentView(R.layout.activity_main)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            1 -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                    Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "No permission granted!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}

