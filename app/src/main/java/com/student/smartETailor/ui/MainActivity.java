package com.student.smartETailor.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.navigation.NavigationView;
import com.student.smartETailor.R;
import com.student.smartETailor.constants.Const;
import com.student.smartETailor.models.User;
import com.student.smartETailor.ui.fragments.AdminFragment;
import com.student.smartETailor.ui.fragments.CustomerFragment;
import com.student.smartETailor.ui.fragments.RiderFragment;
import com.student.smartETailor.ui.fragments.TailorFragment;
import com.student.smartETailor.utils.UsersUtils;
import com.student.smartETailor.utils.Utils;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    //Drawer Navigation Code
    DrawerLayout drawer;
    NavigationView navigationView;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerSetting();

        openFragment();
    }

    private void openFragment() {
        Fragment mFragment = new Fragment();
        User user = UsersUtils.getInstance(this).fetchUser();
        if (user.getUID().equals("") || user.getType().equals("")) {
            Utils.getInstance().signOut(this);
        }
        switch (user.getType()) {
            case Const.USER_TYPE_TAILOR:
                mFragment = new TailorFragment();
                break;
            case Const.USER_TYPE_RIDER:
                mFragment = new RiderFragment();
                break;
            case Const.USER_TYPE_CUSTOMER:
                mFragment = new CustomerFragment();
                break;
            case Const.USER_TYPE_ADMIN:
                mFragment = new AdminFragment();
                break;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_main, mFragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void drawerSetting() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_profile:
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        break;
                    case R.id.nav_share:
                        Utils.getInstance().shareApp(MainActivity.this);
                        break;

                    case R.id.nav_rate:
                        Utils.getInstance().rateApp(MainActivity.this);
                        break;

                    case R.id.nav_logout:
                        Utils.getInstance().signOut(MainActivity.this);
                        break;
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        mToggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
        drawer.addDrawerListener(mToggle);
        mToggle.syncState();
    }
}