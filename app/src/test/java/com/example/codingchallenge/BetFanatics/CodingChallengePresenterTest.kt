package com.example.codingchallenge.BetFanatics

import com.example.codingchallenge.Betfanatics.CodingChallengeContract
import com.example.codingchallenge.Betfanatics.CodingChallengeFragmentView
import com.example.codingchallenge.Betfanatics.CodingChallengeModel
import com.example.codingchallenge.Betfanatics.CodingChallengePresenter
import com.example.codingchallenge.Data.User
import com.example.codingchallenge.Network.CodingChallengeNetworkInstance
import com.example.codingchallenge.Network.CodingChallengeService
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class CodingChallengePresenterTest {

    lateinit var presenter: CodingChallengePresenter
    lateinit var view: CodingChallengeContract.View
    lateinit var model: CodingChallengeContract.Model
    lateinit var user: User
    lateinit var updatedUser: User

    @Before
    fun setup() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler {
            Schedulers.trampoline()
        }
        view = mock(CodingChallengeFragmentView::class.java)
        model = mock(CodingChallengeModel::class.java)
        presenter = CodingChallengePresenter(view, model)
        user = User(
            id = 123,
            name = "name",
            gender = "gender",
            email = "email",
            status = "status"
        )

        updatedUser = User(
            id = 123,
            name = "New name",
            gender = "gender",
            email = "email",
            status = "status"
        )
    }

    @Test
    fun `when retrievePage is called, getUsersByPageNo in model is called `() {
        presenter.retrievePage(PAGE_4)

        verify(model).getUsersByPageNo(PAGE_4.toInt())
    }

    @Test
    fun `when retrievePage is called without argument, getUserByPageNo is called with default PageNo`() {
        presenter.retrievePage("")

        verify(model).getUsersByPageNo(DEFAULT_PAGE)
    }

    @Test
    fun `when userRepsonse is called, showMessage in View is called`() {
        presenter.userResponse(user)

        verify(view).showMessage(user.toString())
    }

    @Test
    fun `when usersResposne is called, showMessage is view is called with Last User detail, updateUser is called in model`() {
        val listOfUser = ArrayList<User>()
        listOfUser.add(user)
        listOfUser.add(user)
        presenter.usersResponse(listOfUser)

        verify(view).showMessage(LAST_USER_DETAIL)
        verify(model).updateUser(updatedUser)
    }

    @Test
    fun `when showMessage is called, showMessage in View is called`() {
        presenter.showMessage(LAST_USER_DETAIL)

        verify(view).showMessage(LAST_USER_DETAIL)
    }

    @Test
    fun `when deleteUser is called, deleteUser in model is called`() {
        presenter.deleteUser(PAGE_4)

        verify(model).deleteUser(PAGE_4.toInt())
    }

    @Test
    fun `when deleteUser has no id, deleteUser in model is called with VALID_User id`() {
        presenter.deleteUser("")

        verify(model).deleteUser(VALID_USER)
    }

    companion object {
        private const val DEFAULT_PAGE = 3
        private const val PAGE_4 = "4"
        private const val LAST_USER_DETAIL = "Last user name: name id:123"
        private const val VALID_USER = 3813
    }
}