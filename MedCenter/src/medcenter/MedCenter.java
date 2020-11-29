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
package medcenter;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.JOptionPane;
import medcenter.helpers.UserDataInFile;
import medcenter.models.User;
import medcenter.views.DoctorHome;
import medcenter.views.Login;
import medcenter.views.StaffHome;
import medcenter.views.StudentHome;

/**
 *
 * @author Chandima Bandara
 */
public class MedCenter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FlatDarkLaf.install();
        try {
            User currentUser = new UserDataInFile().retriveUserData();
            switch (currentUser.getRole()) {
                case "DOCTOR":
                    DoctorHome doctorHome = new DoctorHome();
                    doctorHome.setLocationRelativeTo(null);
                    doctorHome.setVisible(true);
                    break;

                case "STUDENT":
                    StudentHome studentHome = new StudentHome();
                    studentHome.setLocationRelativeTo(null);
                    studentHome.setVisible(true);
                    break;

                case "STAFF":
                    StaffHome staffHome = new StaffHome();
                    staffHome.setLocationRelativeTo(null);
                    staffHome.setVisible(true);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "There might be an error,Please restart the application", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception Ex) {
            Login login = new Login();
            login.setLocationRelativeTo(null);
            login.setVisible(true);
        }

    }

}
