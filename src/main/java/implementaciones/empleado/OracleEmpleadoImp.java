package implementaciones.empleado;

import daos.EmpleadoDAO;
import factory.OracleDAOFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pojos.Empleado;

/**
 * Clase que implementa los métodos de la interfaz del objeto empleado para
 * interactuar con la bbdd Oracle
 *
 * @author Hp
 */
public class OracleEmpleadoImp implements EmpleadoDAO {

    Connection conexion;

    public OracleEmpleadoImp() {

    }

    /**
     * Método que inserta un departamento en bbdd
     *
     * @param emp bean del empleado a insertar
     * @return
     */
    @Override
    public boolean insertarEmp(Empleado emp) {
        conexion = OracleDAOFactory.crearConexion();
        boolean valor = false;
        String sql = "INSERT INTO empleados (nombre,cargo,telefono,direccion) "
                + "VALUES(?, ?, ?, ?)";
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, emp.getNombre());
            sentencia.setString(2, emp.getCargo());
            sentencia.setString(3, emp.getTelefono());
            sentencia.setString(4, emp.getDireccion());
            int filas = sentencia.executeUpdate();
            //System.out.printf("Filas insertadas: %d%n", filas);
            if (filas > 0) {
                valor = true;
                System.out.printf("Empleado %d insertado%n", emp);
            }

        } catch (SQLException e) {
            mensajeExcepcion(e);
        } finally {
            try {
                sentencia.close();
                OracleDAOFactory.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(OracleEmpleadoImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    /**
     * Método que elimina un empleao de bbdd
     *
     * @param numemp número del epleado que se va a eliminar
     * @return
     */
    @Override
    public boolean eliminarEmp(int numemp) {
        conexion = OracleDAOFactory.crearConexion();
        boolean valor = false;
        String sql = "DELETE FROM empleados WHERE emp_no = ? ";
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, numemp);
            int filas = sentencia.executeUpdate();
            //System.out.printf("Filas eliminadas: %d%n", filas);
            if (filas > 0) {
                valor = true;
                System.out.printf("Empleado %d eliminado%n", numemp);
            }

        } catch (SQLException e) {
            mensajeExcepcion(e);
        } finally {
            try {
                sentencia.close();
                OracleDAOFactory.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(OracleEmpleadoImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return valor;
    }

    /**
     * Método que modifica un empleado
     *
     * @param numemp número del empleado a modificar
     * @param emp empleado con los datos a modificar
     * @return
     */
    @Override
    public boolean modificarEmp(int numemp, Empleado emp) {
        conexion = OracleDAOFactory.crearConexion();
        boolean valor = false;
        String sql = "UPDATE empleados SET nombre= ?, cargo = ?, telefono=?, direccion= ?"
                + " WHERE emp_no = ? ";
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(5, numemp);
            sentencia.setString(1, emp.getNombre());
            sentencia.setString(2, emp.getCargo());
            sentencia.setString(3, emp.getTelefono());
            sentencia.setString(4, emp.getDireccion());
            int filas = sentencia.executeUpdate();
            //System.out.printf("Filas modificadas: %d%n", filas);
            if (filas > 0) {
                valor = true;
                System.out.printf("Empleado %d modificado%n", numemp);
            }

        } catch (SQLException e) {
            mensajeExcepcion(e);
        } finally {
            try {
                sentencia.close();
                OracleDAOFactory.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(OracleEmpleadoImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return valor;
    }

    /**
     * Método que busca en bbdd el empleado cuyo número se pasa por parámetro
     *
     * @param numemp número del empleado a consultar
     * @return
     */
    @Override
    public Empleado consultarEmp(int numemp) {
        conexion = OracleDAOFactory.crearConexion();
        String sql = "SELECT emp_no,nombre,cargo,direccion,telefono FROM empleados"
                + " where emp_no=?";
        PreparedStatement sentencia = null;
        ResultSet rs = null;
        Empleado emp = new Empleado();
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, numemp);
            rs = sentencia.executeQuery();
            if (rs.next()) {
                emp.setNumemp(rs.getInt("emp_no"));
                emp.setNombre(rs.getString("nombre"));
                emp.setCargo(rs.getString("cargo"));
                emp.setDireccion(rs.getString("direccion"));
                emp.setTelefono(rs.getString("telefono"));
            } else {
                System.out.printf("Empleado: %d No existe%n", numemp);
            }

        } catch (SQLException e) {
            mensajeExcepcion(e);
        } finally {
            try {
                rs.close();// liberar recursos
                sentencia.close();
                OracleDAOFactory.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(OracleEmpleadoImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return emp;
    }

    /**
     * Método que lista todos los empleados que hay en bbdd
     *
     * @return devuelve un arraylist con todos los empelados
     */
    @Override
    public ArrayList listarEmp() {
        conexion = OracleDAOFactory.crearConexion();
        String sql = "SELECT emp_no,nombre,cargo,direccion,telefono FROM empleados";
        Statement sentencia = null;
        ResultSet rs = null;
        ArrayList<Empleado> listado = new ArrayList();

        try {
            sentencia = conexion.createStatement();
            rs = sentencia.executeQuery(sql);
            while (rs.next()) {
                Empleado emp = new Empleado();
                emp.setNumemp(rs.getInt("emp_no"));
                emp.setNombre(rs.getString("nombre"));
                emp.setCargo(rs.getString("cargo"));
                emp.setDireccion(rs.getString("direccion"));
                emp.setTelefono(rs.getString("telefono"));
                listado.add(emp);
            }

        } catch (SQLException e) {
            mensajeExcepcion(e);
        } finally {
            try {
                rs.close();// liberar recursos
                sentencia.close();
                OracleDAOFactory.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(OracleEmpleadoImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listado;
    }

    /**
     * Método que imprime por consola las excepciones SQL
     *
     * @param e
     */
    private void mensajeExcepcion(SQLException e) {
        System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
        System.out.printf("Mensaje   : %s %n", e.getMessage());
        System.out.printf("SQL estado: %s %n", e.getSQLState());
        System.out.printf("Cód error : %s %n", e.getErrorCode());
    }

}
