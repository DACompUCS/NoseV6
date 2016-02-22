package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import de.toolforge.googlechartwrapper.ConcentricPieChart;
import de.toolforge.googlechartwrapper.PieChart;
import de.toolforge.googlechartwrapper.data.ConcentricPieChartSlice;
import de.toolforge.googlechartwrapper.data.PieChartSlice;
import de.toolforge.googlechartwrapper.label.ChartTitle;
import de.toolforge.googlechartwrapper.style.ChartMargin;

/**
 * 
 * @author steffan
 *
 */
public class PieChartTest {

            
	PieChart pieChart = new PieChart(new Dimension(300,200));
	public PieChartTest() {
		// TODO Auto-generated constructor stub
		pieChart.addPieChartSlice(new PieChartSlice.PieChartSliceBuilder(20).build());
		pieChart.addPieChartSlice(new PieChartSlice.PieChartSliceBuilder(50).build());
		pieChart.addPieChartSlice(new PieChartSlice.PieChartSliceBuilder(30).build());
		
	}
     
	public static void main(String[] args) {
		PieChartTest t = new PieChartTest();
		System.out.println("pie");
	}
}