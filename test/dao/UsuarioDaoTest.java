package dao;

import play.db.Database;
import play.db.Databases;
import play.db.jpa.*;
import org.junit.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import java.util.List;

import models.*;

public class UsuarioDaoTest {

    Database db;
    JPAApi jpa;

    @Before
    public void initDatabase() {
        db = Databases.inMemoryWith("jndiName", "DefaultDS");
        // Necesario para inicializar el nombre JNDI de la BD
        db.getConnection();
        // Se activa la compatibilidad MySQL en la BD H2
        db.withConnection(connection -> {
            connection.createStatement().execute("SET MODE MySQL;");
        });
        jpa = JPA.createFor("memoryPersistenceUnit");
    }

    @After
    public void shutdownDatabase() {
        db.withConnection(connection -> {
            connection.createStatement().execute("DROP TABLE Usuario;");
        });
        jpa.shutdown();
        db.shutdown();
    }

    @Test
    public void creaBuscaUsuario() {
        Integer id = jpa.withTransaction(() -> {
            Usuario nuevo = new Usuario("pepe", "pepe");
            nuevo = UsuarioDAO.create(nuevo);
            return nuevo.id;
        });

        jpa.withTransaction(() -> {
            Usuario usuario = UsuarioDAO.find(id);
            assertThat(usuario.login, equalTo("pepe"));
        });
    }

    @Test
    public void buscaUsuarioLogin() {
        jpa.withTransaction(() -> {
            Usuario usuario = new Usuario("david", "david");
            Usuario usuarioBuscar = UsuarioDAO.loginUser(usuario);
            assertNull(usuarioBuscar);
        });
    }

    @Test
    public void actualizarUsuario() {
        jpa.withTransaction(() -> {
            Usuario usuario = new Usuario("David", "David");
            UsuarioDAO.create(usuario);
            usuario.login = "login_modificado";
            UsuarioDAO.update(usuario);
            Usuario comprobar = UsuarioDAO.loginUser(usuario);
            assertThat(comprobar.login, equalTo("login_modificado"));
        });
    }

    @Test
    public void borrarUsuario() {
        jpa.withTransaction(() -> {
            Usuario usuario = new Usuario("UsuarioBorrar", "UsuarioBorrar");
            UsuarioDAO.create(usuario);
            UsuarioDAO.delete(usuario.id);
            UsuarioDAO.find(usuario.id);
        });
    }

    @Test
    public void buscaTodosUsuarios() {
        jpa.withTransaction(() -> {
            Usuario usuario1 = new Usuario("Usuario1", "Usuario1");
            UsuarioDAO.create(usuario1);
            Usuario usuario2 = new Usuario("Usuario2", "Usuario2");
            UsuarioDAO.create(usuario2);
            Usuario usuario3 = new Usuario("Usuario3", "Usuario3");
            UsuarioDAO.create(usuario3);
            List<Usuario> usuarios = UsuarioDAO.findAll();
            assertThat(usuarios.get(0).login, equalTo("Usuario1"));
            assertThat(usuarios.get(1).login, equalTo("Usuario2"));
            assertThat(usuarios.get(2).login, equalTo("Usuario3"));
        });
    }
}
