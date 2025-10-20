# Referencia Rápida de Endpoints - VitalApp Backend API

## 📌 Tabla de Contenidos
- [Autenticación](#autenticación)
- [Admin - EPS](#admin---eps)
- [Admin - Especialidades](#admin---especialidades)
- [Admin - Medicamentos](#admin---medicamentos)
- [Admin - Médicos](#admin---médicos)
- [Admin - Pacientes](#admin---pacientes)
- [Médico - Perfil](#médico---perfil)
- [Médico - Citas](#médico---citas)
- [Paciente - Perfil](#paciente---perfil)
- [Paciente - Citas](#paciente---citas)
- [Paciente - Fórmulas](#paciente---fórmulas)
- [Health Check](#health-check)

---

## Autenticación

| Método | Endpoint | Rol Requerido | Descripción |
|--------|----------|---------------|-------------|
| POST | `/api/auth/login` | Público | Login de usuario, retorna token JWT |

---

## Admin - EPS

| Método | Endpoint | Rol Requerido | Descripción |
|--------|----------|---------------|-------------|
| POST | `/api/admin/eps/registrar` | ADMIN | Registrar nueva EPS |
| GET | `/api/admin/eps/{id}` | ADMIN | Obtener EPS por ID |
| GET | `/api/admin/eps/listar` | ADMIN | Listar todas las EPS |

---

## Admin - Especialidades

| Método | Endpoint | Rol Requerido | Descripción |
|--------|----------|---------------|-------------|
| POST | `/api/admin/especialidad/registro` | ADMIN | Registrar nueva especialidad |
| GET | `/api/admin/especialidad/{id}` | ADMIN | Obtener especialidad por ID |
| GET | `/api/admin/especialidad/listar` | ADMIN | Listar todas las especialidades |

---

## Admin - Medicamentos

| Método | Endpoint | Rol Requerido | Descripción |
|--------|----------|---------------|-------------|
| POST | `/api/admin/medicamento/registro` | ADMIN | Registrar nuevo medicamento |
| GET | `/api/admin/medicamento/{id}` | ADMIN | Obtener medicamento por ID |
| GET | `/api/admin/medicamento/listar` | ADMIN | Listar todos los medicamentos |

---

## Admin - Médicos

| Método | Endpoint | Rol Requerido | Descripción | Query Params |
|--------|----------|---------------|-------------|--------------|
| POST | `/api/admin/medico/registro` | ADMIN | Registrar nuevo médico | - |
| GET | `/api/admin/medico/{id}` | ADMIN | Obtener médico por ID | - |
| GET | `/api/admin/medico/buscar-email` | ADMIN | Buscar médico por email | `email` |
| DELETE | `/api/admin/medico/eliminar-perfil` | ADMIN | Eliminar médico | - |
| GET | `/api/admin/medico/listar` | ADMIN | Listar médicos con paginación | `pagina`, `size`, `idEspecialidad` |
| GET | `/api/admin/medicos/{id}/agenda` | ADMIN | Ver agenda del médico | - |
| GET | `/api/admin/medico/{id}/citas` | ADMIN | Ver citas del médico | - |

---

## Admin - Pacientes

| Método | Endpoint | Rol Requerido | Descripción | Query Params |
|--------|----------|---------------|-------------|--------------|
| POST | `/api/admin/paciente/registro` | ADMIN | Registrar nuevo paciente | - |
| GET | `/api/admin/paciente/{id}` | ADMIN | Obtener paciente por ID | - |
| GET | `/api/admin/paciente/buscar-email` | ADMIN | Buscar paciente por email | `email` |
| GET | `/api/admin/paciente/listar` | ADMIN | Listar pacientes con paginación | `pagina`, `size`, `idEps`, `idCiudad` |
| GET | `/api/admin/paciente/{id}/citas` | ADMIN | Ver citas del paciente | - |
| GET | `/api/admin/paciente/{id}/formula` | ADMIN | Ver fórmulas del paciente | - |
| GET | `/api/admin/paciente/formula/{id}` | ADMIN | Ver fórmula específica | - |
| GET | `/api/admin/paciente/formula/{id}/detalles` | ADMIN | Ver detalles de fórmula | - |

---

## Médico - Perfil

| Método | Endpoint | Rol Requerido | Descripción | Query Params |
|--------|----------|---------------|-------------|--------------|
| GET | `/api/medico/{id}` | MEDICO | Ver perfil por ID | - |
| GET | `/api/medico/buscar-email` | MEDICO | Ver perfil por email | `email` |
| PUT | `/api/medico/editar-perfil` | MEDICO | Editar perfil | - |
| PUT | `/api/medico/editar-email` | MEDICO | Cambiar email | - |
| PUT | `/api/medico/editar-password` | MEDICO | Cambiar contraseña | - |
| GET | `/api/medico/{id}/agenda` | MEDICO | Ver mi agenda | - |

---

## Médico - Citas

| Método | Endpoint | Rol Requerido | Descripción |
|--------|----------|---------------|-------------|
| GET | `/api/medico/{id}/citas` | MEDICO | Ver todas mis citas |
| GET | `/api/medico/citas/{id}` | MEDICO | Ver cita por ID |
| PUT | `/api/medico/citas/revision` | MEDICO | Poner cita en revisión |
| POST | `/api/medico/cita/formula/registro` | MEDICO | Registrar fórmula médica |

---

## Paciente - Perfil

| Método | Endpoint | Rol Requerido | Descripción | Query Params |
|--------|----------|---------------|-------------|--------------|
| GET | `/api/paciente/{id}` | PACIENTE | Ver perfil por ID | - |
| GET | `/api/paciente/buscar-email` | PACIENTE | Ver perfil por email | `email` |
| PUT | `/api/paciente/editar-perfil` | PACIENTE | Editar perfil | - |
| PUT | `/api/paciente/editar-email` | PACIENTE | Cambiar email | - |
| PUT | `/api/paciente/editar-password` | PACIENTE | Cambiar contraseña | - |
| DELETE | `/api/paciente/eliminar-perfil` | PACIENTE | Eliminar cuenta | - |

---

## Paciente - Citas

| Método | Endpoint | Rol Requerido | Descripción |
|--------|----------|---------------|-------------|
| POST | `/api/paciente/citas/registro` | PACIENTE | Agendar nueva cita |
| GET | `/api/paciente/{id}/citas/pendientes` | PACIENTE | Ver citas pendientes |
| GET | `/api/paciente/{id}/citas` | PACIENTE | Ver todas mis citas |
| GET | `/api/paciente/citas/{id}` | PACIENTE | Ver cita por ID |
| PUT | `/api/paciente/citas/cancelar` | PACIENTE | Cancelar cita |
| GET | `/api/paciente/citas/registro/especialidades` | PACIENTE | Listar especialidades disponibles |
| GET | `/api/paciente/citas/registro/especialidades/{id}/medicos` | PACIENTE | Listar médicos por especialidad |
| GET | `/api/paciente/citas/medicos/{id}/agenda` | PACIENTE | Ver agenda disponible de médico |

---

## Paciente - Fórmulas

| Método | Endpoint | Rol Requerido | Descripción |
|--------|----------|---------------|-------------|
| GET | `/api/paciente/{id}/formula` | PACIENTE | Ver mis fórmulas |
| GET | `/api/paciente/formula/{id}` | PACIENTE | Ver fórmula por ID |
| GET | `/api/paciente/formula/{id}/detalles` | PACIENTE | Ver detalles de fórmula |

---

## Health Check

| Método | Endpoint | Rol Requerido | Descripción |
|--------|----------|---------------|-------------|
| GET | `/actuator/health` | Público | Estado de salud de la aplicación |

---

## 📝 Resumen de Estadísticas

### Total de Endpoints: **60**

#### Por Rol:
- **Público**: 2 endpoints (Login, Health Check)
- **ADMIN**: 24 endpoints
- **MEDICO**: 10 endpoints
- **PACIENTE**: 24 endpoints

#### Por Método HTTP:
- **GET**: 44 endpoints
- **POST**: 9 endpoints
- **PUT**: 6 endpoints
- **DELETE**: 2 endpoints

#### Por Categoría:
- **Autenticación**: 1 endpoint
- **EPS**: 3 endpoints
- **Especialidades**: 3 endpoints
- **Medicamentos**: 3 endpoints
- **Médicos**: 7 endpoints (Admin)
- **Pacientes**: 8 endpoints (Admin)
- **Perfil Médico**: 6 endpoints
- **Citas Médico**: 4 endpoints
- **Perfil Paciente**: 6 endpoints
- **Citas Paciente**: 8 endpoints
- **Fórmulas Paciente**: 3 endpoints
- **Health Check**: 1 endpoint

---

## 🔑 Leyenda de Roles

| Rol | Descripción | Acceso |
|-----|-------------|--------|
| Público | Sin autenticación | Login y Health Check |
| ADMIN | Administrador del sistema | Gestión completa de usuarios y catálogos |
| MEDICO | Médico del sistema | Gestión de perfil, citas y fórmulas |
| PACIENTE | Paciente del sistema | Gestión de perfil, agendar citas, ver fórmulas |

---

## 📋 Formato de Respuesta General

### Respuesta Exitosa
```json
{
  "error": false,
  "respuesta": {
    // ... datos
  }
}
```

### Respuesta con Error
```json
{
  "error": true,
  "respuesta": "Mensaje de error"
}
```

---

## 🔐 Autenticación

Todos los endpoints (excepto `/api/auth/login` y `/actuator/health`) requieren:

```http
Authorization: Bearer {token}
```

El token se obtiene mediante el endpoint de login y tiene información del usuario y su rol.

---

## 📍 URL Base

### Desarrollo
```
http://localhost:8080
```

### Producción
```
https://[TU_DOMINIO]
```

---

## 📖 Convenciones

### Path Parameters
- `{id}` - ID del recurso (Long)
- `{idMedico}` - ID del médico
- `{idPaciente}` - ID del paciente
- `{idCita}` - ID de la cita
- `{idFormula}` - ID de la fórmula
- `{idEspecializacion}` - ID de la especialización

### Query Parameters Comunes
- `email` - Email del usuario
- `pagina` - Número de página (default: 0)
- `size` - Elementos por página (default: 10)
- `idEspecialidad` - Filtro por especialidad
- `idEps` - Filtro por EPS
- `idCiudad` - Filtro por ciudad

---

## 🎯 Endpoints Más Utilizados

### Para Frontend de Paciente:
1. `POST /api/auth/login` - Login
2. `GET /api/paciente/{id}` - Ver perfil
3. `GET /api/paciente/citas/registro/especialidades` - Ver especialidades
4. `GET /api/paciente/citas/registro/especialidades/{id}/medicos` - Ver médicos
5. `GET /api/paciente/citas/medicos/{id}/agenda` - Ver agenda disponible
6. `POST /api/paciente/citas/registro` - Agendar cita
7. `GET /api/paciente/{id}/citas` - Ver mis citas
8. `GET /api/paciente/{id}/formula` - Ver mis fórmulas

### Para Frontend de Médico:
1. `POST /api/auth/login` - Login
2. `GET /api/medico/{id}` - Ver perfil
3. `GET /api/medico/{id}/citas` - Ver mis citas
4. `GET /api/medico/citas/{id}` - Ver detalle de cita
5. `PUT /api/medico/citas/revision` - Poner cita en revisión
6. `POST /api/medico/cita/formula/registro` - Registrar fórmula
7. `GET /api/medico/{id}/agenda` - Ver mi agenda

### Para Frontend de Admin:
1. `POST /api/auth/login` - Login
2. `GET /api/admin/paciente/listar` - Listar pacientes
3. `GET /api/admin/medico/listar` - Listar médicos
4. `POST /api/admin/paciente/registro` - Registrar paciente
5. `POST /api/admin/medico/registro` - Registrar médico
6. `GET /api/admin/especialidad/listar` - Listar especialidades
7. `GET /api/admin/eps/listar` - Listar EPS
8. `GET /api/admin/medicamento/listar` - Listar medicamentos

---

**Última actualización**: Octubre 2025  
**Versión de API**: 1.0


