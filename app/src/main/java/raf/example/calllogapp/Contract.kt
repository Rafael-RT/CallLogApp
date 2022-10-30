package raf.example.calllogapp

import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import com.google.android.material.textfield.TextInputLayout

interface Contract {
    // I decided to create project without model
    interface View {
        // method to hide button search
        fun showHide(view: android.view.View)
    }

    interface Presenter {
        // method to check if phone number valid or not
        fun checkPhoneNumber(editText: EditText, phoneLayout: TextInputLayout):Boolean

        // method get information from call log and return duration
        fun getInformation(phoneNumber: String, activity: FragmentActivity): String

        // converting second in days, hours, minutes
        fun convertTime(callTime: Int):String

    }
}