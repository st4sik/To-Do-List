package com.example.to_do_list;

import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity implements NewItemFragment.OnNewItemAddedListener,
LoaderManager.LoaderCallbacks<Cursor>{

	private ToDoItemAdapter aa;
	private  ArrayList<ToDoItem> todoItems;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		FragmentManager fm=getFragmentManager();
		ToDoListFragment toDoListFragment=(ToDoListFragment)fm.findFragmentById(R.id.ToDoListFragment);
		todoItems=new ArrayList<ToDoItem>();
		int resID=R.layout.todolist_item;
		aa=new ToDoItemAdapter(this,resID,todoItems);
		
		toDoListFragment.setListAdapter(aa);
		
		getLoaderManager().initLoader(0, null, this);
		
	}

	
	@Override
	protected void onResume()
	{
		super.onResume();
		getLoaderManager().restartLoader(0, null, this);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onNewItemAdded(String newItem)
	{
		ContentResolver cr=getContentResolver();
		ContentValues cv=new ContentValues();
		
		cv.put(ToDoContetnProvider.KEY_TASK, newItem);
		cr.insert(ToDoContetnProvider.CONTENT_URI, cv);
		getLoaderManager().restartLoader(0, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		CursorLoader loader=new CursorLoader(this, ToDoContetnProvider.CONTENT_URI, null, null, null, null);
		
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		int keyTaskIndex=data.getColumnIndexOrThrow(ToDoContetnProvider.KEY_TASK);
		todoItems.clear();
		while(data.moveToNext())
		{
			ToDoItem newItem=new ToDoItem(data.getString(keyTaskIndex));
			todoItems.add(newItem);
		}
		aa.notifyDataSetChanged();
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		
	}
}
