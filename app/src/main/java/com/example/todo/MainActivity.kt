package com.example.todo

import android.content.DialogInterface
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.todo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var bind :ActivityMainBinding
    var itemss=ArrayList<String>()

    private val fileHelper = FileHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind=ActivityMainBinding.inflate(layoutInflater)
        val view=bind.root
        setContentView(view)



        itemss=fileHelper.readData(this)
        var apna_adapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,itemss)
        bind.list.adapter=apna_adapter



        bind.button.setOnClickListener {
            var s :String =bind.editText.text.toString()
            itemss.add(s)
            bind.editText.setText("")
            fileHelper.writeData(itemss,applicationContext)
            apna_adapter.notifyDataSetChanged()
        }

        bind.list.setOnItemClickListener { parent, view, i, id ->

            var alert=AlertDialog.Builder(this)
            alert.setTitle("Delete")
            alert.setMessage("Acomplished???")

            alert.setCancelable(false)
            alert.setNegativeButton("No", DialogInterface.OnClickListener{dialogInterface, i ->
                dialogInterface.cancel()
            })


            //GPT SNIPPET
            alert.setPositiveButton("Yes") { dialogInterface, _ ->
                // Remove the item from the adapter directly
                apna_adapter.remove(apna_adapter.getItem(i))
                apna_adapter.notifyDataSetChanged()

                // Update the file after modifying the adapter
                fileHelper.writeData(itemss, applicationContext)
            }
//            alert.setPositiveButton("Yes", DialogInterface.OnClickListener{dialogInterface, i ->
//                apna_adapter.remove(apna_adapter.getItem(i))
//                apna_adapter.notifyDataSetChanged()
//                fileHelper.writeData(itemss,applicationContext)
//            })
            alert.create().show()
        }

    }
}