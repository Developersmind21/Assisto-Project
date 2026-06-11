package com.example.assisto

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.assisto.navigation.AssistoNavGraph
import com.example.assisto.ui.theme.AssistoTheme

@Composable
fun AssistoApp(openIncomingRequest: Boolean = false) {
    AssistoTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AssistoNavGraph(openIncomingRequest = openIncomingRequest)
        }
    }
}
