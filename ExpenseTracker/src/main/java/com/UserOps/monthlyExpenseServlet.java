package com.UserOps;



import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
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

@WebServlet("/monthlyExpenseServlet")
public class monthlyExpenseServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		 //LocalDate currentdate = LocalDate.now();
		 //Month currentMonth = currentdate.getMonth();
		 
		Calendar cal = Calendar.getInstance();
        int CalMonth = cal.get(Calendar.MONTH);
        PrintWriter out = res.getWriter();
       // out.println(CalMonth);
        CalMonth+=1;
        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        Query<Entity> query = Query.newEntityQueryBuilder().setKind("expense").build();
        QueryResults<Entity> results = datastore.run(query);
        int sum = 0;
        while(results.hasNext())
        {
        	Entity e = results.next();
        	Timestamp t = e.getTimestamp("expenseDate");
        	Date date = t.toDate();
        	//out.println(date);
        	DateFormat monthFormat =  new SimpleDateFormat("M");
        	String month = monthFormat.format(date);
    		if(CalMonth==Integer.parseInt(month))
    		{
    			sum = Integer.parseInt(e.getString("amount")) + sum ;
    		}
    	}
        
        if(sum==0)
		{
			out.print("No expenses made in this month!");
		}
		else
		{
			out.println("Total expense for the date :"+sum); 
		}
	}

}
