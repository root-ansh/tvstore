package io.github.curioustools.tvstore.base

data class FailureInfo(val error: Throwable, val code:Int, val message: String)

fun Throwable.extractFailureInfo(): FailureInfo {
    val t = this
    val pair =  when (t) {
        is retrofit2.HttpException -> {
            val code = t.code()
            val message = try {
                t.response()?.errorBody()?.string()?.takeIf { it.isNotBlank() } ?: t.message()
            } catch (_: Exception) {
                t.message()
            }
            code to (message ?: "HTTP error $code")
        }

        is com.google.gson.JsonSyntaxException,
        is com.google.gson.JsonIOException,
        is com.google.gson.stream.MalformedJsonException -> 422 to "Malformed or unexpected response (parse error)"
        is java.net.SocketTimeoutException -> 408 to "Request timeout"
        is java.net.ConnectException -> 503 to "Failed to connect to server"
        is java.net.UnknownHostException -> 504 to "No internet connection"
        is javax.net.ssl.SSLHandshakeException -> 495 to "SSL handshake failed"
        else -> -1 to (t.message ?: "Unexpected error")
    }
    return FailureInfo(t,pair.first,pair.second)
}