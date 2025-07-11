package com.example.lifetrack.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lifetrack.ui.components.LifeTrackTopBar
import com.example.lifetrack.ui.components.QuickActionsRow

@Composable
fun HomeScreen(
    onEmergency: () -> Unit,
    onSearch: () -> Unit,
    onAlma: () -> Unit
) {
    Scaffold(
        topBar = { LifeTrackTopBar() },
        bottomBar = {
            QuickActionsRow(
                onEmergencyClick = onEmergency,
                onSearchClick = onSearch,
                onAlmaClick = onAlma
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        )
        {
//            item { HealthSummaryCard() }
//            item { AppointmentsSection() }
//            item { NearbyHospitals() }
        }
    }
}