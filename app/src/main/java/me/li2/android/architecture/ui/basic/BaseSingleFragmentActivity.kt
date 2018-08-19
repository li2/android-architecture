package me.li2.android.architecture.ui.basic

import android.os.Bundle
import android.support.v4.app.Fragment
import dagger.android.support.DaggerAppCompatActivity
import me.li2.android.architecture.R

abstract class BaseSingleFragmentActivity : DaggerAppCompatActivity() {

    protected abstract fun createFragment(): Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_fragment_activity)

        // Get the FragmentManager.
        val fm = supportFragmentManager
        // Ask the FragmentManager for the fragment with a container view ID,
        // If this fragment is already in the list, the FragmentManager will return it,
        var fragment: Fragment? = fm.findFragmentById(R.id.fragmentContainer)

        // Or create a new Fragment,
        if (fragment == null) {
            fragment = createFragment()
            // Create a new fragment transaction, include one add operation in it, and then commit it.
            fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit()
        }
    }
}
