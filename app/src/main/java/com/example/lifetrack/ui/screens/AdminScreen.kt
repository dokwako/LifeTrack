package com.example.lifetrack.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lifetrack.data.model.Kiongozi
import com.example.lifetrack.data.model.Practitioner
import com.example.lifetrack.data.model.User
import com.example.lifetrack.data.repository.UserRepositoryImpl
import com.example.lifetrack.presenter.UserPresenter
import com.example.lifetrack.view.UserView



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(navController: NavController) {
    var selectedTab by remember { mutableIntStateOf(0) }

    val tabs = listOf("Patients", "Practitioners", "Admins")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Admin Panel") },
                navigationIcon = { IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.Edit, contentDescription = "Back")
                } }
            )
        },
    ) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {

                    PrimaryTabRow(selectedTabIndex = selectedTab) {
                        tabs.forEachIndexed { index, title ->
                            Tab(selected = selectedTab == index, onClick = { selectedTab = index }) {
                                Text(
                                    text = title,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }

            when (selectedTab) {
                0 -> ManagePatients()
                1 -> ManagePractitioners()
                2 -> ManageAdmins()
            }
        }
    }
}

@Composable
fun ManageAdmins() {
    var admins by remember { mutableStateOf(listOf<Kiongozi>()) }
    var showAddDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(admins.size) { index ->
                val admin = admins[index]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(admin.fullName, style = MaterialTheme.typography.titleMedium)
                            Text(admin.emailAddress, style = MaterialTheme.typography.bodySmall)
                        }
                        IconButton(onClick = { admins = admins.minus(admin) }) {
                            Icon(Icons.Filled.Edit, contentDescription = "Delete Admin")
                        }
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = { showAddDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add Admin")
        }
        if (showAddDialog) {
            AddAdminDialog(
                onDismiss = { showAddDialog = false },
                onAdd = { newAdmin -> admins = admins + newAdmin }
            )
        }
    }
}

@Composable
fun AddAdminDialog(onDismiss: () -> Unit, onAdd: (Kiongozi) -> Unit) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Admin") },
        text = {
            Column {
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = { Text("Full Name") }
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (fullName.isNotBlank() && email.isNotBlank()) {
                        onAdd(Kiongozi(id = java.util.UUID.randomUUID().toString(), fullName = fullName, emailAddress = email))
                        onDismiss()
                    }
                }
            ) { Text("Add") }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun ManagePatients() {
    var patients by remember { mutableStateOf(listOf<User>()) }
    var showAddDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(patients.size) { index ->
                val patient = patients[index]
                PatientCard(patient, onDelete = {
                    patients = patients.minus(patient)
                }, onUpdate = {
                })
            }
        }

        FloatingActionButton(
            onClick = { showAddDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add Patient")
        }

        if (showAddDialog) {
            AddPatientDialog(onDismiss = { showAddDialog = false }, onAdd = { newPatient ->
                patients = patients.plus(newPatient)
            })
        }
    }
}

@Composable
fun AddPatientDialog(onDismiss: () -> Unit, onAdd: (User) -> Unit) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Patient") },
        text = {
            Column {
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = { Text("Full Name") }
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") }
                )
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (fullName.isNotBlank() && email.isNotBlank()) {
                        onAdd(User(lifetrackId = java.util.UUID.randomUUID().toString(), fullName = fullName, phoneNumber = phone, emailAddress = email))
                        onDismiss()
                    }
                }
            ) { Text("Add") }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun PatientCard(user: User, onDelete: () -> Unit, onUpdate: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(user.fullName, style = MaterialTheme.typography.titleMedium)
                Text(user.emailAddress, style = MaterialTheme.typography.bodySmall)
            }
            Row {
                IconButton(onClick = onUpdate) {
                    Icon(Icons.Filled.Edit, contentDescription = "Edit Patient")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete Patient")
                }
            }
        }
    }
}

@Composable
fun PractitionerCard(user: Practitioner, onDelete: () -> Unit, onUpdate: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(user.fullName, style = MaterialTheme.typography.titleMedium)
                Text(user.emailAddress, style = MaterialTheme.typography.bodySmall)
            }
            Row {
                IconButton(onClick = onUpdate) {
                    Icon(Icons.Filled.Edit, contentDescription = "Edit Patient")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete Patient")
                }
            }
        }
    }
}

@Composable
fun AddPractitionerDialog(onDismiss: () -> Unit, onAdd: (Practitioner) -> Unit) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Practitioner") },
        text = {
            Column {
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = { Text("Full Name") }
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") }
                )
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (fullName.isNotBlank() && email.isNotBlank()) {
                        onAdd(Practitioner(lifetrackId = java.util.UUID.randomUUID().toString(), fullName = fullName, phoneNumber = phone, emailAddress = email, hospitalId = java.util.UUID.randomUUID().toString()))
                        onDismiss()
                    }
                }
            ) { Text("Add") }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun ManagePractitioners() {
    val userView = remember {
        object : UserView {
            override fun showUserData(user: User) {}
            override fun updateUserUI(user: User) {}
            override fun showError(message: String) {}
            override fun showMessage(message: String) {}
            override fun onLogout() {}
        }
    }
    val userPresenter = remember { UserPresenter(userView, UserRepositoryImpl()) }
    var practitioners by remember { mutableStateOf(listOf<Practitioner>()) }
    var showEditDialog by remember { mutableStateOf(false) }
    var practitionerToEdit by remember { mutableStateOf<Practitioner?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        userPresenter.getPractitioners { fetchedList ->
            practitioners = fetchedList
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(practitioners.size) { index ->
                val practitioner = practitioners[index]
                PractitionerCard(
                    user = practitioner,
                    onDelete = {
                        userPresenter.deletePractitioner(practitioner) {
                            practitioners = practitioners.filter { it.lifetrackId != practitioner.lifetrackId }
                        }
                    },
                    onUpdate = {
                        practitionerToEdit = practitioner
                        showEditDialog = true
                    }
                )
            }
        }

        FloatingActionButton(
            onClick = { showAddDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add Practitioner")
        }

        if (showAddDialog) {
            AddPractitionerDialog(
                onDismiss = { showAddDialog = false },
                onAdd = { newPractitioner ->
                    userPresenter.addPractitioner(newPractitioner) { added ->
                        practitioners = practitioners + added
                        showAddDialog = false
                    }
                }
            )
        }

        if (showEditDialog && practitionerToEdit != null) {
            EditPractitionerDialog(
                practitioner = practitionerToEdit!!,
                onDismiss = { showEditDialog = false },
                onUpdate = { updatedPractitioner ->
                    userPresenter.updatePractitioner(updatedPractitioner) { updated ->
                        practitioners = practitioners.map {
                            if (it.lifetrackId == updated.lifetrackId) updated else it
                        }
                        showEditDialog = false
                    }
                }
            )
        }
    }
}
@Composable
fun EditPractitionerDialog(
    practitioner: Practitioner,
    onDismiss: () -> Unit,
    onUpdate: (Practitioner) -> Unit
) {
    var fullName by remember { mutableStateOf(practitioner.fullName) }
    var email by remember { mutableStateOf(practitioner.emailAddress) }
    var phone by remember { mutableStateOf(practitioner.phoneNumber) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Practitioner") },
        text = {
            Column {
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = { Text("Full Name") }
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") }
                )
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (fullName.isNotBlank() && email.isNotBlank()) {
                        onUpdate(
                            practitioner.copy(
                                fullName = fullName,
                                emailAddress = email,
                                phoneNumber = phone
                            )
                        )
                        onDismiss()
                    }
                }
            ) { Text("Update") }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
