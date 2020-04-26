package factory;

import daos.DepartamentoDAO;
import daos.EmpleadoDAO;

/**
 * Clase abstracta que nos permite acceder a bbdd diferentes: Mysql,
 * Neodatis u Oracle, siguiendo el patrón Factory.
 * 
 *
 * @author Hp
 */
public abstract class DAOFactory {

    //Bases de datos soportadas
    public static final int MYSQL = 1;
    public static final int NEODATIS = 2;
    public static final int ORACLE = 3;
    
    
    //Método abstracto que produce el DAO para empleado
    public abstract EmpleadoDAO getEmpleadoDAO();
    
    //Método abstracto que produce el DAO para departamento
    public abstract DepartamentoDAO getDepartamentoDAO();

    /**
     * Método que devuelve una instancia del objeto de bbdd que 
     * seleccione el usuario en el método principal.
     *
     * @param bd: ddbb:mysql, neodatos u Oracle
     * @return 
     */
    public static DAOFactory getDAOFactory(int bd) {
        switch (bd) {
            case MYSQL:
                return new MysqlDAOFactory();
            case NEODATIS:
                return new NeodatisDAOFactory();
            case ORACLE:
                return new OracleDAOFactory();
            default:
                return null;
        }
    }

}
