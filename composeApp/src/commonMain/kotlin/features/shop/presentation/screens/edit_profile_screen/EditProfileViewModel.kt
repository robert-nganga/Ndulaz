package features.shop.presentation.screens.edit_profile_screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EditProfileViewModel: ViewModel() {

    private val _editProfileState = MutableStateFlow(EditProfileScreenState())
    val editProfileState = _editProfileState.asStateFlow()



    fun onNameChange(name: String){
        _editProfileState.update {
            it.copy(name = name)
        }
    }

    fun onEmailChange(email: String){
        _editProfileState.update {
            it.copy(email = email)
        }
    }

    fun onPhoneNumberChange(phoneNumber: String){
        _editProfileState.update {
            it.copy(phone = phoneNumber)
        }
    }
}