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

import static medcenter.models.types.CommonTypes.UserRole.DOCTOR;

/**
 *
 * @author Chandima Bandara
 */
public class Doctor extends User {

    String title;
    String description;

    public Doctor(int id, String firstname, String lastname, String username, String title, String description) {
        super(id, firstname, lastname, username, DOCTOR);
        this.title = !title.isEmpty() ? title : "N/A";
        this.description = !description.isEmpty() ? description : "N/A";
    }

    @Override
    public String getName() {
        return String.format("Dr. %s %s", this.firstname, this.lastname);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
