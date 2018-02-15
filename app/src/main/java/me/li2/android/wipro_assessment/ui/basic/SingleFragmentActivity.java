package me.li2.android.wipro_assessment.ui.basic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import me.li2.android.wipro_assessment.R;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_fragment_activity);
        
        // Get the FragmentManager.
        FragmentManager fm = getSupportFragmentManager();
        // Ask the FragmentManager for the fragment with a container view ID, 
        // If this fragment is already in the list, the FragmentManager will return it,
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        
        // Or create a new CrimeFragment,
        if (fragment == null) {
            fragment = createFragment();
            // Create a new fragment transaction, include one add operation in it, and then commit it.
            fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
        }
    }

    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
 }
