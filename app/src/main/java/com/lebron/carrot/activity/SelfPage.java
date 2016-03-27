package com.lebron.carrot.activity;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.lebron.carrot.R;
import com.lebron.carrot.common.Constant;
import com.lebron.carrot.imple.ModelVolley;
import com.lebron.carrot.interfaces.GetDataListener;
import com.lebron.carrot.manager.MyActivityManager;

public class SelfPage extends AppCompatActivity implements GetDataListener{
    private String urlString = Constant.urlPageApi;
//    private String urlString = "http://114.215.117.169/index.php";
    private Toolbar toolBar;
    private CollapsingToolbarLayout collapsingToolbar;
    private ModelVolley modelVolley;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_page);
        MyActivityManager.getInstance().addActivity(this);
        initToolBar();
        initCollapsingToolbar();

        modelVolley = new ModelVolley();
        modelVolley.getDataFromServer(String.format(urlString, 1), this);
    }

    /**
     * 给页面设置工具栏
     */
    private void initToolBar(){
        toolBar = (Toolbar) findViewById(R.id.toolbar_self);
        setSupportActionBar(toolBar);
        //给左上角图标的左边加上一个返回的图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * //设置工具栏标题
     */
    private void initCollapsingToolbar(){
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Carrot");
        //设置展开和收缩的颜色
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);
        collapsingToolbar.setCollapsedTitleTextColor(Color.BLACK);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.self, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.self_settings:
                return true;
            case android.R.id.home: //对toolBar左上角的返回图标的事件
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 接口回调得到的json字符串
     * @param string
     */
    @Override
    public void onSuccess(String string) {
        Toast.makeText(SelfPage.this, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError() {
        Toast.makeText(SelfPage.this, "error", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyActivityManager.getInstance().removeActivity(this);
    }
}
