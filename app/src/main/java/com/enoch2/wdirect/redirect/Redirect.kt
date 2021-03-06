package com.enoch2.wdirect.redirect

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.MutableState

fun redirectWithoutMsg(phoneNumber: MutableState<String>, message: MutableState<String>,
                       link: MutableState<String>, context: Context) {
    if (phoneNumber.value == ""){
        Toast.makeText(context, "Enter a number to continue",
            Toast.LENGTH_SHORT).show()
    } else {
        link.value = link.value + phoneNumber.value
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link.value)))
        link.value = "https://wa.me/"
        phoneNumber.value = ""
        message.value = ""
    }
}

fun redirectWithMsg(phoneNumber: MutableState<String>, message: MutableState<String>,
                       link: MutableState<String>, linkWithMsg: MutableState<String>,
                        context: Context) {
    if (phoneNumber.value == "" || message.value == ""){
        Toast.makeText(context, "Enter a number and message to continue",
            Toast.LENGTH_SHORT).show()
    } else {
        linkWithMsg.value = link.value + phoneNumber.value + "?text=" +
                createLinkWithMsg(message.value)
        context.startActivity(Intent(Intent.ACTION_VIEW,
            Uri.parse(linkWithMsg.value)))
        link.value = "https://wa.me/"
        phoneNumber.value = ""
        message.value = ""
    }
}

fun createLinkWithMsg(message: String): String {
    return message.replace(" ", "%20")
}
