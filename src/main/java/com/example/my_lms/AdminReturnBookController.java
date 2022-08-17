package com.example.my_lms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;
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
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;


public class AdminReturnBookController implements Initializable {
    public ImageView ivBack;
    public TableView<Book> tReturnTable;
    public TableColumn<Book, Integer> tcBookID;
    public TableColumn<Book, String> tcBookName;
    public TableColumn<Book, Integer> tcStudentID;
    public TableColumn<Book, String> tcStudentName;
    public TableColumn<Book, Date> tcIssueDate;
    public TableColumn<Book, Date> tcDueDate;
    public DatePicker dpReturnDate;


    ObservableList<Book> list = FXCollections.observableArrayList();


    Connection con = null;
    Statement stnt = null;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showReturnBooks();

    }


    public void showReturnBooks(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();

            list.clear();
            tReturnTable.getItems().clear();

            String query ="SELECT * FROM returnbook";
            ResultSet rs = stnt.executeQuery(query);
            while(rs.next()){
                list.add(new Book(rs.getInt("bookid"), rs.getString("bookname"), rs.getInt("studentid"), rs.getString("studentname"), rs.getDate("issuedate"), rs.getDate("duedate")));

            }
            tReturnTable.setItems(list);
            tcBookID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            tcBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
            tcStudentID.setCellValueFactory(new PropertyValueFactory<>("studID"));
            tcStudentName.setCellValueFactory(new PropertyValueFactory<>("studName"));
            tcIssueDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
            tcDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));


            stnt.close();
            con.close();

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }




    public void ReturnBookAction(ActionEvent actionEvent) {

        int id = tReturnTable.getSelectionModel().getSelectedItem().getID();
        if(dpReturnDate.getValue()==null){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Please Enter Return Date");
            a.show();
            return;
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();

            java.util.Date date = java.util.Date.from(dpReturnDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date returnDate = new java.sql.Date(date.getTime());

            String sql = "INSERT INTO returned(bookid, bookname, studentid, studentname, issuedate, duedate, returndate)" + "Values("+id+",'"+tReturnTable.getSelectionModel().getSelectedItem().getBookName() +"', "+tReturnTable.getSelectionModel().getSelectedItem().getStudID()+", '"+tReturnTable.getSelectionModel().getSelectedItem().getStudName()+"', '"+tReturnTable.getSelectionModel().getSelectedItem().getIssueDate()+"', '"+tReturnTable.getSelectionModel().getSelectedItem().getDueDate()+"', '" + returnDate +"' )";
            stnt.execute(sql);

            sql = "DELETE FROM returnbook WHERE bookid = "+ id;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();

            String query = "UPDATE book SET status ='Issueable', studentid = null, studentname = null, issuedate = null, duedate = null WHERE id = "+ id+"";
            ps = con.prepareStatement(query);
            ps.executeUpdate();

            dpReturnDate.setValue(null);

            showReturnBooks();
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Book Returned Successfully");
            a.show();

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void ivBackAction(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage)ivBack.getScene().getWindow();
        stage.close();
        HelloApplication.SceneSwitch( "Admin.fxml", "Admin",(Stage)ivBack.getScene().getWindow() );

    }
}
