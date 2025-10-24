import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class ChartGenerator {
    public static JFreeChart generateLineChart(String title) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Sample data points
        dataset.addValue(70, "Score", "Week 1");
        dataset.addValue(80, "Score", "Week 2");
        dataset.addValue(85, "Score", "Week 3");

        return ChartFactory.createLineChart(
            title,             // Chart title
            "Week",            // X-axis label
            "Score",           // Y-axis label
            dataset,           // Data
            PlotOrientation.VERTICAL,
            true,              // Include legend
            true,              // Tooltips
            false              // URLs
        );
    }
}