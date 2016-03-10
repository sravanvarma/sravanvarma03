

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.util.JSON;

@WebServlet("/next")
public class NextServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NextServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Working");
		PrintWriter var=response.getWriter();
		var.println("Record Found in Database..!!");

		var.println("<br>");
		var.println("<br>");
		
		
		String s=request.getParameter("cityName");
		System.out.println("The entered name is: "+s);
		
		MongoClientURI uri = new MongoClientURI("mongodb://main:1234@ds037622.mlab.com:37622/places");
		MongoClient client = new MongoClient(uri);

		DB db = client.getDB(uri.getDatabase());
		DBCollection users = db.getCollection("placecollection");
		System.out.println(users.getName());
		if(s.length()>0)
		{
		BasicDBObject query = new BasicDBObject();
		query.put("city",s);
		DBCursor docs = users.find(query);
		System.out.println(docs.toArray().toString());
		JSONArray ja=new JSONArray(docs.toArray());
		JSONObject jO=new JSONObject();
		jO=(JSONObject)ja.getJSONObject(0);
		//response.getWriter().write(jO.getString("Description:"));
		var.println("City: "+jO.getString("city"));
		var.println("Temperature: "+jO.getDouble("Temperature"));
		var.println("Humidity: "+jO.getDouble("Humidity"));
		var.println("Description: "+jO.getString("Description"));
		var.println("Favorite: "+jO.getBoolean("Favorite"));
		}
		else
		{
			BasicDBObject query = new BasicDBObject();
			DBCursor docs = users.find();
			System.out.println(docs.toArray().toString());
			JSONArray ja=new JSONArray(docs.toArray());
			JSONObject jO=new JSONObject();
			for(int i=0;i<ja.length();i++)
			{
			jO=(JSONObject)ja.getJSONObject(i);
			//response.getWriter().write(jO.getString("Description:"));
			var.println("City: "+jO.getString("city"));
			var.println("Temperature: "+jO.getDouble("Temperature"));
			var.println("Humidity: "+jO.getDouble("Humidity"));
			var.println("Description: "+jO.getString("Description"));
			var.println("<br>");
			var.println("<br>");
			}
		}
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");
		doGet(request, response);
	}

}
