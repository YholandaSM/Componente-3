package pojos;

/**
 * POJO para el objeto de negocio EMPLEADO, según el patrón DAO.
 * @author Hp
 */
public class Empleado {

    private int numemp;
    private String nombre;
    private String cargo;
    private String telefono;
    private String direccion;

    String modificar;
    String eliminar;
    String insertar;

    public Empleado() {
    }

    public Empleado(int numemp, String nombre, String cargo, String telefono, String direccion) {
        this.numemp = numemp;
        this.nombre = nombre;
        this.cargo = cargo;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public Empleado(String nombre, String cargo, String telefono, String direccion) {
        this.nombre = nombre;
        this.cargo = cargo;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public Empleado(String cargo, String telefono, String direccion) {
        this.cargo = cargo;
        this.telefono = telefono;
        this.direccion = direccion;
    }
    
    

    public int getNumemp() {
        return numemp;
    }

    public void setNumemp(int numemp) {
        this.numemp = numemp;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getModificar() {
        return modificar;
    }

    public void setModificar(String modificar) {
        this.modificar = modificar;
    }

    public String getEliminar() {
        return eliminar;
    }

    public void setEliminar(String eliminar) {
        this.eliminar = eliminar;
    }

    public String getInsertar() {
        return insertar;
    }

    public void setInsertar(String insertar) {
        this.insertar = insertar;
    }
    
    

    @Override
    public String toString() {
        return "Empleados{" + "numemp=" + numemp + ", nombre=" + nombre + ", cargo=" + cargo + ", telefono=" + telefono + ", direccion=" + direccion + '}';
    }

}
