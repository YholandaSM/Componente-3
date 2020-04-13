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
public class NeodatisDAOFactory extends DAOFactory{

   

    static ODB odb = null;

    public NeodatisDAOFactory() {

    }

    public static ODB crearConexion() {
        if (odb == null) {
            odb = ODBFactory.open("Feedback.BD");
        }
        return odb;
    }
    
      

    

    @Override
    public EmpleadoDAO getEmpleadoDAO() {
        return new NeodatisEmpleadoImp(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DepartamentoDAO getDepartamentoDAO() {
          return new NeodatisDepartamentoImpl(); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
