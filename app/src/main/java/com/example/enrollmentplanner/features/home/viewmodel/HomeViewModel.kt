package com.example.enrollmentplanner.features.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.enrollmentplanner.core.data.model.UserModel
import com.example.enrollmentplanner.core.state.DataState
import com.example.enrollmentplanner.features.home.domain.usecase.DeleteAccountUseCase
import com.example.enrollmentplanner.features.home.domain.usecase.GetUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase
) : ViewModel() {

    private val _userState = MutableStateFlow<DataState<List<UserModel>>>(DataState.Loading)
    val userUiState: StateFlow<DataState<List<UserModel>>> get() = _userState

    init {
        getUserData()
    }

    private fun getUserData() {
        viewModelScope.launch {
            getUserDataUseCase.invoke().collect { dataState ->
                _userState.value = dataState
            }
        }
    }

    fun deleteUserData(user: UserModel) {
        viewModelScope.launch {
            deleteAccountUseCase.invoke(user).collect {
                when(it) {
                    is DataState.Success -> getUserData()
                    is DataState.Error -> {}
                    DataState.Loading -> {}
                }
            }
        }
    }
}