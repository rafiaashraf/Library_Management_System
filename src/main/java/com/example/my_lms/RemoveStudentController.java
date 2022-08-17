package com.example.my_lms;

import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;


public class RemoveStudentController implements Initializable {
    public TableView<Student> tRemoveStudent;
    public TableColumn<Student, Integer> tcId;
    public TableColumn<Student, String> tcName;
    public TableColumn<Student, String> tcFatherName;
    public TableColumn<Student, Date> tcDob;
    public TableColumn<Student, String> tcEmail;
    public TableColumn<Student, String> tcCourse;
    public TextField tfSearch;
    public ImageView ivBack;




    Connection con = null;
    Statement stnt = null;

    ObservableList<Student> list = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showStudData();

    }


    public void DeleteAction(ActionEvent actionEvent) {

        try {


            int id = tRemoveStudent.getSelectionModel().getSelectedItem().getId();

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();

            String query = "DELETE FROM student where ID = " + id + " ";
            stnt.execute(query);

            stnt.close();
            con.close();

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Student Data deleted Successfully");
            a.show();

            tRemoveStudent.getItems().clear();
            showStudData();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void RefreshAction(MouseEvent mouseEvent) {
        showStudData();
        tfSearch.setText("");

    }

    public void SearchAction(ActionEvent actionEvent) {

        try {
            int id = Integer.parseInt(tfSearch.getText());
            boolean found = false;

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();

            String query = "SELECT * FROM student";
            ResultSet rs = stnt.executeQuery(query);

            while (rs.next()) {
                if (rs.getInt("id") == id) {
                    found = true;
                    tRemoveStudent.getItems().clear();
                    list.clear();
                    list.add(new Student(rs.getInt("id"), rs.getString("name"), rs.getString("fathername"), rs.getDate("dob"), rs.getString("emailaddress"), rs.getString("course")));

                    tRemoveStudent.setItems(list);
                    tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
                    tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
                    tcFatherName.setCellValueFactory(new PropertyValueFactory<>("fatherName"));
                    tcDob.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
                    tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
                    tcCourse.setCellValueFactory(new PropertyValueFactory<>("course"));


                    stnt.close();
                    con.close();

                    break;
                }

            }
            if (!found) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("No such Student Record found");
                a.show();
            }
        }
        catch (NumberFormatException e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Enter only integer values");
            a.show();
        }
        catch (Exception e){

            e.printStackTrace();
        }

    }

    public void ivBackAction(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) ivBack.getScene().getWindow();
        stage.close();
        HelloApplication.SceneSwitch( "Admin.fxml", "Admin",(Stage)ivBack.getScene().getWindow() );

    }
    public void showStudData(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();

            String sql = "SELECT * " +" FROM student";
            ResultSet rs = stnt.executeQuery(sql);

            tRemoveStudent.getItems().clear();
            list.clear();
            while(rs.next()) {

                list.add(new Student(rs.getInt("id"), rs.getString("name"), rs.getString("fathername"),  rs.getDate("dob"), rs.getString("emailaddress"), rs.getString("course")));

            }

            tRemoveStudent.setItems(list);
            tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
            tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tcFatherName.setCellValueFactory(new PropertyValueFactory<>("fatherName"));
            tcDob.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
            tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            tcCourse.setCellValueFactory(new PropertyValueFactory<>("course"));

            stnt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
