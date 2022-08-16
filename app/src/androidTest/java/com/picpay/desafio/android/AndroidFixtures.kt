package com.picpay.desafio.android

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

fun runMockServer(requestPath: String?, block: () -> Unit) {
    val server = MockWebServer()
    server.dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                requestPath -> successResponse
                else -> errorResponse
            }
        }
    }
    server.start(SERVER_PORT)
    block()
    server.close()
}

const val ERROR_CODE = 404
const val SUCCESS_CODE = 200
const val SERVER_PORT = 8080

private val successResponse by lazy {
    val successUserBody =
        "[{\"id\":1001,\"name\":\"Eduardo Santos\",\"img\":\"https://randomuser.me/api/portraits/men/9.jpg\",\"username\":\"@eduardo.santos\"}]"

    MockResponse()
        .setResponseCode(SUCCESS_CODE)
        .setBody(successUserBody)
}

private val errorResponse by lazy {
    val errorUserBody = "[{}]"
    MockResponse().setResponseCode(ERROR_CODE).setBody(errorUserBody)
}


