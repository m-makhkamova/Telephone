package uz.itschool.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.itschool.R
import uz.itschool.databinding.ContactItemBinding
import uz.itschool.dataclass.Contact

class ContactsAdapter(var contacts:MutableList<Contact>, var myInterface:MyInterface, var context: Context): RecyclerView.Adapter<ContactsAdapter.MyHolder>() {
    class MyHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(parent.context).inflate(R.layout.contact_item,parent,false))
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        var binding = ContactItemBinding.bind(holder.itemView)
        var contact = contacts[position]
        binding.name.text = contact.name+""+contact.surname
        binding.phone.text = contact.phone

        holder.itemView.setOnClickListener {
            myInterface.onItemClick(contact)
        }
        binding.callIcon.setOnClickListener {
            var callIntent = Intent(Intent.ACTION_CALL).apply {
                data = Uri.parse("tel:" + contact.phone)
            }
            context.startActivity(callIntent)
        }

        binding.callIcon.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + contact.phone)
            context.startActivity(dialIntent)
        }
    }
    interface MyInterface{
        fun onItemClick(contact:Contact)
    }
}