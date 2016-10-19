package services;

import play.db.Database;
import play.db.Databases;
import play.db.jpa.*;
import org.junit.*;
import org.dbunit.*;
import org.dbunit.dataset.*;
import org.dbunit.dataset.xml.*;
import org.dbunit.operation.*;
import java.io.FileInputStream;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.*;

public class UsuariosServiceTest {

    static Database db;
    static JPAApi jpa;
    JndiDatabaseTester databaseTester;

    @BeforeClass
    static public void initDatabase() {
        db = Databases.inMemoryWith("jndiName", "DefaultDS");
        // Necesario para inicializar el nombre JNDI de la BD
        db.getConnection();
        // Se activa la compatibilidad MySQL en la BD H2
        db.withConnection(connection -> {
            connection.createStatement().execute("SET MODE MySQL;");
        });
        jpa = JPA.createFor("memoryPersistenceUnit");
    }

    @Before
    public void initData() throws Exception {
        databaseTester = new JndiDatabaseTester("DefaultDS");
        IDataSet initialDataSet = new FlatXmlDataSetBuilder().build(new
        FileInputStream("test/resources/usuarios_dataset.xml"));
        databaseTester.setDataSet(initialDataSet);

        // Definimos como operación TearDown DELETE_ALL para que se
        // borren todos los datos de las tablas del dataset
        // (el valor por defecto DbUnit es DatabaseOperation.NONE)
        databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);

        // Definimos como operación SetUp CLEAN_INSERT, que hace un
        // DELETE_ALL de todas las tablase del dataset, seguido por un
        // INSERT. (http://dbunit.sourceforge.net/components.html)
        // Es lo que hace DbUnit por defecto, pero así queda más claro.
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);

        databaseTester.onSetup();
    }

    @After
    public void clearData() throws Exception {
        databaseTester.onTearDown();
    }

    @AfterClass
    static public void shutdownDatabase() {
        jpa.shutdown();
        db.shutdown();
    }

    @Test
    public void actualizaUsuarioLanzaExcepcionSiLoginYaExiste() {
        jpa.withTransaction(() -> {
            Usuario usuario = UsuariosService.findUsuario(2);

            // Copiamos el objeto usuario para crear un objeto igual
            // pero desconectado de la base de datos. De esta forma,
            // cuando modificamos sus atributos JPA no actualiza la
            // base de datos.

            Usuario desconectado = usuario.copy();

            // Cambiamos el login por uno ya existente
            desconectado.login = "juan";

            try {
                UsuariosService.modificaUsuario(desconectado);
                fail("No se ha lanzado excepción login ya existe");
            }
            catch (UsuariosException ex) {
            }
        });
    }

    @Test
    public void grabaUsuarioServices() {
        jpa.withTransaction(() -> {
            Usuario usuario = new Usuario("usuario1", "Usuario1");
            UsuariosService.grabaUsuario(usuario);
            Usuario usuario2 = UsuariosService.loginUsuario(usuario);
            assertThat(usuario.login, equalTo(usuario2.login));
        });
    }

    @Test
    public void buscarUsuarioServices() {
        jpa.withTransaction(() -> {
            Usuario usuario = new Usuario("usuarioBuscar", "usuarioBuscar");
            UsuariosService.grabaUsuario(usuario);
            Usuario usuario2 = UsuariosService.findUsuario(usuario.id);
            assertThat(usuario2.id, equalTo(usuario.id));
        });
    }

    @Test
    public void loginUsuarioServicesExcepcionLoginNull() {
        jpa.withTransaction(() -> {
            Usuario usuario = new Usuario();

            try {
                Usuario usuario2 = UsuariosService.loginUsuario(usuario);
                fail("No ha saltado la excepción LoginNull");
            }
            catch(UsuariosException e) {
                System.out.println("Error: " + e);
            }
        });
    }

    @Test
    public void borrarUsuariosServices() {
        jpa.withTransaction(() -> {
            Usuario usuario = new Usuario("usuarioBorrar", "usuarioBorrar");
            UsuariosService.grabaUsuario(usuario);
            UsuariosService.deleteUsuario(usuario.id);
            Usuario usuario2 = UsuariosService.loginUsuario(usuario);
            assertNull(usuario2);
        });
    }
}
