package controllers;

import play.*;
import play.mvc.*;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.*;

import models.*;
import services.*;

import views.html.*;

import javax.inject.*;
import java.util.List;

public class TareasController extends Controller {
    @Transactional(readOnly = true)
    // Devuelve una pagina con la lista de tareas de un usuario
    public Result listaTareas(Integer usuarioId) {
        // Obtenemos los mensajes flash guardados en la peticion
        // por los controllers aviso y error
        String aviso = flash("aviso");
        String error = flash("error");
        Logger.debug("Mensaje de aviso: " + aviso);
        Logger.debug("Mensaje de error: " + error);
        Usuario usuario = UsuariosService.findUsuario(usuarioId);

        if (usuario == null) {
            return notFound("Usuario no encontrado");
        }
        else {
            List<Tarea> tareas = TareasService.listaTareasUsuario(usuarioId);
            return ok(listaTareas.render(tareas, usuario.id, error));
        }
    }

    // Devuelve un formulario para crear una nueva tarea
     public Result formularioNuevaTarea(Integer idUsuario) {
         return ok(formCreacionTarea.render(Form.form(Tarea.class),idUsuario,""));
    }

    @Transactional
    // Anyade una nueva tarea de un usuario a la BD
    // Devuelve el codigo HTTP de redireccion a la lista de tareas
    // de un usuario
    public Result grabaNuevaTarea(Integer usuarioId) {
      Form<Tarea> tareaForm = Form.form(Tarea.class).bindFromRequest();

      if (tareaForm.hasErrors()) {
          return badRequest(formCreacionTarea.render(tareaForm,usuarioId, "Hay errores en el formulario"));
      }

      Tarea tarea = tareaForm.get();
      tarea.usuario = UsuariosService.findUsuario(usuarioId);
      tarea = TareasService.grabaTarea(tarea);
      flash("creaTarea", "El usuario se ha grabado correctamente");

      return redirect(controllers.routes.TareasController.listaTareas(usuarioId));
    }

    @Transactional
    // Recibimos por parametro el id de un usuario y el id de una tarea para
    // saber cual es la tarea a modificar
    // Seguidamente, se llama a la vista de modificacion de tareas para poder
    // mostrarla por pantalla y proceder a cambiar los datos que necesitemos
    public Result editarTarea(Integer id, Integer idTarea) {
      Tarea tarea = TareasService.findTarea(idTarea);
      Form<Tarea> formularioModificarTarea = Form.form(Tarea.class);
      Form<Tarea> tareaForm = formularioModificarTarea.fill(tarea);
      return ok(formModificacionTarea.render(tareaForm,id,""));
    }

    @Transactional
    // Con el formulario ya completado, se verificara que no existan errores
    // en el formulario
    // Sera la vista de la modificacion de tareas la encargada de llamar
    // a este metodo para que la tarea que se acaba de modificar sea guardada
    public Result grabaTareaModificada(Integer idTarea) {
      Form<Tarea> tareaForm = Form.form(Tarea.class).bindFromRequest();

      if (tareaForm.hasErrors()) {
          return badRequest(formModificacionTarea.render(tareaForm, idTarea, "Hay errores en el formulario"));
      }

      Tarea tarea = tareaForm.get();
      tarea = TareasService.modificaTarea(tarea);
      tarea.usuario = UsuariosService.findUsuario(idTarea);
      flash("grabaTareaModificada", "La tarea se ha grabado correctamente");
      return redirect(controllers.routes.TareasController.listaTareas(idTarea));
    }

    @Transactional
    // Mediante el id del usuario y el id de la tarea, se busca en la base de datos
    // para proceder al borrado de una tarea
    // Con la tarea ya borrada, se redirecciona al usuario hasta la lista
    // de tareas que le queden aun disponible
    public Result borrarTarea(Integer idUsuario, Integer idTarea) {
      if (TareasService.deleteTarea(idTarea)) {
        return redirect(controllers.routes.TareasController.listaTareas(idUsuario));
      }
      else {
        return notFound();
      }
    }
}
