package com.remote.start;

import java.util.ArrayList;
import com.remote.activity.R;
import com.remote.tablehost.MainGroupActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class GuideViewDemoActivity extends Activity {
    private ViewPager viewPager;  
    private ArrayList<View> pageViews;  
    private ViewGroup main, group;  
    private ImageView imageView;  
    private ImageView[] imageViews; 
	private Button startBt;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = getLayoutInflater();  
        pageViews = new ArrayList<View>();  
        pageViews.add(inflater.inflate(R.layout.item01, null));  
        pageViews.add(inflater.inflate(R.layout.item02, null));  
        pageViews.add(inflater.inflate(R.layout.item03, null));  
        View v4=inflater.inflate(R.layout.item04, null,true);
        pageViews.add(v4);  
        startBt = (Button) v4.findViewById(R.id.startBtn);
        startBt.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				startbutton();
				
			}
        	
        });
        imageViews = new ImageView[pageViews.size()];  
        main = (ViewGroup)inflater.inflate(R.layout.start_main, null);   
        group = (ViewGroup)main.findViewById(R.id.viewGroup);  
        viewPager = (ViewPager)main.findViewById(R.id.guidePages);  
        for (int i = 0; i < pageViews.size(); i++) {  
            imageView = new ImageView(GuideViewDemoActivity.this);  
            imageView.setLayoutParams(new LayoutParams(20,20));  
            imageView.setPadding(20, 0, 20, 0);  
            imageViews[i] = imageView;  
            if (i == 0) {
            } else {  
                imageViews[i].setBackgroundResource(R.drawable.page_indicator);  
            }  
            group.addView(imageViews[i]);  
        }  
        setContentView(main);  
        viewPager.setAdapter(new GuidePageAdapter(pageViews));  
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());  
        
    }
    
    private void startbutton() {  
      	Intent intent = new Intent();
		intent.setClass(GuideViewDemoActivity.this,MainGroupActivity.class);
		startActivity(intent);
		this.finish();
      }
   
    
    
    class GuidePageChangeListener implements OnPageChangeListener {  
        public void onPageScrollStateChanged(int arg0) {  
  
        }   
        public void onPageScrolled(int arg0, float arg1, int arg2) {  
  
        }  
        public void onPageSelected(int arg0) {  
            for (int i = 0; i < imageViews.length; i++) {  
                imageViews[arg0]  
                        .setBackgroundResource(R.drawable.page_indicator_focused);  
                if (arg0 != i) {  
                    imageViews[i]  
                            .setBackgroundResource(R.drawable.page_indicator);  
                }  
            }
        }  
    } 
}