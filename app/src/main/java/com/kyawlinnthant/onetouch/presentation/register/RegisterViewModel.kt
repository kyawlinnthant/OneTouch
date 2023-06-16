package com.kyawlinnthant.onetouch.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyawlinnthant.onetouch.common.DataResult
import com.kyawlinnthant.onetouch.domain.Repository
import com.kyawlinnthant.onetouch.navigation.AppNavigator
import com.kyawlinnthant.onetouch.presentation.main.Graph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repo: Repository,
    private val navigator: AppNavigator
) : ViewModel() {

    private val vmState = MutableStateFlow(RegisterViewModelState())
    val isLoading = vmState
        .map(RegisterViewModelState::loading)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = vmState.value.loading()
        )

    fun signup(email: String, pwd: String) {
        vmState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            repo.signupWithEmail(email = email, pwd = pwd).also {
                vmState.update { state ->
                    state.copy(isLoading = false)
                }
                when (it) {
                    is DataResult.Fail -> {

                    }

                    is DataResult.Success -> {
                        navigator.to(
                            route = Graph.Feature.route,
                            popupToRoute = Graph.Auth.route,
                            inclusive = true
                        )
                    }
                }
            }
        }
    }
}
