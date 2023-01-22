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
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;


@WebServlet("/customRangeExpenseServlet")
public class customRangeExpenseServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		PrintWriter out = res.getWriter();
		String startDateString = req.getParameter("startdate");
		String endDateString = req.getParameter("enddate");
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateString);
			//out.println(startDate);
			endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateString);
			//out.println(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	    Query<Entity> query = Query.newEntityQueryBuilder().setKind("expense").build();
	    QueryResults<Entity> results = datastore.run(query);
	    
	    int sum = 0;
	    
	   
	    	
	    	while(results.hasNext())
	    	{
	    		Entity e = results.next();
		    	Timestamp t = e.getTimestamp("expenseDate");
		    	String dateString = t.toString();
		    	Date date = null;
		    	try {
					  date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
					  
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
		    	if(date.compareTo(startDate)>=0&&date.compareTo(endDate)<=0)
		    	{
		    		//out.println(date);
		    		//out.println(Integer.parseInt(e.getString("amount")));
		    		sum = sum + Integer.parseInt(e.getString("amount"));
		    	}
		    	
		    }
	    	
	    
	    
	    if(sum!=0)
	    {
	    	out.println("Total expense for this time period:"+sum);
	    }
	    else
	    {
	    	out.print("No expenses were made in this time period!");
	    }
		
		
		
		
	}
	
	private Date NextDayDate(Date date) {
		long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
		return new Date(date.getTime() + MILLIS_IN_A_DAY);
	}

}
