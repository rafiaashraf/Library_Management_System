package com.example.my_lms;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;


public class ProfileController implements Initializable {
    public ImageView ivBack;
    public Label lblName;
    public Label lblFatherName;
    public Label lblDob;
    public Label lblEmail;
    public Label lblCourse;
    public Label lblId;


    Connection con = null;
    Statement stnt = null;


    public void ivBackAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) ivBack.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();

            String query = "SELECT * FROM student";
            ResultSet rs = stnt.executeQuery(query);

            while (rs.next()){
                if(rs.getInt("id") == HelloApplication.studID){
                    lblName.setText(rs.getString("name"));
                    lblId.setText(""+HelloApplication.studID);
                    lblFatherName.setText(rs.getString("fathername"));
                    lblDob.setText(""+rs.getDate("dob"));
                    lblEmail.setText(rs.getString("emailaddress"));
                    lblCourse.setText(rs.getString("course"));
                }
            }
            stnt.close();
            con.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

}
