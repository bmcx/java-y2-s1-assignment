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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import medcenter.framework.PersistUserData;
import medcenter.models.User;
import medcenter.models.types.CommonTypes.UserRole;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Chandima Bandara
 */
public class UserDataInFile implements PersistUserData {

    private final String filename = "user_data.json";

    @Override
    public void saveUserData(User user) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("id", user.getId());
        jsonObj.put("firstname", user.getFirstname());
        jsonObj.put("lastname", user.getLastname());
        jsonObj.put("username", user.getUsername());
        jsonObj.put("role", user.getRole());

        PrintWriter pw;
        try {
            pw = new PrintWriter(filename);
            pw.write(jsonObj.toJSONString());
            pw.flush();
            pw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UserDataInFile.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public User retriveUserData() {

        Object obj;
        try {
            try (FileReader f = new FileReader(filename)) {
                obj = new JSONParser().parse(f);
            }
        } catch (IOException ex) {
            return null;
        } catch (ParseException ex) {
            Logger.getLogger(UserDataInFile.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        JSONObject jsonObj = (JSONObject) obj;
        int id = (int) (long) jsonObj.get("id");
        String firstname = (String) jsonObj.get("firstname");
        String lastname = (String) jsonObj.get("lastname");
        String username = (String) jsonObj.get("username");
        UserRole role = UserRole.valueOf((String) jsonObj.get("role"));
        return new User(id, firstname, lastname, username, role);
    }

    @Override
    public void clearUserData() {

        try {
            System.out.println(Files.deleteIfExists(Paths.get(filename)));
        } catch (IOException ex) {
            Logger.getLogger(UserDataInFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
