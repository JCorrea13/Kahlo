package kahlo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.CombinedRangeXYPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.PlotState;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.experimental.chart.plot.CombinedXYPlot;

/**
 * @author JCORREA
 */
public class Grafica {

    private final String titulo;
    private JFreeChart grafica = null;
    private final XYSeriesCollection datos = new XYSeriesCollection();
    
    //Varialbles para graficar
    public static final int SUBPLOT_COUNT = 3;
    public TimeSeriesCollection[] datasetsTiempo;
    public XYSeriesCollection [] datasetsAltura;
    
    public double[] lastValue = new double[SUBPLOT_COUNT];

    /**
     * Esta clase tiene como objetio modelar todas las graficas
     * @param tg tipo grafica 
     * @param titulo titulo de la grafica
     */
    public Grafica(tipoGrafica tg, String titulo) {
        this.titulo = titulo;
        inicializarChart(tg);
    }

    /**
     * Este metodo iniciliza la grafica dependiendo el tipo 
     * que se pasa como parametro
     * @param tg 
     */
    private void inicializarChart(tipoGrafica tg) {

        if (tg == tipoGrafica.LinealTiempo) {
            configurarGrafica(titulo);
        
        }else if( tg == tipoGrafica.LinialTiempoAltura ){
            configurarGraficaTiempoAltura(titulo);
            
        } else if (tg == tipoGrafica.BarraEstado) {
            
            //configurarGraficaBarra(titulo);
        }

    }
    
    public void configurarGrafica(String tituloGrafica){
       final CombinedDomainXYPlot plot = new CombinedDomainXYPlot(new DateAxis("Tiempo"));
        this.datasetsTiempo = new TimeSeriesCollection[SUBPLOT_COUNT];

        this.lastValue[0] = 100.0;
        final TimeSeries series = new TimeSeries(titulo, Millisecond.class);
        this.datasetsTiempo[0] = new TimeSeriesCollection(series);
        final NumberAxis rangeAxis = new NumberAxis(titulo);
        rangeAxis.setAutoRangeIncludesZero(false);
        final XYPlot subplot = new XYPlot(
                this.datasetsTiempo[0], null, rangeAxis, new StandardXYItemRenderer()
        );
        subplot.setBackgroundPaint(Color.BLACK);
        subplot.setDomainGridlinePaint(Color.GREEN);
        subplot.setRangeGridlinePaint(Color.GREEN);
        plot.add(subplot);

        grafica = new JFreeChart(tituloGrafica, plot);
        grafica.setBorderPaint(Color.black);
        grafica.setBorderVisible(true);
        grafica.setBackgroundPaint(Color.white);

        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        final ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange(true);
        axis.setFixedAutoRange(60000.0);  // 60 seconds

        final JPanel content = new JPanel(new BorderLayout());

        final ChartPanel chartPanel = new ChartPanel(grafica);
        content.add(chartPanel);

        final JPanel buttonPanel = new JPanel(new FlowLayout());

        content.add(buttonPanel, BorderLayout.SOUTH);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 470));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }
    
    public void configurarGraficaTiempoAltura(String tituloGrafica){
       final CombinedRangeXYPlot plot = new CombinedRangeXYPlot(new NumberAxis(tituloGrafica));

        this.datasetsTiempo = new TimeSeriesCollection[SUBPLOT_COUNT];
        this.datasetsAltura = new  XYSeriesCollection[SUBPLOT_COUNT];
        
        this.lastValue[0] = 100.0;
        final TimeSeries seriesTiempo = new TimeSeries(titulo, Millisecond.class);
        final XYSeries seriesAltura = new XYSeries("Altura");
        this.datasetsTiempo[0] = new TimeSeriesCollection(seriesTiempo);
        //this.datasetsTiempo[1] = new TimeSeriesCollection(seriesAltura);
        this.datasetsAltura[0] = new XYSeriesCollection(seriesAltura);
        
        final DateAxis rangeAxis = new DateAxis("Tiempo");
        //rangeAxis.setAutoRangeIncludesZero(false);
        //final ValueAxis axis = plot.getRangeAxis();
        rangeAxis.setAutoRange(true);
        rangeAxis.setFixedAutoRange(2000);
        
        final NumberAxis rangeAxis1 = new NumberAxis("Altura");
        //rangeAxis1.setAutoRangeIncludesZero(false);
        rangeAxis1.setAutoRange(true);
        rangeAxis1.setFixedAutoRange(200);
        
        final XYPlot subplot = new XYPlot(
                this.datasetsTiempo[0], rangeAxis, null, new StandardXYItemRenderer()
        );
        final XYPlot subplot1 = new XYPlot(
                this.datasetsAltura[0], rangeAxis1, null, new StandardXYItemRenderer()
        );
        
        subplot.setBackgroundPaint(Color.BLACK);
        subplot.setDomainGridlinePaint(Color.GREEN);
        subplot.setRangeGridlinePaint(Color.GREEN);
        
        subplot1.setBackgroundPaint(Color.BLACK);
        subplot1.setDomainGridlinePaint(Color.GREEN);
        subplot1.setRangeGridlinePaint(Color.GREEN);
        
        
        plot.add(subplot);
        plot.add(subplot1);

        grafica = new JFreeChart(tituloGrafica, plot);
        grafica.setBorderPaint(Color.black);
        grafica.setBorderVisible(true);
        grafica.setBackgroundPaint(Color.white);

        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        

    }
    public void actualizarGraficaBarra(String tituloGrafica, DefaultCategoryDataset d){
        // Creando el Grafico
        grafica = ChartFactory.createBarChart
        ("General","Sensor", "Valor",
        d, PlotOrientation.HORIZONTAL, true,true, false);
        
        grafica.setBackgroundPaint(Color.BLACK);
    } 
    
    public void agregarGrafica(String id, double[] x, double[] y) {
        XYSeries s = new XYSeries(id);
        for (int i = 0; i < y.length; i++) {
            s.add(x[i], y[i]);
        }
        
            datos.addSeries(s);
    }
    
    public void limpiarGraficas(){
        datos.removeAllSeries();
    }

    public JPanel obtenerPanel() {
        ChartPanel c = null;
        try {
            
            c = new ChartPanel(grafica);
            c.setName(titulo);
            
        } catch (Exception ex) {
        }
        return c;
    }

}

enum tipoGrafica {

    LinealTiempo, BarraEstado, LinialTiempoAltura
}
