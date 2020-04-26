package daos;

import java.util.ArrayList;
import pojos.Empleado;

/**
 * Interfaz para el objeto de negocio empleado, según el patrón DAO.
 * Se definen las operaciones que se pueden realizar con este objeto.
 * 
 * @author Hp
 */
public interface EmpleadoDAO {

    public boolean insertarEmp(Empleado emp);
    public boolean eliminarEmp(int numemp);
    public boolean modificarEmp(int numemp, Empleado emp);
    public Empleado consultarEmp(int numemp);
    public ArrayList listarEmp();
    
    
    
  
    
    

}
