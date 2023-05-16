package uz.itschool.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.itschool.R
import uz.itschool.adapter.ContactsAdapter
import uz.itschool.database.DBHelper
import uz.itschool.databinding.FragmentContactsBinding
import uz.itschool.dataclass.Contact


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ContactsFragment : Fragment() {
    private var contacts:MutableList<Contact> = mutableListOf()
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentContactsBinding.inflate(inflater,container,false)
        binding.toolbar.inflateMenu(R.menu.menu)

        var db = DBHelper(requireContext())

        contacts = db.viewContact()

        binding.rv.adapter = ContactsAdapter(contacts, object:ContactsAdapter.MyInterface {
            override fun onItemClick(contact: Contact) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main, ContactInfoFragment.newInstance(contact)).commit()
            }
        }, requireContext())

        if(contacts.isEmpty()){
            binding.imageView.visibility = View.VISIBLE
        }else{
            binding.imageView.visibility = View.GONE

        }


        binding.addContactIcon.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, AddContactFragment()).commit()
        }
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(contact:Contact) =
            ContactsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}