package com.example.notification

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TimePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

const val EXTRA_PESAN = "EXTRA_PESAN"
class MainActivity : AppCompatActivity() {

    private var cal = Calendar.getInstance()

    private fun setMyTimeFOrmat(): String{
        val myFormat = "HH:mm" // mention the format you need
        val sdf = SimpleDateFormat(myFormat)
        return sdf.format(cal.getTime())
    }

    private fun myTimePicker() : TimePickerDialog.OnTimeSetListener{
        val timeSetListener = object : TimePickerDialog.OnTimeSetListener{
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                cal.set(Calendar.HOUR_OF_DAY,hourOfDay)
                cal.set(Calendar.MINUTE, minute)
                timeAlarm.text = setMyTimeFOrmat()
            }
        }
        return timeSetListener
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mAlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val requestCode = 100

        var mPendingIntent : PendingIntent? = null
        setAlarm.setOnClickListener {
            val setTime = Calendar.getInstance()
            val dateTime = timeAlarm.text.split(":")
            setTime.set(Calendar.HOUR_OF_DAY,dateTime[0].toInt())
            setTime.set(Calendar.MINUTE,dateTime[1].toInt())
            setTime.set(Calendar.SECOND,0)

            val sendIntent = Intent(this,myAlarmReceiver::class.java)
            sendIntent.putExtra(EXTRA_PESAN,myMessage.text.toString())

            mPendingIntent = PendingIntent.getBroadcast(this,requestCode,
                sendIntent,0)

            mAlarmManager.set(AlarmManager.RTC_WAKEUP, setTime.timeInMillis,mPendingIntent)
            Toast.makeText(this,"Alarm Manager 시작${timeAlarm.text}:00",Toast.LENGTH_SHORT).show()

        }

        cancelAlarm.setOnClickListener {
            if(mPendingIntent != null){
                mAlarmManager.cancel(mPendingIntent)
                Toast.makeText(this,"Alarm Manager 취소",Toast.LENGTH_SHORT).show()
            }
        }
        showTimePicker.setOnClickListener {
            TimePickerDialog(this,myTimePicker(),
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
                ).show() }
    }
}
