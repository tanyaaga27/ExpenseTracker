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
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Key;

@WebServlet("/DeleteExpenseServlet")
public class DeleteExpenseServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		PrintWriter out = res.getWriter();
		Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
		String title = req.getParameter("title");
		KeyFactory keyFactory = datastore.newKeyFactory().setKind("expense");
		Key key = keyFactory.newKey(title);
		Entity entity = datastore.get(key);
		if(entity!=null)
		{
			datastore.delete(key);
			out.println("Entry Deleted Successfully!");
		}
		else
		{
			out.println("Entry does not exist!");
		}
		
	}



}
