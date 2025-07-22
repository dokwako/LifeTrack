package com.example.lifetrack.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PractitionerDashboardScreen(navController: NavController) {
    var isVisible by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    var patientName by remember { mutableStateOf("John Doe") }
    var nationalId by remember { mutableStateOf("12345678") }
    var dob by remember { mutableStateOf("1990-05-15") }
    var sex by remember { mutableStateOf("Male") }
    var contact by remember { mutableStateOf("+254712345678") }
    var nextOfKin by remember { mutableStateOf("Jane Doe") }
    var allergies by remember { mutableStateOf("Penicillin") }
    var chronicConditions by remember { mutableStateOf("Diabetes") }
    var bloodGroup by remember { mutableStateOf("A+") }
    var insurance by remember { mutableStateOf("NHIF #987654") }
    var visitType by remember { mutableStateOf("General Checkup") }
    var visitDate by remember { mutableStateOf("2025-07-20") }
    var reason by remember { mutableStateOf("Routine Visit") }
    var vitals by remember { mutableStateOf("BP: 120/80, Temp: 36.8°C") }
    var examFindings by remember { mutableStateOf("Normal") }
    var provisionalDiagnosis by remember { mutableStateOf("Healthy") }
    var finalDiagnosis by remember { mutableStateOf("Healthy") }
    var labTests by remember { mutableStateOf("Blood Test") }
    var imaging by remember { mutableStateOf("X-Ray") }
    var medications by remember { mutableStateOf("Paracetamol") }
    var procedures by remember { mutableStateOf("None") }
    var nutrition by remember { mutableStateOf("Balanced Diet") }
    var followUp by remember { mutableStateOf("2025-07-27") }
    var referrals by remember { mutableStateOf("Specialist") }
    var discharge by remember { mutableStateOf("Discharged") }
    var billing by remember { mutableStateOf("KES 500") }

    LaunchedEffect(Unit) {
        delay(300)
        isVisible = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Practitioner Dashboard",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                actions = {
                    if (isEditing) {
                        IconButton(onClick = {
                            isEditing = false // Simulate save for now
                            // TODO: Add backend save logic here
                        }) {
                            Icon(
                                Icons.Filled.Save,
                                contentDescription = "Save",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    } else {
                        IconButton(onClick = { isEditing = true }) {
                            Text("Edit", color = MaterialTheme.colorScheme.onPrimary)
                        }
                    }
                }
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    AnimatedVisibility(
                        visible = isVisible,
                        enter = fadeIn(animationSpec = tween(600))
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    "Patient Master Profile",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Divider(modifier = Modifier.padding(vertical = 8.dp))
                                if (isEditing) {
                                    OutlinedTextField(
                                        value = patientName,
                                        onValueChange = { patientName = it },
                                        label = { Text("Full Name") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = nationalId,
                                        onValueChange = { nationalId = it },
                                        label = { Text("National ID/NHIF No.") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = dob,
                                        onValueChange = { dob = it },
                                        label = { Text("Date of Birth") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = sex,
                                        onValueChange = { sex = it },
                                        label = { Text("Sex") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = contact,
                                        onValueChange = { contact = it },
                                        label = { Text("Contact Info") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = nextOfKin,
                                        onValueChange = { nextOfKin = it },
                                        label = { Text("Next of Kin") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = allergies,
                                        onValueChange = { allergies = it },
                                        label = { Text("Allergies") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = chronicConditions,
                                        onValueChange = { chronicConditions = it },
                                        label = { Text("Chronic Conditions") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = bloodGroup,
                                        onValueChange = { bloodGroup = it },
                                        label = { Text("Blood Group") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = insurance,
                                        onValueChange = { insurance = it },
                                        label = { Text("Insurance Details") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                } else {
                                    Text("Full Name: $patientName", style = MaterialTheme.typography.bodyMedium)
                                    Text("National ID/NHIF No.: $nationalId", style = MaterialTheme.typography.bodyMedium)
                                    Text("Date of Birth: $dob", style = MaterialTheme.typography.bodyMedium)
                                    Text("Sex: $sex", style = MaterialTheme.typography.bodyMedium)
                                    Text("Contact Info: $contact", style = MaterialTheme.typography.bodyMedium)
                                    Text("Next of Kin: $nextOfKin", style = MaterialTheme.typography.bodyMedium)
                                    Text("Allergies: $allergies", style = MaterialTheme.typography.bodyMedium)
                                    Text("Chronic Conditions: $chronicConditions", style = MaterialTheme.typography.bodyMedium)
                                    Text("Blood Group: $bloodGroup", style = MaterialTheme.typography.bodyMedium)
                                    Text("Insurance Details: $insurance", style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }
                    }
                }
                item {
                    AnimatedVisibility(
                        visible = isVisible,
                        enter = fadeIn(animationSpec = tween(800))
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    "Visit Record",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Divider(modifier = Modifier.padding(vertical = 8.dp))
                                if (isEditing) {
                                    OutlinedTextField(
                                        value = visitType,
                                        onValueChange = { visitType = it },
                                        label = { Text("Visit Type") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = visitDate,
                                        onValueChange = { visitDate = it },
                                        label = { Text("Date of Visit") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = reason,
                                        onValueChange = { reason = it },
                                        label = { Text("Reason for Visit") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = vitals,
                                        onValueChange = { vitals = it },
                                        label = { Text("Vitals") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = examFindings,
                                        onValueChange = { examFindings = it },
                                        label = { Text("Physical Exam Findings") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = provisionalDiagnosis,
                                        onValueChange = { provisionalDiagnosis = it },
                                        label = { Text("Provisional Diagnosis") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = finalDiagnosis,
                                        onValueChange = { finalDiagnosis = it },
                                        label = { Text("Final Diagnosis") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = labTests,
                                        onValueChange = { labTests = it },
                                        label = { Text("Lab Tests Ordered") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = imaging,
                                        onValueChange = { imaging = it },
                                        label = { Text("Imaging Ordered") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = medications,
                                        onValueChange = { medications = it },
                                        label = { Text("Medications Prescribed") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = procedures,
                                        onValueChange = { procedures = it },
                                        label = { Text("Procedures Done") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = nutrition,
                                        onValueChange = { nutrition = it },
                                        label = { Text("Nutrition Notes") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = followUp,
                                        onValueChange = { followUp = it },
                                        label = { Text("Follow-Up Appointments") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = referrals,
                                        onValueChange = { referrals = it },
                                        label = { Text("Referrals") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = discharge,
                                        onValueChange = { discharge = it },
                                        label = { Text("Discharge Summary") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    OutlinedTextField(
                                        value = billing,
                                        onValueChange = { billing = it },
                                        label = { Text("Billing Summary") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                } else {
                                    Text("Visit Type: $visitType", style = MaterialTheme.typography.bodyMedium)
                                    Text("Date of Visit: $visitDate", style = MaterialTheme.typography.bodyMedium)
                                    Text("Reason for Visit: $reason", style = MaterialTheme.typography.bodyMedium)
                                    Text("Vitals: $vitals", style = MaterialTheme.typography.bodyMedium)
                                    Text("Physical Exam Findings: $examFindings", style = MaterialTheme.typography.bodyMedium)
                                    Text("Provisional Diagnosis: $provisionalDiagnosis", style = MaterialTheme.typography.bodyMedium)
                                    Text("Final Diagnosis: $finalDiagnosis", style = MaterialTheme.typography.bodyMedium)
                                    Text("Lab Tests Ordered: $labTests", style = MaterialTheme.typography.bodyMedium)
                                    Text("Imaging Ordered: $imaging", style = MaterialTheme.typography.bodyMedium)
                                    Text("Medications Prescribed: $medications", style = MaterialTheme.typography.bodyMedium)
                                    Text("Procedures Done: $procedures", style = MaterialTheme.typography.bodyMedium)
                                    Text("Nutrition Notes: $nutrition", style = MaterialTheme.typography.bodyMedium)
                                    Text("Follow-Up Appointments: $followUp", style = MaterialTheme.typography.bodyMedium)
                                    Text("Referrals: $referrals", style = MaterialTheme.typography.bodyMedium)
                                    Text("Discharge Summary: $discharge", style = MaterialTheme.typography.bodyMedium)
                                    Text("Billing Summary: $billing", style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}