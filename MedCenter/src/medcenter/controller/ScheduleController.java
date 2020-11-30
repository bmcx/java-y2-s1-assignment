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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import medcenter.helpers.Database;
import medcenter.models.Booking;
import medcenter.models.Doctor;
import medcenter.models.Schedule;
import medcenter.models.Student;
import medcenter.models.types.CommonTypes;
import medcenter.models.types.DataNotFoundException;

/**
 *
 * @author Chandima Bandara
 */
public class ScheduleController {

    Connection con = Database.createConnection();

    public List<Schedule> fetchAll() {
        List<Schedule> list = new ArrayList<>();
        UserController userController = new UserController();
        try {
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM schedule ORDER BY day DESC, timeFrom ASC;");

            while (rs.next()) {

                int id = rs.getInt("id");
                Doctor doctor = userController.fetchDoctorById(rs.getInt("doctorId"));
                LocalDate day = rs.getDate("day").toLocalDate();
                LocalTime from = rs.getTime("timeFrom").toLocalTime();
                LocalTime to = rs.getTime("timeTo").toLocalTime();
                int maxBookingCount = rs.getInt("maxCount");

                Schedule schedule = new Schedule(id, doctor, day, from, to, maxBookingCount);
                list.add(schedule);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataNotFoundException ex) {
            Logger.getLogger(ScheduleController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public void addSchedule(int doctorId, LocalDate day, String from, String to) throws SQLException {

        Statement stmt = (Statement) con.createStatement();
        stmt.executeUpdate("INSERT INTO schedule(doctorId, day, timeFrom, timeTo) VALUES ('" + doctorId + "','" + day + "','" + from + "','" + to + "');");

    }

    public void deleteSchedule(int id) {

        try {
            Statement stmt = (Statement) con.createStatement();
            stmt.executeUpdate("DELETE FROM schedule WHERE id='" + id + "';");
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<Schedule> fetchAllActive() {
        List<Schedule> list = new ArrayList<>();
        UserController userController = new UserController();
        try {
            Statement stmt = (Statement) con.createStatement();
            //only fetch today's and future schedules
            ResultSet rs = stmt.executeQuery("SELECT * FROM schedule WHERE day >= CURRENT_DATE() ORDER BY day DESC, timeFrom ASC;");

            while (rs.next()) {

                int id = rs.getInt("id");
                Doctor doctor = userController.fetchDoctorById(rs.getInt("doctorId"));
                LocalDate day = rs.getDate("day").toLocalDate();
                LocalTime from = rs.getTime("timeFrom").toLocalTime();
                LocalTime to = rs.getTime("timeTo").toLocalTime();
                int maxBookingCount = rs.getInt("maxCount");

                Schedule schedule = new Schedule(id, doctor, day, from, to, maxBookingCount);
                list.add(schedule);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataNotFoundException ex) {
            Logger.getLogger(ScheduleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Schedule fetchScheduleById(int scheduleId) throws DataNotFoundException {

        UserController userController = new UserController();
        try {
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM schedule WHERE id='" + scheduleId + "';");

            if (rs.first()) {

                int id = rs.getInt("id");
                Doctor doctor = userController.fetchDoctorById(rs.getInt("doctorId"));
                LocalDate day = rs.getDate("day").toLocalDate();
                LocalTime from = rs.getTime("timeFrom").toLocalTime();
                LocalTime to = rs.getTime("timeTo").toLocalTime();
                int maxBookingCount = rs.getInt("maxCount");

                return new Schedule(id, doctor, day, from, to, maxBookingCount);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new DataNotFoundException();
    }

    public List<Schedule> fetchScheduleByDoctorId(int doctorId) {
        List<Schedule> list = new ArrayList<>();
        UserController userController = new UserController();
        try {
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM schedule WHERE doctorId='" + doctorId + "' ORDER BY day DESC, timeFrom DESC;");

            while (rs.next()) {

                int id = rs.getInt("id");
                Doctor doctor = userController.fetchDoctorById(rs.getInt("doctorId"));
                LocalDate day = rs.getDate("day").toLocalDate();
                LocalTime from = rs.getTime("timeFrom").toLocalTime();
                LocalTime to = rs.getTime("timeTo").toLocalTime();

                Schedule schedule = new Schedule(id, doctor, day, from, to, -1);
                list.add(schedule);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataNotFoundException ex) {
            Logger.getLogger(ScheduleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
