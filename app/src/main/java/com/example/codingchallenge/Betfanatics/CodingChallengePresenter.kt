package com.example.codingchallenge.Betfanatics

import android.util.Log
import com.example.codingchallenge.Data.User
import kotlin.collections.ArrayList

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

    override fun usersResponse(users: ArrayList<User>) {
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

    override fun userResponse(it: User) {
        Log.d(TAG, "user response $it")
        codingChallengeView.showMessage(it.toString())
    }

    override fun showMessage(errorMessage: String) {
        Log.d(TAG, "onErrorResponse $errorMessage")
        codingChallengeView.showMessage(errorMessage)
    }

    override fun totalPages(totalPages: String?) {
        totalPages.let {
            val message = "Total Pages: $totalPages"
            Log.d(TAG, message)
            codingChallengeView.showMessage(message)
        }
    }

    companion object {
        private const val TAG = "CodingChallengePresenter"
        private const val PAGE_NO = 3
        private const val NON_EXISTENT_USER = 5555
        private const val VALID_USER = 3813
        private const val UPDATED_NAME = "New name"
    }
}