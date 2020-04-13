package implementaciones.empleado;

import daos.EmpleadoDAO;
import factory.MysqlDAOFactory;
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
 *
 * @author Hp
 */
public class MysqlEmpleadoImpl implements EmpleadoDAO {

    Connection conexion;

    public MysqlEmpleadoImpl() {
        conexion = MysqlDAOFactory.crearConexion();
    }

    @Override
    public boolean insertarEmp(Empleado emp) {
        boolean valor = false;
        String sql = "INSERT INTO empleados (nombre,cargo,telefono,direccion) "
                + "VALUES(?, ?, ?, ?)";
        PreparedStatement sentencia;
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
               
            }
            sentencia.close();

        } catch (SQLException e) {
            mensajeExcepcion(e);
        }
        return valor;
    }

    @Override
    public boolean eliminarEmp(int numemp) {
        boolean valor = false;
        String sql = "DELETE FROM empleados WHERE emp_no = ? ";
        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, numemp);
            int filas = sentencia.executeUpdate();
            //System.out.printf("Filas eliminadas: %d%n", filas);
            if (filas > 0) {
                valor = true;
                System.out.printf("Empleado %d eliminado%n", numemp);
            }
            sentencia.close();
        } catch (SQLException e) {
            mensajeExcepcion(e);
        }
        return valor;
    }

    @Override
    public boolean modificarEmp(int numemp, Empleado emp) {      
            
        boolean valor = false;
        String sql = "UPDATE empleados SET  cargo = ?, telefono=?, direccion= ?"
                + " WHERE emp_no = ? ";
        
        
        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(4, numemp);
           
            sentencia.setString(1, emp.getCargo());
            sentencia.setString(2, emp.getTelefono());
            sentencia.setString(3, emp.getDireccion());
            int filas = sentencia.executeUpdate();
            //System.out.printf("Filas modificadas: %d%n", filas);
            if (filas > 0) {
                valor = true;
                System.out.printf("Empleado %d modificado%n", numemp);
            }
            sentencia.close();
        } catch (SQLException e) {
            mensajeExcepcion(e);
        }
        return valor;
    }

    @Override
    public Empleado consultarEmp(int numemp) {
        String sql = "SELECT emp_no,nombre,cargo,direccion,telefono FROM empleados"
                + " where emp_no=? order by emp_no";
        PreparedStatement sentencia;
        Empleado emp = new Empleado();
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, numemp);
            ResultSet rs = sentencia.executeQuery();
            if (rs.next()) {
                emp.setNumemp(rs.getInt("emp_no"));
                emp.setNombre(rs.getString("nombre"));
                emp.setCargo(rs.getString("cargo"));
                emp.setDireccion(rs.getString("direccion"));
                emp.setTelefono(rs.getString("telefono"));
            } else {
                System.out.printf("Empleado: %d No existe%n", numemp);
            }

            rs.close();// liberar recursos
            sentencia.close();

        } catch (SQLException e) {
            mensajeExcepcion(e);
        }
        return emp;
    }

    @Override
    public ArrayList listarEmp() {
        String sql = "SELECT emp_no,nombre,cargo,direccion,telefono FROM empleados";
        Statement sentencia;
        ArrayList<Empleado> listado = new ArrayList();

        try {
            sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery(sql);
            while (rs.next()) {
                Empleado emp = new Empleado();
                emp.setNumemp(rs.getInt("emp_no"));
                emp.setNombre(rs.getString("nombre"));
                emp.setCargo(rs.getString("cargo"));
                emp.setDireccion(rs.getString("direccion"));
                emp.setTelefono(rs.getString("telefono"));
                listado.add(emp);
            }

            rs.close();// liberar recursos
            sentencia.close();

        } catch (SQLException e) {
            mensajeExcepcion(e);
        }
        return listado;
    }

    private void mensajeExcepcion(SQLException e) {
        System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
        System.out.printf("Mensaje   : %s %n", e.getMessage());
        System.out.printf("SQL estado: %s %n", e.getSQLState());
        System.out.printf("Cód error : %s %n", e.getErrorCode());
    }

    @Override
    public void cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException ex) {
           System.out.println("Error al cerrar conexión "+ex.getMessage()+"-- "+ex.getSQLState());
        }
    }

    
}
