package com.dipumba.ytsocialapp.android

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import com.dipumba.ytsocialapp.android.common.datastore.UserSettings
import com.dipumba.ytsocialapp.android.common.datastore.toAuthResultData
import kotlinx.coroutines.flow.map

class MainActivityViewModel(
    dataStore: DataStore<UserSettings>
): ViewModel() {

    val authState = dataStore.data.map { it.toAuthResultData().token }
}