/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package view.backing;

import java.util.Date;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author student
 */
@Named(value = "adderBean")
@RequestScoped
public class AdderBean {

    /**
     * Creates a new instance of AdderBean
     */
    public AdderBean() {
    }
    
    private int output;
    private int a;
    private int b;
    private Date curDate = new Date();
    private String growl;

    /**
     * @return the output
     */
    public int getOutput() {
        return output;
    }

    /**
     * @param output the output to set
     */
    public void setOutput(int output) {
        this.output = output;
    }
    
    public void update()
    {
        setOutput( getA() + getB());
        String growlInfo = a + " + " + b + " = " + output;
        FacesContext.getCurrentInstance().
              addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
              growlInfo, growlInfo));  
    }
    
//    public int AddNumbers()
//    {
//        return getA() + getB();
//    }

    /**
     * @return the a
     */
    public int getA() {
        return a;
    }

    /**
     * @param a the a to set
     */
    public void setA(int a) {
        this.a = a;
    }

    /**
     * @return the b
     */
    public int getB() {
        return b;
    }

    /**
     * @param b the b to set
     */
    public void setB(int b) {
        this.b = b;
    }

    /**
     * @return the curDate
     */
    public Date getCurDate() {
        return new Date();
    }

    /**
     * @param curDate the curDate to set
     */
    public void setCurDate(Date curDate) {
        this.curDate = curDate;
    }


    
    
    
}
