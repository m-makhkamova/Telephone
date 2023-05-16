package uz.itschool.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.itschool.R
import uz.itschool.database.DBHelper
import uz.itschool.databinding.FragmentAddContactBinding
import uz.itschool.dataclass.Contact

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AddContactFragment : Fragment() {
    // TODO: Rename and change types of parameters
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
        var binding = FragmentAddContactBinding.inflate(inflater, container, false)
        binding.back.setOnClickListener {
            findNavController().navigate(R.id.action_addContactFragment_to_contactsFragment)
        }
        binding.saveContact.setOnClickListener {
            if (binding.editTextTextPersonName.text.toString() != "" && binding.phoneNumber.text.toString() != "") {
                var db = DBHelper(requireContext())
                db.addContact(Contact(0,binding.editTextTextPersonName.text.toString(), binding.editTextTextSurname.text.toString(), binding.phoneNumber.text.toString()))
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main, ContactsFragment()).commit()
            }
        }

        binding.back.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, ContactsFragment()).commit()
        }
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddContactFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddContactFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}