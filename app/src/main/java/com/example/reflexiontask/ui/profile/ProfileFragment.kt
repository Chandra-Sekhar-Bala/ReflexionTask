package com.example.reflexiontask.ui.profile

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.reflexiontask.R
import com.example.reflexiontask.databinding.FragmentProfileBinding

@Suppress("NAME_SHADOWING")
class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding
    private var image_uri: String? = null
    private lateinit var sharedPf: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPf = requireContext().getSharedPreferences("user_data", Context.MODE_PRIVATE)

        // first disable editable views
        activeEditableViews(false)
    }

    override fun onResume() {
        super.onResume()

        setDataToProfile()

        binding.imagePicker.setOnClickListener {
            imgResult.launch("image/*")
        }

        binding.btnEdit.setOnClickListener {
            if (binding.btnEdit.text == "Edit") {
                activeEditableViews(true)
                binding.imagePicker.visibility = View.VISIBLE
                binding.btnEdit.text = "Save"
                binding.btnEdit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_save, 0, 0, 0)
            } else {
                if(validateInputFields()) {
                    binding.btnEdit.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_edit,
                        0,
                        0,
                        0
                    )
                    binding.btnEdit.text = "Edit"
                    binding.imagePicker.visibility = View.INVISIBLE
                    saveDetails()
                }
            }
        }
    }

    private fun setDataToProfile() {
        // Retrieve user data from SharedPreferences
        val userName = sharedPf.getString("user_name", null)
        val userEmail = sharedPf.getString("user_email", null)
        val userPhone = sharedPf.getString("user_phone", null)
        val profileUri = sharedPf.getString("profile_uri", null)

        if (userName != null) {
            binding.edtName.setText(userName)
            binding.edtEmail.setText(userEmail)
            binding.edtPhone.setText(userPhone)
            if (profileUri != null) {
                val profileUri = Uri.parse(profileUri)
                Glide.with(requireActivity())
                    .load(profileUri)
                    .into(binding.imgProfile)
            }
        }
    }

    private fun activeEditableViews(value: Boolean) {
        binding.edtName.isEnabled = value
        binding.edtEmail.isEnabled = value
        binding.edtPhone.isEnabled = value
    }

    // save the details of user
    private fun saveDetails() {
        // Save user data in SharedPreferences
        if (validateInputFields()) {
            activeEditableViews(false)
            sharedPf.edit().apply {
                putString("user_name", binding.edtName.text.toString().trim())
                putString("user_email", binding.edtEmail.text.toString().trim())
                putString("user_phone", binding.edtPhone.text.toString().trim())
                putString("profile_uri", image_uri)
                Log.i("TAG", "saveDetails: $image_uri")
                apply()
            }
        }
    }

    // check input validation
    private fun validateInputFields(): Boolean {
        val name = binding.edtName.text.toString().trim()
        val email = binding.edtEmail.text.toString().trim()
        val phone = binding.edtPhone.text.toString().trim()

        if (name.isEmpty()) {
            binding.edtName.error = "Please enter your name"
            return false
        }

        if (email.isEmpty()) {
            binding.edtEmail.error = "Please enter your email"
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmail.error = "Please enter a valid email address"
            return false
        }

        if (phone.isEmpty()) {
            binding.edtPhone.error = "Please enter your phone number"
            return false
        }

        return true
    }

    // getting image from gallery

    private val imgResult = registerForActivityResult(ActivityResultContracts.GetContent()) {
        it.let {
            image_uri = it.toString()
            binding.imgProfile.setImageURI(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        saveDetails()
    }
}
