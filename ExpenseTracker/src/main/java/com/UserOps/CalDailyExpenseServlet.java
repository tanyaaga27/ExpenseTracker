package com.UserOps;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.Timestamp;


@WebServlet("/CalDailyExpenseServlet")
public class CalDailyExpenseServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		PrintWriter out = res.getWriter();
		String date = req.getParameter("date");
	    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	    Query<Entity> query = Query.newEntityQueryBuilder().setKind("expense").build();
	    QueryResults<Entity> results = datastore.run(query);
	    int sum = 0;
	    while(results.hasNext())
	    {
	    	Entity e = results.next();
	    	Timestamp t = e.getTimestamp("expenseDate");
	    	String dateString = t.toString();
	    	dateString = dateString.substring(0, 10);
	    	if(dateString.equals(date))
	    	{
	    		sum = Integer.parseInt(e.getString("amount")) + sum ;
	    	}
	    	
	    }
	    if(sum!=0)
	    {
	    	out.println("Total expense for the date :"+sum);
	    }
	    else
	    {
	    	out.print("No expenses were made on this date!");
	    }
	    
	    
		
	}

}
