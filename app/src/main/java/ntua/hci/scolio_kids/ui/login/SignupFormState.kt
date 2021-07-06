package ntua.hci.scolio_kids.ui.login

data class SignupFormState(val nameError: Int? = null,
                           val usernameError: Int? = null,
                           val passwordError: Int? = null,
                           val retypePasswordError: Int? = null,
                           val passwordMismatchError: Int? = null,
                           val isDataValid: Boolean = false)
