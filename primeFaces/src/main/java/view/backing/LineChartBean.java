/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package view.backing;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author mkwoj
 */
@Named(value = "lineChartBean")
@RequestScoped
public class LineChartBean {

    /**
     * Creates a new instance of LineChartBean
     */
    public LineChartBean() {
      lineModel = new LineChartModel();
      LineChartSeries sin = new LineChartSeries();
      LineChartSeries cos = new LineChartSeries();
      sin.setLabel("Sin");
      cos.setLabel("Cos");
      
      for (int i = 0; i <= 360; i+=10){
          sin.set(i, sin(Math.toRadians(i)));
      }
      for (int i = 0; i <= 360; i+=10){
          cos.set(i, cos(Math.toRadians(i)));
      }

      lineModel.addSeries(sin);
      lineModel.addSeries(cos);
      lineModel.setLegendPosition("e");
      lineModel.setZoom(true);
      Axis y = lineModel.getAxis(AxisType.Y);
      y.setMin(-1);
      y.setMax(1);
      y.setLabel("Value");

      Axis x = lineModel.getAxis(AxisType.X);
      x.setMin(0);
      x.setMax(360);
      x.setTickInterval("10");
      x.setLabel("Degree"); 
    }
    
    private LineChartModel lineModel;
    
    public LineChartModel getLineModel() {
      return lineModel;
    }
}
