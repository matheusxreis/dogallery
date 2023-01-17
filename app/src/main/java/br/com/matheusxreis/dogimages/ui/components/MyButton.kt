package br.com.matheusxreis.dogimages.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun MyButton(
    onClick: ()->Unit,
    text: String,
    icon: ImageVector,
    enabled: Boolean,
    secondary: Boolean = false
){
    Button(onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor= if(secondary) Color.LightGray else MaterialTheme.colorScheme.primary,
        ),
        modifier = Modifier.fillMaxWidth()) {
        Icon(icon, contentDescription = "", tint = MaterialTheme.colorScheme.background)

        Text(text = text.uppercase())
    }
}
