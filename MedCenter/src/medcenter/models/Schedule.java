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
package medcenter.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import medcenter.controller.BookingController;
import medcenter.models.types.DataNotFoundException;

/**
 *
 * @author Chandima Bandara
 */
public class Schedule {

    int id;
    List<Booking> bookings;
    Doctor doctor;
    LocalDate day;
    LocalTime from;
    LocalTime to;
    int maxBookingCount;

    public Schedule(int id, Doctor doctor, LocalDate day, LocalTime from, LocalTime to, int maxBookingCount) {
        this.id = id;
        this.doctor = doctor;
        this.day = day;
        this.from = from;
        this.to = to;
        this.maxBookingCount = maxBookingCount;
        this.fetchBookings();
    }

    @Override
    public String toString() {
        if (this.maxBookingCount == -1) {
            return String.format("%s, %s-%s Bookings: %s", this.day.toString(), this.from.toString(), this.to.toString(), this.bookings.size());
        }
        return String.format("%s %20s, %s-%s", this.doctor.getName(), this.day.toString(), this.from.toString(), this.to.toString());
    }

    public void fetchBookings() {
        BookingController bookings = new BookingController();
        this.bookings = bookings.fetchBookingsByScheduleId(this.id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public LocalTime getFrom() {
        return from;
    }

    public void setFrom(LocalTime from) {
        this.from = from;
    }

    public LocalTime getTo() {
        return to;
    }

    public void setTo(LocalTime to) {
        this.to = to;
    }

    public int getMaxBookingCount() {
        return maxBookingCount;
    }

    public void setMaxBookingCount(int maxBookingCount) {
        this.maxBookingCount = maxBookingCount;
    }

}
