package com.example.my_lms;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.util.Date;

public class Student {

    int id;
    String name, fatherName, email, course, password;
    public Date dateOfBirth;


    public Student(int id, String name, String fatherName,Date dateOfBirth, String email, String course) {
        this.id = id;
        this.name = name;
        this.fatherName = fatherName;
        this.email = email;
        this.course = course;
        //this.password = password;
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getEmail() {
        return email;
    }

    public String getCourse() {
        return course;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

}
