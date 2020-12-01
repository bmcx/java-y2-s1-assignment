/*
 * Copyright (C) 2020 Chandima Bandara
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package medcenter.helpers;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Chandima Bandara
 */
public class Database {

    static Connection con = null;

    public static Connection createConnection() {
        if (con != null) {
            return con;
        }
        String url = "jdbc:mysql://localhost:3306/medcenter"; //MySQL URL and followed by the database name
        String username = "root"; //MySQL username
        String password = ""; //MySQL password   
        return createConnection(url, username, password);
    }

    public static Connection createConnection(String url, String username, String password) {

        try {
            try {
                Class.forName("com.mysql.jdbc.Driver"); //loading mysql driver 
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, "JDBC Driver class is missing", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
            con = (Connection) DriverManager.getConnection(url, username, password); //attempting to connect to MySQL database
            System.out.println("Printing connection object " + con);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database connection error", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return con;
    }
}
