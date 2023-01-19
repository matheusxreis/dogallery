package br.com.matheusxreis.dogimages.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog


@Composable
fun MyDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
){
    if(visible){
        Dialog(
            onDismissRequest = onDismissRequest
        ){
            content()
        }

    }
}