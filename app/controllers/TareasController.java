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
    public Result listaTareas(Integer usuarioId) {
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
            return ok(listaTareas.render(tareas, usuario, aviso, error));
        }
    }

    // Devuelve un formulario para crear una nueva tarea
     public Result formularioNuevaTarea(Integer idUsuario) {
         return ok(formCreacionTarea.render(Form.form(Tarea.class),idUsuario,""));
    }

    @Transactional
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
  public Result editarTarea(Integer id, Integer idTarea) {
      Tarea tarea = TareasService.findTarea(idTarea);
      Form<Tarea> formularioModificarTarea = Form.form(Tarea.class);
      Form<Tarea> tareaForm = formularioModificarTarea.fill(tarea);
      return ok(formModificacionTarea.render(tareaForm,id,""));
  }

  @Transactional
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
}
