package com.example.my_lms;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class StudentLoginController {
    @FXML
    public Button btnLogin;
    public PasswordField pfPass;
    public TextField tfStudentId;
    public Button btnSignUp;
    @FXML
    private ImageView ivBack;

    Connection con = null;
    Statement stnt = null;

    public void LOGIN(ActionEvent actionEvent) {

        if(tfStudentId.getText().length()==0 || pfPass.getText().length()==0 ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("All fields are not filled!...");
            alert.show();
            return;
        }
        HelloApplication.studID=Integer.parseInt(tfStudentId.getText());
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();

            String sql = "SELECT id, password " +" FROM student";
            ResultSet rs = stnt.executeQuery(sql);
            boolean present = false;
            while(rs.next()) {
                if (rs.getInt("id")==Integer.parseInt(tfStudentId.getText()) && (rs.getString("password").equals(pfPass.getText()))) {
                    present = true;
                    break;
                }
            }
            if(present){

                tfStudentId.setText("");
                pfPass.setText("");

                HelloApplication.SceneSwitch( "Student.fxml", "Student",(Stage)btnLogin.getScene().getWindow() );

            }
            else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Incorrect Name or Password");
                a.show();
            }
            stnt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SignUp(ActionEvent actionEvent) throws IOException {
        HelloApplication.SceneSwitch( "StudentSignUp.fxml", "SignUp", (Stage)btnSignUp.getScene().getWindow() );
    }

    public void Reset(ActionEvent actionEvent) {
        tfStudentId.setText("");
        pfPass.setText("");
    }

    public void ivBackAction(MouseEvent mouseEvent) throws IOException {

        HelloApplication.SceneSwitch( "login.fxml", "Library Management System",(Stage)ivBack.getScene().getWindow() );
    }
}
