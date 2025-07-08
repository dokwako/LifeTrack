package com.example.lifetrack.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.lifetrack.R
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size


@Composable
fun QuickActionsRow(
    onEmergencyClick: () -> Unit,
    onSearchCLick: () -> Unit,
    OnAlmaClick: () -> Unit,
    modifier: Modifier =Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        //Emergency Button
        ActionButton(
            iconRes = R.drawable.ic_emergency,
            tint = Color(0xFFE53935),
            onClick = onEmergencyClick,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(16.dp))

        //Search Button
        ActionButton(
            iconRes = R.drawable.ic_search,
            tint = MaterialTheme.colorScheme.primary,
            onClick = onSearchCLick,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(16.dp))

        //Alma chatbot
        ActionButton(
            iconRes = R.drawable.ic_alma,
            tint = MaterialTheme.colorScheme.primary,
            onClick = OnAlmaClick,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ActionButton(
    iconRes: Int,
    tint: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(32.dp)
        )
    }

}