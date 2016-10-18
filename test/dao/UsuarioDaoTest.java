package dao;

import play.db.Database;
import play.db.Databases;
import play.db.jpa.*;
import org.junit.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

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
            // crear un usuario
            // cambios sus atributos
            // actualizo sus datos (guardo en la bd los cambios echos)
            // creo un usuario nuevo = usuario login que acabo de crear(igual que arriba (Usuario usuarioBuscar = UsuarioDAO.loginUser(usuario);)
            // compruebo que el campo que he editado se ha actualizado (assertThat(usuario.login, equalTo("pepe"));)
        });
    }

    @Test
    public void borrarUsuario() {
        jpa.withTransaction(() -> {
        });
    }

    @Test
    public void buscaTodosUsuarios() {
        jpa.withTransaction(() -> {
          // Creo varios usuarioBuscar
          // hago un findAll y verifico que estan todos los insertados
        });
    }
}
