package com.example.enrollmentplanner.core.data.repository

import com.example.enrollmentplanner.core.data.model.FirebaseUserModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseRepository(
    val fireStore: FirebaseFirestore,
) {
    @Throws(Exception::class)
    suspend inline fun <reified T : Any> fetchAllData(
        table: String
    ): List<T> {
        val snapshot = fireStore.collection(table).get().await()
        return snapshot.documents.mapNotNull {
            it.toObject(T::class.java)
        }
    }

    @Throws(Exception::class)
    suspend fun saveUserData(
        table: String,
        documentId: String,
        userData: FirebaseUserModel
    ): Boolean {
        val wasDocumentCreated = createOrUpdateDocument(table, documentId, userData)

        return wasDocumentCreated
    }

    @Throws(Exception::class)
    private suspend fun createOrUpdateDocument(
        table: String,
        documentId: String,
        data: FirebaseUserModel,
    ): Boolean {
        val task = fireStore
            .collection(table)
            .document(documentId)
            .set(data)
            .continueWith { it.isSuccessful }
            .await()

        if (!task) {
            throw Exception("Failed to create or update document.")
        }

        return task
    }
}