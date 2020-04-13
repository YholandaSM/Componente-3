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
 *
 * @author Hp
 */
public class NeodatisEmpleadoImp implements EmpleadoDAO {

    static ODB bd;

    public NeodatisEmpleadoImp() {
        bd = NeodatisDAOFactory.crearConexion();
    }

    @Override
    public boolean insertarEmp(Empleado emp) {

        bd.store(emp);
        bd.commit();
        System.out.println("Empleado: insertado " + emp);
        return true;
    }

    @Override
    public boolean eliminarEmp(int numemp) {
        boolean valor = false;
        OID oid = OIDFactory.buildObjectOID(numemp);
        Empleado emp = (Empleado) bd.getObjectFromId(oid);
        try {
            bd.delete(emp);
            bd.commit();
            valor = true;
        } catch (IndexOutOfBoundsException i) {
            System.out.println("Empleado a eliminar: %d No existe%n" + numemp);
        }
        return valor;
    }

    @Override
    public boolean modificarEmp(int numemp, Empleado emp) {
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
        }
        return valor;
    }

    @Override
    public Empleado consultarEmp(int numemp) {
        IQuery query = new CriteriaQuery(Empleado.class, Where.equal("deptno", numemp));
        Objects<Empleado> objetos = bd.getObjects(query);
        Empleado emp = new Empleado();
        if (objetos != null) {
            try {
                emp = (Empleado) objetos.getFirst();
            } catch (IndexOutOfBoundsException i) {
                System.out.printf("Empleado: %d No existe%n", numemp);

            }
        }
        return emp;
    }

    @Override
    public ArrayList listarEmp() {
        IQuery query = new CriteriaQuery(Empleado.class);
        Objects<Empleado> objetos = bd.getObjects(query);
        ArrayList<Empleado> listado = new ArrayList();
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
        return listado;
    }

    @Override
    public void cerrarConexion() {
        bd.close();
    }

}
