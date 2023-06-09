package com.kyawlinnthant.onetouch.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyawlinnthant.onetouch.domain.Repository
import com.kyawlinnthant.onetouch.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: Repository,
    private val navigator: AppNavigator
) : ViewModel() {

    private val vmState = MutableStateFlow(ProfileViewModelState())
    val currentUser = vmState
        .map(ProfileViewModelState::user)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = vmState.value.user()
        )

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            repository.getCurrentUser().collect {
                vmState.update { state ->
                    state.copy(
                        currentUser = it
                    )
                }
            }
        }
    }
}