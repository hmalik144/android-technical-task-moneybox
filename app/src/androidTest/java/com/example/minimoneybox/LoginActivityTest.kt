package com.example.minimoneybox


import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.minimoneybox.ui.login.LoginActivity
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    val validEmail = "email_address@domain.com"
    val validPassword = "oLa95he!ui"
    val invalidEmail = "email_address"
    val invalidPassword = "oaa"
    val validName = "Ludacris"
    val invalidName = "Dj"


    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(LoginActivity::class.java)

    @Test
    fun noEntriesSubmission_invalidLogin() {
        onView(withId(R.id.btn_sign_in)).perform(click())

        onView(withId(R.id.til_password)).check(matches(hasTextInputLayoutErrorText(getString(R.string.password_error))))
        onView(withId(R.id.til_email)).check(matches(hasTextInputLayoutErrorText(getString(R.string.email_address_error))))
    }

    @Test
    fun validEmailNoPasswordNoName_invalidLogin() {
        onView(withId(R.id.et_email)).perform(typeText(validEmail)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_sign_in)).perform(click())

        onView(withId(R.id.til_password)).check(matches(hasTextInputLayoutErrorText(getString(R.string.password_error))))
        onView(withId(R.id.til_email)).check(matches(not(hasTextInputLayoutErrorText(getString(R.string.email_address_error)))))
    }

    @Test
    fun validPasswordNoEmailNoName_invalidLogin() {
        onView(withId(R.id.et_password)).perform(typeText(validPassword)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_sign_in)).perform(click())

        onView(withId(R.id.til_email)).check(matches(hasTextInputLayoutErrorText(getString(R.string.email_address_error))))
        onView(withId(R.id.til_password)).check(matches(not(hasTextInputLayoutErrorText(getString(R.string.password_error)))))
    }

    @Test
    fun validEmailAndPassword_noName_validLogin() {
        onView(withId(R.id.et_email)).perform(typeText(validEmail)).check(matches(isDisplayed()))
        onView(withId(R.id.et_password)).perform(typeText(validPassword)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_sign_in)).perform(click())

        onView(withId(R.id.til_email)).check(matches(not(hasTextInputLayoutErrorText(getString(R.string.email_address_error)))))
        onView(withId(R.id.til_password)).check(matches(not(hasTextInputLayoutErrorText(getString(R.string.password_error)))))
    }

    @Test
    fun validEmailAndPassword_validName_validLogin() {
        onView(withId(R.id.et_email)).perform(typeText(validEmail)).check(matches(isDisplayed()))
        onView(withId(R.id.et_password)).perform(typeText(validPassword)).check(matches(isDisplayed()))
        onView(withId(R.id.et_name)).perform(typeText(validName)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_sign_in)).perform(click())

        onView(withId(R.id.til_email)).check(matches(not(hasTextInputLayoutErrorText(getString(R.string.email_address_error)))))
        onView(withId(R.id.til_password)).check(matches(not(hasTextInputLayoutErrorText(getString(R.string.password_error)))))
        onView(withId(R.id.til_name)).check(matches(not(hasTextInputLayoutErrorText(getString(R.string.full_name_error)))))
    }

    @Test
    fun validEmailAndPassword_invalidName_validLogin() {
        onView(withId(R.id.et_email)).perform(typeText(validEmail)).check(matches(isDisplayed()))
        onView(withId(R.id.et_password)).perform(typeText(validPassword)).check(matches(isDisplayed()))
        onView(withId(R.id.et_name)).perform(typeText(invalidName)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_sign_in)).perform(click())

        onView(withId(R.id.til_email)).check(matches(not(hasTextInputLayoutErrorText(getString(R.string.email_address_error)))))
        onView(withId(R.id.til_password)).check(matches(not(hasTextInputLayoutErrorText(getString(R.string.password_error)))))
        onView(withId(R.id.til_name)).check(matches(hasTextInputLayoutErrorText(getString(R.string.full_name_error))))
    }

    @Test
    fun invalidEmailValidPasswordNoName_invalidLogin() {
        onView(withId(R.id.et_email)).perform(typeText(invalidEmail)).check(matches(isDisplayed()))
        onView(withId(R.id.et_password)).perform(typeText(validPassword)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_sign_in)).perform(click())

        onView(withId(R.id.til_password)).check(matches(not(hasTextInputLayoutErrorText(getString(R.string.password_error)))))
        onView(withId(R.id.til_email)).check(matches(hasTextInputLayoutErrorText(getString(R.string.email_address_error))))
    }

    @Test
    fun validEmailInvalidPasswordNoName_invalidLogin() {
        onView(withId(R.id.et_email)).perform(typeText(validEmail)).check(matches(isDisplayed()))
        onView(withId(R.id.et_password)).perform(typeText(invalidPassword)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_sign_in)).perform(click())

        onView(withId(R.id.til_password)).check(matches(hasTextInputLayoutErrorText(getString(R.string.password_error))))
        onView(withId(R.id.til_email)).check(matches(not(hasTextInputLayoutErrorText(getString(R.string.email_address_error)))))
    }

    private fun getString(id: Int): String = mActivityTestRule.activity.getString(id)

    private fun hasTextInputLayoutErrorText(expectedErrorText: String): Matcher<View?>? {
        return object : TypeSafeMatcher<View?>() {

            override fun matchesSafely(item: View?): Boolean {
                if (item !is TextInputLayout) {
                    return false
                }
                val error = (item).error ?: return false
                val hint = error.toString()
                return expectedErrorText == hint
            }

            override fun describeTo(description: org.hamcrest.Description?) {}
        }
    }

}
