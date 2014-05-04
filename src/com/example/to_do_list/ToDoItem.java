package com.example.to_do_list;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ToDoItem {

	String task;
	Date created;
	
	public String getTask()
	{
		return task;
	}
	
	public Date getCreated()
	{
		return created;
		
	}
	
	public ToDoItem(String _task)
	{
		this(_task,new Date(java.lang.System.currentTimeMillis()));
		
	}
	public ToDoItem(String _task, Date _date)
	{
		task=_task;
		created=_date;
	}
	@Override
	public String toString()
	{
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yy");
		String dateString=sdf.format(created);
		return "("+dateString+")"+task;
	}
}
