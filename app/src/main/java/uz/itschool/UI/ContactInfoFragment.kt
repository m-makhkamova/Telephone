package uz.itschool.UI

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.itschool.R
import uz.itschool.database.DBHelper
import uz.itschool.databinding.FragmentContactInfoBinding
import uz.itschool.dataclass.Contact

private const val ARG_PARAM1 = "param1"
class ContactInfoFragment : Fragment() {

    private var param1: Contact? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Contact
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentContactInfoBinding.inflate(inflater, container,false)
        var db = DBHelper(requireContext())

        binding.contactName.text = param1?.name +" "+param1?.surname
        binding.contactPhone.text = param1?.phone
        binding.call.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + param1?.phone)
            requireContext().startActivity(dialIntent)
        }

        binding.delete.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())

            builder.setTitle("Confirm")
            builder.setMessage("Are you sure about deleting contact?")

            builder.setPositiveButton(
                "YES",
                DialogInterface.OnClickListener { dialog, _ ->
                    param1?.let { it1 -> db.deleteContact(it1) }
                    dialog.dismiss()
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.main, ContactsFragment())
                })

            builder.setNegativeButton(
                "NO",
                DialogInterface.OnClickListener { dialog, _ ->
                    dialog.dismiss()
                })

            val alert: AlertDialog = builder.create()
            alert.show()
        }

        binding.edit.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, EditContactFragment.newInstance(param1)).commit()
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(contact:Contact) =
            ContactInfoFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1,contact)
                }
            }
    }
}