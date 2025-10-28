package dev.luisbaena.prodentclient.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.luisbaena.prodentclient.data.remote.api.AuthApiService
import dev.luisbaena.prodentclient.domain.repository.AuthRepository
import dev.luisbaena.prodentclient.domain.usecase.ChangePasswordUseCase
import dev.luisbaena.prodentclient.domain.usecase.GetProfileUseCase
import dev.luisbaena.prodentclient.domain.usecase.UpdateProfileUseCase
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton



/*
 * Funcion principal: Proveer las dependencias relacionadas con la red.
 *  provideJson: Provee una instancia de Json configurada para la serialización/deserialización de JSON.
 *  provideHttpLoggingInterceptor: Provee un interceptor de logging para las solicitudes HTTP.
 *  provideOkHttpClient: Provee una instancia de OkHttpClient configurada con el interceptor de logging y tiempos de espera, que permite realizar solicitudes HTTP.
 *  provideRetrofit : Provee una instancia de Retrofit configurada con la URL base, el cliente OkHttp y el convertidor de JSON, que facilita la comunicación con APIs REST.
 *  provideAuthApiService: Provee una instancia de AuthApiService creada a partir de Retrofit, que define los endpoints de autenticación.
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // TODO: VERIFICAR ESTO y documentar el archivo
    //private const val BASE_URL = "https://prodent.onrender.com/api/"

    private const val BASE_URL = "https://prodent-api.onrender.com"
    // private const val BASE_URL = "http://localhost:8080/"

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true // Permite ignorar campos desconocidos en el JSON
        coerceInputValues = true // Intenta convertir valores que no coinciden exactamente con el tipo esperado
        isLenient = true // Permite un análisis más flexible del JSON
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Registra el cuerpo completo de las solicitudes y respuestas HTTP
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // Agrega el interceptor de logging
            .connectTimeout(30, TimeUnit.SECONDS) // Tiempo de espera para establecer la conexión
            .readTimeout(30, TimeUnit.SECONDS) // Tiempo de espera para leer datos
            .writeTimeout(30, TimeUnit.SECONDS)// Tiempo de espera para escribir datos
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        json: Json
    ): retrofit2.Retrofit {
        return retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL) // Establece la URL base para las solicitudes HTTP
            .client(okHttpClient) // Usa el cliente OkHttp configurado
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType())) // Usa el convertidor de JSON
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService{
        return retrofit.create(AuthApiService::class.java) // Crea una implementación de AuthApiService usando Retrofit
    }

    @Provides
    fun provideGetProfileUseCase(authRepository: AuthRepository): GetProfileUseCase {
        return GetProfileUseCase(authRepository)
    }

    @Provides
    fun provideUpdateProfileUseCase(authRepository: AuthRepository): UpdateProfileUseCase {
        return UpdateProfileUseCase(authRepository)
    }

    @Provides
    fun provideChangePasswordUseCase(authRepository: AuthRepository): ChangePasswordUseCase {
        return ChangePasswordUseCase(authRepository)
    }
}