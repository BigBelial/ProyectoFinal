# 🚗 Sistema de Gestión de Parqueadero

Un sistema completo de gestión de parqueaderos desarrollado en Java con interfaz gráfica Swing y base de datos MySQL, diseñado para controlar el ingreso y salida de vehículos, gestión de usuarios y generación de facturas.

## 👥 Autores

- **Juan David Quintero García**
- **Juan José Ospina Sánchez**

## 📋 Descripción

Este sistema permite gestionar un parqueadero con capacidad para diferentes tipos de vehículos (carros, motos y bicicletas), con un sistema completo de autenticación por roles, control de tiempos y facturación automática.

## ✨ Características Principales

### 🏢 Gestión de Base de Datos
- Creación y eliminación de bases de datos MySQL
- Conexión automática a la base de datos

### 🔐 Sistema de Autenticación
- **Administrador (Nivel 1)**: Acceso completo al sistema
- **Empleado (Nivel 2)**: Gestión de vehículos y búsqueda de facturas
- **Cliente (Nivel 3)**: Consulta de facturas propias

### 👤 Gestión de Usuarios
- Crear nuevos usuarios
- Modificar información de usuarios existentes
- Visualizar y eliminar usuarios
- Control de permisos por rol

### 🚙 Gestión de Vehículos
- **Ingreso de vehículos** con validación de formatos de placa
- **Salida de vehículos** con cálculo automático de tarifas
- **Capacidad del parqueadero**:
  - 10 espacios para carros (puestos 1-10)
  - 10 espacios para motos (puestos 11-20)
  - 5 espacios para bicicletas (puestos 21-25)

### 💰 Sistema de Facturación
- Cálculo automático de tiempo de permanencia
- Tarifas configurables por tipo de vehículo
- Generación y búsqueda de facturas
- Exportación de facturas a archivos

### 📊 Funciones de Búsqueda y Reportes
- Buscar registros de vehículos
- Modificar registros existentes
- Visualización de espacios disponibles/ocupados
- Búsqueda de facturas por diferentes criterios

## 🛠️ Tecnologías Utilizadas

- **Lenguaje**: Java 8
- **GUI**: Swing
- **Base de Datos**: MySQL
- **Conector**: MySQL Connector/J 8.0.33
- **IDE**: NetBeans
- **Build Tool**: Apache Ant

## 📦 Estructura del Proyecto

```
Proyecto Final/
├── src/
│   └── Aplic/
│       ├── Main.java                 # Clase principal con menú
│       ├── Base.java                 # Modelo de datos para BD
│       ├── ingresoVehiculo.java      # Interfaz ingreso vehículos
│       ├── salidaVehiculo.java       # Interfaz salida vehículos
│       ├── iniciarSesion.java        # Sistema de login
│       ├── crearUsuario.java         # Creación de usuarios
│       ├── modificarUsuario.java     # Modificación de usuarios
│       ├── verUsuario.java           # Visualización de usuarios
│       ├── buscarRegistro.java       # Búsqueda de registros
│       ├── buscarFactura.java        # Búsqueda de facturas
│       ├── valorHora.java            # Configuración de tarifas
│       ├── validarIngreso.java       # Validaciones de ingreso
│       ├── validarSalida.java        # Validaciones de salida
│       └── CrearArchivo.java         # Generación de archivos
├── mysql-connector-j-8.0.33.jar     # Driver MySQL
└── build.xml                        # Configuración Ant
```

## 🚀 Instalación y Configuración

### Prerrequisitos

1. **Java Development Kit (JDK) 8** o superior
2. **MySQL Server** instalado y ejecutándose
3. **NetBeans IDE** (opcional, para desarrollo)

### Pasos de Instalación

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/BigBelial/ProyectoFinal.git
   cd ProyectoFinal
   ```

2. **Configurar MySQL**:
   - Asegúrate de que MySQL esté ejecutándose en `localhost:3306`
   - Usuario: `root`
   - Contraseña: `""` (vacía)

3. **Compilar el proyecto**:
   ```bash
   cd "Proyecto Final"
   javac -cp "mysql-connector-j-8.0.33.jar" -d build/classes src/Aplic/*.java
   ```

4. **Ejecutar la aplicación**:
   ```bash
   java -cp "build/classes:mysql-connector-j-8.0.33.jar" Aplic.Main
   ```

## 📖 Manual de Uso

### 1. Configuración Inicial
- Al iniciar, crear una nueva base de datos desde el menú "Bases de datos"
- Crear el primer usuario administrador

### 2. Formatos de Placa Requeridos

#### 🚗 **Carros**: `c-[PUESTO]-[PLACA]-[HORA]-[MINUTO]`
- Ejemplo: `c-5-ABC123-14-30`
- Puestos: 1-10

#### 🏍️ **Motos**: `m-[PUESTO]-[PLACA]-[HORA]-[MINUTO]`
- Ejemplo: `m-15-ABC12D-14-30`
- Puestos: 11-20

#### 🚲 **Bicicletas**: `b-[PUESTO]-[PLACA]-[HORA]-[MINUTO]`
- Ejemplo: `b-23-1234-14-30`
- Puestos: 21-25

### 3. Flujo de Trabajo Típico
1. **Iniciar sesión** con credenciales válidas
2. **Registrar ingreso** de vehículo con formato correcto
3. **Gestionar salida** cuando el cliente retire el vehículo
4. **Generar factura** automáticamente
5. **Consultar reportes** según sea necesario

## 🔧 Configuración de Tarifas

Las tarifas por hora pueden configurarse desde el menú "Vehículos → Valor Hora" (solo administradores).

## 📱 Interfaz de Usuario

El sistema cuenta con una interfaz intuitiva organizada en menús:
- **Bases de datos**: Crear/eliminar BD
- **Login**: Iniciar/cerrar sesión
- **Usuario**: CRUD de usuarios
- **Vehículos**: Gestión completa de parqueadero

## 🛡️ Seguridad y Validaciones

- Validación de formatos de placa específicos por tipo de vehículo
- Control de acceso basado en roles
- Validación de disponibilidad de espacios
- Prevención de registros duplicados

## 📊 Base de Datos

El sistema crea automáticamente las siguientes tablas:
- `usuarios`: Información de usuarios del sistema
- `ingreso`: Registros de entrada de vehículos
- `salida`: Registros de salida de vehículos
- `facturas`: Facturas generadas

## 🙏 Agradecimientos

- Profesores y compañeros que apoyaron el desarrollo del proyecto
- Documentación oficial de Java Swing y MySQL
- Comunidad de desarrolladores Java

---

⭐ Si este proyecto te fue útil, ¡dale una estrella en GitHub!
