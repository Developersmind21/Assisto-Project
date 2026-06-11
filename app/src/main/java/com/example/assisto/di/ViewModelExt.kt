package com.example.assisto.di

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assisto.AssistoApplication

@Composable
inline fun <reified VM : ViewModel> assistoViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current),
): VM {
    val app = LocalContext.current.applicationContext as AssistoApplication
    return viewModel(
        viewModelStoreOwner = viewModelStoreOwner,
        factory = AssistoViewModelFactory(app, app.container),
    )
}
