package com.sayler.roletest

import android.app.role.RoleManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val ROLE_REQUEST_CODE = 1234
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        val roleManager = getSystemService(RoleManager::class.java)

        if (roleManager.isRoleAvailable(RoleManager.ROLE_GALLERY)) {
            if (roleManager.isRoleHeld(RoleManager.ROLE_GALLERY)) {
                grant_role_button.visibility = View.GONE
                role_granted_text_view.visibility = View.VISIBLE
            }else{
                grant_role_button.visibility = View.VISIBLE
                role_granted_text_view.visibility = View.GONE
            }
        }

        grant_role_button.setOnClickListener {
            if (roleManager.isRoleAvailable(RoleManager.ROLE_GALLERY)) {
                if (roleManager.isRoleHeld(RoleManager.ROLE_GALLERY)) {
                    // This app is the default gallery app.
                    Toast.makeText(this, "Default gallery app!", Toast.LENGTH_SHORT).show()
                } else {
                    // This app isn't the default gallery app, but the role is available,
                    // so request it.
                    val roleRequestIntent = roleManager.createRequestRoleIntent(
                            RoleManager.ROLE_GALLERY)
                    startActivityForResult(roleRequestIntent, ROLE_REQUEST_CODE)
                }
            }
        }

        super.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ROLE_REQUEST_CODE) {
            if (resultCode == RESULT_OK)
                Toast.makeText(this, "ROLE_GALLERY granted!", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "ROLE_GALLERY not granted!", Toast.LENGTH_SHORT).show()
        }
    }
}
