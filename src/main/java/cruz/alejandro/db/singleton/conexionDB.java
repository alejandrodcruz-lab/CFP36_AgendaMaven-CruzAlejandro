/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cruz.alejandro.db.singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Cruz Alejandro
 */
public class conexionDB {

    private static conexionDB instance = null;
    private Connection con;
    private String host = "jdbc:mysql://localhost:33066/db_agenda_maven?serverTimezone=UTC";
    private String username = "root";
    private String password = "root";

    private conexionDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.con = DriverManager.getConnection(host, username, password);
            System.out.println("BASE DE DATOS ON");
        } catch (Exception e) {
            System.out.println("ERROR ->" + e);
        }

    }

    public Connection getConnection() {
        return con;
    }

    public static conexionDB getInstance() {
        try {
            if (instance == null) {
                instance = new conexionDB();

            } else if (instance.getConnection().isClosed()) {
                instance = new conexionDB();
            }

            return instance;
        } catch (SQLException e) {

            return instance;

        }

    }
}
