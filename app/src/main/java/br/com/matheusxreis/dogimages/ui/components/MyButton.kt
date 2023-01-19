package br.com.matheusxreis.dogimages.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun MyButton(
    onClick: ()->Unit,
    text: String,
    icon: ImageVector,
    enabled: Boolean,
    loading: Boolean = false,
    secondary: Boolean = false,
    color: Color? = null
){
    Button(onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor= color ?: if(secondary) Color.LightGray else MaterialTheme.colorScheme.primary,
        ),
        modifier = Modifier.fillMaxWidth()) {

        if(loading){
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.width(24.dp).height(24.dp)
            );
        }else {
            Icon(icon, contentDescription = "", tint = MaterialTheme.colorScheme.background)
            Text(text = text.uppercase())
        }

    }
}
