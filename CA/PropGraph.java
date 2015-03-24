import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;


public class PropGraph extends ApplicationFrame{
	
	public PropGraph(final String title, JFreeChart chart, ChartPanel cp){
		super(title);
		
		cp.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(cp);
	}
	
}
