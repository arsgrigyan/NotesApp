package com.southernsunrise.notesapp.utils

import androidx.annotation.AnimRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.southernsunrise.notesapp.R

fun NavController.currentDestinationID() = this.currentDestination?.id

fun AppCompatActivity.openFragment(
    containerID: Int,
    fragment: Fragment,
    addToBackStack: Boolean = true,
    replace: Boolean = false,
    @AnimRes enterAnimationResId: Int = R.anim.enter_from_bottom,
    @AnimRes exitAnimationResId: Int = R.anim.exit_to_top,
    @AnimRes popEnterAnimationResId: Int = R.anim.enter_from_top,
    @AnimRes popExitAnimationResId: Int = R.anim.exit_to_bottom
) {
    val transaction = this.supportFragmentManager.beginTransaction()
    transaction.apply {
        setCustomAnimations(
            enterAnimationResId,
            exitAnimationResId,
            popEnterAnimationResId,
            popExitAnimationResId
        )
        if (replace) replace(containerID, fragment) else add(containerID, fragment, fragment.tag)
        if (addToBackStack) addToBackStack(fragment.tag)
    }
    transaction.commit()

}