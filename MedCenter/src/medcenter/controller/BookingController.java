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
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import medcenter.helpers.Database;
import medcenter.models.Booking;
import medcenter.models.Doctor;
import medcenter.models.Student;
import medcenter.models.types.CommonTypes;
import medcenter.models.types.DataNotFoundException;

/**
 *
 * @author Chandima Bandara
 */
public class BookingController {

    Connection con = Database.createConnection();

    public List<Booking> fetchBookingsByDoctorId(int doctorId) {
        List<Booking> list = new ArrayList<>();
        UserController userController = new UserController();
        try {
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM booking WHERE doctorId='" + doctorId + "';");

            while (rs.next()) {

                int id = rs.getInt("id");
                Doctor doctor = userController.fetchDoctor(rs.getInt("doctorId"));
                Student student = userController.fetchStudent(rs.getInt("studentId"));
                int scheduleId = rs.getInt("scheduleId");
                CommonTypes.BookingStatus status = CommonTypes.BookingStatus.valueOf(rs.getString("status"));
                LocalDateTime createdAt = rs.getTimestamp("createdAt").toLocalDateTime();

                Booking booking = new Booking(id, doctor, student, scheduleId, status, createdAt);
                list.add(booking);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataNotFoundException ex) {
            Logger.getLogger(BookingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Booking> fetchBookingsByScheduleId(int scheduleId) {
        List<Booking> list = new ArrayList<>();
        UserController userController = new UserController();
        try {
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM booking WHERE scheduleId='" + scheduleId + "';");

            while (rs.next()) {

                int id = rs.getInt("id");
                Doctor doctor = userController.fetchDoctor(rs.getInt("doctorId"));
                Student student = userController.fetchStudent(rs.getInt("studentId"));
                CommonTypes.BookingStatus status = CommonTypes.BookingStatus.valueOf(rs.getString("status"));
                LocalDateTime createdAt = rs.getTimestamp("createdAt").toLocalDateTime();

                Booking booking = new Booking(id, doctor, student, scheduleId, status, createdAt);
                list.add(booking);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataNotFoundException ex) {
            Logger.getLogger(BookingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
