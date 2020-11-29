/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medcenter.models;

/**
 *
 * @author ThesunonG
 */
public class Doctormodel {

    String name;
    double contact;
    String password;
    String Email;
    String Specialization;

    //public Doctormodel(){}
    public Doctormodel(String name, double contact, String password, String Email, String Specialization) {
        this.name = name;
        this.contact = contact;
        this.password = password;
        this.Email = Email;
        this.Specialization = Specialization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getContact() {
        return contact;
    }

    public void setContact(double contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getSpecialization() {
        return Specialization;
    }

    public void setSpecialization(String Specialization) {
        this.Specialization = Specialization;
    }

    // class main
    public static void main(String[] agrs) {
         //Doctormodel obj= new Doctormodel();
        //obj.name("hhy");
        //System.out.println(obj.name());
    }
}
