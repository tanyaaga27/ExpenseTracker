package com.UserOps;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;

@WebServlet("/AddExpenseServlet")
public class AddExpenseServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		PrintWriter out = res.getWriter();
		String title = req.getParameter("title");
		String amount = req.getParameter("amount");
		String category = req.getParameter("category");
		String stringdate = req.getParameter("date");
		//out.println("User input Date="+stringdate);
		Date date;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(stringdate);
			/*out.println("String to Date="+date);
			Timestamp.of(date);
			out.println("date to timestamp="+Timestamp.of(date));*/
			Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
			KeyFactory keyFactory = datastore.newKeyFactory().setKind("expense");
			Key key = keyFactory.newKey(title);
			Entity entity = Entity.newBuilder(key).set("title",title).set("amount",amount).set("category",category).set("expenseDate", Timestamp.of(date)).build();
			datastore.put(entity);
			out.println("Entry added Successfully!");
			out.println(Timestamp.of(date));
			
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			out.print("Some error occured!Please try Again!");
			e.printStackTrace();
		}
		
	
		
		
	}

}
