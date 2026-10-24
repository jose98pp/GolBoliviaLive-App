# Rediseño Apple TV Sports & Integración Firebase Firestore

Transformación integral de **GolBolivia Live** a una experiencia cinematográfica inspirada directamente en la interfaz deportiva de **Apple TV+ / MLS Season Pass** (con Hero superior interactivo y carrusel inferior de tarjetas divididas por colores de clubes), respaldada por sincronización reactiva en tiempo real desde **Firebase Firestore**.

---

### User Review & Critical Decisions

> [!IMPORTANT]
> **Decisiones confirmadas por el usuario en Fase 1:**
> 1. **Estructura Principal:** Hero superior cinematográfico con transmisión de video / radar táctico en vivo y carrusel horizontal inferior de partidos destacados.
> 2. **Fuente de Datos Firebase Firestore:** Escucha en tiempo real mediante `addSnapshotListener` sobre las colecciones `/liveEvents` y `/matches` con fallback a datos base bolivianos en caso de modo offline.
> 3. **Estilo Visual de Tarjetas:** Tarjetas dual-tone divididas exactamente como la imagen de referencia (color oficial del club local a la izquierda, color oficial del club visitante a la derecha, insignias de alta fidelidad, píldora "EN VIVO" roja y marcador/tiempo de juego).

---

### 1. Overview & Core Concept

- **Qué hace:** Ofrece a los aficionados del fútbol boliviano una interfaz de transmisión deportiva premium idéntica a Apple TV. Presenta un *Spotlight Hero* inmersivo de borde a borde para el partido estelar (con ExoPlayer y radar 2D táctico de contingencia), seguido de una marquesina horizontal de tarjetas de partidos estilizadas con la identidad cromática de los clubes de la División Profesional de Bolivia (Bolívar, The Strongest, Wilstermann, Oriente Petrolero, Blooming, Always Ready, etc.).
- **Público Objetivo:** Hinchas y usuarios móviles que buscan seguir la División Profesional de Bolivia con una estética visual moderna, fluida y sin pantallas de error.
- **Valor Principal:** Elimina la apariencia genérica de listas simples; proporciona la sensación visual de un servicio de streaming de primer nivel internacional y refleja en milisegundos los goles, minutos y cambios de señal registrados en Firebase Firestore.

---

### 2. User Experience & Visual Design

#### Flujo de Usuario Clave
1. **Entrada a la App (Home Apple TV):**
   - La pantalla abre en modo inmersivo oscuro (`#070B14`).
   - El **Hero Superior** sintoniza el partido estelar o la señal activa con badge flotante "EN VIVO", título del clásico, estadio y tiempo de juego.
   - Si no hay señal de video externa disponible o hay desconexión en el emulador, el Hero pasa fluidamente al **Radar Táctico 2D** en la cancha sin interrumpir la experiencia.
2. **Carrusel Horizontal de Partidos ("En Vivo & Próximos"):**
   - Justo debajo del Hero, el usuario desliza horizontalmente tarjetas con esquinas redondeadas (16dp) divididas en 50/50 con los colores de cada club (ej. celeste Bolívar / oro-negro The Strongest; rojo-azul Wilstermann / verde Oriente Petrolero).
   - Al tocar cualquier tarjeta del carrusel, el Hero superior cambia al partido seleccionado de inmediato.
3. **Pestañas y Controles Flotantes de Apple TV:**
   - Barra de navegación inferior flotante tipo pastilla esmerilada (Frosted Glass Pill) con iconos limpios: *En Vivo*, *Posiciones*, *Canales IPTV*, *Clubes*.
   - Sección de estadísticas, incidencias y chat en vivo con diseño limpio y tipografía de alto contraste.

#### Identidad Visual y Paleta de Colores
- **Fondo:** Dark OLED (`#05080F` y `#0B1120`).
- **Acento Primario:** Verde Esmeralda Live (`#10B981`) y Rojo Transmisión (`#EF4444`).
- **Superficies:** Tarjetas de vidrio esmerilado con borde sutil (`rgba(255, 255, 255, 0.08)`).
- **Tarjetas Bicolores:** Colores heráldicos oficiales de cada club boliviano con gradientes diagonales sutiles y contraste de texto asegurado (blanco/oscuro según luminancia).

---

### 3. Key Product Decisions & Trade-Offs

- **Decisión 1: Hero Dual (ExoPlayer + InteractivePitchRadar)**
  - *Enfoque:* El Hero integra ExoPlayer con reconexión automática y detección de red; ante errores (`UnknownHostException` o falta de internet en el emulador sandbox), conmuta suavemente al radar 2D en cancha.
  - *Por qué:* Garantiza que el usuario nunca vea pantallas negras ni errores de reproducción; siempre hay acción en pantalla.
- **Decisión 2: Capa de Datos Híbrida Firestore con Resiliencia Local**
  - *Enfoque:* `FirestoreMatchRepository` escucha los documentos en tiempo real de Firestore (`/liveEvents` o `/config/stream_settings` y `/match/scoreboard`). Si la base de datos está vacía o el dispositivo no tiene red, se inicializan automáticamente con la fecha oficial del fútbol boliviano y continúan sincronizando en caché.
  - *Por qué:* El usuario disfruta de datos reales actualizados desde la nube sin riesgo de que la app quede en blanco por falta de conexión o colecciones vacías.

---

### 4. Technical Architecture & Data Strategy

#### Diagrama de Arquitectura del Sistema

```
┌────────────────────────────────────────────────────────────────────────┐
│                        GOLBOLIVIA LIVE (APP)                           │
│                                                                        │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │                    APPLE TV SPOTLIGHT HERO                       │  │
│  │  - VideoPlayerSurface (ExoPlayer HLS / Resilient DataSource)     │  │
│  │  - InteractivePitchRadar (Canvas 2D Tactical Pitch Fallback)     │  │
│  │  - Floating Frosted Glass Metadata Pill (Live, Score, Minute)    │  │
│  └──────────────────────────────────────────────────────────────────┘  │
│                                   │                                    │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │             HORIZONTAL SPLIT DUAL-COLOR MATCH CARDS              │  │
│  │  [ Local (Color A) | Visita (Color B) ] -> Tap to Focus Hero     │  │
│  └──────────────────────────────────────────────────────────────────┘  │
│                                   │                                    │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │              INTERACTIVE MATCH HUB (TABS & SHEETS)               │  │
│  │  - Chat en Vivo con Insignias  - Estadísticas Cara a Cara        │  │
│  │  - Línea de Incidencias        - Tabla de Posiciones División P. │  │
│  └──────────────────────────────────────────────────────────────────┘  │
└───────────────────────────────────┬────────────────────────────────────┘
                                    │
                         StateFlow & UI State
                                    │
┌───────────────────────────────────┴────────────────────────────────────┐
│                    LIVE MATCH & FIRESTORE VIEWMODEL                    │
│  - Observa StateFlow<List<LiveMatch>> y StateFlow<LiveMatch>           │
│  - Maneja cambio de partido enfocado, recarga de señal y chat          │
└───────────────────────────────────┬────────────────────────────────────┘
                                    │
┌───────────────────────────────────┴────────────────────────────────────┐
│                  FIRESTORE MATCH REPOSITORY (ANDROID)                  │
│  - FirebaseFirestore.getInstance()                                    │
│  - Escucha Snapshot de /liveEvents y /match/scoreboard                 │
│  - Mapeo seguro a modelos inmutables de Kotlin con Fallback            │
└────────────────────────────────────────────────────────────────────────┘
```

#### Plan de Tareas de Implementación
1. **Componente de Tarjetas Divididas Apple TV (`AppleTvMatchCard.kt`):**
   - Renderiza tarjeta con dos mitades coloreadas (diagonal o vertical 50/50).
   - Insignias de ambos clubes con sombras y emojis heráldicos.
   - Píldora de estado (EN VIVO con punto pulsante / hora del encuentro).
   - Marcador prominente y minutos jugados.
2. **Carrusel de Partidos Apple TV (`AppleTvMatchCarousel.kt`):**
   - Fila de desplazamiento horizontal con espaciado ergonómico de 12dp.
   - Efecto de selección activa ("Focused Card") con borde iluminado en esmeralda.
3. **Repositorio Firestore en Tiempo Real (`FirestoreMatchRepository.kt`):**
   - Conexión con Firestore para consultar y escuchar la colección de partidos y marcadores.
   - Transformación de eventos a modelos de UI con compatibilidad para los clubes bolivianos preconfigurados.
4. **Integración en `LiveMatchScreen.kt` & `MainActivity.kt`:**
   - Montaje del Hero y el carrusel en la vista principal con animación de transición suave.
   - Reemplazo de listas estáticas por la experiencia inmersiva estilo Apple TV.
5. **Verificación y Compilación:**
   - Ejecución de `compile_applet` y generación del APK actualizado listo para el emulador y descarga móvil.
