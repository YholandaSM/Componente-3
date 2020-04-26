package implementaciones.departamento;



import daos.DepartamentoDAO;
import factory.NeodatisDAOFactory;
import java.util.ArrayList;
import org.neodatis.odb.ODB;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import pojos.Departamento;

/**
 * Clase que implementa los métodos de la interfaz del objeto 
 * departamento para interactuar con la bbdd Neodatis.
 * @author Hp
 */
public class NeodatisDepartamentoImpl implements DepartamentoDAO {

    static ODB bd;

    public NeodatisDepartamentoImpl() {

    }
    
    /**
     * Método que inserta un departamento en bbdd 
     * @param dep  bean del departamento a insertar
     * @return 
     */
    @Override
    public boolean insertarDep(Departamento dep) {
        bd = NeodatisDAOFactory.crearConexion();
        try {
            bd.store(dep);
            bd.commit();
            System.out.printf("Departamento: %d Insertado %n", dep.getDeptno());
        } finally {
            NeodatisDAOFactory.cerrarConexion();
        }
        return true;
    }
    
    /**
     * Método que elimina de bbdd, el departamento cuyo número
     * es el pasado por parámetro
     * @param deptno número del departamento a eliminar
     * @return 
     */
    @Override
    public boolean eliminarDep(int deptno) {
        bd = NeodatisDAOFactory.crearConexion();
        boolean valor = false;
        IQuery query = new CriteriaQuery(Departamento.class, Where.equal("deptno", deptno));
        Objects<Departamento> objetos = bd.getObjects(query);
        try {
            Departamento depart = (Departamento) objetos.getFirst();
            bd.delete(depart);
            bd.commit();
            valor = true;
        } catch (IndexOutOfBoundsException i) {
            System.out.printf("Departamento a eliminar: %d No existe%n", deptno);
        } finally {
            NeodatisDAOFactory.cerrarConexion();
        }
        // bd.close();
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
    public boolean modificarDep(int deptno, Departamento dep) {
        bd = NeodatisDAOFactory.crearConexion();
        boolean valor = false;
        IQuery query = new CriteriaQuery(Departamento.class, Where.equal("deptno", deptno));
        Objects<Departamento> objetos = bd.getObjects(query);
        try {
            Departamento depart = (Departamento) objetos.getFirst();
            depart.setDnombre(dep.getDnombre());
            depart.setLoc(dep.getLoc());
            bd.store(depart); // actualiza el objeto 
            valor = true;

        } catch (IndexOutOfBoundsException i) {
            System.out.printf("Departamento: %d No existe%n", deptno);
        } finally {
            NeodatisDAOFactory.cerrarConexion();
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
        bd = NeodatisDAOFactory.crearConexion();
        IQuery query = new CriteriaQuery(Departamento.class, Where.equal("deptno", deptno));
        Objects<Departamento> objetos = bd.getObjects(query);
        Departamento dep = new Departamento();
        if (objetos != null) {
            try {
                dep = (Departamento) objetos.getFirst();
                System.out.println(dep);
            } catch (IndexOutOfBoundsException i) {
                System.out.printf("Departamento: %d No existe%n", deptno);

            } finally {
                NeodatisDAOFactory.cerrarConexion();
            }
        }

        return dep;
    }
    
    /**
     * Método que lista todos los departamentos que nhay en bbdd
     * @return 
     */
    @Override
    public ArrayList<Departamento> listarDep() {
        bd = NeodatisDAOFactory.crearConexion();
        ArrayList<Departamento> listado;
        try {
            IQuery query = new CriteriaQuery(Departamento.class);
            Objects<Departamento> objetos = bd.getObjects(query);
            listado = new ArrayList();
            Departamento dep = new Departamento();
            if (objetos != null) {
                while (objetos.hasNext()) {
                    listado.add(objetos.next());
                }
            }

        } finally {

            NeodatisDAOFactory.cerrarConexion();
        }

        return listado;
    }

}