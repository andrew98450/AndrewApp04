package com.andrew.andrewapp_04

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bt_calcbmi = findViewById<Button>(R.id.bt_calcbmi)
        val radio_group = findViewById<RadioGroup>(R.id.radioGroup)
        val edit_weight = findViewById<EditText>(R.id.edit_weight)
        val edit_height = findViewById<EditText>(R.id.edit_height)
        val tv_weight = findViewById<TextView>(R.id.tv_weight)
        val tv_bmi = findViewById<TextView>(R.id.tv_bmi)
        val handler = Handler()

        bt_calcbmi.setOnClickListener{
            var status_prog = 0
            val progressbar = ProgressDialog(it.context)
            progressbar.setMessage("Loading...")
            progressbar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            progressbar.progress = 0
            progressbar.max = 100
            progressbar.show()

            val runthread = Thread {

                while (status_prog <= 100) {
                    Thread.sleep(50)
                    status_prog += 1
                    progressbar.progress = status_prog
                }
                progressbar.dismiss()

                handler.post {
                    val cal_weight = edit_weight.text.toString().toDouble()
                    val cal_height = edit_height.text.toString().toDouble()
                    var cal_stdweight: Double = 0.0
                    var cal_bmi: Double = 0.0
                    when (radio_group.checkedRadioButtonId) {
                        R.id.radioButtonMale -> {
                            cal_stdweight = (cal_height - 80) * 0.7
                            cal_bmi = (cal_weight - 0.88 * cal_stdweight) / cal_weight * 100
                        }
                        R.id.radioButtonFemale -> {
                            cal_stdweight = (cal_height - 70) * 0.6
                            cal_bmi = (cal_weight - 0.82 * cal_stdweight) / cal_weight * 100
                        }
                    }
                    tv_weight.text = String.format("標準體重\n%.2f", cal_stdweight)
                    tv_bmi.text = String.format("體脂肪\n%.2f", cal_bmi)
                }
            }
            runthread.start()
        }
    }
}