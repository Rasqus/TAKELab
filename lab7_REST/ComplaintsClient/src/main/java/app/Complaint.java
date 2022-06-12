/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

/**
 *
 * @author mkwoj
 */
public class Complaint {
    
    private Integer id;
    private String complaintDate;
    private String complaintText;
    private String author;
    private String status;

    public Complaint(Integer id, String complaintDate, String complaintText, String author, String status) {
        this.id = id;
        this.complaintDate = complaintDate;
        this.complaintText = complaintText;
        this.author = author;
        this.status = status;
    }

    public Complaint() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComplaintDate() {
        return complaintDate;
    }

    public void setComplaintDate(String complaintDate) {
        this.complaintDate = complaintDate;
    }

    public String getComplaintText() {
        return complaintText;
    }

    public void setComplaintText(String complaintText) {
        this.complaintText = complaintText;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Date = " + complaintDate 
                + ", Text = " + complaintText 
                + ", Author = " + author 
                + ", Status = " + status + "\n";
    }    
}
