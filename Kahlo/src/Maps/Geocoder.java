package Maps;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;


public class Geocoder {

	// URL prefix to the geocoder
	private static final String GEOCODER_REQUEST_PREFIX_FOR_XML = "http://maps.google.com/maps/api/geocode/xml";

	private static List<Float> lats = new ArrayList<Float>();
	private static List<Float> longits = new ArrayList<Float>();

	public static JLabel labelMap = new JLabel();

	public static void addPuntoControlMapa(Punto p) {
		lats.add(p.getLatitud());
		longits.add(p.getLongitud());
	}

	public static Image createImageMap() throws MalformedURLException,
			IOException {
		String params = "";
		int count = 0;
		for (Float f : lats) {
			params += "|" + f + "," + longits.get(count);
			count++;
		}
		URLConnection con = new URL(
				"http://maps.googleapis.com/maps/api/staticmap?size=10240x10240&sensor=true&markers=color:0x0000ff"
						+ params).openConnection();
		InputStream is = con.getInputStream();
		ByteBuffer bb = ByteBuffer.allocate(con.getContentLength());
		int readed = 0;
		byte[] b = new byte[1];
		while (readed < bb.capacity()) {
			readed += is.read(b);
			bb.put(b);
		}
		System.out.println(readed);
		is.close();
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image map = tk.createImage(bb.array());
		// tk.prepareImage(map, 400, 400, null);

		return map;
	}

	public static void clear() {
		lats.clear();
		longits.clear();
	}
}
