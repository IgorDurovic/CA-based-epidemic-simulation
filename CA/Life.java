import processing.core.*;

import java.util.ArrayList;
import java.util.Random;

import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

public class Life extends PApplet 
{
	int millis = 100;
	int cellDim = 25;
	public static boolean bool = false;
	static double mortalityRate = 0.34620418848;
	int population;
	
	int susceptiblePopulation;
	int infectedPopulation;
	int recoveredPopulation;
	int deaths;

	Cell grid[][];
	Cell savedGrid[][];
	int lastTime = 0;
	int count = 0;

    final static XYSeries series1 = new XYSeries("Susceptible");
    final static XYSeries series2 = new XYSeries("Infected");
    final static XYSeries series3 = new XYSeries("Recovered");
    final static XYSeries series4 = new XYSeries("Deaths");
    final static XYSeriesCollection dataset = new XYSeriesCollection();
    
    final static XYDataset dataset1 = createDataset();
    final static JFreeChart chart = createChart(dataset1);
    final static ChartPanel chartPanel = new ChartPanel(chart);
	
	public void setup() 
	{
		size(1280, 720);

		grid = new Cell[width / cellDim][height / cellDim];
		savedGrid = new Cell[width / cellDim][height / cellDim];

		stroke(48);

		for (int a = 0; a < width / cellDim; a++) 
		{
			for (int b = 0; b < height / cellDim; b++) 
			{
				if(a == (width/cellDim)/2 && b == (height/cellDim)/2){
					bool = true;
					grid[a][b] = new Cell(50, 10, 10, 0.2);
				}
				else{
					grid[a][b] = new Cell(50, 10, 10, 0.2);
				}
			}
		}
		background(256, 256, 256);
	}

	public void draw() 
	{
		for (int a = 0; a < width / cellDim; a++) 
		{
			for (int b = 0; b < height / cellDim; b++) 
			{
				///////////
				fill(256, Math.round(256.0*grid[a][b].getHealthy()), 
						Math.round(256.0*grid[a][b].getHealthy()));
				rect(cellDim * a, cellDim * b, cellDim, cellDim);
				///////////
			}
		}

		if (millis() - lastTime > millis) 
		{
			
			cycle();
			lastTime = millis();
		}
	}

	public void keyPressed() 
	{

	}

	public void cycle() 
	{
		infectedPopulation = 0;
		susceptiblePopulation = 0;
		recoveredPopulation = 0;
		
		//movement phase
		ArrayList<Person> immigrants = null;
		for (int a = 0; a < width / cellDim; a++) 
		{
			for (int b = 0; b < height / cellDim; b++) 
			{
				immigrants = grid[a][b].Movement();
				while(!immigrants.isEmpty())
				{
					int x = a, y = b;
					Random rndobj = new Random();
					/*if(quot < 0){
						prob = 0-rndobj.nextDouble();
						if(prob > quot){
							yRand = -1;
						}
						else{
							if(prob > -0.5){
								yRand = 1;
							}
							else{
								yRand = -1;
							}
						}
					}
					else{
						prob = rndobj.nextDouble();
						if(prob < quot){
							yRand = 1;
						}
						else{
							if(prob < 0.5){
								yRand = 1;
							}
							else{
								yRand = -1;
							}
						}
					}*/
					int xRand = rndobj.nextInt(3) - 1;
					int yRand = rndobj.nextInt(3) - 1;
					
					if((x + xRand < 0) || (grid.length <= x + xRand))
					{
						xRand = -xRand;
					}

					if((y + yRand < 0) || (grid[0].length <= y + yRand))
					{
						yRand = -yRand;
					}
					x += xRand;
					y += yRand;
					
					if(grid[x][y].getCellPopulation() < grid[x][y].getCarryingCapacity())
					{
						grid[x][y].addPerson(immigrants.remove(immigrants.size()-1));
					}
				}
			}
		}
		
		//infection phase
		for(int a = 0; a < width/cellDim; a++)
		{
			for(int b = 0; b < height/cellDim; b++)
			{
				grid[a][b].infection();
			}
		}

		//additional processing
		//1. recovery
		//2. death
		//3. data graphing
		deaths = 0;
		for(int a = 0; a < width/cellDim; a++)
		{
			for(int b = 0; b < height/cellDim; b++)
			{
				grid[a][b].recovery();
				grid[a][b].death();
				susceptiblePopulation += grid[a][b].getSusceptiblePopulation();
				infectedPopulation += grid[a][b].getInfectedPopulation();
				recoveredPopulation += grid[a][b].getRecoveredPopulation();
				deaths = grid[a][b].getDeaths();
			}
		}
		graphing(susceptiblePopulation, infectedPopulation, recoveredPopulation, deaths);
		count++;
	}
	
	public void graphing(int sp, int ip, int rp, int d){
		dataset(sp, ip, rp, d);
	}
	
	private void dataset(int s, int i, int r, int d) {
        
        series1.add(count, s);

        series2.add(count, i);

        series3.add(count, r);
        
        series4.add(count, r);
                   
    }
	
	public static XYDataset createDataset(){
		final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        dataset.addSeries(series4);
		return dataset;
	}
	
	private static JFreeChart createChart(final XYDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "Epidemic Simulation",      // chart title
            "cycle",                      // x axis label
            "number of people",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);

        //final StandardLegend legend = (StandardLegend) chart.getLegend();
        //legend.setDisplaySeriesShapes(true);
        
        // get a reference to the plot for further customization
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        //plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.
                
        return chart;
        
    }

	static public void main(String[] passedArgs) 
	{
		String[] appletArgs = new String[] { "--full-screen",
				"--bgcolor=#666666", "--stop-color=#cccccc", "Life" };
		if (passedArgs != null) 
		{
			PApplet.main(concat(appletArgs, passedArgs));
		} 
		else 
		{
			PApplet.main(appletArgs);
		}
		Life obj = new Life();
		PropGraph pg = new PropGraph("simulation", obj.createChart(obj.createDataset()), chartPanel);
		pg.pack();
		RefineryUtilities.centerFrameOnScreen(pg);
        pg.setVisible(true);
	}
}
