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

import com.mysql.jdbc.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import medcenter.helpers.Database;
import medcenter.helpers.UserDataInFile;
import medcenter.models.User;
import medcenter.models.types.CommonTypes;
import medcenter.models.types.InvalidLoginData;

/**
 *
 * @author Chandima Bandara
 */
public class AuthController {

    public AuthController() {
    }

    Connection con = Database.createConnection();
    UserDataInFile localData = new UserDataInFile();

    public User loginUser(String username, String password) throws InvalidLoginData {
        try {
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User WHERE username='" + username + "' AND password='" + password + "';");
            if (rs.first()) {
                int id = rs.getInt("id");;
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                CommonTypes.UserRole role = CommonTypes.UserRole.valueOf(rs.getString("role"));
                User retrievedUser = new User(id, firstname, lastname, username, role);
                localData.saveUserData(retrievedUser);
                return retrievedUser;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new InvalidLoginData();
    }

    public void logout() {
        localData.clearUserData();
    }

}
