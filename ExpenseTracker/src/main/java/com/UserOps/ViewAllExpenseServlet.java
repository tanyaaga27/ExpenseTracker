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

@WebServlet("/ViewAllExpenseServlet")
public class ViewAllExpenseServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		PrintWriter out = res.getWriter();
		Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
		Query<Entity> query = Query.newEntityQueryBuilder().setKind("expense").build();
		QueryResults<Entity> results = datastore.run(query);
		while(results.hasNext())
		{
			out.println("Expense :");
			Entity e = results.next();
			out.println(e.getString("title"));
			out.println(e.getString("amount"));
			out.println(e.getString("category"));
			out.println(e.getTimestamp("expenseDate").toString());
		}
		
		
	}

}
