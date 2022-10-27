package com.example.codingchallenge.Betfanatics

import com.example.codingchallenge.Data.User

interface CodingChallengeContract {

    interface View {
        fun showMessage(message: String)
    }

    interface Presenter {
        fun onViewLoaded()
        fun onViewDetached()
        fun retrievePage(text: String)
        fun retrieveNonExistentUser()
        fun deleteUser(userID: String)
        fun retrieveUser(userID: String)
    }

    interface Model {
        fun init(onResponseListener: OnResponseListener)
        fun getUsersByPageNo(pageNo: Int)
        fun updateUser(updatedUser: User)
        fun retreiveUser(userID: Int)
        fun deleteUser(userID: Int)
        fun destroy()

        interface OnResponseListener {
            fun emptyResponse()
            fun UsersResponse(users: ArrayList<User>)
            fun UserResponse(it: User)
            fun showMessage(errorMessage: String)
        }
    }
}