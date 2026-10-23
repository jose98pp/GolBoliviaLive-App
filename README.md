# GolBolivia Live — Android Application (Kotlin & Jetpack Compose)

Aplicación nativa de Android para la transmisión en vivo, seguimiento minuto a minuto y cobertura oficial de la **División Profesional del Fútbol Boliviano**.

## Arquitectura de la Aplicación Android

La aplicación está construida siguiendo las mejores prácticas de desarrollo Android moderno:

- **Lenguaje:** Kotlin 2.0+
- **Interfaz de Usuario:** Jetpack Compose + Material Design 3 (M3)
- **Patrón de Arquitectura:** Clean Architecture / MVVM (`ViewModel`, `StateFlow`, `Repository`)
- **Reproducción de Video en Vivo:** Android Media3 (ExoPlayer + ExoPlayer HLS) para streaming de baja latencia y alta resiliencia.
- **Gestión de Recursos y Assets:** Version Catalog (`gradle/libs.versions.toml`), Kotlin DSL (`build.gradle.kts`), ícono adaptativo custom (`ic_launcher`), y paleta de colores estadio (`#070B14`, `#10B981`, `#0284C7`, `#EAB308`).

## Características Principales Portadas

1. **Transmisión de Partidos en Vivo (Streaming HLS):**
   - Reproductor de video nativo con soporte para flujos HLS `.m3u8` en directo.
   - Selector multiseñal en tiempo real: Señal Principal HD (1080p 60fps), Señal de Respaldo (720p HD) y Radio/Demo oficial.
   - Botón de recarga de señal con animación de rotación e indicador de estado en búfer.

2. **Marcador Dinámico & Minuto a Minuto:**
   - Visualización de escudos, resultado en tiempo real, minuto y periodo actual ("1T", "Descanso", "2T").
   - Detalle del estadio y altitud sobre el nivel del mar (ej. *Estadio Hernando Siles • 3,637 msnm*, *Estadio Villa Ingenio • 4,083 msnm*).
   - Banner de anuncios oficiales del relator en vivo.
   - Contador de audiencia e hinchas conectados en directo con desglose por departamentos de Bolivia.

3. **Pestañas Interactivas del Partido:**
   - **Chat en Vivo:** Mensajes en tiempo real con distintivos de clubes bolivianos (Bolívar ⚡, The Strongest 🐯, Wilstermann ✈️, Oriente 🛢️, etc.), SuperChat en Bolivianos (Bs.), etiquetas para Relatores Oficiales y suscriptores VIP, y barra de reacciones rápidas (⚽, 🔥, 🇧🇴, 👏).
   - **Estadísticas Oficiales:** Comparativa visual en barras de posesión de balón, tiros al arco, córners, faltas, tarjetas amarillas/rojas y efectividad de pases.
   - **Incidencias:** Línea de tiempo cronológica con goles, tarjetas, sustituciones y revisiones VAR.
   - **Encuesta en Vivo:** Votación comunitaria con barras de progreso animadas y porcentajes en tiempo real.

4. **Catálogo de Canales Deportivos IPTV:**
   - Pestaña dedicada con canales FAST deportivos y señales satelitales (Claro Sports Latam, Red Bull TV, FIFA+ En Vivo, etc.).
   - Filtros rápidos por país: Todos (🌍), Bolivia (🇧🇴), Argentina (🇦🇷), Colombia (🇨🇴), México (🇲🇽), España (🇪🇸) e Internacional (🌐).
   - Búsqueda en vivo de canales y gestión de canales favoritos (⭐).

5. **Zona de Clubes de la División Profesional:**
   - Carrusel de los 16 clubes bolivianos con ciudad, estadio, altitud y colores representativos.
   - Tabla de posiciones oficial del torneo de fútbol boliviano.
   - Videos y contenidos exclusivos (Camerinos, Entrenamientos, Ruedas de Prensa y Entrevistas).

6. **Apoyo a la Transmisión & VIP:**
   - Modal de apoyo con QR Simple Bolivia, transferencias Banco Unión / BNB y Tigo Money.
   - Beneficios exclusivos VIP (streaming sin publicidad, insignia dorada en chat y cámaras exclusivas).

## Estructura del Código Fuente Android

```
app/
 ├── build.gradle.kts
 └── src/
     └── main/
         ├── AndroidManifest.xml
         ├── assets/crests/           # Escudos oficiales de clubes
         ├── java/com/example/golbolivialive/
         │   ├── MainActivity.kt      # ComponentActivity con enableEdgeToEdge()
         │   ├── GolBoliviaApp.kt     # Scaffold principal y navegación M3
         │   ├── data/
         │   │   ├── datasource/      # Catálogo de clubes y canales IPTV
         │   │   ├── model/           # Data classes (Club, Match, Iptv, Chat)
         │   │   └── repository/      # Repositorio y flujos StateFlow
         │   └── ui/
         │       ├── components/      # VideoPlayerSurface, ScoreboardHeader, MatchTabs
         │       ├── screens/         # LiveMatchScreen, IptvChannelsScreen, ClubZoneScreen
         │       ├── theme/           # Color, Theme, Type Material 3
         │       └── viewmodel/       # FootballViewModel
         └── res/
             ├── drawable/            # Iconos adaptativos y vectores
             ├── mipmap-*/            # Iconos ráster para mdpi, hdpi, xhdpi, xxhdpi, xxxhdpi
             └── values/              # strings.xml, colors.xml, themes.xml
```

## Compilación y Ejecución

Para compilar el proyecto en Android Studio o por línea de comandos:

```bash
gradle assembleDebug
```

El APK generado se ubica en:
`app/build/outputs/apk/debug/app-debug.apk`
