# Guía descriptiva de la funcionalides adicionales de la práctica 1

- [1. Introducción] (#1-introducción)
- [2. Pasos empleados en el desarrollo de cada funcionalidad] (#2-pasos-empleados-en-el-desarrollo-de-cada-funcionalidad)
- [3. Funcionalidades adicionales] (#3-funcionalidades-adicionales)
  - [3.1 Registro] (#31-registro)
    - [3.1.1 Pasos previos] (#311-pasos-previos)
    - [3.1.2 Creación de la ruta] (#312-creación-de-la-ruta)
    - [3.1.3 Creación de la acción] (#313-creación-de-la-acción)
    - [3.1.4 Creación de la vista] (#314-creación-de-la-vista)
    - [3.1.5 Creación de los Services] (#315-creación-de-los-services)
    - [3.1.6 Creación del UsuarioDAO] (#316-creación-del-usuariodao)
    - [3.1.7 Subida al repositorio] (#317-subida-al-repositorio)
  - [3.2 Login] (#32-login)
    - [3.2.1 Pasos previos] (#311-pasos-previos)
    - [3.2.2 Creación de la ruta] (#312-creación-de-la-ruta)
    - [3.2.3 Creación de la acción] (#313-creación-de-la-acción)
    - [3.2.4 Creación de la vista] (#314-creación-de-la-vista)
    - [3.2.5 Creación de los Services] (#315-creación-de-los-services)
    - [3.2.6 Creación del UsuarioDAO] (#316-creación-del-usuariodao)
    - [3.2.7 Subida al repositorio] (#317-subida-al-repositorio)
- [4. Conclusión] (#4-conclusión)

## 1. Introducción

La primera práctica de la asignatura de MADS se basa en el desarrollo de una página en lenguaje Play Framwork (Java). La práctica consta de dos partes. La primera de ellas es guiada y nos muestra paso por paso todo lo que tenemos que crear, como tenemos que ejecutarlo y como debemos guardarlo en GitHub. El resto de funcionalidades de esta parte se realizan de manera muy similar a las guiadas.
En la segunda parte de la práctica tenemos que añadir a la página dos funcionalidades nuevas, como son el registro y el login.

## 2. Pasos empleados en el desarrollo de cada funcionalidad

Para desarrollar cada una de las funciones de la práctica hemos seguido diversos pasos, que son iguales para todas las funcionalidades, con el fin de tener un desarrollo sencillo, ordenado y claro de las funciones.
Los pasos que hemos seguido son:
- Creamos una nueva rama en GitHub, para evitar trabajar en la rama master y que tengamos algún error que no impida volver a un estado anterior donde funcionaba la aplicación de manera distinta a la que nos encontramos en ese preciso instante.
- Configuramos el archivo de rutas de la página. Con esta acción permitimos al navegador acceder a una determinada vista de la página mediante una url que se escriba en el navegador.
- La vista es aquello que se muestra en el navegador cuando accedemos a su dirección url.
- El archivo UsuariosController.java es el que se encarga de cargar la vista que acabamos de crear y también aplica las diversas funcionalidades a la vista con la que va asociado.
- Por otra parte, el UsuariosServices.java es el encargado de llamar a la función necesaria del archivo UsuarioDAO.java
- El UsuarioDAO.java es el que se encarga de buscar en la base de datos de la página.
- Por último, mediante la herramienta Trello, detallaremos la descripción de la tarea que vamos a realizar, cuales son los objetivos que creemos que serán importantes para determinar que un apartado está terminado y un link al merge realizado a GitHub con todos los archivos terminados y funcionando de manera más que satisfactoria.

## 3. Funcionalidades adicionales

### 3.1 Registro

#### 3.1.1 Pasos previos

Antes de comenzar a editar cualquier documento, como bien debemos de saber, estaremos ubicados en la rama master de nuestro repositorio, por lo tanto debemos crear una nueva rama que nos permita trabajar de manera idependiente de los datos que tenemos en la rama master de nuestro repositorio mads-todolist. Para crear una nueva rama y empezar a trebajar necesitamos utilizar los siguientes comandos:

  ```
  $ git checkout -b tic-9-registro
  Switched to a new branch 'tic-9-registro'
  mads@mads:~/Escritorio/GitHub/mads-todolist$ git branch
  * tic-9-registro
  master
  ```
Después de realizar este prodecidimiento y verificar que estamos en la rama correcta podemos empezar a crear todo aquello que necesitemos sin la preocupación de poder cambiar algo que afecte al trabajo que ya teniamos realizado hasta la fecha.

#### 3.1.2 Creación de la ruta

Primeramente, será necesario crear la rutas o las rutas que van a determinar como podemos acceder a la página de login mediante el navegador. Para ello hemos añadido el siguiente código al archivo **conf/routes:**
 
  ```
  ...
  GET     /registro                   controllers.UsuariosController.registroUsuario()
  POST    /registro                   controllers.UsuariosController.grabaNuevoRegistro()
  ...
  ```

Cabe comentar que el **GET** y el **POST** se utilizan para determinar que la parte del **POST** no puede mostrarse por pantalla si no se ha completado la acción del **GET** de manera correcta.

#### 3.1.3 Creación de la acción

Después de haber creado las diferentes rutas vamos a proceder a crear las diferentes acciones que realizará nuestra página de registro. Para ello hemos utilizado los siguientes fragmentos de código dentro del archivo **controllers/UsuariosController.java

  ```
package controllers;

import java.util.List;
import javax.inject.*;

import play.*;
import play.mvc.*;
import views.html.*;
import static play.libs.Json.*;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.*;
import static java.lang.System.out;

import services.*;
import models.*;

public class UsuariosController extends Controller {
@Transactional
  public Result registroUsuario() {
    return ok(formRegistroUsuario.render(formFactory.form(Usuario.class),""));
  }

  @Transactional
  public Result grabaNuevoRegistro() {
    Form<Usuario> usuarioForm = formFactory.form(Usuario.class).bindFromRequest();

    if (usuarioForm.hasErrors()) {
        return badRequest(formCreacionUsuario.render(usuarioForm, "Hay errores en el formulario"));
    }

    Usuario usuario = usuarioForm.get();
    Usuario usuarioBD = UsuariosService.loginUsuario(usuario);

    if (usuarioBD.login != null) {
      if (usuarioBD.password == null) {
        usuario.id = usuarioBD.id;
        usuario = UsuariosService.modificaUsuario(usuario);

        return ok(formBienvenida.render());
      }

      return badRequest(formRegistroUsuario.render(usuarioForm, "Ya existe el usuario"));
    }
    else {
      usuario = UsuariosService.grabaUsuario(usuario);

      return ok(formBienvenida.render());
    }
  }
}
  ```
En el código anterior podemos visualizar dos módulos. El primero de los dos módulos **public Result registroUsuario()** se encarga de llamar a la vista de registro de usuarios. Para llamar a una vista se utiliza la función **render**.

Por otra parte, el módulo **public Result grabaNuevoUsuario()** realiza diversas tareas. La primera de todas es verificar si el formulario contiene algún tipo de error como puede ser que algún campo esté vacío o que no cumpla los requisitos establecidos. 
Seguidamente, pasaré a verificar si el usuario insertado pertenece o no a la base de datos de la aplicación. En caso de que el usuario exista, pasare a verificar si su contraseña se encuentra en la base de datos o si es un campos vacío. Si el campo está vacío procedere a insertar la nueva contraseña, de forma contraria, si la contraseña existe en la base de datos mostrar el mensaje **Ya existe el usuario**. Si el usuario no existía o tenía el campos password vacío, actualizaré estos datos y redigiré al usuario a la vista **formBienvenida**, lo cual será indicativo de que el registro a sido realizado de manera satisfactoria.

#### 3.1.4 Creación de la vista

En este apartado del desarrollo será necesaria la creación de la vista de aquello de queremos que se muestre por pantalla. Para ello tendremos que crear dos vistas. La primera de las dos será la que nos muestre el formulario que permitirá que un usuario se registre, mientras que la otra será la que muestre el mensaje de bienvenida para los usuarios registrados de manera satisfactoria o para los usuarios que se hagan login de manera correcta. Los nombres de los archivos seràn: **formLoginUsuario.scala.html** y ** formBienvenida.scala.html**.
La vista de bienvenida será muy simple y tan solo nos mostrará por pantalla el mensaje: **Bienvenido al gestor de tareas!**. Por otra parte, la vista restante será un poco más extensa y tendrá el siguiente formato:

  ```
  @(usuarioForm: Form[Usuario], mensaje: String)
  @main("Registro") {
    @if(mensaje != "") {
        <div class="alert alert-danger">
            @mensaje
        </div>
    }

  <h1>Registro</h1>
  @helper.form(action = routes.UsuariosController.grabaNuevoRegistro()) {
      <fieldset>
          <legend>Registrar usuario</legend>
          @helper.inputText(usuarioForm("login"), '_label -> "Login")
          @helper.inputPassword(usuarioForm("password"), '_label -> "Password")
          @helper.inputText(usuarioForm("nombre"), '_label -> "Nombre")
          @helper.inputText(usuarioForm("apellidos"), '_label -> "Apellidos")
          @helper.inputText(usuarioForm("eMail"), '_label -> "Correo electrónico")
          @helper.inputText(usuarioForm("fechaNacimiento"), '_label -> "Fecha nacimiento (dd-mm-aaaa)")
      </fieldset>
      <p>
      <input type="submit" class="btn btn-primary" value="Guardar">
      </p>
    }
  }
  ```
Como bien podemos apreciar la vista de registro es algo más extensa, ya posee 6 campos donde el usuario tiene que insertar sus datos. A la hora de realizar inserciones utilizaremos el comando **@helper.inputText(usuarioForm("campo"), '_label -> "Campo")** teniendo en cuenta que la palabra **imputText** se refiere a un campo que mostrará sus carácteres. Esto será útil modificarlo a la hora de insertar contraseñas, el cual será cambiado por **imputPassword**, lo que produce que la contraseña quede escondida por los *.
Por último, para terminar con la vista del registro, tendremos un botón que nos permitirá guardar la información que ha sido insertada en los diferentes campos en la BD. La instrucción para realizar dicha acción es la que podemos visualizar justo antes de que termine la vista.

#### 3.1.5 Creación de los Services

Como queremos verificar si un usuario existe en nuestra base de datos tendremos que crear el siguiente módulo:

  ````
  package services;

  import play.*;
  import play.mvc.*;
  import play.db.jpa.*;
  import java.util.List;
  import java.util.Date;
  import java.util.ArrayList;

  import models.*;

  public class UsuariosService {
  ...
    public static Usuario loginUsuario(Usuario usuario) {
          return UsuarioDAO.loginUser(usuario);
    }
  ...
  }
  ```

Mediante este módulo, la función llamará a la función creada en el fichero UsuarioDAO.java, el cual realizrá la función de verificar si existe dicho login.

#### 3.1.6 Creación del UsuarioDAO

Para poder verificar si existe un usuario en la base de datos, el archivo UsuariosServices.java hará una llamada al fichero UsuarioDAO.java para que este realice una consulta a la BD para verificar si un determinado login existe o no. Para ello será necesario crear la siguiente función que contenga la consulta a la base de datos.

  ```
  package models;

  import play.*;
  import play.mvc.*;
  import play.db.jpa.*;
  import javax.persistence.*;
  import java.util.List;
  import java.util.Date;

  public class UsuarioDAO {
  ...
    public static Usuario loginUser(Usuario usuario) {
      try {
        return (Usuario) JPA.em().createQuery("select u from Usuario u where u.login =" + "'" + usuario.login+ "'").getSingleResult();
      }
      catch(Exception NoResultException) {
        Usuario  usuarioLogin = new Usuario();

        if(usuario.login.equals("admin") && usuario.password.equals("admin")) {
          return usuario;
        }

        return usuarioLogin;
      }
    }
  ...
  ```

La función **public static Usuario loginUser(Usuario usuario)**, verificará si existe dicho login en la base de datos, de lo contrario creará un nuevo usuario partiendo de los datos que se han insertado en los campos que hay creados mediante la vista.

#### 3.1.7 Subida al repositorio

Una vez ya terminada la función de registro y verificado su correcto funcionamiento ha llegado el momento de combinar el contenido de nuestra rama con el contenido de la rama master, con el fin de que la rama master esté también actualizada a la última versión. Para ello será necesario teclear los siguientes comandos mediante la ayuda del terminal:

  ```
  $ git add .
  $ git commit -am "TIC-9 Añadida Página la página de registro de un usuario"
  $ git checkout master
  $ git merge --no-ff tic-9-registro -m "TIC-9 Merge Página de registro"
  $ git branch -d tic-9-registro
  $ git push
  ```
  
Después de completar estos pasos, tan solo tendremos que poner nuestro usuario y contraseña de GitHub para que nuestros cambios surjan efecto. Antes de cambiar de función, subiremos a Trello el enlace del merge que acabamos de realizar y daremos esta tarea por terminada.

### 3.2 Login

#### 3.2.1 Pasos previos

Al igual que hemos creado para el apartado del registro una nueva rama donde trabajar, en esta funcionalidad realizaremos las mismas acciones.

 ```
  $ git checkout -b tic-8-login
  Switched to a new branch 'tic-8-login'
  mads@mads:~/Escritorio/GitHub/mads-todolist$ git branch
  * tic-9-login
  master
  ```

#### 3.2.2 Creación de la ruta

Con el fin de simplificar la extensión de la documentación, para explicar la funcionalidad del login solo vamos a mostrar las funciones empleadas para que funcione esta nueva función, evitando así colocar otra vez todas las librerías y demás utilizadas para el corrector funcionamiento de la aplicación.

  ````
  GET     /login                      controllers.UsuariosController.loginUsuario()
  POST    /login                      controllers.UsuariosController.autenticarUsuario()

  GET     /                           controllers.UsuariosController.loginUsuario()
  POST    /                           controllers.UsuariosController.autenticarUsuario()
  ```
  
Para esta función hemos decidido crear 4 rutas diferentes para llamar al login, aunque verdaderamente son solo dos **localhost:9000/** y **localhost:9000/login**. **localhost:9000/** hemos decidido añadirla también ya que consideramos que para esta primera práctica puede ser nuestra página principal, debido a que casi todas la funciones dependen de dicha página.

#### 3.2.3 Creación de la acción

Esta secuencia de código que se muestra a continuación es el que provoca que el login de la página funcione de de manera correcta. La función **public Result loginUsuario()** llamamos a la vista de login, mientras que en la función restante tenemos que verificar si los datos introducidos son correctos o no.
Primeramente, y al igual que haciamos en la función de registro, verificamos que el formulario, que esta vez tan solo tiene los campos login y contraseña, esté completo.
Por otra parte, si el usuario no inserta un login, el sistema mostrará el error de que el usuario no existe. Seguidamente, en caso de que se trate de un administrador del sistema, cuyo usuario sea **admin** y contraseña **admin**, se nos abrirá de manera directa la pantalla de bienvenida, lo cual significa que el usuario se ha autenticado de manera correcta.
En caso de que el usuario se encuentre en la base de datos y la contraseña sea correcta, el sistema nos mostrará también el mensaje de bienvenida, por el contrario, si el usuario no existe se mostrará un mensaje indicando dicha incidencia.

  ```
  ...
  @Transactional
   public Result loginUsuario() {
     return ok(formLoginUsuario.render(Form.form(Usuario.class),""));
   }

   @Transactional
	 public Result autenticarUsuario() {
		Form<Usuario> usuarioForm = Form.form(Usuario.class).bindFromRequest();

		if (usuarioForm.hasErrors()) {
			return badRequest(formLoginUsuario.render(usuarioForm, "Login erroneo"));
    }

		Usuario usuario = usuarioForm.get();
		Usuario usuarioBD = UsuariosService.loginUsuario(usuario);

		if(usuario.login == null) {
      return badRequest(formLoginUsuario.render(usuarioForm,  "No existe el usuario"));
    }

    if(usuario.login.equals("admin") && usuario.password.equals("admin")) {
			return redirect(controllers.routes.UsuariosController.listaUsuarios());
    }

    if(usuario.login.equals(usuarioBD.login) && usuario.password.equals(usuarioBD.password)) {
      return ok(formBienvenida.render());
    }
    
    return badRequest(formLoginUsuario.render(usuarioForm, "Login erroneo"));
	}
  ...
  ```

#### 3.2.4 Creación de la vista

La creación de la vista del usuario será bastante más corta que la del registro, debido a que el login tan solo contiene dos campos. Este es el aspecto que tiene el código de la vista del login:

  ```
  ...
  @(usuarioForm: Form[Usuario], mensaje: String)
  @main("Login") {
    @if(mensaje != "") {
        <div class="alert alert-danger">
            @mensaje
        </div>
    }
    <h1>Login</h1>
    @helper.form(action = routes.UsuariosController.autenticarUsuario()) {
         <fieldset>
            @helper.inputText(usuarioForm("login"), '_label -> "Login")
            @helper.inputPassword(usuarioForm("password"), '_label -> "Password")
        </fieldset>
        <input type="submit" class="btn btn-primary" value="Login">
        <a class="btn btn-primary" href="@routes.UsuariosController.registroUsuario()">Registrar</a>
    }
  }
  ...
  ```

#### 3.2.5 Creación de los Services

Al igual que pasaba en la función registro, el UsuarioServices hará la llamada al UsuarioDAO, para que este haga una consulta a la base de datos y verifique si existe un usuario o no. El código de este apartado es igual que el código adjuntado para realizar el registro.

#### 3.2.6 Creación del UsuarioDAO

Para esta parte del login hemos utilizado la misma función que teniamos en el registro de un usuario.

#### 3.2.7 Subida al repositorio

Al igual que hemos en el apartado de registro, con la página ya probada y funcionando de manera correcta, procedemos a subir nuestros cambios a GitHub.

  ```
  $ git add .
  $ git commit -am "TIC-9 Añadida Página la página de login"
  $ git checkout master
  $ git merge --no-ff tic-9-login -m "TIC-9 Merge Página de login"
  $ git branch -d tic-9-login
  $ git push
  ```

Por último solo queda especificar nuestro usuario y contraseña en GitHub y adjuntar el enlace del merge a "TIC" de Trello. Después de esto habrá terminado ya nuestra práctica 1.

## 4. Conclusión

Mediante la realización de la práctica hemos podido aprender la parte más básica de Play Framework, para la realización de páginas web y de como se gestionan las bases de datos en dicho lenguaje JPA.
Para mi humilde opinión sobre el lenguaje y demás cosas utilizadas para esta primera práctica es necesario decir que, una vez coges el ritmo de trabajo que te ofrece Play, es bastante fácil y metódico realizar nuevas funcionalidades. Por otra parte, la utilización de GitHub abre una ventana muy extensa de trabajo que te hace olvidar la posibilidad de guardar un cambio del cual no estás del todo seguro si funcionará de manera correcta, o si antes funcionaba de manera más óptima. Por último, la utilación de Trello ayuda bastante a situar al desarrollador/programar do al conjunto a determinar en todo momento cual es la tarea realizada hasta el momento y cuales son los objetivos a cumplir, algo que ayuda bastante a marcarse retos personales para poder lograrlos lo antes posible y de la mejor manera posible.
