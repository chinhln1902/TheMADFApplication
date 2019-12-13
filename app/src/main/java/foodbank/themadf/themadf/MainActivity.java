package foodbank.themadf.themadf;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


import java.util.ArrayList;

/*Main Activity class for the application, decides navigation & back button
 * hatfib@uw.edu
 */
public class MainActivity extends AppCompatActivity {



    private Fragment frag = null;
    private BottomNavigationView mBottomNav;
    private int mSelectedItem;
    public static ArrayList<String> arrList = new ArrayList<String>();
    public static ArrayList<String> arrList2 = new ArrayList<String>();
    public static ArrayList<String> arrList3 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        frag = new HomeFragment();
        ft.replace(R.id.container, frag);
        ft.commit();
        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectFragment(item);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        MenuItem homeItem = mBottomNav.getMenu().getItem(0);
        MenuItem menuItem = mBottomNav.getMenu().getItem(0);
        if (mSelectedItem != homeItem.getItemId()) {
            // select home item
            selectFragment(homeItem);
          menuItem.setChecked(menuItem.getItemId()== 0);
        } else {
            super.onBackPressed();

        }
    }

    //page navigation
    private void selectFragment(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                frag = new HomeFragment();
                break;
            case R.id.navigation_client:
                frag = new CheckInFragment();
                break;
            case R.id.navigation_food:
                frag = new FoodWeightFragment();
                break;
            case R.id.navigation_volunteer:
                frag = new VolunteerFragment();
                break;
            case R.id.navigation_about:
                frag = new AboutFragment();
                break;

        }

        //grabs the page name and updates the navigation bar
        updateToolbarText(item.getTitle());
        FragmentManager fm = getSupportFragmentManager();
        if (frag != null) {
            fm.popBackStack(frag.getClass().getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, frag);
            ft.addToBackStack(frag.getClass().getSimpleName());
            ft.commit();
        }
    }

    public void updateToolbarText(CharSequence text) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(text);
        }
    }




}
