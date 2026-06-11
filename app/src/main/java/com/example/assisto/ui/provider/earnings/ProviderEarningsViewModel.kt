package com.example.assisto.ui.provider.earnings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assisto.data.repository.EarningsData
import com.example.assisto.data.repository.EarningsPeriod
import com.example.assisto.data.repository.UserRepository
import com.example.assisto.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProviderEarningsViewModel constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _period = MutableStateFlow(EarningsPeriod.THIS_WEEK)
    val period: StateFlow<EarningsPeriod> = _period.asStateFlow()

    private val _data = MutableStateFlow<UiState<EarningsData>>(UiState.Loading)
    val data: StateFlow<UiState<EarningsData>> = _data.asStateFlow()

    init { load() }

    fun setPeriod(p: EarningsPeriod) {
        _period.value = p
        load()
    }

    private fun load() {
        viewModelScope.launch {
            _data.value = UiState.Loading
            userRepository.getEarnings(_period.value).collect { d ->
                _data.value = UiState.Success(d)
            }
        }
    }
}
