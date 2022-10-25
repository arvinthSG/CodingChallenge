package com.example.codingchallenge.Betfanatics

import android.util.Log
import com.example.codingchallenge.Data.User
import kotlin.collections.ArrayList


//Retrieve page 3 of the list of all users. - DONE
//Using a logger, log the total number of pages from the previous request.
//Sort the retrieved user list by name. - DONE
//After sorting, log the name of the last user. - DONE
//Update that user's name to a new value and use the correct http method to save it.
//Delete that user.
//Attempt to retrieve a nonexistent user with ID 5555. Log the resulting http response code. - DONE
//Write unit tests for all code, mocking out calls to the actual API service.


class CodingChallengePresenter(
    private val codingChallengeView: CodingChallengeContract.View,
    private val codingChallengeModel: CodingChallengeContract.Model
) : CodingChallengeContract.Presenter, CodingChallengeContract.Model.OnResponseListener {
    override fun onViewLoaded() {
        Log.d(TAG, "onViewLoaded()")
        codingChallengeModel.init(this)
        codingChallengeModel.getUsersByPageNo(PAGE_NO)
        codingChallengeModel.retreiveUser(VALID_USER)
    }

    override fun onViewDetached() {
        TODO("Not yet implemented")
    }

    override fun retrievePage() {
        codingChallengeModel.getUsersByPageNo(3)
    }

    override fun retrieveNonExistentUser() {
        codingChallengeModel.retreiveUser(NON_EXISTENT_USER)
    }

    override fun emptyResponse() {
        Log.d(TAG, "emptyResponse()")
    }

    override fun UsersResponse(users: ArrayList<User>) {
        Log.d(TAG, "users retrieved : ${users.size}")

        users.sortBy { it.name }
        Log.d(TAG, "user 10 ${users[users.size - 1]}")
        val lastUser = users[users.size - 1]

        val updatedUser = User(
            id = lastUser.id,
            name = "New name",
            gender = lastUser.gender,
            email = lastUser.email,
            status = lastUser.status
        )

        codingChallengeModel.updateUser(updatedUser)
    }

    override fun UserResponse(it: User) {
        Log.d(TAG, "user response $it")
    }

    override fun errorResponse(errorMessage: String) {
        Log.d(TAG, "onErrorResponse $errorMessage")
    }

    companion object {
        private const val TAG = "CodingChallengePresenter"
        private const val PAGE_NO = 3
        private const val NON_EXISTENT_USER = 5555
        private const val VALID_USER = 3875
    }
}