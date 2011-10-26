package com.hol.folder;

import java.io.File;

import android.app.ListActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hol.folder.util.FolderListAdapter;
import com.hol.folder.util.FolderUtils;

public class FolderExpActivity extends ListActivity implements OnItemClickListener{
	private static final String TAG = "FolderExp";
	
	private TextView txtCurrentFolder = null;
	private ProgressBar pgbLoading = null;
	private FolderListAdapter adapter = null;
	private File currentFolder = null;
	private Context thisContext = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        thisContext = this;
        findView();
        setListener();
        initFolder();
    }
    
    private void findView(){
    	txtCurrentFolder = (TextView) findViewById(R.id.main_current_folder);
    	pgbLoading = (ProgressBar) findViewById(R.id.main_loader);
    }
    
    private void setListener(){
    	getListView().setOnItemClickListener(this);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	// TODO Auto-generated method stub
    	if (event.getKeyCode() == KeyEvent.KEYCODE_BACK){
    		return disposeBackKey();
    	}
    	return super.onKeyDown(keyCode, event);
    }
    
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		File nextFile = (File) adapter.getItem(arg2);
		if (nextFile != null){
			disposeNextFile(nextFile);
		}

	}
    
    /**
     * 处理返回键
     */
    private boolean disposeBackKey(){
    	boolean notLastDir = true;
    	if (currentFolder.getAbsolutePath().toLowerCase().equals(FolderUtils.DIR_MNT)){
    		notLastDir = false;
    		this.finish();
    		Log.d(TAG, "is last folder");
    	}else{
    		disposeNextFile(currentFolder.getParentFile());
    		Log.d(TAG, "not last folder");
    	}
    	return notLastDir;
    }
    
    private void disposeNextFile(File nextFile){
    	if (nextFile.isDirectory()){
	    	FolderLoader loader = new FolderLoader();
	    	loader.execute(nextFile);
    	}else{
    		//TODO 不是文件夹，则执行文件
    	}
    }
    
    private void initFolder(){
    	System.out.println(FolderUtils.DIR_SD);
    	currentFolder = new File(FolderUtils.DIR_SD).getParentFile();
    	txtCurrentFolder.setText(currentFolder.getAbsolutePath());
    	disposeNextFile(currentFolder);
    }
    
    class FolderLoader extends AsyncTask<File, Void, File[]>{
    	
    	@Override
    	protected void onPreExecute() {
    		// TODO Auto-generated method stub
    		super.onPreExecute();
    		pgbLoading.setVisibility(View.VISIBLE);
    	}
    	
		@Override
		protected File[] doInBackground(File... params) {
			// TODO Auto-generated method stub
			File[] fileList = null;
			for (File folder : params){
				currentFolder = folder;
				fileList = FolderUtils.getCurrentDirFolders(currentFolder);
			}
			return fileList;
		}
    	
		@Override
		protected void onPostExecute(File[] result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (adapter == null){
				adapter = new FolderListAdapter(thisContext, result);
				setListAdapter(adapter);
			}else{
				adapter.changeDate(result);
			}
			txtCurrentFolder.setText(currentFolder.getAbsolutePath());
			pgbLoading.setVisibility(View.GONE);
		}
    }

}