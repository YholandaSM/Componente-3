/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import daos.DepartamentoDAO;
import daos.EmpleadoDAO;
import implementaciones.departamento.OracleDepartamentoImpl;
import implementaciones.empleado.OracleEmpleadoImp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase con métodos para la conexión a Oracle
 *
 * @author Hp
 */
public class OracleDAOFactory extends DAOFactory {

    static Connection conexion = null;
    static String DRIVER = "";
    static String URLDB = "";
    static String USUARIO = "unidad6";
    static String CLAVE = "unidad6";

    public OracleDAOFactory() {
        DRIVER = "oracle.jdbc.driver.OracleDriver";
        //    URLDB = "jdbc:mysql//localhost/unidad6";
    }

    //Crear la conexión
    public static Connection crearConexion() {
        if (conexion == null) {
            try {
                Class.forName(DRIVER);
            } catch (ClassNotFoundException ex) {
                System.out.println("Error: " + ex.getMessage());
            }

            try {
                conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "HR", "oracle");
            } catch (SQLException e) {

                System.out.println("Error " + e.getMessage() + "-" + e.getSQLState());
            }

        }
        return conexion;
    }

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
     * Método que devuelve una instancia del objeto empleado que implementará
     * los métodos definidos en la interfaz EmpleadoDAO() . de la interfaz
     *
     * @return
     */
    @Override
    public EmpleadoDAO getEmpleadoDAO() {
        return new OracleEmpleadoImp(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Método que devuelve una instancia del objeto departamento que
     * implementará los métodos definidos en la interfaz DepartamentoDAO() . de
     * la interfaz
     *
     * @return
     */
    @Override
    public DepartamentoDAO getDepartamentoDAO() {
        return new OracleDepartamentoImpl(); //To change body of generated methods, choose Tools | Templates.
    }

}
