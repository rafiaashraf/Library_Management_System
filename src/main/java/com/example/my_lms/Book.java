package com.example.my_lms;

import java.util.Date;

public class Book {

    public int ID, studID;
    public String bookName, authorName,type, status, studName;




    public Date arrivalDate, issueDate, dueDate, returnDate;

    public String getStatus() {
        return status;
    }

    public Book(int ID, String bookName, String authorName, Date arrivalDate, String type, String status) {
        this.ID = ID;
        this.bookName = bookName;
        this.authorName = authorName;
        this.type = type;
        this.arrivalDate = arrivalDate;
        this.status = status;
    }

    public Book(int ID, String bookName, int studID, String studName){
        this.ID = ID;
        this.bookName = bookName;
        this.studID = studID;
        this.studName = studName;
    }

    public Book(int ID, String bookName, Date issueDate, Date dueDate){
        this.ID = ID;
        this.bookName = bookName;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }
    public Book(int ID, String bookName, int studID, String studName, Date issueDate, Date dueDate){
        this.ID = ID;
        this.bookName = bookName;
        this.studID = studID;
        this.studName = studName;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }

    public Book(int ID, String bookName, int studID, String studName, Date issueDate, Date dueDate, Date returnDate){
        this.ID = ID;
        this.bookName = bookName;
        this.studID = studID;
        this.studName = studName;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }
    public int getID() {
        return ID;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getType() {
        return type;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public int getStudID() {
        return studID;
    }

    public String getStudName() {
        return studName;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

}
