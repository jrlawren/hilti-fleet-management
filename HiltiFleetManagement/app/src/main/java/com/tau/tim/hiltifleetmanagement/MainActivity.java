package com.tau.tim.hiltifleetmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tau.tim.hiltifleetmanagement.Adapters.ImagePagerAdapter;
import com.tau.tim.hiltifleetmanagement.ProjectManagement.ProjectManagementMainActivity;
import com.tau.tim.hiltifleetmanagement.RepairServices.LoginActivity;
import com.tau.tim.hiltifleetmanagement.RepairServices.RepairServiceActivity;
import com.tau.tim.hiltifleetmanagement.RotatingBanner.AutoScrollViewPager;
import com.tau.tim.hiltifleetmanagement.RotatingBanner.ListUtils;
import com.tau.tim.hiltifleetmanagement.ToolManagement.ToolManagementActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //DBHelper mydb;
    private AutoScrollViewPager viewPager;
    private TextView indexText;
    private Button innerViewPagerDemo;
    private ListView listView;
    private List<Integer> imageIdList;
    private int[] images = { R.drawable.project_tran_icon, R.drawable.tool_trans_icon, R.drawable.repair_trans_icon, R.drawable.project_tran_icon};
    private String[] names = { "Project Management", "Tool Management", "Repair Services", "Logout"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        viewPager = (AutoScrollViewPager)findViewById(R.id.view_pager);
        indexText = (TextView)findViewById(R.id.view_pager_index);

        imageIdList = new ArrayList<Integer>();
        imageIdList.add(R.drawable.banner1);
        imageIdList.add(R.drawable.banner2);
        imageIdList.add(R.drawable.banner3);
        imageIdList.add(R.drawable.banner4);
        viewPager.setAdapter(new ImagePagerAdapter(getApplicationContext(), imageIdList).setInfiniteLoop(true));
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

        viewPager.setInterval(2000);
        viewPager.startAutoScroll();
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % ListUtils.getSize(imageIdList));

        //menuß
        listView = (ListView) findViewById(R.id.itemListView);
        listView.setAdapter(new MyAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parents, View view, int position,
                                    long id) {
                // TODO Auto-generated method stub
                switch (position) {
                    case 0:
                        projectButtonPress(view);
                        break;
                    case 1:
                        toolButtonPress(view);
                        break;
                    case 2:
                        repairButtonPress(view);
                        break;
                    case 3:
                        logoutButtonPress(view);
                        break;
                }
                //Toast.makeText(MainActivity.this, "您�?�择�?" + names[position], Toast.LENGTH_SHORT).show();
            }
        });

        //mydb = new DBHelper(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // stop auto scroll when onPause
        viewPager.stopAutoScroll();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // start auto scroll when onResume
        viewPager.startAutoScroll();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void projectButtonPress(View view) {

        Intent projectIntent = new Intent(this, ProjectManagementMainActivity.class);
        startActivity(projectIntent);
    }

    public void toolButtonPress(View view) {
        Intent toolIntent = new Intent(this, ToolManagementActivity.class);
        startActivity(toolIntent);
    }

    public void repairButtonPress(View view) {
        Intent repairIntent = new Intent(this, RepairServiceActivity.class);
        startActivity(repairIntent);
    }

    public void logoutButtonPress(View view) {
        Intent logoutIntent = new Intent(this, LoginActivity.class);
        startActivity(logoutIntent);
    }

    static class ViewHolder{
        ImageView iv;
        TextView tv;
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            indexText.setText(new StringBuilder().append((position) % ListUtils.getSize(imageIdList) + 1).append("/")
                    .append(ListUtils.getSize(imageIdList)));
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageScrollStateChanged(int arg0) {}
    }

    //Self-defined adapter
    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return names[position];
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            System.out.println("position=" + position);
            //            System.out.println(convertView);
            //            System.out.println("------------------------");
            ViewHolder vh = new ViewHolder();

            //通过下面的条件判断语句，来循环利用�?�如果convertView = null ，表示屏幕上没有可以被重复利用的对象�?
            if(convertView==null){
                //创建View
                convertView = getLayoutInflater().inflate(R.layout.activity_main_item, null);
                vh.iv = (ImageView) convertView.findViewById(R.id.imageView1);
                vh.tv = (TextView) convertView.findViewById(R.id.textView1);
                convertView.setTag(vh);
            }else{
                vh = (ViewHolder)convertView.getTag();
            }
            vh.iv.setImageResource(images[position]);
            vh.tv.setText(names[position]);
            return convertView;
        }


    }

}