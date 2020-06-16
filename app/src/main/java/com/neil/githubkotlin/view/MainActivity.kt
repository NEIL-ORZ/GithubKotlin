package com.neil.githubkotlin.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.appcompat.app.AppCompatActivity
import com.neil.common.ext.no
import com.neil.common.ext.otherwise
import com.neil.githubkotlin.R
import com.neil.githubkotlin.model.account.AccountManager
import com.neil.githubkotlin.model.account.OnAccountStateChangeListener
import com.neil.githubkotlin.network.entities.User
import com.neil.githubkotlin.network.services.RepositoryService
import com.neil.githubkotlin.utils.doOnLayoutAvailable
import com.neil.githubkotlin.utils.loadWithGlide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import java.util.*

class MainActivity : AppCompatActivity(), OnAccountStateChangeListener {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_repository, R.id.nav_gallery, R.id.nav_about
            ), drawer_layout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)

        initNavigationView()

        AccountManager.onAccountStateChangeListeners.add(this)

//        RepositoryService.listRepositoriesOfUser("NEIL-ORZ")
//            .subscribe({}, {
//                Log.e("zh", it.toString())
//            })
//        RepositoryService.allRepositories(1, q = "pushed:<2020-06-16")
//            .subscribe({
//                Log.e("zh", "list size=" + it.items.size)
//            }, {
//                Log.e("zh", it.toString())
//            })
    }

    override fun onDestroy() {
        super.onDestroy()
        AccountManager.onAccountStateChangeListeners.remove(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun initNavigationView() {
        Log.e("zh", AccountManager.currentUser.toString())
        AccountManager.currentUser?.let(::updateNavigationView) ?: clearNavigationView()
        initNavigationHeadEvent()
    }

    private fun updateNavigationView(user: User) {
        Log.e("zh", "updateNavigationView")
        nav_view.doOnLayoutAvailable {
            username.text = user.login
            textView.text = user.html_url
            imageView.loadWithGlide(user.avatar_url, R.drawable.ic_launcher_foreground)
        }
    }

    private fun clearNavigationView() {
        Log.e("zh", "clearNavigationView")
        nav_view.doOnLayoutAvailable {
            username.text = "请登录"
            textView.text = ""
            imageView.setImageResource(R.drawable.ic_launcher_foreground)
        }
    }

    private fun initNavigationHeadEvent() {
        nav_view.doOnLayoutAvailable {
            nav_head.setOnClickListener {
                AccountManager.isLoggedIn().no {
                    startActivity(Intent(this, LoginActivity::class.java))
                }.otherwise {
                    AccountManager.logout()
                        .subscribe({
                            Toast.makeText(this, "注销成功", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        }, {
                            Toast.makeText(this, "注销失败", Toast.LENGTH_LONG).show()
                        })
                }
            }
        }
    }

    override fun onLogin(user: User) {
        updateNavigationView(user)
    }

    override fun onLogout() {
        clearNavigationView()
    }
}
