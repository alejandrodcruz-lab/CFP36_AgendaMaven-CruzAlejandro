/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

	1. Todos los contactos (si el contacto cumple años mostrar mensaje)
	2. Buscar contacto (por nombre o apellido) utilizar LIKE
	3. Agregar nuevo contacto
	4. Editar contacto
	5. Eliminar contacto

 */
package cruz.alejandro.main;

import cruz.alejandro.clases.agenda;
import java.util.Scanner;

/**
 *
 * @author Cruz Alejandro
 */
public class agendaApp {

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);
        Integer seleccion = null;
        Boolean estado = false;
        agenda agenda = new agenda();

        do {

            System.out.println("== Agenda Contactos CFP36 ==");
            System.out.println("Por favor elija una opcion\n"
                    + "1 - Ver Contactos \n"
                    + "2 - Buscar un Contacto \n"
                    + "3 - Agregar un Contacto \n"
                    + "4 - Editar un Contacto \n"
                    + "5 - Eliminar un Contacto \n"
                    + "6 - Salir \n"
                    + "============================\n");

            seleccion = teclado.nextInt();

            switch (seleccion) {
                case 1:
                    System.out.println("NOMBRE || APELLIDO || FECHA_NACIMIENTO || EMAIL || TELEFONO || AVISO");
                    agenda.verTodosContactosActivos();
                    System.out.println("\n");
                    continue;
                case 2:
                    System.out.println("Ingrese el Nombre o Apellido");
                    String clave = teclado.next();
                    System.out.println("NOMBRE || APELLIDO || FECHA_NACIMIENTO || EMAIL || TELEFONO || AVISO");
                    agenda.buscarContacto(clave);
                    System.out.println("\n");
                    continue;
                case 3:
                    System.out.println("Ingrese los datos del nuevo Contacto \n");

                    System.out.println("Formato: Nombre : String \n ");
                    agenda.setNombre(teclado.next());
                    System.out.println("Formato: Apellido : String \n ");
                    agenda.setApellido(teclado.next());
                    System.out.println("Formato: Fecha Nacimiento : AAAA-MM-DD \n ");
                    agenda.setFechaNacimiento(teclado.next());
                    System.out.println("Formato: Email : String \n ");
                    agenda.setEmail(teclado.next());
                    System.out.println("Formato: Telefono : Int \n ");
                    agenda.setNumeroTelefono(teclado.nextInt());
                    
                    agenda.agregarNuevoContacto(agenda.getNombre(), agenda.getApellido(), agenda.getFechaNacimiento(), agenda.getEmail(), agenda.getNumeroTelefono());
                    
                    System.out.println("\n");
                    continue;
                case 4:
                    System.out.println("Seleccione el contacto a Editar \n");
                    agenda.verTodosContactosActivos();
                    Integer idEdicion = teclado.nextInt();

                    System.out.println("Ingrese los nuevos valores");
                    System.out.println("Formato: Nombre : String \n ");
                    agenda.setNombre(teclado.next());
                    System.out.println("Formato: Apellido : String \n ");
                    agenda.setApellido(teclado.next());
                    System.out.println("Formato: Fecha Nacimiento : AAAA-MM-DD \n ");
                    agenda.setFechaNacimiento(teclado.next());
                    System.out.println("Formato: Email : String \n ");
                    agenda.setEmail(teclado.next());
                    System.out.println("Formato: Telefono : Int \n ");
                    agenda.setNumeroTelefono(teclado.nextInt());

                    agenda.editarContacto(idEdicion, agenda.getNombre(), agenda.getApellido(), agenda.getFechaNacimiento(), agenda.getEmail(), agenda.getNumeroTelefono());
                    System.out.println("\n");
                    continue;
                case 5:
                    System.out.println("Seleccione el contacto a Eliminar \n");
                    agenda.verTodosContactosActivos();
                    Integer idEliminar = teclado.nextInt();
                    agenda.agregarHistoricosEliminados(idEliminar);
                    agenda.eliminarContacto(idEliminar);
                    
                    System.out.println("\n");
                    continue;
                case 6:
                    estado = true;
                    teclado.close();
                    System.out.println("Hasta Luego");
                    break;
            }
        } while ((seleccion < 1 || seleccion > 6) || estado == false);
    }
}
