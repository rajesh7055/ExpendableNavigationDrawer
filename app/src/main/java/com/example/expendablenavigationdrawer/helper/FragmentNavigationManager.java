package com.example.expendablenavigationdrawer.helper;


import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.expendablenavigationdrawer.BuildConfig;
import com.example.expendablenavigationdrawer.Interfaces.NavigationManager;
import com.example.expendablenavigationdrawer.MainActivity;
import com.example.expendablenavigationdrawer.R;
import com.example.expendablenavigationdrawer.fragment.FragmentContent;

public class FragmentNavigationManager implements NavigationManager {

    private static FragmentNavigationManager mInstance;
    private FragmentManager mFragmentManager;
    private MainActivity mainActivity;

    public static FragmentNavigationManager getmInstance(MainActivity mainActivity){
        if(mInstance==null)
            mInstance=new FragmentNavigationManager();
        mInstance.configure(mainActivity);
        return mInstance;
    }

    private void configure(MainActivity mainActivity) {

        mainActivity=mainActivity;
        mFragmentManager=mainActivity.getSupportFragmentManager();

    }

    @Override
    public void showFragment(String title) {

        showFragment(FragmentContent.newInstance(title),false);

    }

    private void showFragment(FragmentContent newInstance, boolean b) {
        FragmentManager fm=mFragmentManager;
        FragmentTransaction ft=fm.beginTransaction().replace(R.id.container,newInstance);
        ft.addToBackStack(null);

        if(b || !BuildConfig.DEBUG){
            ft.commitAllowingStateLoss();
        }
        else{
            ft.commit();
        }
        fm.executePendingTransactions();
    }
}
