package com.example.codingchallenge.BetFanatics

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.codingchallenge.Betfanatics.CodingChallengeContract
import com.example.codingchallenge.Betfanatics.CodingChallengeModel
import com.example.codingchallenge.Betfanatics.CodingChallengePresenter
import com.example.codingchallenge.Data.User
import com.example.codingchallenge.Network.CodingChallengeService
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.Mockito.mock

class CodingChallengeModelTest {
    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    val dummyUser = User(
        id = 123,
        name = "name",
        gender = "gender",
        email = "email",
        status = "status"
    )

    @Mock
    private lateinit var codingChallengeService: CodingChallengeService
    private lateinit var onResponseListener: CodingChallengeContract.Model.OnResponseListener
    private lateinit var codingChallengeModel: CodingChallengeModel

    @Before
    fun setup() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler {
            Schedulers.trampoline()
        }
        onResponseListener = mock(CodingChallengePresenter::class.java)
        codingChallengeModel = CodingChallengeModel()
        codingChallengeService = mock(CodingChallengeService::class.java)

        codingChallengeModel.init(
            onResponseListener,
            codingChallengeService
        )
    }

    @Test
    fun `when getUserByID is called, userResponse gets called in Presenter`() {
        val observable = Observable.just(dummyUser)
        `when`(codingChallengeService.getUsersByID(3)).thenReturn(
            observable
        )
        codingChallengeModel.retreiveUser(3)

        verify(onResponseListener).userResponse(dummyUser)
    }

    @Test
    fun `when getUserByID throws error, showMessage gets called in Presenter`() {
        `when`(codingChallengeService.getUsersByID(3)).thenReturn(
            Observable.error(Throwable("404"))
        )
        codingChallengeModel.retreiveUser(3)

        verify(onResponseListener).showMessage("404")
    }

    @Test
    fun `when deleteUser successful, show success message`() {
        val list = ArrayList<User>()
        list.add(dummyUser)
        list.add(dummyUser)
        `when`(codingChallengeService.deleteUser(3)).thenReturn(Completable.complete())

        codingChallengeModel.deleteUser(3)

        verify(onResponseListener).showMessage("User deleted")
    }

    @Test
    fun `when deleteUser failed, show error message`() {
        `when`(codingChallengeService.deleteUser(3))
            .thenReturn(Completable.error(Throwable("404")))

        codingChallengeModel.deleteUser(3)

        verify(onResponseListener).showMessage("User deletion failed :404")
    }

    @Test
    fun `when userUpdate successful, show success message`() {
        `when`(codingChallengeService.updateUser(dummyUser.id.toInt(), dummyUser))
            .thenReturn(Completable.complete())

        codingChallengeModel.updateUser(dummyUser)

        verify(onResponseListener).showMessage("User updated")
    }

    @Test
    fun `when userUpdate failed, show error message`() {
        `when`(codingChallengeService.updateUser(dummyUser.id.toInt(), dummyUser))
            .thenReturn(Completable.error(Throwable("404")))

        codingChallengeModel.updateUser(dummyUser)

        verify(onResponseListener).showMessage("User update error: 404")
    }
}