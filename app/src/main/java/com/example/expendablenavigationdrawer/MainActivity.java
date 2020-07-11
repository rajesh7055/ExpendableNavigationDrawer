package com.example.expendablenavigationdrawer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.expendablenavigationdrawer.Adaptor.CustomExpandableListAdaptor;
import com.example.expendablenavigationdrawer.Interfaces.NavigationManager;
import com.example.expendablenavigationdrawer.helper.FragmentNavigationManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private String[] items;

    private ExpandableListView expandableListView;
    private ExpandableListAdapter adapter;
    private List<String> lstTitle;
    private Map<String,List<String>> lstChild;
    private NavigationManager navigationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //InitView

        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle=getTitle().toString();
        expandableListView=(ExpandableListView)findViewById(R.id.nav_List);
        navigationManager= FragmentNavigationManager.getmInstance(this);
        
        initItems();

        View listHeaderView=getLayoutInflater().inflate(R.layout.nav_header,null,false);
        expandableListView.addHeaderView(listHeaderView);

        genData();

        addDrawersItem();
        setupDrawer();

        if(savedInstanceState==null)
            selectFirstItemAsDefault();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Rajesh Prajapati");


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void selectFirstItemAsDefault() {

        if(navigationManager!=null){
            String firstItem=lstTitle.get(0);
            navigationManager.showFragment(firstItem);
            getSupportActionBar().setTitle(firstItem);
        }

    }

    private void setupDrawer() {

        mDrawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getSupportActionBar().setTitle("Rajesh Prajapati");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    private void addDrawersItem() {

        adapter=new CustomExpandableListAdaptor(this,lstTitle,lstChild);
        expandableListView.setAdapter(adapter);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                getSupportActionBar().setTitle(lstTitle.get(groupPosition).toString());   //to set title for Toolbar when you open menu
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                getSupportActionBar().setTitle("Rajesh Prajapati");
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                //Change Fragment when click on item:

                String selectedItem=((List)(lstChild.get(lstTitle.get(groupPosition))))
                        .get(childPosition).toString();
                getSupportActionBar().setTitle(selectedItem);

                if(items[0].equals(lstTitle.get(groupPosition)))
                    navigationManager.showFragment(selectedItem);
                else
                    throw  new IllegalArgumentException("Not Supported Fragment");

                mDrawerLayout.closeDrawer(GravityCompat.START);

                return false;
            }
        });

    }

    private void genData() {

        List<String> title= Arrays.asList("Android Programming","Xamarin Programming","iOs Programming");
        List<String> childItem= Arrays.asList("Beginner","Intermediate","Advance","Professional");
        List<String> childItem1= Arrays.asList("John","David","Russo","William");
        List<String> childItem2= Arrays.asList("Alexa","AJ Style","Johnson","Symonds");


        lstChild=new TreeMap<>();
        lstChild.put(title.get(0),childItem);
        lstChild.put(title.get(1),childItem1);
        lstChild.put(title.get(2),childItem2);

        lstTitle=new ArrayList<>(lstChild.keySet());



    }

    private void initItems() {

       items=new String[]{"Android Programming","Xamarin Programming","iOs Programming"};

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();

        if(mDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}