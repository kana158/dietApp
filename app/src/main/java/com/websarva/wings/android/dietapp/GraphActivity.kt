package com.websarva.wings.android.dietapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class GraphActivity : AppCompatActivity() {
    private val dbHelper = DatabaseHelper(this@GraphActivity,"DietDB",null,1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            android.R.id.home ->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun btnOnGraClick(view: View){
        var i = 0
        val spinner1 = findViewById<Spinner>(R.id.spList)
        val idx = spinner1.selectedItemPosition + 1
        val spinner2 = findViewById<Spinner>(R.id.dtList)
        val idx2 = (spinner2.selectedItemPosition + 1) * 7

        val x: List<Float> = List(idx2){it.toFloat() + 1f}
        val ary = FloatArray(idx2)

        val databaseR = dbHelper.readableDatabase
        val sql = "select * from dietTable order by id desc"
        val cursor = databaseR.rawQuery(sql,null)
        if(cursor.count > 0){
            while(cursor.moveToNext() && i < idx2){
                if(cursor.getFloat(idx) !== 0f){
                    ary[idx2-1-i] = cursor.getFloat(idx)
                    ++i
                }
            }
        }

        var entryList = mutableListOf<Entry>()//1本目の線
        for(i in x.indices){
            entryList.add(
                Entry(x[i].toFloat(), ary[i])
            )
        }

        val lineDataSets = mutableListOf<ILineDataSet>()
        val lineDataSet = LineDataSet(entryList, "変化線")
        lineDataSet.color = Color.BLUE
        lineDataSets.add(lineDataSet)

        val lineData = LineData(lineDataSets)
        val lineChart = findViewById<LineChart>(R.id.lineChart)
        lineChart.setNoDataText("まだデータがありません")
        lineChart.setNoDataTextColor(Color.GRAY)
        lineChart.setBorderColor(Color.CYAN)
        lineChart.setTouchEnabled(true)

        lineChart.data = lineData
        lineChart.xAxis.apply {
            isEnabled = true
            textColor = Color.BLACK
        }
        lineChart.invalidate()
    }

    fun btnOnClear(view: View){
        val database = dbHelper.writableDatabase
        val sql = "delete from dietTable"
        database.execSQL(sql)
    }
    fun btnOnDelete(view: View){
        val database = dbHelper.writableDatabase
        val sql = "delete from dietTable where id = (select id from dietTable order by id desc limit 1)"
        database.execSQL(sql)
    }
}