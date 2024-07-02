package core.domain

class InputValidation {

    fun validatePassword(password: String): String?{
        return when{
            password.length < 8-> {
                "Password must be more than 8 characters"
            }
            else -> null
        }
    }

    fun validateEmail(email: String): String? {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return when {
            email.isEmpty() -> "Email must not be empty"
            !email.matches(emailRegex.toRegex()) -> "Invalid email format"
            else -> null
        }
    }

    fun validateName(name: String): String?{
        return when{
            name.isEmpty()-> {
                "Name must not be empty"
            }
            else -> null
        }
    }


}