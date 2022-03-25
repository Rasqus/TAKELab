package view.backing;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */

import com.github.javafaker.Faker;
import static java.lang.Math.random;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author student
 */
@Named(value = "studentBean")
@RequestScoped
public class StudentBean {

    /**
     * Creates a new instance of StudentBean
     */
    public StudentBean() {
        
        ArrayList<String> student = new ArrayList<String>();
        student.add("Wojtek");
        student.add("Marciniak");
        student.add("5,00");
        students.add(student);
        
        Faker faker = new Faker();
        Random r = new Random();
        
        for (int i = 0; i < 10; i++)
        {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            ArrayList<String> stu = new ArrayList<>();
            stu.add(firstName);
            stu.add(lastName);
            double number = (2.0 + r.nextDouble() * (5.0 - 2.0));
//            stu.add(Double.toString(number));
            stu.add(df.format(number));
            students.add(stu);
        }
    }
    
    private ArrayList<ArrayList<String>> students = new ArrayList<>(); 
    
    private static final DecimalFormat df = new DecimalFormat("0.00");

    /**
     * @return the students
     */
    public ArrayList<ArrayList<String>> getStudents() {
        return students;
    }

    /**
     * @param students the students to set
     */
    public void setStudents(ArrayList<ArrayList<String>> students) {
        this.students = students;
    }
    
}
