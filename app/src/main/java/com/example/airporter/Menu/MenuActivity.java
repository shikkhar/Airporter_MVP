package com.example.airporter.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.airporter.Menu.HomeFragment.HomeFragment;
import com.example.airporter.Menu.HomeFragment.ShopFragment.ShopFragment;
import com.example.airporter.R;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuActivity extends AppCompatActivity {

    private enum fragmentListTags {HOME, ORDERS, OFFERS, MESSAGES}
    private FragmentManager fm;
    private BottomNavigationView bottomNavigationMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        fm = getSupportFragmentManager();

        bottomNavigationMenu = this.findViewById(R.id.bottomNavigationMenuId);
        bottomNavigationMenu.setOnNavigationItemSelectedListener(new OnMenuItemSelectedListener());
        bottomNavigationMenu.setSelectedItemId(R.id.homeMenuItemID);

    }

    private  class OnMenuItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        Fragment fragment;

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {

                case R.id.homeMenuItemID:
                    fragment = fm.findFragmentByTag(fragmentListTags.HOME.name());
                    if (fragment == null)
                        fragment = new HomeFragment();
                    setFragment(fragment,fragmentListTags.HOME.name());
                    return true;

                case R.id.orderMenuItemID:
                    fragment = fm.findFragmentByTag(fragmentListTags.ORDERS.name());
                    if (fragment == null)
                        fragment = new ShopFragment();
                    setFragment(fragment,fragmentListTags.ORDERS.name());
                    return true;

                case R.id.offersMenuItemID:
                /*fragment = fm.findFragmentByTag(fragmentListTags.OFFERS.name());
                if(fragment == null)
                    fragment = new HomeFragment();
                setFragment(fragment);*/
                    return true;


                case R.id.messageMenuItemID:
                /*fragment = fm.findFragmentByTag(fragmentListTags.MESSAGES.name());
                if(fragment == null)
                    fragment = new HomeFragment();
                setFragment(fragment);*/
                    return true;

                default:
                    return false;
            }
        }

    }

    private void setFragment(Fragment fragment, String fragmentTag) {
        fm.beginTransaction().replace(R.id.menuFrameLayoutId, fragment, fragmentTag).commit();
    }
}
