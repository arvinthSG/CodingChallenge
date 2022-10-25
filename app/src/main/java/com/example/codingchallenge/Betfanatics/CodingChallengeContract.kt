package com.example.codingchallenge.Betfanatics

import com.example.codingchallenge.Data.User

interface CodingChallengeContract {

    interface View {

    }

    interface Presenter {
        fun onViewLoaded()
        fun onViewDetached()
        fun retrievePage()
        fun retrieveNonExistentUser()
    }

    interface Model {
        fun init(onResponseListener: OnResponseListener)
        fun getUsersByPageNo(pageNo: Int)
        fun updateUser(updatedUser: User)
        fun retreiveUser(userID: Int)

        interface OnResponseListener {
            fun emptyResponse()
            fun UsersResponse(users: ArrayList<User>)
            fun UserResponse(it: User)
            fun errorResponse(errorMessage: String)
        }
    }
}