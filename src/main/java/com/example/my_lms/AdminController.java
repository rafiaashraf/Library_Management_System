package com.example.my_lms;

import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class AdminController implements Initializable {

    public ImageView ivBack, ivBook, ivAddStudent, ivRemoveStudent, ivIssueBook, ivReturnBook, ivStatistics;

    public Button btnLogOut;
    public Button btnChangePass;
    public Label lblAdminName;
    public Label lblAddStudentNotification;
    public Label lblIssueBookNotification;
    public Label lblReturnBookNotification;


    Connection con = null;
    Statement stnt = null;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();

            //counts and display number of request of new student sign up
            String query = "select count(*) from addstudent";
            ResultSet rs = stnt.executeQuery(query);
            rs.next();
            int count  = rs.getInt(1);
            if(count!=0) {
                lblAddStudentNotification.setText("" + count);
            }

            //counts aand display the number of issue book requests
            query = "select count(*) from issuebook";
            rs = stnt.executeQuery(query);
            rs.next();
            count  = rs.getInt(1);
            if(count!=0) {
                lblIssueBookNotification.setText("" + count);
            }

            //counts and display return book requests
            query = "select count(*) from returnbook";
            rs = stnt.executeQuery(query);
            rs.next();
            count  = rs.getInt(1);
            if(count!=0) {
                lblReturnBookNotification.setText("" + count);
            }


            //displays admin name
            String sql = "SELECT username" +" FROM admin";
            rs = stnt.executeQuery(sql);

            while(rs.next()) {
                lblAdminName.setText("            "+rs.getString("username"));
            }

            stnt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }







    public void Books(MouseEvent mouseEvent) throws IOException {
        HelloApplication.SceneSwitch( "BookList.fxml", "Books",(Stage)ivBook.getScene().getWindow() );

    }

    public void ChangePasswordAction(ActionEvent actionEvent) throws IOException {
        HelloApplication.SceneSwitch( "AdminPassword.fxml", "Change Admin Password",(Stage)btnChangePass.getScene().getWindow() );

    }

    public void LogoutAction(ActionEvent actionEvent) throws IOException {
        HelloApplication.SceneSwitch( "login.fxml", "Library Management System",(Stage)btnLogOut.getScene().getWindow() );

    }

    public void IssueBook(MouseEvent mouseEvent) throws IOException {
        HelloApplication.SceneSwitch( "AdminIssueBook.fxml", "Issue Book",(Stage)ivIssueBook.getScene().getWindow() );

    }

    public void Statistics(MouseEvent mouseEvent) throws IOException {
        HelloApplication.SceneSwitch( "Statistics.fxml", "Statistics",(Stage)ivStatistics.getScene().getWindow() );

    }

    public void ReturnBook(MouseEvent mouseEvent) throws IOException {
        HelloApplication.SceneSwitch( "AdminReturnBook.fxml", "Return Book",(Stage)ivReturnBook.getScene().getWindow() );

    }

    public void AddStudent(MouseEvent mouseEvent) throws IOException {
        HelloApplication.SceneSwitch( "AddStudent.fxml", "Add Student",(Stage)ivAddStudent.getScene().getWindow() );

    }

   public void RemoveStudent(MouseEvent mouseEvent) throws IOException {
        HelloApplication.SceneSwitch( "RemoveStudent.fxml", "Remove Student",(Stage)ivRemoveStudent.getScene().getWindow() );


    }

}
