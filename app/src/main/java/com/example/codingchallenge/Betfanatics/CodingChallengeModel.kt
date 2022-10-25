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
    private lateinit var getCodingChallengeService: CodingChallengeService
    private val compositeDisposable = CompositeDisposable()

    override fun init(onResponseListener: OnResponseListener) {
        this.onResponseListener = onResponseListener
        service = CodingChallengeNetworkInstance.provideNetworkInstance()
        getCodingChallengeService = service.create(CodingChallengeService::class.java)
    }

    override fun getUsersByPageNo(pageNo: Int) {
        compositeDisposable.add(
            getCodingChallengeService.getUserByPageNo(pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    it.let {
                        if (it.isEmpty()) {
                            onResponseListener.emptyResponse()
                        } else {
                            onResponseListener.UsersResponse(it)
                        }
                    }

                }, {
                    Log.d(TAG, it.message.toString())
                }
                )
        )
    }

    override fun updateUser(updatedUser: User) {

    }

    override fun retreiveUser(userID: Int) {
        compositeDisposable.add(
            getCodingChallengeService.getUsersByID(userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it != null) {
                        onResponseListener.UserResponse(it)
                    }
                }, {
                    onResponseListener.errorResponse(it.localizedMessage.toString())
//                    Log.d(TAG, it.localizedMessage)
                }
                )
        )
    }

    companion object {
        const val TAG = "CodingChallengeModel"
    }
}