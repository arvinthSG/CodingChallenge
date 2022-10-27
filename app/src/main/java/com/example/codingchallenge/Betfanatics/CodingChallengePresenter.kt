package com.example.codingchallenge.Betfanatics

import android.util.Log
import com.example.codingchallenge.Data.User
import kotlin.collections.ArrayList


//Retrieve page 3 of the list of all users. - DONE
//Using a logger, log the total number of pages from the previous request.
//Sort the retrieved user list by name. - DONE
//After sorting, log the name of the last user. - DONE
//Update that user's name to a new value and use the correct http method to save it - done
//Delete that user - done
//Attempt to retrieve a nonexistent user with ID 5555. Log the resulting http response code. - DONE
//Write unit tests for all code, mocking out calls to the actual API service.


class CodingChallengePresenter(
    private val codingChallengeView: CodingChallengeContract.View,
    private val codingChallengeModel: CodingChallengeContract.Model
) : CodingChallengeContract.Presenter, CodingChallengeContract.Model.OnResponseListener {
    override fun onViewLoaded() {
        Log.d(TAG, "onViewLoaded()")
        codingChallengeModel.init(this)
    }

    override fun onViewDetached() {
        codingChallengeModel.destroy()
    }

    override fun retrievePage(text: String) {
        var pageNO = PAGE_NO
        if (text.isNotEmpty()) {
            pageNO = text.toInt()
        }
        codingChallengeModel.getUsersByPageNo(pageNO)
    }

    override fun deleteUser(userID: String) {
        var id = VALID_USER
        if (userID.isNotEmpty()) {
            id = userID.toInt()
        }
        codingChallengeModel.deleteUser(id)
    }

    override fun retrieveUser(userID: String) {
        var id = VALID_USER
        if (userID.isNotEmpty()) {
            id = userID.toInt()
        }
        codingChallengeModel.retreiveUser(id)
    }

    override fun retrieveNonExistentUser() {
        codingChallengeModel.retreiveUser(NON_EXISTENT_USER)
    }

    override fun emptyResponse() {
        Log.d(TAG, "emptyResponse()")
    }

    override fun UsersResponse(users: ArrayList<User>) {
        users.sortBy { it.name }
        val lastUser = users[users.size - 1]
        val userDetails = "Last user name: ${lastUser.name} id:${lastUser.id}"

        Log.d(TAG, "last user $userDetails")
        codingChallengeView.showMessage(userDetails)

        val updatedUser = User(
            id = lastUser.id,
            name = UPDATED_NAME,
            gender = lastUser.gender,
            email = lastUser.email,
            status = lastUser.status
        )

        codingChallengeModel.updateUser(updatedUser)
//        codingChallengeModel.deleteUser(updatedUser.id.toInt())
    }

    override fun UserResponse(it: User) {
        Log.d(TAG, "user response $it")
        val updatedUser = User(
            id = it.id,
            name = UPDATED_NAME,
            gender = it.gender,
            email = it.email,
            status = it.status
        )
        codingChallengeView.showMessage(it.toString())
    }

    override fun showMessage(errorMessage: String) {
        Log.d(TAG, "onErrorResponse $errorMessage")
        codingChallengeView.showMessage(errorMessage)
    }

    companion object {
        private const val TAG = "CodingChallengePresenter"
        private const val PAGE_NO = 3
        private const val NON_EXISTENT_USER = 5555
        private const val VALID_USER = 3813
        private const val UPDATED_NAME = "New name"
    }
}