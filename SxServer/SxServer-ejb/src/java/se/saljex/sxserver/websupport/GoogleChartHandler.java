/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.websupport;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import se.saljex.sxlibrary.SXUtil;

/**
 *
 * @author ulf
 */
public class GoogleChartHandler {
	private static final String simpleEncoding = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static final String URL = "http://chart.apis.google.com/chart";

	private ArrayList<SerieInfo> serier = new ArrayList();
	private ArrayList<String>	etiketter = new ArrayList();

	private int sizeX = 400;
	private int sizeY = 200;

	private String title =null;

	private Double scaleMax = null;

	private String type="lc";
	private String lineStyle="1,1,0";

	private int serieCn = 0;
	private static final String[] COLORS = {"FF0000","00FF00","0000FF","FFFF00","FF00FF","888800","008888","102030","302010"};

	public SerieInfo addSerie(String namn) {
		SerieInfo sf = new SerieInfo(namn,serieCn < COLORS.length ? COLORS[serieCn] : COLORS[0]);
		serier.add(sf);
		serieCn++;
		return sf;
	}
	
	public SerieInfo addSerie(String namn, double[] dArr) {
		SerieInfo sf = addSerie(namn);
		for (double d : dArr) {
			sf.addValue(d);
		}
		return sf;
	}

	public SerieInfo addSerie(String namn, int[] dArr) {
		SerieInfo sf = addSerie(namn);
		for (int d : dArr) {
			sf.addValue(new Double(d));
		}
		return sf;
	}

	/*
	public void addSerie(String[] namn, Double[][] dArr) {
		for (String n : String) {
		SerieInfo sf = addSerie(namn);
		for (Double d : tteddArr) {
			sf.addValue(d);
		}
	}
	*/
	public void setLinjeDiagram() {
		type = "lc";
	}

	public void setMeter() {
		type = "gom";
	}

	public void clearSerier() {
		serieCn = 0;
		serier = new ArrayList();
	}

	public void addEtikett(String s) {
		etiketter.add(s);
	}

	public void setTile(String title) { this.title = title; }

	public void setEtiketterJanToDec() {
		addEtikett("Jan");
		addEtikett("Feb");
		addEtikett("Mar");
		addEtikett("Apr");
		addEtikett("Maj");
		addEtikett("Jun");
		addEtikett("Jul");
		addEtikett("Aug");
		addEtikett("Sep");
		addEtikett("Okt");
		addEtikett("Nov");
		addEtikett("Dec");		
	}

	public void setScaleMax(Double scaleMax) {		this.scaleMax = scaleMax;	}

	public void setSize(int x, int y) {
		sizeX = x;
		sizeY = y;
	}

	public void setType(String s) {
		type = s;
	}

	public String getURL() {
		StringBuilder sb = new StringBuilder();
		sb.append(URL);
		sb.append("?");
		sb.append("chs=" + sizeX + "x" + sizeY);
		sb.append("&cht=" + type);
		sb.append("&chl=");
		for (String s : etiketter) {
			sb.append(s);
			sb.append("|");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("&chd=s:");
		sb.append(simpleEncode());

		sb.append("&chdl=");
		for (SerieInfo si : serier) {
			sb.append(si.getNamn());
			sb.append("|");
		}
		sb.deleteCharAt(sb.length()-1);

		sb.append("&chco=");
		for (SerieInfo si : serier) {
			sb.append(si.getColorString());
			sb.append(",");
		}
		sb.deleteCharAt(sb.length()-1);

		sb.append("&chls=");
		for (SerieInfo si : serier) {
			sb.append(si.getLineStyle());
			sb.append("|");
		}
		sb.deleteCharAt(sb.length()-1);

		// Sätt y-axel
		sb.append("&chxt=y");
		// Sätt skalan på y-axel
		Double maxValue;
		if (scaleMax==null) {
			maxValue = getMaxValue();
		} else {maxValue = scaleMax;}

		sb.append("&chxr=0,0," + Math.round(maxValue) + "," + Math.round(maxValue/5));
		try {
			if (!SXUtil.isEmpty(title)) sb.append("&chtt="+URLEncoder.encode(title, "UTF-8"));
		} catch (UnsupportedEncodingException e) { }
		return sb.toString();
	}

	private double getMaxValue() {
		Double ret = null;
		for (SerieInfo si : serier) {
			for (Double d : si.getValueArr()) {
				if (d!=null) {
					if (ret==null) ret=d;
					if (d>ret) ret = d;
				}
			}
		}
		if (ret==null) ret = 0.0;
		return ret;
	}

	private String simpleEncode() {
		StringBuilder sb = new StringBuilder();

		double maxValue;
		if (scaleMax==null) maxValue = getMaxValue(); else maxValue = scaleMax;
		char c;
		int p;
		for (SerieInfo si : serier) {
			for (Double d : si.getValueArr()) {
				if (d!=null && d >= 0) {
					p = (int)Math.round((simpleEncoding.length()-1) * d / maxValue);
					if (p>simpleEncoding.length()-1) c = '_'; else c=simpleEncoding.charAt(p);
					sb.append(c);
				}
				else {
					sb.append("_");
				}
			}
			sb.append(",");
		}
		if (sb.length()>0) sb.deleteCharAt(sb.length()-1);

		return sb.toString();
	}

	public class SerieInfo {
		private String namn = null;
		private ArrayList<Double> valueArr = new ArrayList();
		private String colorString;
		private String lineStyle;

		public SerieInfo setLineStyleDotted() {
			lineStyle = "1,2,4";
			return this;
		}
		public SerieInfo setLineStyleNormal() {
			lineStyle = "1,1,0";
			return this;
		}

		public SerieInfo setLineColor(String colorStr) {
			this.colorString = colorStr;
			return this;
		}
		public SerieInfo setLineColor(int color) {
			if (color >= 0 && color < COLORS.length)	setLineColor(COLORS[color]);
			return this;
		}

		public String getLineStyle() {			return lineStyle;		}

		public SerieInfo(String namn) {
			this.namn = namn;
			this.colorString = "FF0000";
		}
		public SerieInfo(String namn, String colorString) {
			this.namn = namn;
			this.colorString = colorString;
			setLineStyleNormal();
		}

		public String getColorString() {			return colorString;		}

		public String getNamn() {			return namn;		}
		public ArrayList<Double> getValueArr() {			return valueArr;		}

		public SerieInfo addValue(Double d) {
			valueArr.add(d);
			return this;
		}
		public SerieInfo addValue(Integer d) {
			valueArr.add( new Double(d));
			return this;
		}
	}
}
