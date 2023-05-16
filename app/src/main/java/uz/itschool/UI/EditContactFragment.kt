package uz.itschool.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.itschool.R
import uz.itschool.database.DBHelper
import uz.itschool.databinding.FragmentEditContactBinding
import uz.itschool.dataclass.Contact


private const val ARG_PARAM1 = "param1"

class EditContactFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Contact
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentEditContactBinding.inflate(inflater, container, false)
        var db = DBHelper(requireContext())

        binding.editTextTextPersonName.setText(param1?.name)
        binding.editTextTextPersonSurname.setText(param1?.surname)
        binding.editTextTextPersonPhone.setText(param1?.phone)
        binding.save.setOnClickListener {
            if(binding.editTextTextPersonName.text.toString() != "" && binding.editTextTextPersonPhone.text.toString() != ""){
                param1?.name = binding.editTextTextPersonName.text.toString()
                param1?.surname = binding.editTextTextPersonSurname.text.toString()
                param1?.phone = binding.editTextTextPersonPhone.text.toString()
                param1?.let { it1 -> db.updateContact(it1) }

                parentFragmentManager.beginTransaction()
                    .replace(R.id.main, ContactsFragment())
            }
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
         * @return A new instance of fragment EditContactFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Contact?) =
            EditContactFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }
}