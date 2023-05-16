package uz.itschool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.itschool.UI.ContactsFragment
import uz.itschool.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main, ContactsFragment()).commit()
    }
}