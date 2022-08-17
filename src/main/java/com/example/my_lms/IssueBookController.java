package com.example.my_lms;

import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;


public class IssueBookController  implements Initializable{

    public TableView<Book> tIssueBook;
    public TableColumn<Book, Integer> tcBookId;
    public TableColumn<Book, String> tcBookName;
    public TableColumn<Book, String> tcAuthorName;
    public TableColumn<Book, Date> tcArrivalDate;
    public TableColumn<Book, String> tcType;
    public TableColumn<Book, String> tcStatus;
    public ImageView ivBack;
    public TextField tfSearch;

    ObservableList<Book> list = FXCollections.observableArrayList();


    Connection con = null;
    Statement stnt = null;

   @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showBookTable();


    }



    public void showBookTable(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();

            String sql = "SELECT * " +" FROM book";
            ResultSet rs = stnt.executeQuery(sql);
            tIssueBook.getItems().clear();
            list.clear();
            while(rs.next()) {

                if(rs.getString("type").equalsIgnoreCase("Fresh Arrival")){
                    if(rs.getString("status").equalsIgnoreCase("Requested") || rs.getString("status").equalsIgnoreCase("Issued")){

                    }
                    else {
                        java.sql.Date date = rs.getDate("arrivaldate");
                        java.util.Date current = new Date();
                        java.sql.Date currentDate = new java.sql.Date(current.getTime());
                        long diff = currentDate.getTime() - date.getTime();

                        BigInteger month = new BigInteger("5259600000");

                        if (diff > month.longValue()) {
                            String query = "Update book SET status = 'Issueable' WHERE id = " + rs.getInt("id") + " ";
                            PreparedStatement preparedStatement = con.prepareStatement(query);
                            preparedStatement.executeUpdate();

                        }
                    }

                }

                list.add(new Book(rs.getInt("id"), rs.getString("name"), rs.getString("authorname"),  rs.getDate("arrivalDate"), rs.getString("type"), rs.getString("status")));

            }


            tIssueBook.setItems(list);
            tcBookId.setCellValueFactory(new PropertyValueFactory<>("ID"));
            tcBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
            tcAuthorName.setCellValueFactory(new PropertyValueFactory<>("authorName"));
            tcArrivalDate.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
            tcType.setCellValueFactory(new PropertyValueFactory<>("type"));
            tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

            stnt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void IssueBookAction(ActionEvent actionEvent) {

        int id = tIssueBook.getSelectionModel().getSelectedItem().getID();
        java.util.Date d = new Date();
        java.sql.Date sqlDate = new java.sql.Date(d.getTime());
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();




            String query = "SELECT * FROM book";
            ResultSet rs = stnt.executeQuery(query);

            while(rs.next()){
                if(rs.getInt("id") == id){
                    if(rs.getString("status").equalsIgnoreCase("Issueable")){
                        //String s = "INSERT INTO book(studentid, issuedate)"+ "VALUES('"+HelloApplication.studID+"', '" + sqlDate+"')";
                        String s = "INSERT INTO issuebook(bookid, bookname, studentid)" + "Values('" + rs.getInt("id")+"', '" + rs.getString("name")+"', '"+HelloApplication.studID+"')";
                        Statement st = con.createStatement();
                        st.execute(s);

                        String update = "UPDATE book SET status = 'Requested' WHERE id = "+ id+"";
                        PreparedStatement ps = con.prepareStatement(update);
                        ps.executeUpdate();
                        showBookTable();

                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setContentText("Request sent to admin to issue book");
                        a.show();
                    }
                    else if(rs.getString("status").equalsIgnoreCase("Not Issueable")){
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setContentText("This book can not be issueable to the student");
                        a.show();
                    }
                    else if(rs.getString("status").equalsIgnoreCase("Issued")){
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setContentText("This book is already issued by another student");
                        a.show();
                    }
                    else if(rs.getString("status").equalsIgnoreCase("Requested")){
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setContentText("This book is already requested to issue");
                        a.show();
                    }
                    else{
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setContentText("This book can be issued only after 2 months of arrival");
                        a.show();
                    }
                }
            }
            stnt.close();
            con.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }



    public void SearchAction(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(tfSearch.getText());
            boolean found = false;

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();

            String query = "SELECT * FROM book";
            ResultSet rs = stnt.executeQuery(query);

            while (rs.next()) {
                if (rs.getInt("id") == id) {
                    found = true;
                    tIssueBook.getItems().clear();
                    list.clear();
                    list.add(new Book(rs.getInt("id"), rs.getString("name"), rs.getString("authorname"), rs.getDate("arrivaldate"), rs.getString("type"), rs.getString("status")));


                    tcBookId.setCellValueFactory(new PropertyValueFactory<>("ID"));
                    tcBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
                    tcAuthorName.setCellValueFactory(new PropertyValueFactory<>("authorName"));
                    tcArrivalDate.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
                    tcType.setCellValueFactory(new PropertyValueFactory<>("type"));
                    tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
                    tIssueBook.setItems(list);

                    stnt.close();
                    con.close();

                    break;
                }

            }
            if (!found) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("No such Book Record found");
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

    public void RefreshAction(MouseEvent mouseEvent) {

        showBookTable();
        tfSearch.setText("");

    }

    public void ivBackAction(MouseEvent mouseEvent) throws IOException {

        Stage stage = (Stage) ivBack.getScene().getWindow();
        stage.close();
        HelloApplication.SceneSwitch( "Student.fxml", "Student",stage );
    }
}
