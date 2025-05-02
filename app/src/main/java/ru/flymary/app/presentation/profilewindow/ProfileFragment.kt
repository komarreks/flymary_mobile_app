package ru.flymary.app.presentation.profilewindow

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.flymary.app.App
import ru.flymary.app.R
import ru.flymary.app.databinding.FragmentProductsBinding
import ru.flymary.app.databinding.FragmentProfileBinding
import ru.flymary.app.model.PhoneWithCode
import java.security.MessageDigest
import java.time.LocalDateTime

class ProfileFragment : Fragment() {

    private var _binding:FragmentProfileBinding? = null
    val binding get() = _binding!!

    val model:ProfileModel by viewModels()

    var userID = App.LOCAL_STORAGE.getUID()

    var codeIsSended = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    @OptIn(ExperimentalStdlibApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.autorizePane.isVisible = userID.isEmpty()
        binding.profilePane.isVisible = userID.isNotEmpty()

        binding.codeSendButton.isVisible = false
        binding.registrationButton.isVisible = false
        binding.codeText.isVisible = false
        binding.userNotFound.isVisible = false

        model.answerCode.onEach {
            when(it){
                200 -> {
                    binding.codeText.isVisible = true
                    binding.registrationButton.isVisible = false
                    codeIsSended = true
                    binding.codeSendButton.isVisible = false
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        if (userID.isEmpty()){
            model.userID.onEach {
                userID = it
                if (userID.length > 0){
                    loadProfile()
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        }else{
            loadProfile()
        }

        binding.phoneText.doOnTextChanged { text, start, before, count ->
            if (text?.length == 11 && text.get(0) == '8'){
                binding.codeSendButton.visibility = View.VISIBLE
                binding.codeText.isVisible = codeIsSended
            }else if (text?.length == 12 && text.get(0) == '+' && text.get(1) == '7'){
                binding.codeSendButton.visibility = View.VISIBLE
                binding.codeText.isVisible = codeIsSended
            }
            else{
                binding.codeSendButton.visibility = View.GONE
                codeIsSended = false
                binding.codeText.isVisible = codeIsSended
                model.setDefaultCode()
            }
        }

        binding.codeSendButton.setOnClickListener {
            model.getAutorizeCode(binding.phoneText.text.toString())
        }

        binding.codeText.doOnTextChanged { text, start, before, count ->
            if (text?.length == 4){
                val digester = MessageDigest.getInstance("SHA-256")

                val year = LocalDateTime.now().year
                val month = LocalDateTime.now().month
                val day = LocalDateTime.now().dayOfYear

                val stringDay = text.toString() + year.toString() + month.toString() + day.toString()

                val stringBytes = stringDay.toByteArray()
                digester.update(stringBytes,0,stringBytes.size)

                val value = digester.digest()

                model.autor(PhoneWithCode(binding.phoneText.text.toString(), value.toHexString()))
            }
        }

        model.userData.onEach {
            binding.fio.text = it?.name
            binding.phones.text = it?.getCollection(it.phones, "+")
            binding.adresses.text = it?.getCollection(it.addresses)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.userDataButton.isChecked = true

        if (userID.isNotEmpty()){
            binding.userDataPane.isVisible = true
            binding.ordersPane.isVisible = false
        }

        binding.userDataButton.setOnClickListener {
            binding.ordersButton.isChecked = false
            (it as ToggleButton).isChecked = true

            binding.userDataPane.isVisible = true
            binding.ordersPane.isVisible = false
        }

        binding.ordersButton.setOnClickListener {
            binding.userDataButton.isChecked = false
            (it as ToggleButton).isChecked = true

            binding.ordersPane.isVisible = true
            binding.userDataPane.isVisible = false
        }
    }

    fun loadProfile(){
        val uidFromStorage = App.LOCAL_STORAGE.getUID()
        if (!userID.equals(uidFromStorage) && !userID.isEmpty()){
            App.LOCAL_STORAGE.saveUID(userID)
        }

        model.getUserData(userID)
    }
}