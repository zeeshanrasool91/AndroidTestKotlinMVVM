package ae.android.test.networking


import ae.android.test.base.AppConstants
import ae.android.test.networking.api.ApiMethods
import ae.android.test.networking.api.ResultWrapper
import ae.android.test.networking.enums.ErrorType
import ae.android.test.networking.response.ErrorResponse
import ae.android.test.utils.getObject
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


object NetController {
    private lateinit var mRetrofit: Retrofit
    private lateinit var mApiMethods: ApiMethods

    private val retrofit: Retrofit
        get() {
            if (!::mRetrofit.isInitialized) {
                mRetrofit = Retrofit.Builder()
                    .baseUrl(AppConstants.WEB_URL)
                    .client(httpClient)
                    .addConverterFactory(JacksonConverterFactory.create(mapper))
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
                    .build()
            }
            return mRetrofit
        }

    // Time out Issue to be Resolved
    private val httpClient: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .followRedirects(true)
                .followSslRedirects(true)
            return builder.build()
        }


    val apiMethods: ApiMethods
        get() {
            if (!::mApiMethods.isInitialized) {
                mApiMethods = retrofit.create(ApiMethods::class.java)
            }
            return mApiMethods
        }
    private val gsonBuilder: Gson
        get() {
            return GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setPrettyPrinting()
                .setLenient()
                .create()
        }

    private val mapper: ObjectMapper
        get() {
            val mapper = ObjectMapper()
            //mapper.propertyNamingStrategy = PropertyNamingStrategy.KEBAB_CASE
            return mapper
        }


    val gson: Gson
        get() = GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setPrettyPrinting()
            .setLenient()
            .create()

    //https://www.programmersought.com/article/3973701501/
    suspend fun <T : Any> callApi(
        dispatcher: CoroutineDispatcher,
        call: suspend () -> Response<T>
    ): ResultWrapper<T> {
        return withContext(dispatcher) {
            safeApiResult(call)
        }

    }

    private suspend fun <T : Any> safeApiResult(
        call: suspend () -> Response<T>
    ): ResultWrapper<T> {
        val response = call.invoke()
        return try {
            if (response.isSuccessful && response.body() != null) {
                ResultWrapper.Success(response.body()!!)
            } else if (response.errorBody() != null) {
                try {

                    val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
                    val error = withContext(ioDispatcher) {
                        @Suppress("BlockingMethodInNonBlockingContext")
                        response.errorBody()!!.string()
                    }
                    val errorResponse = error.getObject(ErrorResponse::class.java)
                    errorResponse.statusCode = response.code()
                    ResultWrapper.Failed(errorResponse)
                } catch (e: Exception) {
                    e.printStackTrace()
                    ResultWrapper.Exception(e)
                }
            } else {
                ResultWrapper.Failed(
                    ErrorResponse(
                        statusCode = response.code(),
                        errorType = ErrorType.RESPONSE_ERROR,
                        message = response.message(),
                        data = null
                    )
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
            ResultWrapper.Failed(
                ErrorResponse(
                    ErrorType.NETWORK_ERROR,
                    response.code(),
                    response.message(), null
                )
            )
        }
    }
}