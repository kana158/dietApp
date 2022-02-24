package com.websarva.wings.android.dietapp

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

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

        val values = ContentValues()
        values.put("weight",getString(R.string.bd_weight))
        values.put("bmi",getString(R.string.bd_bmi))
        values.put("fat",getString(R.string.bd_fat))
        values.put("moisture",getString(R.string.bd_moisture))
        values.put("muscle",getString(R.string.bd_muscle))
        values.put("bone",getString(R.string.bd_bone))
        values.put("meta",getString(R.string.bd_meta))
        values.put("visceral",getString(R.string.bd_visceral))

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