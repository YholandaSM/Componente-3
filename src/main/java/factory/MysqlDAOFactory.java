package factory;

import daos.DepartamentoDAO;
import daos.EmpleadoDAO;
import implementaciones.departamento.MysqlDepartamentoImpl;
import implementaciones.empleado.MysqlEmpleadoImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase con métodos para la conexión a Mysql
 *
 * @author Hp
 */
public class MysqlDAOFactory extends DAOFactory {

    static Connection conexion = null;
    static String DRIVER = "";
    static String URLDB = "";
    static String USUARIO = "root";
    static String CLAVE = "";

    public MysqlDAOFactory() {
        DRIVER = "com.mysql.jdbc.Driver";
        URLDB = "jdbc:mysql://localhost:3308/fbaccesodatos";
    }

    /**
     * Método que crea una conexión a una bbdd Mysql
     *
     * @return devuelve un objeto Connection
     */
    public static Connection crearConexion() {
        if (conexion == null) {
            try {
                Class.forName(DRIVER);
            } catch (ClassNotFoundException ex) {
                System.out.println("Error: " + ex.getMessage());
            }

            try {
                conexion = DriverManager.getConnection(URLDB, USUARIO, CLAVE);
            } catch (SQLException e) {

                System.out.println("Error " + e.getMessage() + "-" + e.getSQLState());
            }

        }
        return conexion;
    }

    /**
     * Método para cerrar la conexión con la bbdd Mysql
     */
    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                conexion = null;
            } catch (SQLException ex) {
                System.out.println("Error al cerrar conexión: " + ex.getMessage());
            }
        }
    }

    /**
     * Método que devuelve una instancia del objeto departamento
     * que implementará los métodos definidos en la interfaz DepartamentoDAO() .
     * de la interfaz
     *
     * @return
     */
    @Override
    public DepartamentoDAO getDepartamentoDAO() {
        return new MysqlDepartamentoImpl();
    }

     /**
     * Método que devuelve una instancia del objeto empleado que implementará
     * los métodos definidos en la interfaz EmpleadoDAO() . de la interfaz
     *
     * @return
     */
    @Override
    public EmpleadoDAO getEmpleadoDAO() {
        return new MysqlEmpleadoImpl();
    }

}
