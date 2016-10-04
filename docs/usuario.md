# Instrucciones de uso del gestor de taréas

- [1. Instrucciones de uso de la aplicación] (#1-instrucciones-de-la-aplicacion)
    - [1.1 Login] (#11-login)
    - [1.2 Registro] (#12-registro)
    - [1.3 Pantalla de bienvenida] (#13-pantalla-de-bienvenida)
    - [1.4 Listado de usuarios] (#14-listado-de-usuarios)
    - [1.5 Modificación de usuarios] (#15-modificacion-de-usuarios)
    - [1.6 Detalle de usuario] (#16-detalle-de-usuario)
    - [1.7 Borrar usuario] (#17-borrar-usuario)
    - [1.8 Crear usuario] (#18-crear-usuario)
    - [1.9 Mensajes de campos correctos] (#19-mensajes-de-campos-correctos)
        - [1.9.1 Crear usuario] (#191-crear-usuario)
    - [1.10 Mensajes de error] (#110-mensajes-de-error)
        - [1.10.1 Login vacío] (#1101-login-vacio)
        - [1.10.2 Crear usuario existente] (#1102-crear-usuario-existente)

## 1. Instrucciones de uso de la aplicación

Para la primera práctica de la asignatura MADS vamos a realizar una pequeña página con Play Framework (Java), la cual contendrá diversas funciones que ya son habituales en muchas de las páginas web que podemos encontrar a día de hoy por internet.
Primeramente será necesario crear el proyecto en el directorio donde nosotros deseemos, o donde nos sea más fácil acceder. Un vez dentro de dicho directorio utilizaremos, mediante un terminal, el comando "activator new".
Con el proyecto base ya creado, tan solo tenemos que compilar, arrancar el proyecto y empezar a crear todo aquello que necesitemos qe nuestra página realice; para ello será necesario utilizar los comandos "activator compile" y "activator run". Seguidamente, nuestra página ya será accesible desde un navegador mediante la [url] (http://localhost:9000/).

### 1.1 Login

La primera página que encontramos en la web será el login. Para acceder a dicha pantalla utilzaremos el siguiente enlace: [Login](http://localhost:9000/)
<img src="imagenes/usuario/1-Login.png" width="500px">
Una vez tengamos la pantalla de login delante podremos acceder al sistema de diferentes maneras. La primera de todas será como administrador. 
<img src="imagenes/usuario/2-Login.png" width="500px">
Para acceder como administrador será necesario utilizar el usuario "admin" y la contraseña "admin". Esto provocará que se nos muestre por pantalla el listado de usuarios existentes en la página web hasta el momento.
<img src="imagenes/usuario/3-Listado-de-Usuarios.png" width="500px">
En caso de no disponer de la contraseña del administrador será necesario que nos registremos en la página, con el fin de poder tener más privilegios de los que se tienen sin ser usuario registrado o administrador del sistema.

### 1.2 Registro
Para poder acceder a la pantalla de registro podemos hacerlos de dos formas. La primera de ellas será mediante la url del registro: [Registro](http://localhost:9000/registro). Por otra parte, en la pantalla de login encontraremos un botón que nos llevará de manera directa a la pantalla de registro también. Una vez en la pantalla de registro nos encontramos con el siguiente aspecto:
<img src="imagenes/usuario/4-Registro.png" width="500px">
Con todos los campos rellenados de manera correcta, la web nos redigirá a la pantalla de bienvenida; lo cual será indicio de que ya somos usuarios registrados en la web. En caso contrario, por ejemplo, de que el formulario contenga algún tipo de error, la propia web nos informará del error. Los errores posibles serán vistos en próximos apartados.
<img src="imagenes/usuario/5-Registro.png" width="500px">

### 1.3 Pantalla de bienvenida
Con el registro realizados de manera exitosa nos encontraremos con la siguiente pantalla.
<img src="imagenes/usuario/6-Bienvenida.png" width="500px">
Mediante la visualización de dicha pantalla podemos decir de manera evidente que ya estamos registrados en la web!

### 1.4 Listado de usuarios
<img src="imagenes/usuario/7-Listado-de-Usuarios-(Registro).png" width="500px">

### 1.5 Modificación de usuarios
<img src="imagenes/usuario/8-Modificacion-Usuario.png" width="500px">

### 1.6 Detalle de usuario
<img src="imagenes/usuario/9-Detalle-de-Usuario.png" width="500px">

### 1.7 Borrar usuario
<img src="imagenes/usuario/10-Borrar-Usuario.png" width="500px">

### 1.8 Crear usuario
<img src="imagenes/usuario/11-Crear-Usuario.png" width="500px">
<img src="imagenes/usuario/12-Crear-Usuario.png" width="500px">

### 1.9 Mensajes de campos correctos

   #### 1.9.1 Crear usuario
   <img src="imagenes/usuario/13-Crear-Usuario-(Mensaje).png" width="500px">

### 1.10 Mensajes de error

   #### 1.10.1 Login vacío
   <img src="imagenes/usuario/14-Login-(Error-usuario-vacio).png" width="500px">

   #### 1.10.2 Crear usuario existente
   <img src="imagenes/usuario/15-Crear-Usuario-(Error-usuario-existente).png" width="500px">
