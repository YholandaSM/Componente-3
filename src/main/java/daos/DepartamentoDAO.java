package daos;

import java.util.ArrayList;
import pojos.Departamento;

 
/**
 * Interfaz para el objeto de negocio departamento, según el patrón
 * DAO. Se definen las operaciones que se pueden realizar con este
 * objeto.
 *
 * @author Hp
 */
public interface DepartamentoDAO {

    public boolean insertarDep(Departamento dep);
    public boolean eliminarDep(int deptno);
    public boolean modificarDep(int deptno, Departamento dep);
    public Departamento consultarDep(int deptno);
    public ArrayList listarDep();

}
