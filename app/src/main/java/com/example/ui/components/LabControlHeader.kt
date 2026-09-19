package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.Graphite700
import com.example.ui.theme.Graphite800
import com.example.ui.theme.Graphite850
import com.example.ui.theme.Graphite900
import com.example.ui.theme.Graphite950
import com.example.ui.theme.IndustrialAmber
import com.example.ui.theme.IndustrialAmberBright
import com.example.ui.theme.Ivory100
import com.example.ui.theme.Ivory200
import com.example.ui.theme.Ivory50
import com.example.ui.theme.LabSoftwareVerified
import com.example.ui.theme.MetallicSilver
import com.example.ui.theme.MetallicSilverDark

enum class UserLabRole(val displayName: String, val icon: ImageVector) {
    STUDENT("Student Lead", Icons.Default.Engineering),
    SUPERVISOR("Faculty Supervisor", Icons.Default.School),
    ADMIN("Lab Administrator", Icons.Default.AdminPanelSettings)
}

@Composable
fun LabControlHeader(
    currentRole: UserLabRole,
    onRoleSelected: (UserLabRole) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Graphite950)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        // Telemetry top bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(IndustrialAmber)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "INDUS ENGINEERING CTRL-SYS // ONLINE",
                    style = MaterialTheme.typography.labelSmall,
                    color = MetallicSilver
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "FYP REPO: 2026",
                    style = MaterialTheme.typography.labelSmall,
                    color = MetallicSilverDark
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(LabSoftwareVerified)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Role Selector Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .background(Graphite900)
                .padding(3.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            UserLabRole.values().forEach { role ->
                val isSelected = currentRole == role
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(4.dp))
                        .testTag("role_tab_${role.name.lowercase()}")
                        .clickable { onRoleSelected(role) },
                    color = if (isSelected) Graphite800 else Graphite900,
                    border = BorderStroke(
                        1.dp,
                        if (isSelected) IndustrialAmber.copy(alpha = 0.8f) else Graphite850
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 6.dp, horizontal = 6.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = role.icon,
                            contentDescription = role.displayName,
                            tint = if (isSelected) IndustrialAmberBright else MetallicSilverDark,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = role.displayName,
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                            color = if (isSelected) Ivory50 else MetallicSilver
                        )
                    }
                }
            }
        }
    }
}
