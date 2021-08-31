package com.fariq.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import com.fariq.githubuser.AlarmReceiver
import com.fariq.githubuser.ReminderPreference
import com.fariq.githubuser.databinding.ActivityReminderBinding

class ReminderActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {
    private lateinit var binding : ActivityReminderBinding
    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var reminderPreference: ReminderPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alarmReceiver = AlarmReceiver()
        reminderPreference = ReminderPreference(this)
        binding.switchReminder.isChecked = reminderPreference.getReminderValue()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.switchReminder.setOnCheckedChangeListener(this)

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (isChecked) {
            val repeatTime = "11:49"
            val repeatMessage = "Daily Reminder Github User"
            alarmReceiver.setRepeatingAlarm(this, repeatTime, repeatMessage)
            reminderPreference.setReminderValue(binding.switchReminder.isChecked)
        }
        else {
            alarmReceiver.cancelAlarm(this, AlarmReceiver.TITLE)
            reminderPreference.setReminderValue(binding.switchReminder.isChecked)
        }
    }
}