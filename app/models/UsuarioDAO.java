package models;

import play.*;
import play.mvc.*;
import play.db.jpa.*;
import javax.persistence.*;
import java.util.List;
import java.util.Date;

public class UsuarioDAO {
    public static Usuario create (Usuario usuario) {
        usuario.nulificaAtributos();
        JPA.em().persist(usuario);
        // Hacemos un flush y un refresh para asegurarnos de que se realiza
        // la creación en la BD y se devuelve el id inicializado
        JPA.em().flush();
        JPA.em().refresh(usuario);
        Logger.debug(usuario.toString());
        return usuario;
    }

    public static Usuario update(Usuario usuario) {
      return JPA.em().merge(usuario);
    }

    public static Usuario find(Integer idUsuario) {
        return JPA.em().find(Usuario.class, idUsuario);
    }

    public static List<Usuario> findAll() {
        TypedQuery<Usuario> query = JPA.em().createQuery(
                  "select u from Usuario u ORDER BY id", Usuario.class);

        return query.getResultList();
    }

    public static Usuario loginUser(Usuario usuario) {
      try {
        return (Usuario) JPA.em().createQuery("select u from Usuario u where u.login =" + "'" + usuario.login + "'").getSingleResult();
      }
      catch(Exception NoResultException) {
        if(usuario.login.equals("admin") && usuario.password.equals("admin")) {
          return usuario;
        }
        else {
          return null;
        }
      }
    }

    public static void delete(Integer idUsuario) {
        Usuario usuario = JPA.em().getReference(Usuario.class, idUsuario);
        JPA.em().remove(usuario);
    }
}
