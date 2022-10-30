package raf.example.calllogapp

import android.database.Cursor
import android.provider.CallLog
import android.view.View
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import com.google.android.material.textfield.TextInputLayout
import java.lang.StringBuilder
import java.util.*

class CallTimePresenter: Contract.Presenter {
    private var mainView: View? = null

    constructor(view: View?){
        mainView = view
    }

    override fun checkPhoneNumber(editText: EditText, phoneLayout: TextInputLayout): Boolean {
        val phoneNumber = editText.text.toString()

        var error = when {
            phoneNumber.contains("\\D".toRegex()) -> {
                "It's not number"
            }
            phoneNumber.length < 10 -> {
                "Not enough number"
            }
            else -> {
                null
            }
        }

        phoneLayout.error = error
        if(error.isNullOrEmpty()){
            return true
        }
        return false
    }

    override fun getInformation(phoneNumber: String, activity:FragmentActivity): String {
        val managedCursor: Cursor? = activity.contentResolver.query(CallLog.Calls.CONTENT_URI, null,null,null,null)
        val number = managedCursor!!.getColumnIndex(CallLog.Calls.NUMBER)
        val duration = managedCursor!!.getColumnIndex(CallLog.Calls.DURATION)
        val date = managedCursor!!.getColumnIndex(CallLog.Calls.DATE)
        val type = managedCursor!!.getColumnIndex(CallLog.Calls.TYPE)
        val current = Date()

        var callTimeIncome = 0
        var callTimeOutcome = 0

        while(managedCursor.moveToNext()){
            val callType = managedCursor.getString(type)
            val callDate:Long = managedCursor.getLong(date)
            val callDayTime = Date(callDate)
            val phNumber = managedCursor.getString(number)
            val callDuration = managedCursor.getString(duration).toInt()

            val dircode:Int = callType.toInt()
            when(dircode){
                CallLog.Calls.INCOMING_TYPE -> {
                    val timeDiff:Long = current.time - callDayTime.time
                    val days = timeDiff / 1000 / 60 / 60 / 24

                    if(phoneNumber.equals(phNumber) && days < 4){
                        callTimeIncome += callDuration
                    }
                }
                CallLog.Calls.OUTGOING_TYPE -> {
                    if(phoneNumber.equals(phNumber)){
                        callTimeOutcome += callDuration
                    }
                }
            }
        }

        return convertTime(callTimeIncome) + ";" + convertTime(callTimeOutcome)
    }

    override fun convertTime(callTime: Int): String {
        if(callTime == 0) {
            return "you don't have calls with this number"
        }
        var callTimeStringBuilder = StringBuilder()

        val callDay = callTime / 86_400
        if(callDay > 0)
            callTimeStringBuilder.append("$callDay days, ")

        val callHours = (callTime % 86_400) / 3_600
        if(callHours > 0)
            callTimeStringBuilder.append("$callHours hours, ")

        val callMinutes = (callTime % 86_400) % 3_600 / 60
        if(callMinutes > 0)
            callTimeStringBuilder.append("$callMinutes minutes, ")

        val callSecond = (callTime % 86_400) % 3_600 % 60
        callTimeStringBuilder.append("$callSecond seconds")

        return callTimeStringBuilder.toString()
    }

}