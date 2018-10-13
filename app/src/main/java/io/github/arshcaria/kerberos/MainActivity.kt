package io.github.arshcaria.kerberos

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import io.github.arshcaria.kerberos.model.Item
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.intentFor

class MainActivity : AppCompatActivity() {

    private val doublePressTimeWindow = 300
    private var lastBackPressedTime = System.currentTimeMillis()

    private lateinit var mainViewPagerAdapter: MainViewPagerAdapter

    private val tabIcons = arrayOf(
            R.drawable.ic_favorite,
            R.drawable.ic_recent,
            R.drawable.ic_items_grid)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar()
        setupDrawer()
        setupViewPager()

        mainFab.setOnClickListener {
            startActivity(intentFor<AddItemActivity>())
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }
    }

    private fun setupDrawer() {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawerLayout.closeDrawers()
            when (menuItem.itemId) {
                R.id.nav_camera -> {
                    Toast.makeText(this, "Camera clicked", Toast.LENGTH_LONG).show()
                }
                R.id.nav_gallery -> {
                    Toast.makeText(this, "Gallery clicked", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
    }

    private fun setupViewPager() {
        mainViewPagerAdapter = MainViewPagerAdapter(supportFragmentManager)
        mainViewPager.adapter = mainViewPagerAdapter
        tabs.setupWithViewPager(mainViewPager)
        tabs.getTabAt(0)?.setIcon(tabIcons[0])
        tabs.getTabAt(1)?.setIcon(tabIcons[1])
        tabs.getTabAt(2)?.setIcon(tabIcons[2])
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastBackPressedTime > doublePressTimeWindow) {
            lastBackPressedTime = currentTime
        } else {
            super.onBackPressed()
        }
    }
}

private const val ARG_OBJECT = "object"

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
class MainViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int = 3

    override fun getItem(i: Int): Fragment {
        val fragment = PageFragment()
        fragment.arguments = Bundle().apply {
            // Our object is just an integer :-P
            putInt(ARG_OBJECT, i + 1)
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        // we don't need tab titles
        return ""
    }
}

class PageFragment : Fragment() {

    private lateinit var rvItems: RecyclerView
    private lateinit var adater: RvAdapter
    private lateinit var items: List<Item>


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // The last two arguments ensure LayoutParams are inflated properly.
        val rootView: ViewGroup = inflater.inflate(R.layout.list_items, container, false) as ViewGroup

        rvItems = rootView.findViewById(R.id.rvItems)
        adater = RvAdapter()
        rvItems.layoutManager = LinearLayoutManager(context)
        rvItems.adapter = adater
        return rootView
    }

    override fun onResume() {
        super.onResume()
        items = KerbApp.db.itemDao().getAll()
        adater.setData(items)
    }

}