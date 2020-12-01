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
package medcenter.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import medcenter.controller.AuthController;
import medcenter.controller.BookingController;
import medcenter.controller.ScheduleController;
import medcenter.controller.UserController;
import medcenter.helpers.UserDataInFile;
import medcenter.models.Booking;
import medcenter.models.Doctor;
import medcenter.models.Schedule;
import medcenter.models.User;

/**
 *
 * @author Chandima Bandara
 */
public class StaffHome extends javax.swing.JFrame {

    private AuthController authController;
    private ListSelectionListener timeSlotsSelectionListener;
    private ListSelectionListener bookingsSelectionListener;
    private ScheduleController scheduleController;
    private DefaultListModel timeSlotsModel;
    private UserController userController;
    private ActionListener taskPerformer;
    DefaultTableModel tableModel;

    /**
     * Creates new form StaffHome
     */
    public StaffHome() {
        this.timeSlotsModel = new DefaultListModel();
        this.scheduleController = new ScheduleController();
        this.authController = new AuthController();
        this.userController = new UserController();

        this.taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnDeleteBooking.setEnabled(lstBookings.getSelectedIndex() != -1);
                if (lstTimeSlots.getSelectedIndex() == -1) {
                    jLabel5.setText("Select a slot to view bookings");
                } else {
                    Schedule selected = (Schedule) lstTimeSlots.getSelectedValue();
                    if (selected.getBookings().size() > 0) {
                        jLabel5.setText(selected.getBookings().size() + " Bookings available");
                    } else {
                        jLabel5.setText("There are no bookings yet");
                    }

                }
            }
        };

        initTimeSlotListListner();
        initComponents();
        setCurrentUser();
        drawTimeSlotList();
        initDoctorsData();

        Timer timer = new Timer(100, taskPerformer);
        timer.setRepeats(true);
        timer.start();
    }

    private void setCurrentUser() {
        User currentUser = new UserDataInFile().retriveUserData();
        lblUsername.setText("Logged in as " + currentUser.getName());
    }

    private void drawTimeSlotList() {

        List<Schedule> schedules = scheduleController.fetchAll();
        timeSlotsModel.clear();
        schedules.forEach((schedule) -> timeSlotsModel.addElement(schedule));

        lstTimeSlots.setModel(timeSlotsModel);
        ListCellRenderer renderer = new TimeSlotsListCellRenderer();
        lstTimeSlots.setCellRenderer(renderer);
        lstTimeSlots.addListSelectionListener(timeSlotsSelectionListener);

    }

    private void initTimeSlotListListner() {

        this.timeSlotsSelectionListener = (ListSelectionEvent listSelectionEvent) -> {
            boolean adjust = listSelectionEvent.getValueIsAdjusting();
            if (!adjust) {
                try {
                    JList list = (JList) listSelectionEvent.getSource();
                    Object selectionValue = list.getSelectedValue();
                    Schedule selected = (Schedule) selectionValue;
                    drawBookingsList(selected.getBookings());
                } catch (Exception Ex) {

                }
            }
        };

    }

    private void initDoctorsData() {
        String[] columnNames = {"Id", "Username", "Firstname", "Lastname", "Title", "Description"};
        tableModel = new DefaultTableModel(columnNames, 0);
        List<Doctor> doctors = this.userController.fetchAllDoctors();
        doctors.forEach((item) -> tableModel.addRow(getRow(item)));
        tblDoctors.setModel(tableModel);
        tblDoctors.putClientProperty("terminateEditOnFocusLost", true);
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    DefaultTableModel tableModel = (DefaultTableModel) ((TableModel) (e.getSource()));
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                    String data = String.valueOf(tableModel.getValueAt(tblDoctors.getSelectedRow(), tblDoctors.getSelectedColumn()));
                    userController.editDoctor(id, columnNames[column].toLowerCase(), data);
                }
            }
        });
    }

    private String[] getRow(Doctor doctor) {
        String[] data = {Integer.toString(doctor.getId()), doctor.getUsername(), doctor.getFirstname(), doctor.getLastname(), doctor.getTitle(), doctor.getDescription()};
        return data;
    }

    private void drawBookingsList(List<Booking> bookings) {
        DefaultListModel model = new DefaultListModel();

        bookings.forEach((booking) -> model.addElement(booking));

        lstBookings.setModel(model);
        ListCellRenderer renderer = new BookingsListCellRenderer();
        lstBookings.setCellRenderer(renderer);
        lstBookings.addListSelectionListener(bookingsSelectionListener);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstTimeSlots = new javax.swing.JList();
        jLabel4 = new javax.swing.JLabel();
        lstBookings = new javax.swing.JList();
        jLabel5 = new javax.swing.JLabel();
        btnAddBooking = new javax.swing.JButton();
        btnDeleteBooking = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        btnRefresh = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDoctors = new javax.swing.JTable(){
            public boolean isCellEditable(int row,int column){
                if(column == 0) return false;//the 4th column is not editable
                return true;
            }
        };
        jLabel2 = new javax.swing.JLabel();
        btnAddDoc = new javax.swing.JButton();
        btnRefreshTable = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MedCenter");
        setLocation(new java.awt.Point(0, 0));

        jPanel1.setBackground(javax.swing.UIManager.getDefaults().getColor("EditorPane.background"));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel1.setText("MedCenter");

        lblUsername.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUsername.setText("Firstname Lastname");

        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        jLabel3.setText("Management Application");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLogout, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblUsername, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(lblUsername)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLogout))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        lstTimeSlots.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstTimeSlots.setToolTipText("");
        jScrollPane1.setViewportView(lstTimeSlots);

        jLabel4.setText("Avaliable time slots");

        lstBookings.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstBookings.setToolTipText("");

        jLabel5.setText("Bookings");

        btnAddBooking.setText("Add");
        btnAddBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddBookingActionPerformed(evt);
            }
        });

        btnDeleteBooking.setText("Delete");
        btnDeleteBooking.setEnabled(false);
        btnDeleteBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteBookingActionPerformed(evt);
            }
        });

        jLabel6.setText("Actions");

        btnRefresh.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        btnRefresh.setText("Refresh Data");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefresh)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lstBookings, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnDeleteBooking, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAddBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(btnRefresh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnAddBooking)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDeleteBooking)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                    .addComponent(lstBookings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Booking", jPanel2);

        tblDoctors.setAutoCreateRowSorter(true);
        tblDoctors.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblDoctors.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tblDoctors);

        jLabel2.setText("*All the changes you make here will be updated in the database immediately.");

        btnAddDoc.setText("Add new");
        btnAddDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDocActionPerformed(evt);
            }
        });

        btnRefreshTable.setText("Refresh Data");
        btnRefreshTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshTableActionPerformed(evt);
            }
        });

        jButton3.setText("Delete");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(11, 11, 11)
                        .addComponent(btnRefreshTable)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                        .addComponent(btnAddDoc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(btnAddDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefreshTable, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Doctors", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        int dialogResult = JOptionPane.showConfirmDialog(null, "You will be logged out and the application will close!", "Warning", JOptionPane.OK_CANCEL_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            authController.logout();
            dispose();
            Login login = new Login();
            login.setLocationRelativeTo(null);
            login.setVisible(true);
        }
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnAddBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddBookingActionPerformed
        Schedule selected = (Schedule) lstTimeSlots.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(null, "Please select a time slot to add a booking", "Time slot not selected!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            AddBooking addBooking = new AddBooking(selected);
            addBooking.setLocationRelativeTo(null);
            addBooking.setVisible(true);
        }

    }//GEN-LAST:event_btnAddBookingActionPerformed

    private void btnDeleteBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteBookingActionPerformed
        BookingController bookingController = new BookingController();
        Booking selected = (Booking) lstBookings.getSelectedValue();
        if (selected != null) {
            int dialogResult = JOptionPane.showConfirmDialog(null, "This action cannot be undone!", "Are you sure", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                bookingController.deleteBooking(selected.getId());
                refresh();
            }
        }
    }//GEN-LAST:event_btnDeleteBookingActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        refresh();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnRefreshTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshTableActionPerformed
        refresh();
    }//GEN-LAST:event_btnRefreshTableActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int dialogResult = JOptionPane.showConfirmDialog(null, "This action cannot be undone!", "Are you sure", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            int row = tblDoctors.getSelectedRow();
            Object doc = tableModel.getValueAt(row, 0);
            userController.deleteDoctor(Integer.parseInt(doc.toString()));
            refresh();
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnAddDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDocActionPerformed
        NewDoctor newDoctor = new NewDoctor();
        newDoctor.setLocationRelativeTo(null);
        newDoctor.setVisible(true);
    }//GEN-LAST:event_btnAddDocActionPerformed
    void refresh() {
        DefaultListModel emptyModel = new DefaultListModel();
        initDoctorsData();
        drawTimeSlotList();
        lstBookings.clearSelection();
        lstBookings.setModel(emptyModel);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddBooking;
    private javax.swing.JButton btnAddDoc;
    private javax.swing.JButton btnDeleteBooking;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRefreshTable;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JList lstBookings;
    private javax.swing.JList lstTimeSlots;
    private javax.swing.JTable tblDoctors;
    // End of variables declaration//GEN-END:variables
}
