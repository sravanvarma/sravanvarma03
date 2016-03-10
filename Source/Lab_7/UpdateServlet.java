

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
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/UpdateServlet")
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String s=request.getParameter("firstname");
		System.out.println("The entered name is: "+s);
		
		MongoClientURI uri = new MongoClientURI("mongodb://main:1234@ds037622.mlab.com:37622/places");
		MongoClient client = new MongoClient(uri);

		DB db = client.getDB(uri.getDatabase());
		DBCollection users = db.getCollection("placecollection");
		System.out.println(users.getName());
		
		BasicDBObject newDocument = new BasicDBObject();
	    newDocument.append("$set", new BasicDBObject().append("Favorite", false));
		System.out.println(newDocument.toString());
		BasicDBObject searchQuery = new BasicDBObject().append("city", s);
		System.out.println(searchQuery.toString());
	    users.update(searchQuery, newDocument);
		System.out.println("updated.............");
		PrintWriter var=response.getWriter();
		BasicDBObject query = new BasicDBObject();
		query.put("Favorite",false);
		DBCursor docs = users.find();
		System.out.println(docs.toArray().toString());
		JSONArray ja=new JSONArray(docs.toArray());
		JSONObject jO=new JSONObject();
		var.println("Cities which are not favorite:");
		var.println("<br>");
		var.println("<br>");
		for(int i=0;i<ja.length();i++)
		{
		jO=(JSONObject)ja.getJSONObject(i);
		if(!jO.getBoolean("Favorite"))
		{
		//response.getWriter().write(jO.getString("Description:"));
		var.println("City: "+jO.getString("city"));
		var.println("Temperature: "+jO.getDouble("Temperature"));
		var.println("Humidity: "+jO.getDouble("Humidity"));
		var.println("Description: "+jO.getString("Description"));
		var.println("Favorite: "+jO.getBoolean("Favorite"));
		var.println("<br>");
		var.println("<br>");
		}
		}
		
		doGet(request, response);
	}

}
