# Prueba Técnica Ingesoft II

Aplicación Android en Kotlin que consume APIs REST, implementa autenticación, control de versiones, sincronización de esquemas de base de datos y visualización de datos en múltiples pantallas.

## Arquitectura

El proyecto sigue **Clean Architecture + MVVM** con tres capas:

- **Data**: Retrofit (API REST), Room (SQLite local), Repository implementations
- **Domain**: Repository interfaces, Use Cases, Modelos de dominio
- **Presentation**: Jetpack Compose, ViewModels, Navigation

**Inyección de dependencias**: Dagger Hilt

---

## Requisitos Cubiertos

### 1. Capa Seguridad

#### Control de Versiones

**Endpoints consumidos:**
```
GET /apicontrollerpruebas/api/ParametrosFramework/ConsultarParametrosFramework/VPStoreAppControl
```

**Implementación:**

| Archivo | Descripción |
|---|---|
| `data/remote/api/ApiService.kt:32` | Definición del endpoint Retrofit |
| `data/remote/dto/VersionResponse.kt` | DTO con campo `Version` |
| `data/remote/RemoteDataSource.kt:26` | Delegación de la llamada con `safeApiCall` |
| `domain/usecase/CheckVersionUseCase.kt` | Lógica de comparación de versiones |
| `presentation/viewmodel/HomeViewModel.kt:78` | Invocación desde la UI |

La versión local se compara contra la del servidor mediante `CheckVersionUseCase.compareVersions()`. Los resultados posibles son:

- **UpdateRequired**: Versión local inferior → mensaje *"Nueva versión disponible: X.X.X. Por favor actualice."*
- **LocalSuperior**: Versión local superior → mensaje *"Versión de pruebas detectada (X.X.X)."*
- **UpToDate**: Versiones iguales → sin mensaje

Si el consumo de API falla, se captura como `NetworkResult.Error` o `NetworkResult.Exception` y se propaga como `VersionStatus.Error`.

#### Login

**Endpoints consumidos:**
```
POST /FtEntregaElectronica/MultiCanales/ApiSeguridadPruebas/api/Seguridad/AuthenticaUsuarioApp
```

**Headers fijos:**
- `Usuario: pam.meredy21`
- `Identificacion: 987204545`
- `Accept: text/json`
- `IdUsuario: pam.meredy21`
- `IdCentroServicio: 1295`
- `NombreCentroServicio: PTO/BOGOTA/CUND/COL/OF PRINCIPAL - CRA 30 # 7-45`
- `IdAplicativoOrigen: 9`

**Body:**
```json
{"Mac":"","NomAplicacion":"Controller APP","Password":"SW50ZXIyMDIx\n","Path":"","Usuario":"cGFtLm1lcmVkeTIx\n"}
```

**Implementación:**

| Archivo | Descripción |
|---|---|
| `data/remote/api/ApiService.kt:15-26` | Endpoint con `@Headers` y `@POST` |
| `data/remote/dto/AuthRequest.kt` | DTO de request con valores por defecto |
| `data/remote/dto/AuthResponse.kt` | DTO de response: `Token`, `Usuario`, `Identificacion`, `Nombre` |
| `data/remote/RemoteDataSource.kt:17-24` | Validación de código HTTP ≠ 200 |
| `data/repository/AuthRepositoryImpl.kt` | Almacena usuario en Room si es exitoso |
| `domain/usecase/AuthenticateUserUseCase.kt` | Orquestación: login → syncSchema → syncLocalities |
| `presentation/viewmodel/HomeViewModel.kt:50-76` | Manejo de UI con estados Success/Error/Exception |

**Manejo de errores HTTP (código ≠ 200):**
- `RemoteDataSource` retorna `NetworkResult.Error` con mensaje *"Alerta de Seguridad: Error en autenticación ({code})"*
- `HomeViewModel` muestra el mensaje en una tarjeta roja en pantalla: *"Alerta: Error en autenticacion ({code}) - {message}"*

**Manejo de excepciones de red:**
- `BaseRemoteDataSource.safeApiCall()` envuelve en `try/catch` y retorna `NetworkResult.Exception`
- `HomeViewModel` muestra *"Excepcion: {localizedMessage}"*

**Almacenamiento local:**
- Tabla `user_session` en Room SQLite
- Campos almacenados: `identificacion`, `usuario`, `nombre`
- Entity: `data/local/entity/UserEntity.kt`
- DAO: `data/local/dao/UserDao.kt`

---

### 2. Capa Datos

#### Base de Datos SQLite Local

**Implementación con Room:**

| Archivo | Descripción |
|---|---|
| `data/local/AppDatabase.kt` | Definición de la base de datos `ingesoft_db` con 4 entidades |
| `data/local/entity/UserEntity.kt` | Tabla `user_session` (identificacion PK, usuario, nombre) |
| `data/local/entity/SchemaTableEntity.kt` | Tabla `schema_tables` (tableName PK, description) |
| `data/local/entity/LocalityEntity.kt` | Tabla `localities` (localId autogenerado, name, code) |
| `data/local/entity/ItemEntity.kt` | Tabla `items` (id PK, title, description) |

**DAOs:**
| DAO | Operaciones |
|---|---|
| `UserDao` | `saveSession` (REPLACE), `getSession`, `clearSession` |
| `SchemaDao` | `insertTables` (REPLACE), `getAllTables`, `clearTables` |
| `LocalityDao` | `insertLocalities` (REPLACE), `getAllLocalities` (Flow), `deleteAll` |

#### Consumo de Esquema

**Endpoint:**
```
GET /apicontrollerpruebas/api/SincronizadorDatos/ObtenerEsquema/true
```

**Implementación:**

| Archivo | Descripción |
|---|---|
| `data/remote/api/ApiService.kt:28-29` | Definición del endpoint |
| `data/remote/RemoteDataSource.kt:30` | Llamada con `safeApiCall` |
| `data/repository/LocalityRepositoryImpl.kt:50-74` | Procesa respuesta, mapea a `SchemaTableEntity` y almacena en Room |

Los datos retornados por la API se transforman a `SchemaTableEntity` y se persisten en la tabla `schema_tables` mediante `SchemaDao.insertTables()`.

---

### 3. Capa Presentación

#### Navegación

**Implementación con Jetpack Compose Navigation:**

| Archivo | Descripción |
|---|---|
| `presentation/ui/Navigation.kt` | Definición de rutas y `NavHost` con 3 destinos |
| `presentation/ui/Screens.kt` | Componibles de las 3 pantallas |

Rutas definidas: `home` → `tablas` → `localidades`

#### Pantalla HOME

**Archivo:** `presentation/ui/Screens.kt:19-74`

Muestra:
- **Mensaje de versión**: tarjeta informativa si hay actualización disponible o versión de pruebas
- **Mensaje de error de login**: tarjeta roja si la autenticación falló
- **Información del usuario**: Nombre, Usuario e Identificación (desde `UserEntity`)
- **Indicador de carga** durante operaciones de red
- **Botones**: "Tablas" y "Localidades" (deshabilitados mientras carga)

**ViewModel asociado:** `presentation/viewmodel/HomeViewModel.kt`
- En `init`: `checkVersion("1.0.0")` y `loadUserOrLogin()`
- Si no hay sesión guardada, ejecuta `performLogin()` automáticamente

#### Pantalla TABLAS

**Archivo:** `presentation/ui/Screens.kt:76-98`

Muestra:
- Título "Tablas del Esquema"
- `LazyColumn` con cada `SchemaTableEntity`: `tableName` como título, `description` como subtítulo

**ViewModel asociado:** `presentation/viewmodel/TablasViewModel.kt`
- Carga las tablas desde Room mediante `GetLocalTablesUseCase`

#### Pantalla LOCALIDADES

**Endpoint:**
```
GET /apicontrollerpruebas/api/ParametrosFramework/ObtenerLocalidadesRecogidas
```

**Archivo:** `presentation/ui/Screens.kt:100-129`

Muestra:
- Título "Localidades Recogidas"
- `LazyColumn` con cada localidad: `AbreviacionCiudad` y `NombreCompleto`
- Tarjeta de error con botón "Reintentar" si falla la carga

**ViewModel asociado:** `presentation/viewmodel/LocalidadesViewModel.kt`
- Consume la API directamente mediante `GetLocalidadesUseCase`
- Maneja estados de carga, error y éxito

---

## Manejo de Errores y Excepciones

### Mecanismo Central

| Archivo | Descripción |
|---|---|
| `core/network/NetworkResult.kt` | `sealed class` con `Success`, `Error` (code + message), `Exception` |
| `core/util/Resource.kt` | `sealed class` con `Success`, `Error`, `Loading` |
| `data/remote/BaseRemoteDataSource.kt` | `safeApiCall()` envuelve toda llamada REST en `try/catch` |

### Matriz de Manejo de Errores

| API | Error de Red | Error HTTP | Respuesta en UI |
|---|---|---|---|
| `VPStoreAppControl` | `VersionStatus.Error("Error de red: ...")` | `VersionStatus.Error("Error al validar versión: ...")` | Mensaje informativo en HOME (solo si no es UpToDate) |
| `AuthenticaUsuarioApp` | `"Excepcion: {msg}"` | `"Alerta de Seguridad: Error en autenticación ({code})"` | Tarjeta roja de alerta en HOME |
| `ObtenerEsquema/true` | Propagado vía `NetworkResult` | Propagado vía `NetworkResult` | No visible directamente al usuario |
| `ObtenerLocalidadesRecogidas` | `"Excepción: {msg}"` | `"Error {code}: {msg}"` | Texto de error + botón "Reintentar" en LOCALIDADES |

---

## Principios SOLID

| Principio | Evidencia |
|---|---|
| **S**ingle Responsibility | Cada clase tiene un propósito único: `ApiService` define endpoints, `RemoteDataSource` orquesta llamadas, cada Repository maneja su dominio, cada ViewModel controla una pantalla |
| **O**pen/Closed | Las interfaces de repositorio permiten extender funcionalidad sin modificar consumidores. La base de datos es extensible agregando entidades |
| **L**iskov Substitution | Las implementaciones concretas (`AuthRepositoryImpl`, etc.) son sustituibles por sus interfaces (`AuthRepository`) |
| **I**nterface Segregation | Las interfaces de repositorio son pequeñas y específicas: `AuthRepository` (2 métodos), `LocalityRepository` (3 métodos), `ItemRepository` (2 métodos) |
| **D**ependency Inversion | La capa de Domain define interfaces; Data las implementa; Presentation depende de abstracciones (use cases, interfaces). Hilt inyecta las dependencias vía constructor |

---

## Tecnologías Usadas

| Tecnología | Propósito |
|---|---|
| Kotlin | Lenguaje de programación |
| Jetpack Compose | UI declarativa |
| Jetpack Navigation Compose | Navegación entre pantallas |
| Retrofit + Gson | Consumo de APIs REST |
| Room | Base de datos SQLite local |
| Dagger Hilt | Inyección de dependencias |
| Coroutines + Flow | Asincronía y reactividad |
