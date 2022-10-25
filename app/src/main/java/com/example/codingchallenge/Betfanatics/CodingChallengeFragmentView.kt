package com.example.codingchallenge.Betfanatics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.codingchallenge.R


/**
 * A simple [Fragment] subclass.
 * Use the [CodingChallengeFragmentView.newInstance] factory method to
 * create an instance of this fragment.
 */
class CodingChallengeFragmentView : Fragment(), CodingChallengeContract.View {
    private lateinit var presenter: CodingChallengePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = CodingChallengePresenter(this, CodingChallengeModel())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_view, container, false)
    }

    override fun onResume() {
        super.onResume()
        presenter.onViewLoaded()
    }

    companion object {
        const val TAG = "HomeFragmentView"
        fun newInstance() = CodingChallengeFragmentView()

    }
}