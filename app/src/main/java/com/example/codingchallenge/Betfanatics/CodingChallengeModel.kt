package com.example.codingchallenge.Betfanatics

import android.util.Log
import com.example.codingchallenge.Betfanatics.CodingChallengeContract.Model.OnResponseListener
import com.example.codingchallenge.Data.User
import com.example.codingchallenge.Network.CodingChallengeNetworkInstance
import com.example.codingchallenge.Network.CodingChallengeService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class CodingChallengeModel : CodingChallengeContract.Model {

    private lateinit var onResponseListener: OnResponseListener
    private lateinit var service: Retrofit
    private lateinit var codingChallengeService: CodingChallengeService
    private val compositeDisposable = CompositeDisposable()

    override fun init(onResponseListener: OnResponseListener) {
        this.onResponseListener = onResponseListener
        service = CodingChallengeNetworkInstance.provideNetworkInstance()
        codingChallengeService = service.create(CodingChallengeService::class.java)
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
                        if (it.isSuccessful) {
                            onResponseListener.showMessage("User updated")
                        } else {
                            onResponseListener.showMessage("User updation error")
                        }
                    }, {
                        onResponseListener.showMessage(it.message.toString())
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
                        if (it.isSuccessful) {
                            onResponseListener.showMessage("User deleted")
                        } else {
                            onResponseListener.showMessage("User deletion failed")
                        }
                    }, {
                        onResponseListener.showMessage(it.message.toString())
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