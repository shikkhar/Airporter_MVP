package com.example.airporter.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.airporter.AppController;
import com.example.airporter.LoginActivity.LoginActivity;
import com.example.airporter.MainActivity.MainActivity;
import com.example.airporter.Menu.HomeFragment.HomeFragment;
import com.example.airporter.Menu.HomeFragment.ShopFragment.ShopFragment;
import com.example.airporter.Menu.MessagesFragment.MessagesFragment;
import com.example.airporter.Menu.OffersFragment.OffersFragment;
import com.example.airporter.Menu.OrdersFragment.OrdersFragment;
import com.example.airporter.R;
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

    private class OnMenuItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        Fragment fragment;

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {

                case R.id.homeMenuItemID:
                    fragment = fm.findFragmentByTag(fragmentListTags.HOME.name());
                    if (fragment == null)
                        fragment = new HomeFragment();
                    setFragment(fragment, fragmentListTags.HOME.name());
                    return true;

                case R.id.ordersMenuItemID:
                    fragment = fm.findFragmentByTag(fragmentListTags.ORDERS.name());
                    if (fragment == null)
                        fragment = new OrdersFragment();
                    setFragment(fragment, fragmentListTags.ORDERS.name());
                    return true;

                case R.id.offersMenuItemID:
                    fragment = fm.findFragmentByTag(fragmentListTags.OFFERS.name());
                    if (fragment == null)
                        fragment = new OffersFragment();
                    setFragment(fragment, fragmentListTags.OFFERS.name());
                    return true;

                case R.id.messageMenuItemID:
                    fragment = fm.findFragmentByTag(fragmentListTags.MESSAGES.name());
                    if (fragment == null)
                        fragment = new MessagesFragment();
                    setFragment(fragment, fragmentListTags.MESSAGES.name());
                    return true;

                default:
                    return false;
            }
        }

    }

    private void setFragment(Fragment fragment, String fragmentTag) {
        fm.beginTransaction().replace(R.id.menuFrameLayoutId, fragment, fragmentTag).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemLogout:
                AppController.getInstance().getPreferenceManager().storeUserId(null);
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
