package com.example.enrollmentplanner.features.form.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.enrollmentplanner.core.data.model.UserModel
import com.example.enrollmentplanner.core.state.DataState
import com.example.enrollmentplanner.features.form.domain.usecase.SetUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormViewModel @Inject constructor(
    private val setUserDataUseCase: SetUserDataUseCase,
) : ViewModel() {

    private val _setUserState = MutableStateFlow<DataState<Boolean>>(DataState.Loading)
    val setUserUi: StateFlow<DataState<Boolean>> get() = _setUserState

    fun setUserData(user: UserModel) {
        viewModelScope.launch {
            setUserDataUseCase.invoke(user).collect { dataState ->
                _setUserState.value = dataState
            }
        }
    }
}