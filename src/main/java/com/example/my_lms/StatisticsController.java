package com.example.my_lms;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;
//prefHeight="697.0" prefWidth="1045.0"

public class StatisticsController implements Initializable {
    @FXML
    public ImageView ivBack;
    public TableView<Book> tIssueBook;
    public TableColumn<Book, Integer> tcBookId;
    public TableColumn<Book, String> tcBookName;
    public TableColumn<Book, Integer> tcStudentId;
    public TableColumn<Book, String> tcStudentName;
    public TableColumn<Book, Date> tcIssueDate;
    public TableColumn<Book, Date> tcDueDate;

    public TableView<Book> tReturnBook;
    public TableColumn<Book, Integer> tcBookId1;
    public TableColumn<Book, String> tcBookName1;
    public TableColumn<Book, Integer> tcStudentId1;
    public TableColumn<Book, String> tcStudentName1;
    public TableColumn<Book, Date> tcIssueDate1;
    public TableColumn<Book, Date> tcDueDate1;
    public TableColumn<Book, Date> tcReturnDate1;


    ObservableList<Book> issueList = FXCollections.observableArrayList();
    ObservableList<Book> returnList = FXCollections.observableArrayList();


    Connection con = null;
    Statement stnt = null;


    public void ivBackAction(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) ivBack.getScene().getWindow();
        stage.close();
        HelloApplication.SceneSwitch( "Admin.fxml", "Admin",(Stage)ivBack.getScene().getWindow() );

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ShowReturnedBook();
        ShowIssuedBook();

    }

    public void ShowReturnedBook(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();

            String query = "SELECT * FROM returned";
            ResultSet rs = stnt.executeQuery(query);
            while(rs.next()){
                issueList.add(new Book(rs.getInt("bookid"), rs.getString("bookname"), rs.getInt("studentid"), rs.getString("studentname"), rs.getDate("issuedate"), rs.getDate("duedate"), rs.getDate("returndate")));
            }
            tReturnBook.setItems(issueList);
            tcBookId1.setCellValueFactory(new PropertyValueFactory<>("ID"));
            tcBookName1.setCellValueFactory(new PropertyValueFactory<>("bookName"));
            tcStudentId1.setCellValueFactory(new PropertyValueFactory<>("studID"));
            tcStudentName1.setCellValueFactory(new PropertyValueFactory<>("studName"));
            tcIssueDate1.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
            tcDueDate1.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
            tcReturnDate1.setCellValueFactory(new PropertyValueFactory<>("returnDate"));


            stnt.close();
            con.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void ShowIssuedBook() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();

            String query = "SELECT * FROM book";
            ResultSet rs = stnt.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("status").equalsIgnoreCase("Issued")) {
                    returnList.add(new Book(rs.getInt("id"), rs.getString("name"), rs.getInt("studentid"), rs.getString("studentname"), rs.getDate("issuedate"), rs.getDate("duedate")));
                }
            }
            tIssueBook.setItems(returnList);
            tcBookId.setCellValueFactory(new PropertyValueFactory<>("ID"));
            tcBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
            tcStudentId.setCellValueFactory(new PropertyValueFactory<>("studID"));
            tcStudentName.setCellValueFactory(new PropertyValueFactory<>("studName"));
            tcIssueDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
            tcDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));


            stnt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    }
