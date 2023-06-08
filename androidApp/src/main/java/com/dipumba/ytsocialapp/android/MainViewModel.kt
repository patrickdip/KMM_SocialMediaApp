package com.dipumba.ytsocialapp.android

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import com.dipumba.ytsocialapp.android.common.datastore.UserPreferences
import com.dipumba.ytsocialapp.android.common.datastore.toAuthResultData
import kotlinx.coroutines.flow.map

class MainViewModel(
    private val dataStore: DataStore<UserPreferences>
): ViewModel() {

    val authState = dataStore.data.map { it.toAuthResultData().token }
}