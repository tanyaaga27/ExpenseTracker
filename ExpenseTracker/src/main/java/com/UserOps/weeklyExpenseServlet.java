package com.UserOps;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

@WebServlet("/weekly")
public class weeklyExpenseServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		PrintWriter out = res.getWriter();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int dayOfWeekNum = cal.get(Calendar.DAY_OF_WEEK);
		//out.println("Today's day" + dayOfWeekNum);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	    Date todaydate = new Date(); 
	    String todaydateString = formatter.format(todaydate);
	    //out.println(todaydateString);
	    
	    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	    Query<Entity> query = Query.newEntityQueryBuilder().setKind("expense").build();
	    QueryResults<Entity> results = datastore.run(query);
	    
	    int sum = 0;
	    
	    if(dayOfWeekNum==1)
	    {
	    	dayOfWeekNum=7;
	    }
	    while(dayOfWeekNum>1)
	    {
	    	while(results.hasNext())
	    	{
	    		Entity e = results.next();
		    	Timestamp t = e.getTimestamp("expenseDate");
		    	String dateString = t.toString();
		    	dateString = dateString.substring(0, 10);
		    	if(todaydateString.equals(dateString))
		    	{
		    		sum=sum+Integer.parseInt(e.getString("amount"));
		    	}
		    	
		    }
	    	
	    	
	    	todaydate = findPrevDay(todaydate);
	    	todaydateString = formatter.format(todaydate);
	    	
	    	dayOfWeekNum--;

	    }
	    if(sum!=0)
	    {
	    	out.println("Total expense for the week:"+sum);
	    }
	    else
	    {
	    	out.print("No expenses were made in this week!");
	    }
	    
		/*Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	    Query<Entity> query = Query.newEntityQueryBuilder().setKind("expense").build();
	    QueryResults<Entity> results = datastore.run(query);
	   // int sum = 0;
	    if(dayOfWeekNum==1)
	    {
	    	dayOfWeekNum=7;
	    }
		while(dayOfWeekNum>1)
		{
			while(results.hasNext())
			{
				Entity e = results.next();
		    	Timestamp t = e.getTimestamp("expenseDate");
		    	Date date = t.toDate();
		    	
			}
			dayOfWeekNum--;
	    	out.println("Prev date :: " + findPrevDay(date));
		}*/
	}

	private Date findPrevDay(Date date) {
		long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
		return new Date(date.getTime() - MILLIS_IN_A_DAY);
		
	}

}
