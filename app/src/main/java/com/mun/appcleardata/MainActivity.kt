package com.mun.appcleardata

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.mun.appcleardata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val savedText = sharedPreferences.getString("savedText", "Empty")
        Toast.makeText(this, savedText, Toast.LENGTH_SHORT).show()
        binding.btnSave.setOnClickListener {
            val textToSave = binding.etName.text.toString()
            if(textToSave != ""){
                val editor = sharedPreferences.edit()
                editor.putString("savedText", textToSave)
                editor.apply()
                binding.etName.text?.clear()
            }else{
                Toast.makeText(this, "Please Enter A name", Toast.LENGTH_SHORT).show()
            }
        }

        //long click implemented
        binding.btnSave.setOnLongClickListener{
            Toast.makeText(this, "Long Clicked", Toast.LENGTH_SHORT).show();
            true

        }

        binding.btnClearData.setOnClickListener {
            Toast.makeText(this, "Entet the password to clear data", Toast.LENGTH_SHORT).show()
            //(getSystemService(ACTIVITY_SERVICE) as ActivityManager).clearApplicationUserData()
            showPasswordDialog()
        }

    }

    private fun showPasswordDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_password, null)
        val passwordEditText = dialogView.findViewById<EditText>(R.id.passwordEditText)

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Enter Password : 123456")
            .setView(dialogView)
            .setPositiveButton("OK") { dialog, _ ->
                val password = passwordEditText.text.toString()
                // Handle the password entered here
                if(password=="123456"){
                    (getSystemService(ACTIVITY_SERVICE) as ActivityManager).clearApplicationUserData()
                }else{
                    Toast.makeText(this, "wrong password", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()
    }
    private fun saveTextToSharedPreferences() {

    }
}