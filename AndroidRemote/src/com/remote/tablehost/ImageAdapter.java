package com.remote.tablehost;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private ImageView images[]=null;
	private Context context=null;
	private int selectImage;
	public ImageAdapter(Context context,int imgs[],int width,int height,int selectImage){
		this.context=context;
		this.images=new ImageView[imgs.length];
		this.selectImage=selectImage;
		for(int x=0;x<imgs.length;x++){
			this.images[x]=new ImageView(context);//ʵ����ImageView����
			this.images[x].setLayoutParams(new GridView.LayoutParams(width,height));//���ô�С
			this.images[x].setAdjustViewBounds(true);//���ֿ�߱�
			this.images[x].setPadding(3, 3, 3, 3);//���ü��
			this.images[x].setImageResource(imgs[x]);//����ͼƬ
		}
	}
	public int getCount() {
		
		return this.images.length;//ͼƬ������
	}

	@Override
	public Object getItem(int position) {
		
		return this.images[position];//ͼƬ�Ķ���
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView view=null;
		if(convertView==null){
			view=this.images[position];
		}else{
			view=(ImageView)convertView;
		}
		return view;
	}
	//��ȡѡ�е�ͼƬ��ID
	public void ImageFource(int setId){
		for(int x=0;x<images.length;x++){
			if(x!=setId){
				this.images[x].setBackgroundResource(0);
			}
		}
		this.images[setId].setBackgroundResource(this.selectImage);
	}
	
}
