# MoneyBox Android Technical Task

A small application for viewing investment products.

# Part A

### Bug 1 - Layout does not look as expected

 - Constraints added to all TextInputLayouts
 - Edit text views within TextInputLayouts and TextInputLayouts within match parent and the TextInputLayouts match parents
 - correct spacing between edges margin of 12dp left and right
 - email TextInputLayout with a 48dp margin at the top

### Bug 2 - Validation is incorrect

 - Email matching android email matcher
 - allValid within allFieldsValid() is true by default
 - once allValid changes to false it remains false and no login called
 - Changes to false every failed validation

### Bug 3 - Animation is looping incorrectly
 - set minimum and maximum frames based the firstAnim Pair<Int, Int> range respectively
 - set an animation completion listener and wait for first animation play to finish
 - on completion of first animation change the min and max frames based on secondAnim Pair<Int, Int> range
 - play animation with new min and max frames

# Part B

## Requirements

Minimum android SDK version 21, Android 5.0.0 (Lollipop)
Permissions : Internet, Network State

## Features

 - Login (Name optional)
 - View investment products
 - Add one off payment of £20 to an investment


## Architectural Pattern

MVVM - Model View Viewmodel
SOLID coding

## Jetpack

* [AndroidX](https://developer.android.com/jetpack)

## Unit tests

### Test case one
 - Respository Unit test (Networkings)

### Test case two
 - Repository Unit test (Storage)

### Test case one
 - Login viewmodel test

### Test case two
 - UserAccount viewmodel test

 ## Integration tests

### Test case one
 - LoginActivity UI test

## Built With

* [Kodein](https://github.com/Kodein-Framework/Kodein-DI) - Painless Kotlin Dependency Injection
* [Retrofit](https://github.com/square/retrofit) - Type-safe HTTP client for Android and Java by Square, Inc
* [Secured Preference Store](https://github.com/iamMehedi/Secured-Preference-Store) - A SharedPreferences wrapper for Android that encrypts the content with 256 bit AES encryption
* [Lottie](https://github.com/airbnb/lottie-android) - Lottie is a mobile library for Android and iOS that parses Adobe After Effects animations exported as json with Bodymovin and renders them natively on mobile!

## Submitted by

* **Haider Malik** - *Android Developer*


