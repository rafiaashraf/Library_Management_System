package com.example.my_lms;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.sql.*;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;


public class AdminIssueBookController  implements Initializable{
    public ImageView ivBack;
    public TableView<Book> tIssueBook;
    public TableColumn<Book, Integer> tcBookId;
    public TableColumn<Book, String> tcBookName;
    public TableColumn<Book, Integer> tcStudentId;
    public TableColumn<Book, String> tcStudentName;
    public DatePicker dpIssueDate;
    public DatePicker dpDueDate;
    Connection con = null;
    Statement stnt = null;
    ObservableList<Book> list = FXCollections.observableArrayList();


    public void ivBackAction(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) ivBack.getScene().getWindow();
        stage.close();
        HelloApplication.SceneSwitch( "Admin.fxml", "Admin",stage);

    }

    public void IssueAction(ActionEvent actionEvent) {
        int id = tIssueBook.getSelectionModel().getSelectedItem().getID();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();

            if(dpIssueDate.getValue() == null || dpDueDate.getValue() == null){
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("Please enter issue and due date");
                a.show();
                return;
            }
            java.util.Date date1 = java.util.Date.from(dpIssueDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date sqlIssueDate = new java.sql.Date(date1.getTime());

            java.util.Date date2 = java.util.Date.from(dpDueDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date sqlDueDate = new java.sql.Date(date2.getTime());


            String sql = "DELETE FROM issuebook WHERE bookid =  " + id+"";
            stnt.execute(sql);


            String query = "UPDATE book SET status ='Issued', studentid = "+tIssueBook.getSelectionModel().getSelectedItem().getStudID()+", studentname = '"+tIssueBook.getSelectionModel().getSelectedItem().getStudName()+"', issuedate = '"+sqlIssueDate+"', duedate = '"+sqlDueDate+"'  WHERE id =   " + id+"";
            PreparedStatement ps = con.prepareStatement(query);
            ps.executeUpdate();

            dpIssueDate.setValue(null);
            dpDueDate.setValue(null);

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Book Issued to Student Successfully");
            a.show();


            showIssueBook();




        }
        catch (Exception e){
            e.printStackTrace();
        }



    }

    public void CancelAction(ActionEvent actionEvent) {
        int id = tIssueBook.getSelectionModel().getSelectedItem().getID();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();

            String sql = "DELETE FROM issuebook WHERE bookid =  " + id+"";
            stnt.execute(sql);

            String query = "UPDATE book SET status ='Issueable' WHERE id = " + id +"";
            PreparedStatement ps = con.prepareStatement(query);
            ps.executeUpdate();
            showIssueBook();

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Student Request to issue book is canceled");
            a.show();


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showIssueBook();

    }



    public void showIssueBook(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();

            String studName = null;
            String sql = "SELECT * " +" FROM issuebook";
            ResultSet rs = stnt.executeQuery(sql);
            tIssueBook.getItems().clear();
            list.clear();
            while(rs.next()) {

                Statement st = con.createStatement();
                String query = "SELECT id, name FROM student";
                ResultSet resultSet = st.executeQuery(query);
                while(resultSet.next()){
                    if(rs.getInt("studentid")==resultSet.getInt("id")){
                        studName = resultSet.getString("name");
                        break;
                    }
                }


                list.add(new Book(rs.getInt("bookid"), rs.getString("bookname"), rs.getInt("studentid"),  studName));

            }


            tIssueBook.setItems(list);
            tcBookId.setCellValueFactory(new PropertyValueFactory<>("ID"));
            tcBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
            tcStudentId.setCellValueFactory(new PropertyValueFactory<>("studID"));
            tcStudentName.setCellValueFactory(new PropertyValueFactory<>("studName"));


            stnt.close();
            con.close();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
