package services;

import java.util.List;
import java.util.ArrayList;

import models.*;

public class TareasService {

    public static List<Tarea> listaTareasUsuario(Integer idUsuario) {
        Usuario usuario = UsuarioDAO.find(idUsuario);
        if (usuario != null) {
            return usuario.tareas;
        } else {
            throw new UsuariosException("Usuario no encontrado");
        }
    }

    public static Tarea grabaTarea(Tarea tarea) {
    	 return TareaDAO.create(tarea);
    }

    public static Tarea findTarea(Integer idTarea) {
      return TareaDAO.find(idTarea);
    }

    public static Tarea modificaTarea(Tarea tarea) {
       return TareaDAO.update(tarea);
   }

   public static boolean deleteTarea(Integer idTarea) {
       TareaDAO.delete(idTarea);
       return true;
   }
}
