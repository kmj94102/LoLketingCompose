package com.example.network.client

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseClient @Inject constructor(
    private val storage: FirebaseStorage,
    private val fireStore: FirebaseFirestore
) {
    fun getStorageReference(path: String) = storage.reference.child(path)

    fun getFireStore(collection: String) = fireStore.collection(collection)

    suspend fun basicAddData(
        collection: String,
        data: Any
    ) = runCatching {
        fireStore
            .collection(collection)
            .add(data)
            .await()
    }
}