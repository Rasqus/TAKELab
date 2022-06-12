/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author mkwoj
 */
public class Main {
    
    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();

        String count =
            client.target("http://localhost:8080/Complaints/" +
                          "resources/complaints/count")
            .request(MediaType.TEXT_PLAIN)
            .get(String.class);
        
        System.out.println("Count: " + count + "\n");
        
        List<Complaint> allComplaints = 
            client.target("http://localhost:8080/Complaints/" + 
                          "resources/complaints/")
            .request(MediaType.APPLICATION_JSON)
            .get(Response.class)
            .readEntity(new GenericType<List<Complaint>>() {});
        
        System.out.println("A) All:\n" + allComplaints.toString() + "\n");

        //zakladam ze pierwsza jest zamknieta
        Complaint singleComplaint =
            client.target("http://localhost:8080/Complaints/" +
                          "resources/complaints/" + allComplaints.get(0).getId())
            .request(MediaType.APPLICATION_JSON)
            .get(Complaint.class);
                        
        System.out.println("B) Single: \n" + singleComplaint.toString() + "\n");

        singleComplaint.setStatus("closed");
        client.target("http://localhost:8080/Complaints/resources/complaints/" + 
            singleComplaint.getId().toString())
            .request()
            .put(Entity.entity(singleComplaint, MediaType.APPLICATION_JSON));

        List<Complaint> openComplaints =
            client.target("http://localhost:8080/Complaints/" +
                          "resources/complaints?status=open")
            .request(MediaType.APPLICATION_JSON)
            .get(Response.class)
            .readEntity(new GenericType<List<Complaint>>() {
            });
        
        System.out.println("D) OpenedComplaints: " + openComplaints.toString());

        client.close();

    }

}
