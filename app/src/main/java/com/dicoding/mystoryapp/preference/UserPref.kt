package com.dicoding.mystoryapp.preference

import android.content.Context

class UserPref(context:Context) {
    companion object{
        const val SP_NAME="user_pref"

        const val USER_ID="user_id"
        const val NAME="name"
        const val TOKEN="token"
    }

    val preference = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)

    fun setUser(userModel: UserModel){
        val prefEditor = preference.edit()
        prefEditor.putString(USER_ID, userModel.userId)
        prefEditor.putString(NAME, userModel.name)
        prefEditor.putString(TOKEN, userModel.token)
        prefEditor.apply()
    }

    fun getUser():UserModel{
        val userModel = UserModel()
        userModel.userId = preference.getString(USER_ID, "")
        userModel.name = preference.getString(NAME, "")
        userModel.token = preference.getString(TOKEN, "")

        return userModel
    }

}