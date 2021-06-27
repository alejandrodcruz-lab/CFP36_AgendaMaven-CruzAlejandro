/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

 /*Tarea
1. Crear una base de datos 
-> db_agenda
	-> contactos
		- idContacto
		- nombre
		- apellido
		- fechaNacimiento
		- email
		- telefono
		
2. crear diagrama EER (WORKBENCH)

3. Crear menu en java
	1. Todos los contactos (si el contacto cumple años mostrar mensaje)
	2. Buscar contacto (por nombre o apellido) utilizar LIKE
	3. Agregar nuevo contacto
	4. Editar contacto
	5. Eliminar contacto
	
4. subir el proyecto a github(diagrama, .sql, proyecto netbeans).*/
package cruz.alejandro.clases;

import cruz.alejandro.db.singleton.conexionDB;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 *
 * @author Cruz Alejandro
 */
public class agenda {

    private Integer idContacto;
    private String nombre;
    private String apellido;
    private String fechaNacimiento;
    private String email;
    private Integer numeroTelefono;
    private conexionDB db;
    private Statement declaracion;
    private ResultSet resultado;

    public agenda() {
        this.db = conexionDB.getInstance();
    }

    public agenda(Integer idContacto, String nombre, String apellido, String fechaNacimiento, String email, Integer numeroTelefono) {
        this.idContacto = idContacto;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.numeroTelefono = numeroTelefono;
        this.db = conexionDB.getInstance();
    }

    public void verTodosContactosActivos() {

        try {
            declaracion = db.getConnection().createStatement();
            resultado = declaracion.executeQuery("SELECT * FROM CONTACTO WHERE ESTADO = 1");

            while (resultado.next()) {
                String cumpleaños = esCumpleaños(resultado.getTimestamp("FECHA_NACIMIENTO"));
                System.out.println(resultado.getInt("ID_CONTACTO") + " " + resultado.getString("NOMBRE") + " " + resultado.getString("APELLIDO") + " " + resultado.getDate("FECHA_NACIMIENTO") + " " + resultado.getString("EMAIL") + " " + resultado.getInt("TELEFONO") + " " + cumpleaños);
            }
            //db.getCon().close();
        } catch (Exception e) {
        }
    }

    public void buscarContacto(String clave) {

        try {

            String query = "SELECT * FROM CONTACTO WHERE NOMBRE LIKE ? OR APELLIDO LIKE ? AND ESTADO = 1";

            PreparedStatement preparacion = db.getConnection().prepareStatement(query);
            preparacion.setString(1, "%" + clave.toUpperCase() + "%");
            preparacion.setString(2, "%" + clave.toUpperCase() + "%");

            resultado = preparacion.executeQuery();

            if (resultado != null) {
                while (resultado.next()) {
                    System.out.println(resultado.getInt("ID_CONTACTO") + " " + resultado.getString("NOMBRE") + " " + resultado.getString("APELLIDO") + " " + resultado.getDate("FECHA_NACIMIENTO") + " " + resultado.getString("EMAIL") + " " + resultado.getInt("TELEFONO"));
                }
            } else {
                System.out.println("No encontrado");
            }

            //db.getCon().close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void agregarNuevoContacto(String nombre, String apellido, String fechaNacimiento, String email, Integer telefono) {

        try {
            String query = "INSERT INTO CONTACTO(NOMBRE,APELLIDO,FECHA_NACIMIENTO,EMAIL,TELEFONO,FECHA_CREACION)" + "VALUES(?,?,?,?,?,CURDATE())";

            PreparedStatement preparacion = db.getConnection().prepareStatement(query);
            preparacion.setString(1, nombre.toUpperCase());
            preparacion.setString(2, apellido.toUpperCase());
            preparacion.setString(3, fechaNacimiento.toUpperCase());
            preparacion.setString(4, email.toUpperCase());
            preparacion.setInt(5, telefono);

            if (!preparacion.execute()) {
                System.out.println("Se ha agregado exitosamente");
            } else {
                System.out.println("No se ha podido agregar");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void editarContacto(Integer id, String nombre, String apellido, String fechaNacimiento, String email, Integer telefono) {

        try {
            String query = "UPDATE CONTACTO SET NOMBRE = ?, APELLIDO = ?, FECHA_NACIMIENTO = ?, EMAIL = ?, TELEFONO = ?, FECHA_MODIFICACION = CURDATE() WHERE ID_CONTACTO = ?";

            PreparedStatement preparacion = db.getConnection().prepareStatement(query);

            preparacion.setString(1, nombre.toUpperCase());
            preparacion.setString(2, apellido.toUpperCase());
            preparacion.setString(3, fechaNacimiento.toUpperCase());
            preparacion.setString(4, email.toUpperCase());
            preparacion.setInt(5, telefono);
            preparacion.setInt(6, id);

            if (!preparacion.execute()) {
                System.out.println("Edicion Exitosa");
            } else {
                System.out.println("No se ha podido Editar");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

//    public void eliminarContacto(Integer id) {
//
//        try {
//            String query = "DELETE FROM CONTACTO WHERE ID_CONTACTO = ?";
//
//            PreparedStatement preparacion = db.getConnection().prepareStatement(query);
//            preparacion.setInt(1, id);
//            if (!preparacion.execute()) {
//                System.out.println("Se ha eliminado exitosamente");
//
//            } else {
//                System.out.println("No se ha podido eliminar");
//            }
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
    
    public void eliminarContacto(Integer id) {

        try {
            String query = "UPDATE CONTACTO SET ESTADO = 0, FECHA_DESACTIVACION = CURDATE() WHERE ID_CONTACTO = ?";

            PreparedStatement preparacion = db.getConnection().prepareStatement(query);
            preparacion.setInt(1, id);
            if (!preparacion.execute()) {
                System.out.println("Se ha eliminado exitosamente");

            } else {
                System.out.println("No se ha podido eliminar");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Integer getIdContacto() {
        return idContacto;
    }

    public void setIdContacto(Integer idContacto) {
        this.idContacto = idContacto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(Integer numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    private String esCumpleaños(Timestamp date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        Calendar c = Calendar.getInstance();

        int diaHoy = c.get(Calendar.DATE);
        int mesHoy = c.get(Calendar.MONTH);

        int dia = calendar.get(Calendar.DATE);
        int mes = calendar.get(Calendar.MONTH);

        if (dia == diaHoy && mes == mesHoy) {
            return "HOY CUMPLE AÑOS!!";
        } else {
            return " ";
        }
    }

    public void agregarHistoricosEliminados(Integer id) {

        try {
            String query = "SELECT * FROM CONTACTO WHERE ID_CONTACTO= ?";

            PreparedStatement preparacion = db.getConnection().prepareStatement(query);
            preparacion.setInt(1, id);
            resultado = preparacion.executeQuery();

            if (resultado != null) {
                while (resultado.next()) {
                    String queryCopia = "INSERT INTO CONTACTO_HIST (NOMBRE,APELLIDO,FECHA_NACIMIENTO,EMAIL,TELEFONO,ID_ORIGINAL)" + "VALUES(?,?,?,?,?,?)";

                    PreparedStatement preCopia = db.getConnection().prepareStatement(queryCopia);
                    preCopia.setString(1, resultado.getString("NOMBRE"));
                    preCopia.setString(2, resultado.getString("APELLIDO"));
                    preCopia.setString(3, resultado.getString("FECHA_NACIMIENTO"));
                    preCopia.setString(4, resultado.getString("EMAIL"));
                    preCopia.setInt(5, resultado.getInt("TELEFONO"));
                    preCopia.setInt(6, resultado.getInt("ID_CONTACTO"));
                    preCopia.execute();

                    System.out.println("Agregado a Historico");
                }
            } else {
                System.out.println("No agregado a Historico");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
