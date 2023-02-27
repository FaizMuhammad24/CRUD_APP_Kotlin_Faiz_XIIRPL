package com.example.crud_app_tubagusmuhammadrofialfaiz_xiirpl

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.crud_app_tubagusmuhammadrofialfaiz_xiirpl.helper.DBHelper
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    lateinit var InputName: TextInputEditText
    lateinit var InputAge: TextInputEditText
    lateinit var btnAdd: Button
    lateinit var btnPrint: Button
    lateinit var textName: TextView
    lateinit var textAge: TextView
    lateinit var textId:TextView
    var progressDialog:ProgressDialog?=null

    //load Handler = tampilkan table

    @SuppressLint("Range", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        InputName = findViewById(R.id.InputName)
        InputAge = findViewById(R.id.inputAge)
        btnAdd = findViewById(R.id.btnAdd)
        btnPrint = findViewById(R.id.btnPrint)
        textName = findViewById(R.id.textname)
        textAge = findViewById(R.id.textAge)
        textId= findViewById(R.id.textId)
        loadHandler()
        btnAdd.setOnClickListener {
            val db = DBHelper(this, null)
            val name = InputName.text.toString()
            val age = InputAge.text.toString()


            db.addProfile(name, age)

            InputAge.text!!.clear()
            InputName.text!!.clear()
        }
        btnPrint.setOnClickListener {
        loadHandler()
        }
    }

    @SuppressLint("Range")
    fun loadHandler() {
        val db = DBHelper(this, null)
        val cursor = db.getProfile()
        progressDialog= ProgressDialog(this@MainActivity)
        progressDialog!!.setTitle("Loading")
        progressDialog!!.setMessage("Sabar Dulu Atuh Masbro...!")
        progressDialog!!.max=100
        progressDialog!!.progress=1
        progressDialog!!.show()
        Thread(Runnable {
            try {
                Thread.sleep(1000)
            } catch (e:Exception){
                e.printStackTrace()
            }
            progressDialog!!.dismiss()
        }).start()

        if (cursor!!.moveToFirst()) {
            textId.text = "Id\n"
            textName.text = "Name\n"
            textAge.text = "Age\n"
            textId.append(
                cursor.getString(
                    cursor.getColumnIndex(DBHelper.ID_COL)
                ) +"\n"
            )
            textName.append(
                cursor.getString(
                    cursor.getColumnIndex(DBHelper.NAME_COL)
                ) + "\n"
            )
            textAge.append(
                cursor.getString(
                    cursor.getColumnIndex(DBHelper.AGE_COL)
                ) + "\n"
            )
        }
        if (cursor.moveToNext()) {
            while (cursor.moveToNext()) {
                textId.append(
                    cursor.getString(
                        cursor.getColumnIndex(DBHelper.ID_COL)
                    ) +"\n"
                )
                textName.append(
                    cursor.getString(
                        cursor.getColumnIndex(DBHelper.NAME_COL)
                    ) + "\n"
                )
                textAge.append(
                    cursor.getString(
                        cursor.getColumnIndex(DBHelper.AGE_COL)
                    ) + "\n"
                )
            }
            cursor.close()
        }

    }
}