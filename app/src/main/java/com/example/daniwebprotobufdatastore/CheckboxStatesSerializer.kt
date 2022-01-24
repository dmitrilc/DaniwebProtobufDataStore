package com.example.daniwebprotobufdatastore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object CheckboxStatesSerializer: Serializer<CheckboxStates> {
    override val defaultValue: CheckboxStates
        get() = CheckboxStates.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): CheckboxStates {
        try {
            return CheckboxStates.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: CheckboxStates, output: OutputStream) {
        t.writeTo(output)
    }
}

val Context.checkboxStatesDataStore: DataStore<CheckboxStates> by dataStore(
    fileName = "checkbox_states.pb",
    serializer = CheckboxStatesSerializer
)
