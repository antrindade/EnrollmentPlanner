package com.example.enrollmentplanner.features.form.domain.usecase

import com.example.enrollmentplanner.core.data.model.UserModel
import com.example.enrollmentplanner.core.data.repository.FirebaseRepository
import com.example.enrollmentplanner.core.di.IoDispatcher
import com.example.enrollmentplanner.core.state.DataState
import com.example.enrollmentplanner.core.state.ErrorType
import com.example.enrollmentplanner.core.util.enum.FirebaseTableEnum
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

sealed class SetUserData : ErrorType {
    data object SetUserDataError : SetUserData()
}

class SetUserDataUseCase @Inject constructor(
    private val repository: FirebaseRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): (UserModel) -> Flow<DataState<Boolean>> {

    override fun invoke(user: UserModel): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        val firstNameLetters = user.name.take(3)?.toUpperCase()

        val lastFourDigits = user.phone.takeLast(4)

        val documentId = "$firstNameLetters$lastFourDigits${user.uf.toUpperCase()}"

        val result = repository.saveUserData(FirebaseTableEnum.User.table, documentId, user.toFirebaseUser())
        emit(DataState.Success(result))
    }.onStart {
        emit(DataState.Loading)
    }.catch { exception ->
        exception.printStackTrace()
        emit(DataState.Error(SetUserData.SetUserDataError))
    }.flowOn(dispatcher)
}