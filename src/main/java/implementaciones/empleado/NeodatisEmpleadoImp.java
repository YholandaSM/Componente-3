package implementaciones.empleado;

import daos.EmpleadoDAO;
import factory.NeodatisDAOFactory;
import java.util.ArrayList;
import org.neodatis.odb.ODB;
import org.neodatis.odb.OID;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.oid.OIDFactory;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

import pojos.Empleado;

/**
 * Clase que implementa los métodos de la interfaz del objeto empleado para
 * interactuar con la bbdd Neodatis.
 *
 * @author Hp
 */
public class NeodatisEmpleadoImp implements EmpleadoDAO {

    static ODB bd;

    public NeodatisEmpleadoImp() {

    }
    
    
    /**
     * Método que inserta un departamento en bbdd
     *
     * @param emp bean del empleado a insertar
     * @return
     */
    @Override
    public boolean insertarEmp(Empleado emp) {
        bd = NeodatisDAOFactory.crearConexion();
        try {
            bd.store(emp);
            bd.commit();
            System.out.println("Empleado: insertado " + emp);
        } finally {
            NeodatisDAOFactory.cerrarConexion();
        }
        return true;
    }
    
     /**
     * Método que elimina un empleao de bbdd
     *
     * @param numemp número del epleado que se va a eliminar
     * @return
     */
    @Override
    public boolean eliminarEmp(int numemp) {
        bd = NeodatisDAOFactory.crearConexion();
        boolean valor = false;
        OID oid = OIDFactory.buildObjectOID(numemp);
        Empleado emp = (Empleado) bd.getObjectFromId(oid);
        try {
            bd.delete(emp);
            bd.commit();
            valor = true;
        } catch (IndexOutOfBoundsException i) {
            System.out.println("Empleado a eliminar: %d No existe%n" + numemp);
        } finally {
            NeodatisDAOFactory.cerrarConexion();
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
        bd = NeodatisDAOFactory.crearConexion();
        boolean valor = false;
        OID oid = OIDFactory.buildObjectOID(numemp);
        Empleado empMod = (Empleado) bd.getObjectFromId(oid);
        try {

            empMod.setCargo(emp.getCargo());

            empMod.setDireccion(emp.getDireccion());

            empMod.setTelefono(emp.getTelefono());

            bd.store(empMod); // actualiza el objeto 
            valor = true;
            bd.commit();
        } catch (IndexOutOfBoundsException i) {
            System.out.printf("Empleado: %d No existe%n", numemp);
        } finally {
            NeodatisDAOFactory.cerrarConexion();
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
        bd = NeodatisDAOFactory.crearConexion();
        Empleado emp = null;
        try {
            IQuery query = new CriteriaQuery(Empleado.class, Where.equal("deptno", numemp));
            Objects<Empleado> objetos = bd.getObjects(query);
            emp = new Empleado();
            if (objetos != null) {
                try {
                    emp = (Empleado) objetos.getFirst();
                } catch (IndexOutOfBoundsException i) {
                    System.out.printf("Empleado: %d No existe%n", numemp);

                }
            }

        } finally {

            NeodatisDAOFactory.cerrarConexion();
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
        bd = NeodatisDAOFactory.crearConexion();
        ArrayList<Empleado> listado = null;
        try {
            IQuery query = new CriteriaQuery(Empleado.class);
            Objects<Empleado> objetos = bd.getObjects(query);
            listado = new ArrayList();
            //   Empleado emp = new Empleado();
            if (objetos != null) {
                while (objetos.hasNext()) {
                    //Vamos utilizar el OID como número de empleado
                    Empleado emp = objetos.next();
                    OID oid = bd.getObjectId(emp);
                    emp.setNumemp(Integer.parseInt(oid.toString()));
                    listado.add(emp);

                }
            }

        } finally {
            NeodatisDAOFactory.cerrarConexion();
        }
        return listado;
    }

}
