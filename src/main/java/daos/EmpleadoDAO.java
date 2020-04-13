package daos;

import java.util.ArrayList;
import pojos.Empleado;

/**
 *
 * @author Hp
 */
public interface EmpleadoDAO {

    public boolean insertarEmp(Empleado emp);

    public boolean eliminarEmp(int numemp);

    public boolean modificarEmp(int numemp, Empleado emp);

    public Empleado consultarEmp(int numemp);

    public ArrayList listarEmp();
    
    public void cerrarConexion();
    
  
    
    

}
