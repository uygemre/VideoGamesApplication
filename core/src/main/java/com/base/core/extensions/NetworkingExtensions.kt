package com.base.core.extensions

import com.base.core.utils.Synk
import io.reactivex.Single

fun <T> Single<T>.updateSynkStatus(key: String): Single<T> {
    return this.doOnSuccess { Synk.syncSuccess(key = key) }
        .doOnError { Synk.syncFailure(key = key) }
}
