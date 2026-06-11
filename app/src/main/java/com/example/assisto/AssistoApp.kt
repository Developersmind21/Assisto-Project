package com.example.assisto

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.assisto.navigation.AssistoNavGraph
import com.example.assisto.ui.components.AssistoStyledSnackbar
import com.example.assisto.ui.components.SnackbarHostListener
import com.example.assisto.ui.theme.AssistoTheme

@Composable
fun AssistoApp(openIncomingRequest: Boolean = false) {
    AssistoTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        SnackbarHostListener(snackbarHostState)
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            snackbarHost = {
                SnackbarHost(snackbarHostState) { data -> AssistoStyledSnackbar(data) }
            },
        ) { _ ->
            AssistoNavGraph(openIncomingRequest = openIncomingRequest)
        }
    }
}
