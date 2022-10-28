package com.example.codingchallenge.Betfanatics

import com.example.codingchallenge.Data.User
import com.example.codingchallenge.Network.CodingChallengeService

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
        fun init(onResponseListener: OnResponseListener, codingChallengeService: CodingChallengeService)
        fun getUsersByPageNo(pageNo: Int)
        fun updateUser(updatedUser: User)
        fun retreiveUser(userID: Int)
        fun deleteUser(userID: Int)
        fun destroy()

        interface OnResponseListener {
            fun emptyResponse()
            fun usersResponse(users: ArrayList<User>)
            fun userResponse(it: User)
            fun showMessage(errorMessage: String)
            fun totalPages(totalPages: String?)
        }
    }
}