package com.example.enrollmentplanner.features.home.domain.usecase

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

sealed class DeleteAccountErrorState: ErrorType {
    data object Generic: DeleteAccountErrorState()
}

class DeleteAccountUseCase @Inject constructor(
    private val repository: FirebaseRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
): (UserModel) -> Flow<DataState<Any>> {
    override fun invoke(user: UserModel) = flow<DataState<Any>> {

        repository.deleteDocument(
            FirebaseTableEnum.User.table,
            user.id
        )

        emit(DataState.Success(Any()))
    }.onStart {
        emit(DataState.Loading)
    }.catch {
        emit(DataState.Error(DeleteAccountErrorState.Generic))
    }.flowOn(dispatcher)
}