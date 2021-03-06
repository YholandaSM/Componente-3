package implementaciones.departamento;

import daos.DepartamentoDAO;
import factory.OracleDAOFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import pojos.Departamento;

/**
 * Clase que implementa los métodos de la interfaz del objeto 
 * departamento para interactuar con la bbdd Oracle.
 * @author Hp
 */
public class OracleDepartamentoImpl implements DepartamentoDAO {

   Connection conexion;

    public OracleDepartamentoImpl() {

    }
    
    /**
     * Método que inserta un departamento en bbdd 
     * @param dep  bean del departamento a insertar
     * @return 
     */
    @Override
    public boolean insertarDep(Departamento dep) {
        conexion = OracleDAOFactory.crearConexion();
        boolean valor = false;
        String sql = "INSERT INTO departamentos VALUES(?, ?, ?)";
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, dep.getDeptno());
            sentencia.setString(2, dep.getDnombre());
            sentencia.setString(3, dep.getLoc());
            int filas = sentencia.executeUpdate();
            //System.out.printf("Filas insertadas: %d%n", filas);
            if (filas > 0) {
                valor = true;
                System.out.printf("Departamento %d insertado%n", dep.getDeptno());
            }

        } catch (SQLException e) {
            mensajeExcepcion(e);
        } finally {
            try {
                sentencia.close();
                OracleDAOFactory.cerrarConexion();
            } catch (SQLException ex) {
                mensajeExcepcion(ex);
            }
        }
        return valor;
    }
    
    /**
     * Método que elimina de bbdd, el departamento cuyo número
     * es el pasado por parámetro
     * @param deptno número del departamento a eliminar
     * @return 
     */
    @Override
    public boolean eliminarDep(int deptno) {
        conexion = OracleDAOFactory.crearConexion();
        boolean valor = false;
        String sql = "DELETE FROM departamentos WHERE dept_no = ? ";
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, deptno);
            int filas = sentencia.executeUpdate();
            //System.out.printf("Filas eliminadas: %d%n", filas);
            if (filas > 0) {
                valor = true;
                System.out.printf("Departamento %d eliminado%n", deptno);
            }

        } catch (SQLException e) {
            mensajeExcepcion(e);
        } finally {
            try {
                sentencia.close();
                OracleDAOFactory.cerrarConexion();
            } catch (SQLException ex) {
                mensajeExcepcion(ex);
            }
        }
        return valor;
    }
    
     /**
     * Método que modifica el departamento cuyo número pasamos por
     * parámetro
     * @param num número del departamento a modificafr
     * @param dep departamento con los datos a modificar
     * @return 
     */
    @Override
    public boolean modificarDep(int num, Departamento dep) {
        conexion = OracleDAOFactory.crearConexion();
        boolean valor = false;
        String sql = "UPDATE departamentos SET dnombre= ?, loc = ? WHERE dept_no = ? ";
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(3, num);
            sentencia.setString(1, dep.getDnombre());
            sentencia.setString(2, dep.getLoc());
            int filas = sentencia.executeUpdate();
            //System.out.printf("Filas modificadas: %d%n", filas);
            if (filas > 0) {
                valor = true;
                System.out.printf("Departamento %d modificado%n", num);
            }

        } catch (SQLException e) {
            mensajeExcepcion(e);
        } finally {
            try {
                sentencia.close();
                OracleDAOFactory.cerrarConexion();
            } catch (SQLException ex) {
                mensajeExcepcion(ex);
            }
        }
        return valor;
    }
    
    /**
     * Método que busca en bbdd el departamento cuyo número se pasa
     * por parámetro
     * @param deptno número del departamento a consultar
     * @return 
     */
    @Override
    public Departamento consultarDep(int deptno) {
        conexion = OracleDAOFactory.crearConexion();
        String sql = "SELECT dept_no, dnombre, loc FROM departamentos WHERE dept_no =  ?";
        PreparedStatement sentencia = null;
        ResultSet rs = null;
        Departamento dep = new Departamento();
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, deptno);
            rs = sentencia.executeQuery();
            if (rs.next()) {
                dep.setDeptno(rs.getInt("dept_no"));
                dep.setDnombre(rs.getString("dnombre"));
                dep.setLoc(rs.getString("loc"));
                System.out.println(dep);
            } else {
                System.out.printf("Departamento: %d No existe%n", deptno);
            }

        } catch (SQLException e) {
            mensajeExcepcion(e);
        } catch (NullPointerException ex) {
            System.out.println("No se ha creado la conexión:  " + ex.getMessage());
        } finally {

            try {
                rs.close();// liberar recursos
                sentencia.close();
                OracleDAOFactory.cerrarConexion();
            } catch (SQLException ex) {
                mensajeExcepcion(ex);
            }
        }
        return dep;
    }
    
     /**
     * Método que lista todos los departamentos que hay en bbdd
     * @return 
     */
    public ArrayList<Departamento> listarDep() {
        conexion = OracleDAOFactory.crearConexion();
        String sql = "SELECT dept_no, dnombre, loc FROM departamentos";
        Statement sentencia = null;
        ResultSet rs = null;
        ArrayList<Departamento> listado = new ArrayList();

        try {
            sentencia = conexion.createStatement();
            rs = sentencia.executeQuery(sql);
            while (rs.next()) {
                Departamento dep = new Departamento();
                dep.setDeptno(rs.getInt("dept_no"));
                dep.setDnombre(rs.getString("dnombre"));
                dep.setLoc(rs.getString("loc"));
                listado.add(dep);
            }

        } catch (SQLException e) {
            mensajeExcepcion(e);
        } finally {
            try {
                rs.close();// liberar recursos
                sentencia.close();
                OracleDAOFactory.cerrarConexion();
            } catch (SQLException ex) {
                mensajeExcepcion(ex);
            }
        }
        return listado;
    }
    
     /**
     * Método que imprime por consola las excepciones SQL
     * @param e 
     */
    private void mensajeExcepcion(SQLException e) {
        System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
        System.out.printf("Mensaje   : %s %n", e.getMessage());
        System.out.printf("SQL estado: %s %n", e.getSQLState());
        System.out.printf("Cód error : %s %n", e.getErrorCode());
    }


}
