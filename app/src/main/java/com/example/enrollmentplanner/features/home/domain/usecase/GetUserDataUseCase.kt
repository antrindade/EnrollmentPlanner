package com.example.enrollmentplanner.features.home.domain.usecase

import com.example.enrollmentplanner.core.data.model.FirebaseUserModel
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

sealed class UserUIState : ErrorType {
    data object GenericError : UserUIState()
}

class GetUserDataUseCase @Inject constructor(
    private val repository: FirebaseRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : () -> Flow<DataState<List<UserModel>>> {

    override fun invoke(): Flow<DataState<List<UserModel>>> = flow {
        emit(DataState.Loading)
        val firebaseUsers = repository.fetchAllData<FirebaseUserModel>(
            FirebaseTableEnum.User.table
        )
        val users = firebaseUsers.map { it.toUserModel() }

        emit(DataState.Success(users))
    }.onStart {
        emit(DataState.Loading)
    }.catch {
        it.printStackTrace()
        emit(DataState.Error(UserUIState.GenericError))
    }.flowOn(dispatcher)
}