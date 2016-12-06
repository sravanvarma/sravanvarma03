

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/sample")
public class MainServlet extends HttpServlet {
	Double latit;
	Double longit;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("working");
		PrintWriter var=response.getWriter();
		String s=request.getParameter("firstname");
		
		var.println("Its working..!!");
		//var.println("");
		//var.println("");
		JSONObject x=new JSONObject();
		x=getDetails1(s);
	var.println("Location: "+x.get("city"));
		var.println("Latitude:"+x.get("lat"));
		var.println("Longitude"+x.get("lon"));
		x=getDetails2(s);
		var.println("Temperature:"+x.getDouble("Temp"));
		var.println(" Min Temperature: "+x.getDouble("Temp_min"));
		var.println("Max Temperature: "+x.getDouble("Temp_max"));
		var.println("Humidity: "+x.getDouble("Hum"));
		var.println("Description: "+x.get("desc"));
		Object img_url = "http://maps.googleapis.com/maps/api/staticmap?center="+latit.toString()+","+longit.toString()+"&zoom=6&size=800x400&sensor=false";	     
		var.println("<img src='"+img_url+"'>");
		doGet(request, response);
	}
	public static String callURL(String myURL) {
		System.out.println("Requeted URL:" + myURL);
		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		InputStreamReader in = null;
		try {
			URL url = new URL(myURL);
			urlConn = url.openConnection();
			if (urlConn != null)
				urlConn.setReadTimeout(60 * 1000);
			if (urlConn != null && urlConn.getInputStream() != null) {
				in = new InputStreamReader(urlConn.getInputStream(),
						Charset.defaultCharset());
				BufferedReader bufferedReader = new BufferedReader(in);
				if(bufferedReader!=null)
				{
					int cp;
					while ((cp = bufferedReader.read()) != -1) {
						sb.append((char) cp);
					}
					bufferedReader.close();
				}
			}
		in.close();
		} catch (Exception e) {
			throw new RuntimeException("Exception while calling URL:"+ myURL, e);
		} 
 

		
		return sb.toString();
				}
	public JSONObject getDetails1(String s)
	{

		 String getIRL="http://maps.googleapis.com/maps/api/geocode/json?address="+s+"&sensor=true";
		 //System.out.println();
		 JSONObject jsonObject=new JSONObject(callURL(getIRL));
			JSONArray jsonArray= jsonObject.getJSONArray("results");
			JSONObject loc=jsonArray.getJSONObject(0).getJSONObject("geometry");
			JSONObject location=loc.getJSONObject("location");
			Double lat=location.getDouble("lat");
			latit=lat;
			Double lon=location.getDouble("lng");
			longit=lon;
			System.out.println(lat.toString()+lon.toString());
		String cityname=jsonArray.getJSONObject(0).getString("formatted_address");
		JSONObject sample=new JSONObject();
		sample.put("city", cityname);
		sample.put("lat",lat);
		sample.put("lon", lon);
		//System.out.println(cityname);
		return sample;
	}
	public JSONObject getDetails2(String city)
	{
		String url="http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=a2c1cdbc7a2e370118610010dfafbaac";
		//System.out.println(callURL(url));
		JSONObject sample=new JSONObject(callURL(url));
		JSONArray weather=sample.getJSONArray("weather");
		String s=weather.getJSONObject(0).getString("description");
		JSONObject main=sample.getJSONObject("main");
		
		Double temp=main.getDouble("temp");
		Double temp_min=main.getDouble("temp_min");
		Double temp_max=main.getDouble("temp_max");
		Double humidity=main.getDouble("humidity");
		//System.out.println(temp.toString()+temp_min.toString()+temp_max.toString()+humidity.toString());
		JSONObject x=new JSONObject();
		x.put("desc", s);
		x.put("Temp", temp);
		x.put("Temp_min", temp_min);
		x.put("Temp_max", temp_max);
		x.put("Hum", humidity);
		System.out.println(x.toString());
		return x;
	}
}
