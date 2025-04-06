package com.example.lookspot.screens

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.lookspot.R
import com.example.lookspot.extras.models.Estadistica
import com.example.lookspot.extras.models.EstadisticaViewModel

class StatsActivity : AppCompatActivity() {

    private val vmodel: EstadisticaViewModel by viewModels { EstadisticaViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)



        val btnResetStats = findViewById<Button>(R.id.btnResetStats)
        btnResetStats.setOnClickListener(this::resetStats)

        vmodel.resultEstadistica.observe(this,this::OnEstaditicaActualitzada)
        vmodel.saved.observe(this,this::OnEstadisticaGuardada)
        vmodel.actualitzaEstadística()
    }

    private fun OnEstadisticaGuardada(success: Boolean) {
        if (success){
            Toast.makeText(this,"Estadistica Guardada Correctament",Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this,"Estadistica Guardada Correctament",Toast.LENGTH_LONG).show()
        }
    }

    private fun OnEstaditicaActualitzada(result: Result<Estadistica>) {
        //Actualitza la UI quan canvii l'estadística
        val dades = result.getOrDefault(Estadistica())

        val promptsText = findViewById<TextView>(R.id.prompts)
        promptsText.text = dades.numPrompts.toString()

        updateBarGraph(dades)
        updatePieGraph(dades)
        if (result.isFailure) {
            Toast.makeText(
                this,
                result.exceptionOrNull()?.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun resetStats(view: View?) {
        vmodel.resetEstadística()
    }

    private fun updatePieGraph(estadistica: Estadistica) {
        val numFavoritas = estadistica.numAsserts
        val numNoFavoritas = estadistica.numResults - numFavoritas

        val entries = listOf(
            PieEntry(numFavoritas.toFloat(), "Favoritas"),
            PieEntry(numNoFavoritas.toFloat(), "No favoritas")
        )

        val pieDataSet = PieDataSet(entries, "Canciones favoritas")
        pieDataSet.colors = listOf(Color.rgb(255, 205, 210), Color.rgb(197, 225, 165)) // rosa y verde claro
        pieDataSet.valueTextColor = Color.BLACK
        pieDataSet.valueTextSize = 14f

        pieChart.apply {
            data = PieData(pieDataSet)
            description.isEnabled = false
            isDrawHoleEnabled = true
            holeRadius = 40f
            setHoleColor(Color.WHITE)
            setEntryLabelColor(Color.BLACK)
            setEntryLabelTextSize(14f)
            animateY(1000)
            invalidate()
        }
    }



    private fun updateBarGraph(estadistica: Estadistica) {
        // Crear un mapa con los valores que ya tienes en tu estadística
        val tipoAlbumCount = mapOf(
            "single" to estadistica.numSingle,
            "album" to estadistica.numAlbum,
            "compilation" to estadistica.numComp
        )

        val tipos = listOf("single", "album", "compilation")
        val entries = ArrayList<BarEntry>()

        for ((index, tipo) in tipos.withIndex()) {
            val count = tipoAlbumCount[tipo] ?: 0
            entries.add(BarEntry(index.toFloat(), count.toFloat()))
        }

        val barDataSet = BarDataSet(entries, "Tipos de álbum")
        barDataSet.colors = listOf(
            Color.rgb(255, 204, 204), // Rosa
            Color.rgb(204, 255, 229), // Verde agua
            Color.rgb(204, 229, 255)  // Azul claro
        )
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.valueTextSize = 14f
        barDataSet.valueFormatter = DefaultValueFormatter(0)

        barChart.apply {
            data = BarData(barDataSet)
            xAxis.valueFormatter = IndexAxisValueFormatter(tipos)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.granularity = 1f
            xAxis.labelCount = tipos.size
            axisLeft.setDrawGridLines(false)
            axisRight.isEnabled = false
            description.text = "Cantidad por tipo de álbum"
            description.isEnabled = true
            animateY(1000)
            invalidate()
        }
    }
}