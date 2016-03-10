import static org.junit.Assert.*;

import org.junit.Test;
import org.json.*;
public class FirstCase {

	@Test
	public void test() {
		fail("Not yet implemented");
		MainServlet ms=new MainServlet();
		String s= ms.callURL("http://api.openweathermap.org/data/2.5/weather?q=kansas&appid=a2c1cdbc7a2e370118610010dfafbaac");
		System.out.println(s);
		assertEquals(s,null);
		JSONObject j1=ms.getDetails1(s);
		assertEquals(j1,null);
		JSONObject j2=ms.getDetails2(s);
		assertEquals(j1,null);
	}

}
