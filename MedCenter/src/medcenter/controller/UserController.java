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
package medcenter.controller;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import medcenter.helpers.Database;
import medcenter.models.Doctor;
import medcenter.models.Student;
import medcenter.models.User;
import medcenter.models.types.CommonTypes;
import medcenter.models.types.DataNotFoundException;

/**
 *
 * @author Chandima Bandara
 */
public class UserController {

    Connection con = Database.createConnection();

    public Student fetchStudentById(int userId) throws DataNotFoundException {
        try {
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM user WHERE id='" + userId + "';");

            if (rs.next()) {

                int id = rs.getInt("id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String username = rs.getString("username");

                return new Student(id, firstname, lastname, username);

            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new DataNotFoundException();
    }

    public Doctor fetchDoctorById(int userId) throws DataNotFoundException {
        try {
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM user WHERE id='" + userId + "';");

            if (rs.next()) {

                int id = rs.getInt("id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String username = rs.getString("username");
                String title = rs.getString("title");
                String description = rs.getString("description");
                return new Doctor(id, firstname, lastname, username, title, description);

            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new DataNotFoundException();
    }

    public User fetchUserById(int userId) throws DataNotFoundException {
        try {
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM user WHERE id='" + userId + "';");

            if (rs.next()) {

                int id = rs.getInt("id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String username = rs.getString("username");
                CommonTypes.UserRole role = CommonTypes.UserRole.valueOf(rs.getString("role"));

                User retrievedUser = new User(id, firstname, lastname, username, role);

                return retrievedUser;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new DataNotFoundException();
    }

    public List<Doctor> fetchAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        try {
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM user WHERE role='DOCTOR';");

            while (rs.next()) {
                int id = rs.getInt("id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String username = rs.getString("username");
                String title = rs.getString("title");
                String description = rs.getString("description");
                doctors.add(new Doctor(id, firstname, lastname, username, title, description));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return doctors;
    }

    public void editDoctor(int doctorId, String col, String value) {

        try {
            Statement stmt = (Statement) con.createStatement();
            stmt.executeUpdate("UPDATE user SET " + col + "='" + value + "' WHERE id=" + doctorId + ";");
            System.out.println("User data updated");
        } catch (SQLException ex) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteDoctor(int doctorId) {

        try {
            Statement stmt = (Statement) con.createStatement();
            stmt.executeUpdate("DELETE FROM user WHERE id='" + doctorId + "';");
        } catch (SQLException ex) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void addDoctor(Doctor doctor, String password) {

        try {
            Statement stmt = (Statement) con.createStatement();
            stmt.executeUpdate("INSERT INTO user(username, password, role, firstname, lastname, title, description) VALUES ('" + doctor.getUsername() + "','" + password + "','" + doctor.getRole() + "','" + doctor.getFirstname() + "','" + doctor.getLastname() + "','" + doctor.getTitle() + "','" + doctor.getDescription() + "');");
            System.out.println("Doctor added");
        } catch (SQLException ex) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
