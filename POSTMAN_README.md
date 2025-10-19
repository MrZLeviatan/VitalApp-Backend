# Colección de Postman - VitalApp Backend API

## 📋 Descripción

Esta colección de Postman contiene todos los endpoints de la API de VitalApp Backend, una aplicación de gestión médica que incluye gestión de pacientes, médicos, citas, fórmulas médicas y más.

## 🚀 Cómo Importar la Colección

1. Abre Postman
2. Haz clic en **Import** (Importar) en la esquina superior izquierda
3. Selecciona el archivo `VitalApp-Backend.postman_collection.json`
4. La colección se importará con todas las carpetas y endpoints organizados

## ⚙️ Configuración de Variables

La colección utiliza variables de entorno para facilitar el uso:

### Variables Predefinidas

- **`base_url`**: URL base del servidor (default: `http://localhost:8080`)
- **`token`**: Token JWT para autenticación (se debe configurar después del login)

### Cómo Configurar las Variables

1. **Opción 1 - Variables de Colección (Recomendado)**:
   - Haz clic derecho en la colección "VitalApp Backend API"
   - Selecciona "Edit"
   - Ve a la pestaña "Variables"
   - Modifica el valor de `base_url` según tu entorno:
     - Desarrollo local: `http://localhost:8080`
     - Producción: `https://tu-dominio-produccion.com`

2. **Opción 2 - Variables de Entorno**:
   - Crea un nuevo Environment en Postman
   - Agrega las variables `base_url` y `token`
   - Selecciona ese entorno antes de hacer las peticiones

## 🔐 Autenticación

La API utiliza JWT (JSON Web Tokens) para autenticación. Sigue estos pasos:

### 1. Realizar Login

Usa el endpoint **Auth > Login** con credenciales válidas:

```json
{
  "email": "usuario@example.com",
  "password": "password123"
}
```

### 2. Guardar el Token

La respuesta incluirá un token JWT:

```json
{
  "error": false,
  "respuesta": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "rol": "ROLE_PACIENTE"
  }
}
```

### 3. Configurar el Token

**Opción Automática** (Recomendado):
- En Postman, ve a la pestaña "Tests" del endpoint de Login
- Agrega este script para guardar automáticamente el token:

```javascript
var jsonData = pm.response.json();
if (jsonData.respuesta && jsonData.respuesta.token) {
    pm.collectionVariables.set("token", jsonData.respuesta.token);
}
```

**Opción Manual**:
- Copia el valor del token de la respuesta
- Ve a las variables de la colección
- Pega el token en la variable `token`

## 👥 Roles de Usuario

La API tiene tres roles principales:

| Rol | Descripción | Acceso |
|-----|-------------|--------|
| **ADMIN** | Administrador del sistema | Gestión completa de usuarios, EPS, especialidades, medicamentos |
| **MEDICO** | Médico | Gestión de su perfil, citas, registro de fórmulas médicas |
| **PACIENTE** | Paciente | Gestión de su perfil, agendar citas, ver fórmulas médicas |

## 📁 Estructura de la Colección

### 1. **Auth** (Autenticación)
- `POST /api/auth/login` - Login de usuario

### 2. **Admin - EPS**
- `POST /api/admin/eps/registrar` - Registrar EPS
- `GET /api/admin/eps/{id}` - Obtener EPS por ID
- `GET /api/admin/eps/listar` - Listar todas las EPS

### 3. **Admin - Especialidades**
- `POST /api/admin/especialidad/registro` - Registrar especialidad
- `GET /api/admin/especialidad/{id}` - Obtener especialidad por ID
- `GET /api/admin/especialidad/listar` - Listar especialidades

### 4. **Admin - Medicamentos**
- `POST /api/admin/medicamento/registro` - Registrar medicamento
- `GET /api/admin/medicamento/{id}` - Obtener medicamento por ID
- `GET /api/admin/medicamento/listar` - Listar medicamentos

### 5. **Admin - Médicos**
- `POST /api/admin/medico/registro` - Registrar médico
- `GET /api/admin/medico/{id}` - Obtener médico por ID
- `GET /api/admin/medico/buscar-email` - Buscar médico por email
- `DELETE /api/admin/medico/eliminar-perfil` - Eliminar médico
- `GET /api/admin/medico/listar` - Listar médicos (con paginación y filtros)
- `GET /api/admin/medicos/{id}/agenda` - Ver agenda de médico
- `GET /api/admin/medico/{id}/citas` - Ver citas de médico

### 6. **Admin - Pacientes**
- `POST /api/admin/paciente/registro` - Registrar paciente
- `GET /api/admin/paciente/{id}` - Obtener paciente por ID
- `GET /api/admin/paciente/buscar-email` - Buscar paciente por email
- `GET /api/admin/paciente/listar` - Listar pacientes (con paginación y filtros)
- `GET /api/admin/paciente/{id}/citas` - Ver citas de paciente
- `GET /api/admin/paciente/{id}/formula` - Ver fórmulas de paciente
- `GET /api/admin/paciente/formula/{id}` - Ver fórmula específica
- `GET /api/admin/paciente/formula/{id}/detalles` - Ver detalles de fórmula

### 7. **Médico - Perfil**
- `GET /api/medico/{id}` - Ver perfil por ID
- `GET /api/medico/buscar-email` - Ver perfil por email
- `PUT /api/medico/editar-perfil` - Editar perfil
- `PUT /api/medico/editar-email` - Cambiar email
- `PUT /api/medico/editar-password` - Cambiar contraseña
- `GET /api/medico/{id}/agenda` - Ver mi agenda

### 8. **Médico - Citas**
- `GET /api/medico/{id}/citas` - Ver mis citas
- `GET /api/medico/citas/{id}` - Ver cita por ID
- `PUT /api/medico/citas/revision` - Poner cita en revisión
- `POST /api/medico/cita/formula/registro` - Registrar fórmula médica

### 9. **Paciente - Perfil**
- `GET /api/paciente/{id}` - Ver perfil por ID
- `GET /api/paciente/buscar-email` - Ver perfil por email
- `PUT /api/paciente/editar-perfil` - Editar perfil
- `PUT /api/paciente/editar-email` - Cambiar email
- `PUT /api/paciente/editar-password` - Cambiar contraseña
- `DELETE /api/paciente/eliminar-perfil` - Eliminar cuenta

### 10. **Paciente - Citas**
- `POST /api/paciente/citas/registro` - Agendar cita
- `GET /api/paciente/{id}/citas/pendientes` - Ver citas pendientes
- `GET /api/paciente/{id}/citas` - Ver todas mis citas
- `GET /api/paciente/citas/{id}` - Ver cita por ID
- `PUT /api/paciente/citas/cancelar` - Cancelar cita
- `GET /api/paciente/citas/registro/especialidades` - Listar especialidades disponibles
- `GET /api/paciente/citas/registro/especialidades/{id}/medicos` - Listar médicos por especialidad
- `GET /api/paciente/citas/medicos/{id}/agenda` - Ver agenda disponible de médico

### 11. **Paciente - Fórmulas**
- `GET /api/paciente/{id}/formula` - Ver mis fórmulas
- `GET /api/paciente/formula/{id}` - Ver fórmula por ID
- `GET /api/paciente/formula/{id}/detalles` - Ver detalles de fórmula

### 12. **Health Check**
- `GET /actuator/health` - Estado de salud de la aplicación

## 📝 Ejemplos de Uso

### Ejemplo 1: Flujo de Registro de Paciente (Admin)

1. **Login como Admin**
   ```
   POST /api/auth/login
   ```

2. **Registrar Paciente**
   ```
   POST /api/admin/paciente/registro
   ```

3. **Listar Pacientes**
   ```
   GET /api/admin/paciente/listar?pagina=0&size=10
   ```

### Ejemplo 2: Flujo de Agendar Cita (Paciente)

1. **Login como Paciente**
   ```
   POST /api/auth/login
   ```

2. **Ver Especialidades Disponibles**
   ```
   GET /api/paciente/citas/registro/especialidades
   ```

3. **Ver Médicos de la Especialidad**
   ```
   GET /api/paciente/citas/registro/especialidades/1/medicos
   ```

4. **Ver Agenda Disponible del Médico**
   ```
   GET /api/paciente/citas/medicos/1/agenda
   ```

5. **Agendar Cita**
   ```
   POST /api/paciente/citas/registro
   ```

### Ejemplo 3: Flujo de Atención Médica (Médico)

1. **Login como Médico**
   ```
   POST /api/auth/login
   ```

2. **Ver Mis Citas**
   ```
   GET /api/medico/1/citas
   ```

3. **Ver Detalle de Cita**
   ```
   GET /api/medico/citas/1
   ```

4. **Poner Cita en Revisión**
   ```
   PUT /api/medico/citas/revision
   ```

5. **Registrar Fórmula Médica**
   ```
   POST /api/medico/cita/formula/registro
   ```

## 🔍 Paginación y Filtros

Algunos endpoints soportan paginación y filtros:

### Listar Médicos
```
GET /api/admin/medico/listar?pagina=0&size=10&idEspecialidad=1
```

**Parámetros:**
- `pagina` (opcional): Número de página (default: 0)
- `size` (opcional): Elementos por página (default: 10)
- `idEspecialidad` (opcional): Filtrar por especialidad

### Listar Pacientes
```
GET /api/admin/paciente/listar?pagina=0&size=10&idEps=1&idCiudad=2
```

**Parámetros:**
- `pagina` (opcional): Número de página (default: 0)
- `size` (opcional): Elementos por página (default: 10)
- `idEps` (opcional): Filtrar por EPS
- `idCiudad` (opcional): Filtrar por ciudad

## 📊 Formato de Respuesta

Todas las respuestas siguen el formato:

```json
{
  "error": false,
  "respuesta": {
    // ... datos de respuesta
  }
}
```

En caso de error:

```json
{
  "error": true,
  "respuesta": "Mensaje de error"
}
```

## 🛠️ Tecnologías Utilizadas

- **Backend**: Spring Boot 3.4.1
- **Base de Datos**: PostgreSQL
- **Autenticación**: JWT (JSON Web Tokens)
- **Documentación**: Swagger/OpenAPI (disponible en `/swagger-ui.html`)
- **Seguridad**: Spring Security

## 🌐 URLs de Entorno

### Desarrollo Local
```
http://localhost:8080
```

### Producción (Ejemplo)
```
https://vitalapp-backend.onrender.com
```

## 📚 Documentación Adicional

### Swagger UI
Una vez que el servidor esté corriendo, puedes acceder a la documentación interactiva de Swagger en:

```
http://localhost:8080/swagger-ui.html
```

### OpenAPI JSON
```
http://localhost:8080/v3/api-docs
```

## 🐛 Solución de Problemas

### Error 401 Unauthorized
- Verifica que el token JWT esté configurado correctamente
- Asegúrate de que el token no haya expirado
- Verifica que estés usando el rol correcto para el endpoint

### Error 403 Forbidden
- El usuario no tiene permisos para acceder a ese endpoint
- Verifica que estés usando el rol correcto (ADMIN, MEDICO, PACIENTE)

### Error 404 Not Found
- Verifica que la URL base esté configurada correctamente
- Asegúrate de que el servidor esté corriendo
- Verifica que el ID del recurso exista

### Error 500 Internal Server Error
- Revisa los logs del servidor
- Verifica que todos los datos requeridos estén presentes en el request body
- Asegúrate de que las relaciones entre entidades sean válidas (ej: IDs de EPS, especialidades, etc.)

## 📞 Contacto y Soporte

Para más información sobre el proyecto VitalApp Backend, consulta el código fuente o contacta al equipo de desarrollo.

---

**Autor**: MrZ Leviatán  
**Versión**: 1.0  
**Última actualización**: Octubre 2025


