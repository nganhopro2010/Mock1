package com.hovanngan.mock1

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.CallLog
import android.provider.ContactsContract
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.hovanngan.mock1.databinding.ActivityMainBinding
import com.hovanngan.mock1.model.Contact
import com.hovanngan.mock1.model.History

class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding? = null
    private lateinit var pagerAdapter: PagerAdapter

    companion object {
        const val REQUEST_CODE: Int = 100
        const val REQUEST_CODE_PERMISSION: Int = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        pagerAdapter = PagerAdapter(this, supportFragmentManager)
        mBinding?.apply {
            pager.adapter = pagerAdapter
            tabLayout.setupWithViewPager(pager)
        }
        checkPermissions()
    }

    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CALL_LOG
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.READ_CALL_LOG),
                REQUEST_CODE
            )
        } else {
            updateUI()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED &&
            grantResults[1] == PackageManager.PERMISSION_GRANTED
        ) {
            updateUI()
        } else {
            Toast.makeText(
                this, getString(R.string.you_have_not_enough_grant_permission), Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:" + this.packageName)
            startActivityForResult(intent, REQUEST_CODE_PERMISSION)

        }
    }

    private fun updateUI() {
        pagerAdapter.setData(
            readContact(),
            readCallHistory()
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            checkPermissions()
        }
    }

    private fun readCallHistory(): ArrayList<History> {
        val histories = ArrayList<History>()
        val cursor = contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        while (cursor!!.moveToNext()) {
            val phoneNumber = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER))
            val totalTime = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION))
            val dateCreate = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE))
            val history = History(phoneNumber, totalTime, dateCreate)
            histories.add(history)

        }
        cursor.close()
        return histories
    }

    private fun readContact(): ArrayList<Contact> {
        val contacts = ArrayList<Contact>()
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        while (cursor!!.moveToNext()) {
            val name =
                cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val numberPhone =
                cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val photoUri =
                cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))
            val contact = Contact(name, null, numberPhone)
            if (photoUri != null) {
                contact.img = MediaStore.Images.Media.getBitmap(
                    contentResolver,
                    Uri.parse(photoUri)
                )
            }
            contacts.add(contact)
        }
        cursor.close()
        return contacts
    }

}


