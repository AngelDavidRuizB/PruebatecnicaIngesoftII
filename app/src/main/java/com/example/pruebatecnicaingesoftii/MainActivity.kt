package com.example.pruebatecnicaingesoftii

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import com.example.pruebatecnicaingesoftii.presentation.ui.AppNavigation
import dagger.hilt.android.AndroidEntryPoint

/**
 * Actividad principal que aloja el sistema de navegación de Compose.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface {
                AppNavigation()
            }
        }
    }
}
