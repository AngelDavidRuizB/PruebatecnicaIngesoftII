package com.example.pruebatecnicaingesoftii.data.remote.api

import com.example.pruebatecnicaingesoftii.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.*

/**
 * Interfaz de Retrofit para definir los endpoints de la API.
 */
interface ApiService {

    @GET("apicontrollerpruebas/api/ParametrosFramework/ConsultarParametrosFramework/VPStoreAppControl")
    suspend fun getAppVersion(): Response<VersionResponse>

    @Headers(
        "Accept: text/json",
        "Content-Type: application/json",
        "Usuario: pam.meredy21",
        "Identificacion: 987204545",
        "IdUsuario: pam.meredy21",
        "IdCentroServicio: 1295",
        "NombreCentroServicio: PTO/BOGOTA/CUND/COL/OF PRINCIPAL - CRA 30 # 7-45",
        "IdAplicativoOrigen: 9"
    )
    @POST("FtEntregaElectronica/MultiCanales/ApiSeguridadPruebas/api/Seguridad/AuthenticaUsuarioApp")
    suspend fun authenticate(@Body request: AuthRequest): Response<AuthResponse>

    @GET("apicontrollerpruebas/api/SincronizadorDatos/ObtenerEsquema/true")
    suspend fun getScheme(): Response<List<Any>>

    @GET("apicontrollerpruebas/api/ParametrosFramework/ObtenerLocalidadesRecogidas")
    suspend fun getLocalities(): Response<List<LocalityDto>>
}
