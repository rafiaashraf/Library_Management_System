package com.example.my_lms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.ZoneId;

public class StudentSignUpController {


    @FXML
    public TextField tfName;
    public TextField tfFatherName;
    public TextField tfEmail;
    public TextField tfCourse;
    public PasswordField pfPassword;
    public PasswordField pfConfirmPassword;
    public DatePicker dpDob;
    public ImageView ivBack;

    Connection con = null;
    Statement stnt = null;

    public void ResetAction(ActionEvent actionEvent) {

        tfName.setText("");
        tfFatherName.setText("");
        dpDob.setValue(null);
        tfEmail.setText("");
        tfCourse.setText("");
        pfPassword.setText("");
        pfConfirmPassword.setText("");

    }

    public void SignUpAction(ActionEvent actionEvent) {

        Alert a = new Alert(Alert.AlertType.ERROR);
        if(tfName.getText().length()==0 || tfFatherName.getText().length()==0|| dpDob.getValue()==null || tfEmail.getText().length()==0 || tfCourse.getText().length()==0 || pfPassword.getText().length()==0 || pfConfirmPassword.getText().length()==0){

            a.setContentText("All fields are not filled");
            a.show();
            return;
        }

        if(!(pfPassword.getText().equals(pfConfirmPassword.getText()))){
            a.setContentText("Confirm Password does not match with the Password");
            a.show();
            return;
        }
        java.util.Date date = java.util.Date.from(dpDob.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        try {

            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();

            String sql = "INSERT INTO addstudent(name, fathername, dob, emailaddress, course, password)" + "Values('"+tfName.getText()+"','"+tfFatherName.getText() +"', '"+sqlDate+"', '"+tfEmail.getText()+"', '"+tfCourse.getText()+"', '" +pfPassword.getText()+"')";
            stnt.execute(sql);

            stnt.close();
            con.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Request sent to Admin for approval!....");
            alert.show();

            tfName.setText("");
            tfFatherName.setText("");
            dpDob.setValue(null);
            tfEmail.setText("");
            tfCourse.setText("");
            pfPassword.setText("");
            pfConfirmPassword.setText("");



        }
        catch (Exception e){
            e.printStackTrace();
        }




    }

    public void Back(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) ivBack.getScene().getWindow();
        stage.close();
        HelloApplication.SceneSwitch( "Studentlogin.fxml", "Student Login",stage );

    }
}
