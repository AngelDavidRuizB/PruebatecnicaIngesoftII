package com.example.pruebatecnicaingesoftii

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Clase Application base para la configuración de Hilt.
 * Esta clase inicializa la inyección de dependencias a nivel de aplicación.
 */
@HiltAndroidApp
class BaseApplication : Application()
