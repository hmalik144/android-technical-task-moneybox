package com.example.minimoneybox.ui.login

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.minimoneybox.R
import com.example.minimoneybox.ui.user.AccountActivity
import com.example.minimoneybox.utils.displayToast
import com.example.minimoneybox.utils.hide
import com.example.minimoneybox.utils.show
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.util.regex.Pattern
import android.util.Patterns.EMAIL_ADDRESS

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity(), KodeinAware {

    //retrieve the viewmodel factory from the kodein dependency injection
    override val kodein by kodein()
    private val factory : LoginViewModelFactory by instance()

    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //views replaced with kotlin synthetic views
        //no need to use findViewById()
        setupViews()
        setupViewModel()
        setupLiveData()
    }

    override fun onStart() {
        super.onStart()
        setupAnimation()
    }

    private fun setupViews() {
        btn_sign_in.setOnClickListener {
            if (allFieldsValid()) {
                progress_circular.show()
                startLogin()
            }
        }
    }

    private fun startLogin(){
        viewModel.attemptLogin(
            et_email.text.toString(),
            et_password.text.toString(),
            et_name.text.toString()
        )
    }

    private fun allFieldsValid(): Boolean {
        var validity = true
        //Using Android Email address matcher
        if (!EMAIL_ADDRESS.matcher(et_email.text.toString()).matches()) {
            til_email.error = getString(R.string.email_address_error)
            validity = false
        }

        if (!Pattern.matches(PASSWORD_REGEX, et_password.text.toString())) {
            til_password.error = getString(R.string.password_error)
            validity = false
        }

        if (et_name.text.isNotEmpty() &&
            !Pattern.matches(NAME_REGEX, et_name.text.toString())
        ) {
            til_name.error = getString(R.string.full_name_error)
            validity = false
        }

        return validity
    }

    private fun setupAnimation() {
        //animation completion listener attached
        //once the animation has run from frame first to frame last
        //then set new frame min and max then play animation again
        animation_view.apply {
            setAnimation("pig.json")
            setMinAndMaxFrame(firstAnim.first, firstAnim.second)
            addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationEnd(animation: Animator?) {
                    setMinAndMaxFrame(secondAnim.first, secondAnim.second)
                    playAnimation()
                }
                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {}
            })

            playAnimation()
        }

    }

    private fun setupViewModel(){
        viewModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)
    }

    private fun setupLiveData(){
        viewModel.operationFailed.observe(this, Observer {
            progress_circular.hide()
            if (it != null) {
                displayToast(it)
            }
        })

        viewModel.operationSuccess.observe(this, Observer {
            progress_circular.hide()
            if (it == true){ startNewActivity() }
        })
    }

    private fun startNewActivity(){
        Intent(this, AccountActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra("NAME_USER", viewModel.name);
            startActivity(this)
        }
    }

    companion object {
        val EMAIL_REGEX = "[^@]+@[^.]+\\..+"
        val NAME_REGEX = "[a-zA-Z]{6,30}"
        val PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[A-Z]).{10,50}$"
        val firstAnim = 0 to 109
        val secondAnim = 131 to 158
    }
}
