# Examen2Eventos
-------------------------------------
Realizado por Pedro Alonso Tapia Lobo
-------------------------------------
Link a mi repositorio: https://github.com/Tapiiaa/Examen2Eventos.git
--------------------------------------------------------------------

Link al video: https://youtu.be/ePlynWvyqJQ
------------------------------------------------
Proyecto Android desarrollado en Java que implementa tres funcionalidades principales, cada una representada por un ejercicio:

1. **Gestión de asignaturas** (App1)
2. **Gestión de eventos** (App2)
3. **Mapa interactivo con farmacias reales** (App3)

---

## Requisitos Previos

Antes de clonar y ejecutar este proyecto, asegúrate de contar con los siguientes requisitos:

- Android Studio instalado (versión mínima recomendada: Arctic Fox o superior).
- Clave de Google Maps API con Places API habilitada.
- Configuración de Firebase Realtime Database:
  - URL personalizada: `https://examen2eventos-default-rtdb.europe-west1.firebasedatabase.app`.
  - Reglas configuradas para admitir nodos `Subjects`, `Events` y `Pharmacy`.

---

## Ejercicios

### **Ejercicio 1: Gestión de Asignaturas (App1)**

Esta funcionalidad permite:

- Añadir asignaturas al Firebase Realtime Database bajo el nodo `Subjects`.
- Visualizar las asignaturas por días de la semana.
- Consultar la última asignatura añadida.

#### **Actividades**

1. **AddSubjectActivity**: Añade una nueva asignatura con los campos necesarios.
2. **ViewScheduleActivity**: Visualiza las asignaturas agrupadas por días.
3. **NowSubjectActivity**: Muestra la última asignatura añadida en Firebase.

#### **Detalles Técnicos**

- Uso de `RecyclerView` para listar las asignaturas.
- Firebase Database para almacenamiento y consulta de datos.
- Interfaces personalizadas para la navegación.

---

### **Ejercicio 2: Gestión de Eventos (App2)**

Esta funcionalidad permite:

- Añadir eventos al Firebase Realtime Database bajo el nodo `Events`.
- Visualizar eventos con detalles básicos como título, descripción y precio.
- Consultar información detallada del evento al hacer clic.

#### **Actividades**

1. **AddEventActivity**: Permite registrar un nuevo evento.
2. **EventDetailActivity**: Muestra detalles ampliados de un evento específico.
3. **App2MainActivity**: Página principal que lista los eventos almacenados.

#### **Detalles Técnicos**

- Uso de `RecyclerView` para listar eventos.
- Botón dinámico para añadir nuevos eventos.
- Integración directa con Firebase.

---

### **Ejercicio 3: Mapa Interactivo con Farmacias Reales (App3)**

Esta funcionalidad permite:

- Mostrar un mapa centrado en Zaragoza.
- Marcar las farmacias reales cercanas usando Google Places API.
- Añadir farmacias seleccionadas al Firebase Realtime Database bajo el nodo `Pharmacy`.

#### **Actividades**

1. **PharmacyMapActivity**: Muestra el mapa y permite interactuar con los marcadores.
2. **App3MainActivity**: Página principal que redirige al mapa.

#### **Detalles Técnicos**

- Google Maps API para la visualización del mapa.
- Google Places API para obtener datos de farmacias reales.
- Firebase Database para registrar las farmacias seleccionadas.

---

## Configuración del Proyecto

### **1. Clonar el Repositorio**

```bash
git clone https://github.com/tu_usuario/Examen2Eventos.git
```

### **2. Configurar Firebase**

- Accede a [Firebase Console](https://console.firebase.google.com/).
- Añade tu proyecto y configura la base de datos en tiempo real.
- Configura las reglas para permitir lectura y escritura:
  ```json
  {
    "rules": {
      ".read": true,
      ".write": true
    }
  }
  ```

### **3. Configurar Google Maps API**

- Ve a [Google Cloud Console](https://console.cloud.google.com/).
- Activa las APIs necesarias:
  - **Maps JavaScript API**
  - **Places API**
- Obtén tu clave API y añádela al archivo `AndroidManifest.xml`:
  ```xml
  <meta-data
      android:name="com.google.android.geo.API_KEY"
      android:value="TU_CLAVE_API" />
  ```

### **4. Ejecutar el Proyecto**

- Abre el proyecto en Android Studio.
- Configura un dispositivo virtual o conecta tu dispositivo físico.
- Ejecuta el proyecto.

---

