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

@WebServlet("/UpdateExpenseServlet")
public class UpdateExpenseServlet extends HttpServlet{
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		PrintWriter out = res.getWriter();         
		Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
		String title = req.getParameter("title");
		String amount = req.getParameter("amount");
		String category = req.getParameter("category");
		String date = req.getParameter("date");
		KeyFactory keyFactory = datastore.newKeyFactory().setKind("expense");
		Key key = keyFactory.newKey(title);
		Entity entity = datastore.get(key);
		if(entity!=null)
		{
			entity = Entity.newBuilder(key).set("title",title).set("amount",amount).set("category",category).set("date",date).build();
			datastore.update(entity);
			out.println("Entry Updated Successfully");
		}
		else
		{
			out.println("Entry Not Found!");
		}
	}

}
