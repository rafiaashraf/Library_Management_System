package com.example.my_lms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;

public class ReturnBookController implements Initializable {
    public ImageView ivBack;
    public TableView<Book> tReturnBook;
    public TableColumn<Book, Integer> tcBookId;
    public TableColumn<Book, String> tcBookName;
    public TableColumn<Book, Date> tcIssueDate;
    public TableColumn<Book, Date> tcDueDate;

    String studName = null;


    ObservableList<Book> list = FXCollections.observableArrayList();


    Connection con = null;
    Statement stnt = null;


    public void ReturnAction(ActionEvent actionEvent) {
        int id = tReturnBook.getSelectionModel().getSelectedItem().getID();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();


            String sql = "INSERT INTO returnbook(bookid, bookname, studentid, studentname, issuedate, duedate)" + "Values("+id+",'"+tReturnBook.getSelectionModel().getSelectedItem().getBookName() +"', "+HelloApplication.studID+", '"+studName+"', '"+tReturnBook.getSelectionModel().getSelectedItem().getIssueDate()+"', '"+tReturnBook.getSelectionModel().getSelectedItem().getDueDate()+"')";
            stnt.execute(sql);

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Request sent to admin to return book");
            a.show();

            showReturnBook();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showReturnBook();

    }
    public void showReturnBook(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();

            list.clear();
            tReturnBook.getItems().clear();
            String query = "SELECT * FROM book";
            ResultSet rs = stnt.executeQuery(query);
            skip:
            while(rs.next()){
                if(rs.getInt("studentid")==HelloApplication.studID){
                    studName = rs.getString("studentname");
                    String sql ="SELECT bookid FROM returnbook";
                    Statement st = con.createStatement();
                    ResultSet resultSet = st.executeQuery(sql);
                    while (resultSet.next()){
                        if(resultSet.getInt("bookid") == rs.getInt("id")){
                            continue skip;
                        }
                    }
                    list.add(new Book(rs.getInt("id"), rs.getString("name"), rs.getDate("issuedate"), rs.getDate("duedate")));
                }
            }
            tReturnBook.setItems(list);
            tcBookId.setCellValueFactory(new PropertyValueFactory<>("ID"));
            tcBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
            tcIssueDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
            tcDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));


            stnt.close();
            con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }


    public void ivBackAction(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage)ivBack.getScene().getWindow();
        stage.close();
        HelloApplication.SceneSwitch( "Student.fxml", "Student",stage );
    }
}
