package com.hol.folder.util;

import java.io.File;

import com.hol.folder.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FolderListAdapter extends BaseAdapter{
	File[] fileList = null;
	LayoutInflater infl = null;
	
	public FolderListAdapter(Context context, File[] fileList){
		infl = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.fileList = fileList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return (fileList == null) ? 0 : fileList.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return (fileList == null) ? null : fileList[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (fileList == null)
			return null;
		ViewHolder holder = null;
		if (convertView == null){
			convertView = infl.inflate(R.layout.file_list_item_layout, null);
			holder = new ViewHolder();
			holder.fileIcon = (ImageView) convertView.findViewById(R.id.file_item_icon);
			holder.fileName = (TextView) convertView.findViewById(R.id.file_item_name);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		File file = fileList[position];
		bindView(holder, file);
		
		return convertView;
	}
	
	private void bindView(ViewHolder holder, File file){
		if (file.isDirectory()){
			holder.fileIcon.setImageResource(R.drawable.ic_folder);
		}else{
			holder.fileIcon.setImageResource(R.drawable.ic_file);
		}
		holder.fileName.setText(file.getName());
	}
	
	public void changeDate(File[] fileList){
		this.fileList = fileList;
		notifyDataSetChanged();
	}

	final class ViewHolder{
		ImageView fileIcon;
		TextView fileName;
	}
}
