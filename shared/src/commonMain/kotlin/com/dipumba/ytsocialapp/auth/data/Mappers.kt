package com.dipumba.ytsocialapp.auth.data

import com.dipumba.ytsocialapp.auth.domain.model.AuthResultData

internal fun AuthResponseData.toAuthResultData(): AuthResultData{
    return AuthResultData(id, name, bio, avatar, token, followersCount, followingCount)
}