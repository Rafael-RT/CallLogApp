package raf.example.calllogapp.ui

import android.database.Cursor
import android.os.Bundle
import android.provider.CallLog
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import raf.example.calllogapp.CallTimePresenter
import raf.example.calllogapp.Contract
import raf.example.calllogapp.databinding.FragmentCallTimeBinding
import java.lang.StringBuilder
import java.util.*


class CallTime : Fragment(), Contract.View {
    private var _binding: FragmentCallTimeBinding? = null
    private val binding get() = _binding!!
    private val presenter = CallTimePresenter(view)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCallTimeBinding.inflate(inflater,container,false)
        var check = false

        binding.inputPhoneEditText.setOnClickListener {
            check = presenter.checkPhoneNumber(binding.inputPhoneEditText, binding.inputPhoneLayout)
        }

        binding.buttonSearch.setOnClickListener {
            check = presenter.checkPhoneNumber(binding.inputPhoneEditText, binding.inputPhoneLayout)
            if(check) {
                val phoneNumber = "+7" + binding.inputPhoneEditText.text.toString()

                if(binding.buttonClear.visibility == View.GONE)
                    showHide(binding.buttonClear)

                val info = presenter.getInformation(phoneNumber, requireActivity()).split(";")

                binding.textViewIncome.text = "Income call from this number in 3 days: " + info.first()
                binding.textViewOutcome.text = "Outcome call to this number: " + info.last()
            } else {
                Toast.makeText(activity,"You cannot find info with this phone number", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonClear.setOnClickListener {
            binding.inputPhoneEditText.text?.clear()
            binding.textViewOutcome.text = null
            binding.textViewIncome.text = null

            showHide(binding.buttonClear)
        }

        binding.buttonExit.setOnClickListener {
            activity?.finish()
        }


        return binding.root
    }

    override fun showHide(view:View) {
        view.visibility = if (view.visibility == View.VISIBLE){
            View.GONE
        } else{
            View.VISIBLE
        }
    }
}