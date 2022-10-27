package com.example.codingchallenge.BetFanatics

import com.example.codingchallenge.Betfanatics.CodingChallengeContract
import com.example.codingchallenge.Betfanatics.CodingChallengeFragmentView
import com.example.codingchallenge.Betfanatics.CodingChallengeModel
import com.example.codingchallenge.Betfanatics.CodingChallengePresenter
import com.example.codingchallenge.Data.User
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class CodingChallengePresenterTest {

    lateinit var presenter: CodingChallengePresenter
    lateinit var view: CodingChallengeContract.View
    lateinit var model: CodingChallengeContract.Model
    lateinit var user: User
    lateinit var updatedUser: User

    @Before
    fun setup() {
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
    fun `assert that onViewLoaded calls model init`() {
        presenter.onViewLoaded()

        verify(model).init(presenter)
    }

    @Test
    fun `assert that onViewDetached calls model destroy`() {
        presenter.onViewDetached()

        verify(model).destroy()
    }

    @Test
    fun `retrieverPage calls getUsersByPageNo with provided page`() {
        presenter.retrievePage(PAGE_4)

        verify(model).getUsersByPageNo(PAGE_4.toInt())
    }

    @Test
    fun `retrieverPage calls default getUsersByPageNo with no provided page`() {
        presenter.retrievePage("")

        verify(model).getUsersByPageNo(DEFAULT_PAGE)
    }

    @Test
    fun `userResponse calls view with user details`() {
        presenter.userResponse(user)

        verify(view).showMessage(user.toString())
    }

    @Test
    fun `userResponse with multiple page calls view with last user name`() {
        val listOfUser = ArrayList<User>()
        listOfUser.add(user)
        listOfUser.add(user)
        presenter.usersResponse(listOfUser)

        verify(view).showMessage(LAST_USER_DETAIL)
        verify(model).updateUser(updatedUser)
    }

    @Test
    fun `showMessage calls view's showMessage`() {
        presenter.showMessage(LAST_USER_DETAIL)

        verify(view).showMessage(LAST_USER_DETAIL)
    }

    @Test
    fun `deleteUser calls model's deleteUser`() {
        presenter.deleteUser(PAGE_4)

        verify(model).deleteUser(PAGE_4.toInt())
    }

    @Test
    fun `deleteUser without id calls model's deleteUser with default value`() {
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