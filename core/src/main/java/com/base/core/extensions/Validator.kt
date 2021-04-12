package com.base.core.extensions

import android.util.Patterns

fun <T> T.validate(validator: Validator<T>): Boolean = validator.validate(this)

interface Validator<in T> {
    fun validate(any: T?): Boolean
}

class EmailValidator : Validator<CharSequence> {
    override fun validate(any: CharSequence?): Boolean =
        !any.isNullOrBlank() && Patterns.EMAIL_ADDRESS.matcher(any).matches()
}

class PasswordValidator : Validator<CharSequence> {
    override fun validate(any: CharSequence?): Boolean = !any.isNullOrBlank() && any.length >= 8
}
/*
fun EditText.extValidateNickNameIsEmpty(): Boolean {

    if (this.text!!.isEmpty()) {
        this.error = resources.getString(R.string.input_warning_user_info)
        return false
    } else {
        val emptyPattern = "^(\\w+\\S+)\$"
        val pattern = Pattern.compile(emptyPattern)
        val match = pattern.matcher(this.text).matches()
        if (match) {
            this.error = null
        } else {
            this.error =
                resources.getString(R.string.input_error_user_info_empty)
            return false
        }
    }
    return true


}

fun EditText.extValidateNickNameIsSpecialCharacter(): Boolean {

    if (this.text!!.isEmpty()) {
        this.error = resources.getString(R.string.input_warning_user_info)
        return false
    } else {
        val specialCharacter = "^[a-zA-Z0-9]{3,25}\$"
        val patternSpecialCharacter = Pattern.compile(specialCharacter)
        val matchSpecialCharacter = patternSpecialCharacter.matcher(this.text).matches()
        if (matchSpecialCharacter) {
            this.error = null
        } else {
            this.error =
                resources.getString(R.string.input_error_user_info)
            return false

        }
    }
    return true


}

fun EditText.extValidateNickName(): Boolean {
    return this.extValidateNickNameIsEmpty()
            && this.extValidateNickNameIsSpecialCharacter()
}

fun EditText.extValidateIsEmail(): Boolean {

    if (this.text!!.isEmpty()) {
        this.error = resources.getString(R.string.input_warning_mail)
        return false
    } else {
        val validPatternEmail = Patterns.EMAIL_ADDRESS.matcher(this.text).matches()
        if (validPatternEmail) {
            this.error = null
        } else {
            this.error = resources.getString(R.string.input_error_mail)
            return false
        }
    }
    return true


}

fun EditText.extValidatePassword(): Boolean {
    if (this.text!!.isEmpty()) {
        Toast.makeText(
            this.context,
            resources.getString(R.string.input_warning_password_retry),
            Toast.LENGTH_SHORT
        ).show()
        return false
    } else {
        val passwordPattern = "^(?=.*\\d)(?=.*[a-zA-Z]).{6,20}\$"
        val pattern = Pattern.compile(passwordPattern)
        val match = pattern.matcher(this.text).matches()
        if (match) {
            this.error = null
        } else {
            Toast.makeText(
                this.context,
                resources.getString(R.string.input_error_password_err),
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
    }
    return true
}

fun EditText.extValidateRetryPassword(newPasswordRepeat: EditText): Boolean {
    if (this.text!!.isEmpty()) {
        Toast.makeText(
            this.context,
            resources.getString(R.string.input_warning_password_retry),
            Toast.LENGTH_SHORT
        ).show()
        return false
    } else {
        val passwordPattern = "^(?=.*\\d)(?=.*[a-zA-Z]).{6,20}\$"
        val pattern = Pattern.compile(passwordPattern)
        val match = pattern.matcher(this.text).matches()
        if (match) {
            this.error = null
        } else {
            Toast.makeText(
                this.context,
                resources.getString(R.string.input_error_password_err),
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
    }

    newPasswordRepeat.let {
        if (it.text!!.isEmpty()) {
            Toast.makeText(
                this.context,
                resources.getString(R.string.input_warning_password_retry),
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else {

            if (this.text!!.toString() != it.text!!.toString()) {
                Toast.makeText(
                    this.context,
                    resources.getString(R.string.input_error_password_no_match),
                    Toast.LENGTH_SHORT
                ).show()
                return false
            } else {
                it.error = null
            }
        }
    }
    return true
}

fun CheckBox.extValidateIsChecked(): Boolean {
    if (!this.isChecked) {
        Toast.makeText(
            this.context,
            resources.getString(R.string.registe_privacy_policy),
            Toast.LENGTH_SHORT
        ).show()
        return false
    }
    return true
}

fun EditText.extValidateIsEmpty(): Boolean {
    if (this.text!!.isEmpty()) {
        this.error = resources.getString(R.string.input_error_required_field)
        return false
    } else {
        this.error = null
    }
    return true
}


 */