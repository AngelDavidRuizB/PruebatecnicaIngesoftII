package com.example.pruebatecnicaingesoftii.domain.usecase

import com.example.pruebatecnicaingesoftii.core.network.NetworkResult
import com.example.pruebatecnicaingesoftii.data.remote.RemoteDataSource
import javax.inject.Inject

/**
 * Caso de uso para verificar la versión del aplicativo.
 * Compara la versión local con la del servidor.
 */
class CheckVersionUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    sealed class VersionStatus {
        data class UpdateRequired(val serverVersion: String) : VersionStatus()
        data class LocalSuperior(val localVersion: String) : VersionStatus()
        object UpToDate : VersionStatus()
        data class Error(val message: String) : VersionStatus()
    }

    suspend operator fun invoke(localVersionName: String): VersionStatus {
        val result = remoteDataSource.checkVersion()
        
        return when (result) {
            is NetworkResult.Success -> {
                val serverVersion = result.data.version ?: "0.0.0"
                compareVersions(localVersionName, serverVersion)
            }
            is NetworkResult.Error -> VersionStatus.Error("Error al validar versión: ${result.message}")
            is NetworkResult.Exception -> VersionStatus.Error("Error de red: ${result.e.localizedMessage}")
        }
    }

    private fun compareVersions(local: String, server: String): VersionStatus {
        // Lógica de comparación simplificada para el ejemplo (asumiendo formato X.Y.Z)
        return try {
            val localParts = local.split(".").map { it.toInt() }
            val serverParts = server.split(".").map { it.toInt() }
            
            for (i in 0 until maxOf(localParts.size, serverParts.size)) {
                val l = localParts.getOrElse(i) { 0 }
                val s = serverParts.getOrElse(i) { 0 }
                if (l < s) return VersionStatus.UpdateRequired(server)
                if (l > s) return VersionStatus.LocalSuperior(local)
            }
            VersionStatus.UpToDate
        } catch (e: Exception) {
            // Si el formato no es numérico, comparación simple de strings
            if (local < server) VersionStatus.UpdateRequired(server)
            else if (local > server) VersionStatus.LocalSuperior(local)
            else VersionStatus.UpToDate
        }
    }
}
