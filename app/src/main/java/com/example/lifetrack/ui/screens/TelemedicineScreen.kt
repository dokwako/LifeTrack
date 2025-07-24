package com.example.lifetrack.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

// Color Definitions
val AvailableColor = Color(0xFF4CAF50)  // Green for available doctors
val BusyColor = Color(0xFFF44336)      // Red for busy doctors
val DisabledColor = Color(0xFF9E9E9E)  // Grey for disabled state
val CardBackground = Color(0xFFF5F5F5) // Light card background
val RatingColor = Color(0xFFFFC107)    // Amber for ratings
val PremiumTeal = Color(0xFF26A69A)    // Teal for premium features
val PremiumGold = Color(0xFFFDD835)    // Gold accent
val PremiumPurple = Color(0xFFAB47BC)  // Purple accent
val GradientStart = Color(0xFFE0F7FA)  // Light gradient start
val GradientEnd = Color(0xFF80DEEA)    // Teal gradient end

data class DoctorProfile(
    val id: Int,
    val name: String,
    val specialty: String,
    val status: String,
    val imageRes: Int,
    val experienceYears: Int,
    val availability: String,
    val rating: Float,
    val hospital: String,
    val waitTime: String
)

data class PremiumFeature(
    val id: Int,
    val title: String,
    val description: String,
    val icon: @Composable () -> Unit,
    val accentColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelemedicineScreen(navController: NavController) {
    var isVisible by remember { mutableStateOf(false) }

    val doctors = listOf(
        DoctorProfile(
            id = 1,
            name = "Dr. Hilary Otieno",
            specialty = "General Practitioner",
            status = "Available",
            imageRes = android.R.drawable.ic_menu_gallery,
            experienceYears = 8,
            availability = "9:00 AM - 1:00 PM",
            rating = 4.7f,
            hospital = "Nakuru General Hospital",
            waitTime = "5-10 mins"
        ),
        DoctorProfile(
            id = 2,
            name = "Dr. Mercy Baraka",
            specialty = "Cardiologist",
            status = "Busy",
            imageRes = android.R.drawable.ic_menu_gallery,
            experienceYears = 12,
            availability = "1:00 PM - 5:00 PM",
            rating = 4.9f,
            hospital = "Rift Valley Provincial Hospital",
            waitTime = "15-20 mins"
        ),
        DoctorProfile(
            id = 3,
            name = "Dr. Tabitha Kerry",
            specialty = "Allergist",
            status = "Available",
            imageRes = android.R.drawable.ic_menu_gallery,
            experienceYears = 6,
            availability = "10:00 AM - 2:00 PM",
            rating = 4.5f,
            hospital = "Kabarak Mission Hospital",
            waitTime = "10-15 mins"
        ),
        DoctorProfile(
            id = 4,
            name = "Dr. James Mwangi",
            specialty = "Pediatrician",
            status = "Available",
            imageRes = android.R.drawable.ic_menu_gallery,
            experienceYears = 10,
            availability = "9:00 AM - 12:00 PM",
            rating = 4.6f,
            hospital = "Nairobi City Hospital",
            waitTime = "5-10 mins"
        ),
        DoctorProfile(
            id = 5,
            name = "Dr. Amina Hassan",
            specialty = "Dermatologist",
            status = "Busy",
            imageRes = android.R.drawable.ic_menu_gallery,
            experienceYears = 7,
            availability = "2:00 PM - 6:00 PM",
            rating = 4.8f,
            hospital = "Mombasa Medical Center",
            waitTime = "20-25 mins"
        ),
        DoctorProfile(
            id = 6,
            name = "Dr. Mitchell Akinyi",
            specialty = "Neurologist",
            status = "Available",
            imageRes = android.R.drawable.ic_menu_gallery,
            experienceYears = 9,
            availability = "8:00 AM - 12:00 PM",
            rating = 4.6f,
            hospital = "Kisumu Referral Hospital",
            waitTime = "10-15 mins"
        ),
        DoctorProfile(
            id = 7,
            name = "Dr. Kingsley Coman",
            specialty = "Orthopedist",
            status = "Busy",
            imageRes = android.R.drawable.ic_menu_gallery,
            experienceYears = 11,
            availability = "1:00 PM - 4:00 PM",
            rating = 4.7f,
            hospital = "Eldoret Teaching Hospital",
            waitTime = "15-20 mins"
        ),
        DoctorProfile(
            id = 8,
            name = "Dr. Emmanuel Mutubi",
            specialty = "Endocrinologist",
            status = "Available",
            imageRes = android.R.drawable.ic_menu_gallery,
            experienceYears = 5,
            availability = "10:00 AM - 3:00 PM",
            rating = 4.4f,
            hospital = "Thika Level 5 Hospital",
            waitTime = "5-10 mins"
        ),
        DoctorProfile(
            id = 9,
            name = "Dr. Curtis Roy",
            specialty = "Ophthalmologist",
            status = "Available",
            imageRes = android.R.drawable.ic_menu_gallery,
            experienceYears = 13,
            availability = "2:00 PM - 6:00 PM",
            rating = 4.9f,
            hospital = "Kenyatta National Hospital",
            waitTime = "20-25 mins"
        )
    )

    val premiumFeatures = listOf(
        PremiumFeature(
            id = 1,
            title = "Priority Access",
            description = "Skip the queue with immediate consultation slots",
            icon = { Icon(Icons.Filled.FlashOn, contentDescription = null, tint = PremiumTeal) },
            accentColor = PremiumTeal
        ),
        PremiumFeature(
            id = 2,
            title = "Extended Sessions",
            description = "30-minute consultations with in-depth care",
            icon = { Icon(Icons.Filled.Schedule, contentDescription = null, tint = PremiumGold) },
            accentColor = PremiumGold
        ),
        PremiumFeature(
            id = 3,
            title = "Specialist Priority",
            description = "Access top-rated specialists first",
            icon = { Icon(Icons.Filled.Star, contentDescription = null, tint = PremiumPurple) },
            accentColor = PremiumPurple
        ),
        PremiumFeature(
            id = 4,
            title = "Personal Health Insights",
            description = "Get detailed health analytics and trends",
            icon = { Icon(Icons.Filled.Insights, contentDescription = null, tint = PremiumTeal) },
            accentColor = PremiumTeal
        )
    )

    LaunchedEffect(Unit) {
        delay(300)
        isVisible = true
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Telemedicine",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(600))
                ) {
                    Column {
                        Text(
                            text = "Available Doctors",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = "Connect with specialists for virtual consultations",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            items(doctors) { doctor ->
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(700 + doctor.id * 100))
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = 4.dp,
                                shape = RoundedCornerShape(12.dp),
                                clip = true
                            ),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = CardBackground
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            // Doctor Header Row
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Image(
                                    painter = painterResource(id = doctor.imageRes),
                                    contentDescription = "Doctor Image",
                                    modifier = Modifier
                                        .size(64.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = doctor.name,
                                        style = MaterialTheme.typography.titleLarge,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = doctor.specialty,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = "${doctor.experienceYears} years experience",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Star,
                                        contentDescription = "Rating",
                                        modifier = Modifier.size(18.dp),
                                        tint = RatingColor
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "%.1f".format(doctor.rating),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = RatingColor,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Status Section
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    imageVector = if (doctor.status == "Available") Icons.Filled.CheckCircle else Icons.Filled.Schedule,
                                    contentDescription = "Status",
                                    modifier = Modifier.size(24.dp),
                                    tint = if (doctor.status == "Available") AvailableColor else BusyColor
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "Status: ${doctor.status}",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = if (doctor.status == "Available") AvailableColor else BusyColor,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = "Available: ${doctor.availability}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = "Wait time: ${doctor.waitTime}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Consultation Button
                            Button(
                                onClick = { /* Handle consultation */ },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (doctor.status == "Available") AvailableColor else BusyColor,
                                    disabledContainerColor = DisabledColor
                                ),
                                enabled = doctor.status == "Available",
                                elevation = ButtonDefaults.buttonElevation(
                                    defaultElevation = 2.dp,
                                    pressedElevation = 4.dp,
                                    disabledElevation = 0.dp
                                )
                            ) {
                                Text(
                                    text = if (doctor.status == "Available") "Start Consultation" else "Currently Busy",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            // Hospital Info
                            Text(
                                text = "Hospital: ${doctor.hospital}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }
            }

            item {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(1200))
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(16.dp),
                                clip = true
                            ),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .background(
                                    brush = Brush.verticalGradient(listOf(GradientStart, GradientEnd))
                                )
                                .padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 12.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = "Premium",
                                    modifier = Modifier.size(28.dp),
                                    tint = PremiumGold
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "Premium Features",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Unlock a premium healthcare experience with exclusive benefits!",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            premiumFeatures.forEach { feature ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 12.dp)
                                        .background(
                                            color = feature.accentColor.copy(alpha = 0.1f),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .padding(8.dp)
                                ) {
                                    feature.icon()
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Column {
                                        Text(
                                            text = feature.title,
                                            style = MaterialTheme.typography.titleMedium,
                                            color = feature.accentColor,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Text(
                                            text = feature.description,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Button(
                                onClick = { /* Handle upgrade */ },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = PremiumTeal
                                ),
                                elevation = ButtonDefaults.buttonElevation(
                                    defaultElevation = 4.dp,
                                    pressedElevation = 6.dp
                                )
                            ) {
                                Text(
                                    text = "Upgrade Now",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }

                            // Call-to-Action Badge
                            Box(
                                modifier = Modifier
                                    .align(Alignment.End)
                                    .padding(top = 8.dp)
                                    .background(
                                        color = PremiumPurple,
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "Limited Offer: 25% Off!",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}