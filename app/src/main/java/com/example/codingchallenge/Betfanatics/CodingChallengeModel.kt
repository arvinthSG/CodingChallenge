package com.example.codingchallenge.Betfanatics

import android.util.Log
import com.example.codingchallenge.Betfanatics.CodingChallengeContract.Model.OnResponseListener
import com.example.codingchallenge.Data.User
import com.example.codingchallenge.Network.CodingChallengeService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CodingChallengeModel : CodingChallengeContract.Model {

    private lateinit var onResponseListener: OnResponseListener
    private lateinit var codingChallengeService: CodingChallengeService
    private val compositeDisposable = CompositeDisposable()

    override fun init(
        onResponseListener: OnResponseListener,
        codingChallengeService: CodingChallengeService
    ) {
        this.onResponseListener = onResponseListener
        this.codingChallengeService = codingChallengeService
    }

    override fun getUsersByPageNo(pageNo: Int) {
        compositeDisposable.add(
            codingChallengeService.getUserByPageNo(pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    it.let {
                        val totalPages = it.headers().get(TOTAL_PAGE_NUMBER)
                        onResponseListener.totalPages(totalPages)
                        it.body().let { usersList ->
                            if (usersList.isNullOrEmpty()) {
                                onResponseListener.emptyResponse()
                            } else {
                                onResponseListener.usersResponse(usersList)
                            }

                        }
                    }
                }, {
                    Log.d(TAG, it.message.toString())
                }
                )
        )
    }

    override fun updateUser(updatedUser: User) {
        compositeDisposable.add(
            codingChallengeService.updateUser(updatedUser.id.toInt(), updatedUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        onResponseListener.showMessage("User updated")
                    }, {
                        onResponseListener.showMessage("User update error: ${it.message}")
                    }
                )
        )
    }

    override fun retreiveUser(userID: Int) {
        compositeDisposable.add(
            codingChallengeService.getUsersByID(userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it != null) {
                        onResponseListener.userResponse(it)
                    }
                }, {
                    onResponseListener.showMessage(it.message.toString())
                }
                )
        )
    }

    override fun deleteUser(userID: Int) {
        compositeDisposable.add(
            codingChallengeService.deleteUser(userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        onResponseListener.showMessage("User deleted")

                    }, {
                        onResponseListener.showMessage("User deletion failed :${it.message}")
                    }
                )
        )
    }

    override fun destroy() {
        compositeDisposable.clear()
    }

    companion object {
        const val TAG = "CodingChallengeModel"
        const val TOTAL_PAGE_NUMBER = "X-Pagination-Pages"
    }
}