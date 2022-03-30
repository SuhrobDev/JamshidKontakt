package com.jamshidprograms.kontakt

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.jamshidprograms.kontakt.adapters.ContactAdapter
import com.jamshidprograms.kontakt.databinding.ActivityMainBinding
import com.jamshidprograms.kontakt.dialogs.AddContactDialog
import com.jamshidprograms.kontakt.dialogs.EditContactDialog
import com.jamshidprograms.kontakt.models.ContactData

class MainActivity : AppCompatActivity() {
    private val adapter by lazy {
        ContactAdapter()
    }
    var position:Int = 0
    lateinit var binding: ActivityMainBinding
    lateinit var alertDialog:AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.list.layoutManager = LinearLayoutManager(this)
        binding.list.adapter = adapter
        binding.add.setOnClickListener {
            val dialog = AddContactDialog(this)
            dialog.setOnAddListener { name, number ->
                adapter.addContact(ContactData(name, number))
            }
            dialog.show()
        }
        adapter.setDeleteListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Siz rostdan xam kontaktni o'chirishni istaysizmi?")
            builder.setCancelable(false)
            builder.setPositiveButton("Ha", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    adapter.deleteContact(it)
                }
            })
            builder.setNegativeButton("Yo'q", object :DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    alertDialog.cancel()
                }

            })
            alertDialog = builder.create()
            alertDialog.show()

        }
        adapter.setCallListener { pos, nomer ->
            val intent = Intent.ACTION_CALL
            var s = adapter.s
        }
        adapter.setEditListener { pos, name, number ->
            val dialog = EditContactDialog(this, name, number)
            dialog.setOnAddListener { nameX, numberX ->
                adapter.editContact(ContactData(nameX, numberX), pos)
            }
            dialog.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
        R.id.share->{
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            intent.putExtra(Intent.EXTRA_SUBJECT, "Ushbu ajoyib ilovani ishlatib ko'ring")
            intent.putExtra(Intent.EXTRA_TEXT, "Ilovangizning havolasi bu yerda")
            startActivity(Intent.createChooser(intent, "Share app"))
        }

        }
        return super.onOptionsItemSelected(item)
    }
}