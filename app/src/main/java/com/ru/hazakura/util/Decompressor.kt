package com.ru.hazakura.util

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import okhttp3.ResponseBody
import okio.GzipSource
import okio.buffer

class DecompressionInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        return if (originalResponse.body != null && "gzip".equals(originalResponse.header("Content-Encoding"), ignoreCase = true)) {
            val gzipSource = GzipSource(originalResponse.body!!.source())
            val decompressedBody = ResponseBody.create(originalResponse.body!!.contentType(), -1,
                gzipSource.buffer()
            )
            originalResponse.newBuilder()
                .body(decompressedBody)
                .build()
        } else {
            originalResponse
        }
    }
}