package com.websarva.wings.android.dietapp

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    private val dbHelper = DatabaseHelper(this@MainActivity,"DietDB",null,1);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }

    fun btnOnClick(view: View){
        val database = dbHelper.writableDatabase

        val bdWeight: EditText = findViewById(R.id.bdWeight)
        val strWeight = bdWeight.text.toString()
        val bdBmi:EditText = findViewById(R.id.bdBmi)
        val strBmi = bdBmi.text.toString()
        val bdFat:EditText = findViewById(R.id.bdFat)
        val strFat = bdFat.text.toString()
        val bdMoisture:EditText = findViewById(R.id.bdMoisture)
        val strMoisture = bdMoisture.text.toString()
        val bdMuscle:EditText = findViewById(R.id.bdMuscle)
        val strMuscle = bdMuscle.text.toString()
        val bdBone:EditText = findViewById(R.id.bdBone)
        val strBone = bdBone.text.toString()
        val bdMeta:EditText = findViewById(R.id.bdMeta)
        val strMeta = bdMeta.text.toString()
        val bdVisceral:EditText = findViewById(R.id.bdVisceral)
        val strVis = bdVisceral.text.toString()

        val values = ContentValues()
        values.put("weight",strWeight)
        values.put("bmi",strBmi)
        values.put("fat",strFat)
        values.put("moisture",strMoisture)
        values.put("muscle",strMuscle)
        values.put("bone",strBone)
        values.put("meta",strMeta)
        values.put("visceral",strVis)

        database.insertOrThrow("DietTable",null,values)

        bdWeight.text = null
        bdBmi.text = null
        bdFat.text = null
        bdMoisture.text = null
        bdMuscle.text = null
        bdBone.text = null
        bdMeta.text = null
        bdVisceral.text = null

        val intent = Intent(this,GraphActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options_menu_list,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        var returnVal = true
        when(item.itemId){
            R.id.menuListOptionGoGraph ->{
                val intent = Intent(this,GraphActivity::class.java)
                startActivity(intent)
            }else ->{
                returnVal = super.onOptionsItemSelected(item)
            }
        }
        return returnVal
    }

}