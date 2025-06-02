package com.yeshuwahane.d4c.data.utils

import okio.IOException
import retrofit2.HttpException


suspend inline fun <T, R> safeApiCall(
    crossinline apiCall: suspend () -> T,
    crossinline mapper: (T) -> R
): DataResource<R> {
    return try {
        val response = apiCall()
        DataResource.success(mapper(response))
    } catch (e: Exception) {
        DataResource.error(e, null)
    }
}



/*
suspend inline fun <T, R> safeApiCall(
    crossinline apiCall: suspend () -> T,
    crossinline mapper: (T) -> R
): DataResource<R> {
    return try {
        val response = apiCall()
        DataResource.success(mapper(response))
    } catch (e: HttpException) {
        DataResource.error(Throwable(e.cause), null)
    } catch (e: IOException) {
        DataResource.error(Throwable("Network error: ${e.localizedMessage}"), null)
    } catch (e: Exception) {
        DataResource.error(Throwable(e.localizedMessage ?: "Unexpected error"), null)
    }
}
*/


