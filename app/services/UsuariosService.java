package services;

import play.*;
import play.mvc.*;
import play.db.jpa.*;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;

import models.*;

public class UsuariosService {
    public static Usuario grabaUsuario(Usuario usuario) {
        return UsuarioDAO.create(usuario);
    }

    public static Usuario modificaUsuario(Usuario usuario) {
      return UsuarioDAO.update(usuario);
    }

    public static Usuario findUsuario(Integer id) {
        return UsuarioDAO.find(id);
    }

    public static Usuario loginUsuario(Usuario usuario) {
        return UsuarioDAO.loginUser(usuario);
    }

    public static boolean deleteUsuario(Integer id) {
        UsuarioDAO.delete(id);

        return true;
    }

    public static List<Usuario> findAllUsuarios() {
        List<Usuario> lista = UsuarioDAO.findAll();
        Logger.debug("Numero de usuarios: " + lista.size());
        return lista;
    }
}
