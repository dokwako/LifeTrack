package com.example.lifetrack.ui.components

import android.graphics.Typeface
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.*
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*

// Color Palette for Medical Charts
private val hospitalBlue = Color(0xFF0077B6)
private val emergencyRed = Color(0xFFE53935)
private val successGreen = Color(0xFF4CAF50)
private val warningYellow = Color(0xFFFFC107)

// Date formatters
private val dateFormat = SimpleDateFormat("MMM dd", Locale.getDefault())
private val monthFormat = SimpleDateFormat("MMM", Locale.getDefault())

// ========== CHART CONFIGURATION EXTENSIONS ==========
private fun LineChart.configureBaseChart() {
    description.isEnabled = false
    legend.isEnabled = true
    setTouchEnabled(true)
    isDragEnabled = true
    setScaleEnabled(true)
    setPinchZoom(true)
    setDrawGridBackground(false)
    legend.textColor = Color.Black.toArgb()
}
private fun HorizontalBarChart.configureBaseChart() {
    description.isEnabled = false
    legend.isEnabled = true
    setTouchEnabled(true)
    isDragEnabled = true
    setScaleEnabled(true)
    setPinchZoom(true)
    setDrawGridBackground(false)
    legend.textColor = Color.Black.toArgb()
}

private fun LineChart.configureBloodPressureAxes() {
    xAxis.apply {
        position = XAxis.XAxisPosition.BOTTOM
        granularity = 86400000f // 1 day
        valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return dateFormat.format(Date(value.toLong()))
            }
        }
        textColor = Color.Black.toArgb()
        setDrawGridLines(false)
    }

    axisLeft.apply {
        axisMinimum = 50f
        axisMaximum = 200f
        textColor = Color.Black.toArgb()
        setDrawGridLines(true)
        gridColor = Color.LightGray.toArgb()
    }

    axisRight.isEnabled = false
}

private fun LineDataSet.styleLineDataSet(color: Int, fillColor: Int) {
    this.color = color
    lineWidth = 2.5f
    setDrawCircles(true)
    setCircleColor(color)
    circleRadius = 4f
    setDrawValues(false)
    mode = LineDataSet.Mode.CUBIC_BEZIER
    setDrawFilled(true)
    setFillColor(fillColor)
    fillAlpha = 100
    valueTextSize = 10f
    valueTypeface = Typeface.DEFAULT_BOLD
}

/* ========== BLOOD PRESSURE TREND CHART ========== */
@Composable
fun BloodPressureChart(
    systolicData: Map<Date, Float>,
    diastolicData: Map<Date, Float>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    AndroidView(
        factory = { context ->
            LineChart(context).apply {
                configureBaseChart()
                configureBloodPressureAxes()

                // Convert data to chart entries
                val systolicEntries = systolicData.map {
                    Entry(it.key.time.toFloat(), it.value)
                }
                val diastolicEntries = diastolicData.map {
                    Entry(it.key.time.toFloat(), it.value)
                }

                // Create datasets
                val systolicSet = LineDataSet(systolicEntries, "Systolic").apply {
                    styleLineDataSet(
                        color = emergencyRed.toArgb(),
                        fillColor = emergencyRed.copy(alpha = 0.2f).toArgb()
                    )
                }

                val diastolicSet = LineDataSet(diastolicEntries, "Diastolic").apply {
                    styleLineDataSet(
                        color = hospitalBlue.toArgb(),
                        fillColor = hospitalBlue.copy(alpha = 0.2f).toArgb()
                    )
                }

                // Add threshold line
                val limitLine = LimitLine(140f, "Normal Threshold").apply {
                    lineColor = successGreen.toArgb()
                    lineWidth = 1.5f
                }
                axisLeft.addLimitLine(limitLine)

                data = LineData(systolicSet, diastolicSet)
                animateX(1000)
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
    )
}

/* ========== MEDICATION ADHERENCE CHART ========== */
@Composable
fun MedicationAdherenceChart(
    adherenceData: Map<String, Float>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    AndroidView(
        factory = { context ->
            HorizontalBarChart(context).apply {
                configureBaseChart()

                // Convert data to entries
                val entries = adherenceData.values.mapIndexed { index, value ->
                    BarEntry(index.toFloat(), value)
                }

                val dataSet = BarDataSet(entries, "Adherence %").apply {
                    colors = listOf(
                        successGreen.toArgb(),
                        warningYellow.toArgb(),
                        emergencyRed.toArgb()
                    )
                    valueTextColor = Color.Black.toArgb()
                    valueTextSize = 10f
                }

                // Configure axes
                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    granularity = 1f
                    valueFormatter = IndexAxisValueFormatter(adherenceData.keys.toList())
                    setDrawGridLines(false)
                    axisMinimum = 0f
                    axisMaximum = 100f
                }

                axisLeft.apply {
                    setDrawGridLines(false)
                    setDrawAxisLine(false)
                    setDrawLabels(false)
                }

                axisRight.isEnabled = false
                data = BarData(dataSet)
                animateY(1000)
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
    )
}

/* ========== DATA CLASSES ========== */
data class LabResult(
    val testName: String,
    val currentValue: Float,
    val previousValue: Float,
    val unit: String,
    val normalRange: String
)