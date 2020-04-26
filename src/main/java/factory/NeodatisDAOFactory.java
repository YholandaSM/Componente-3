package factory;

import daos.DepartamentoDAO;
import daos.EmpleadoDAO;
import implementaciones.departamento.NeodatisDepartamentoImpl;
import implementaciones.empleado.NeodatisEmpleadoImp;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;

/**
 *
 * @author Hp
 */
public class NeodatisDAOFactory extends DAOFactory {

    static ODB odb = null;

    public NeodatisDAOFactory() {

    }

    public static ODB crearConexion() {
        if (odb == null) {
            odb = ODBFactory.open("C:\\MiBBDD\\Empleado.BD");
        }
        return odb;
    }

     /**
     * Método que devuelve una instancia del objeto empleado
     * que implementará los métodos definidos en la interfaz EmpleadoDAO() .
     * de la interfaz
     *
     * @return
     */
    @Override
    public EmpleadoDAO getEmpleadoDAO() {
        return new NeodatisEmpleadoImp(); //To change body of generated methods, choose Tools | Templates.
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
        return new NeodatisDepartamentoImpl(); //To change body of generated methods, choose Tools | Templates.
    }

    public static void cerrarConexion() {
        if (odb != null) {
            odb.close();
            odb = null;
        }
    }

}
