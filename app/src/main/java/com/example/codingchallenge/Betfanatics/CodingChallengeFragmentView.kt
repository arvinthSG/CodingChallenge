package com.example.codingchallenge.Betfanatics

import android.content.Context
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.codingchallenge.R


/**
 * A simple [Fragment] subclass.
 * Use the [CodingChallengeFragmentView.newInstance] factory method to
 * create an instance of this fragment.
 */
class CodingChallengeFragmentView : Fragment(), CodingChallengeContract.View {
    private lateinit var presenter: CodingChallengePresenter
    private lateinit var etPageNo: EditText
    private lateinit var etDelete: EditText
    private lateinit var etRetrieveUser: EditText
    private lateinit var btnLoadPageNo: Button
    private lateinit var btnDeleteUser: Button
    private lateinit var btnRetrieveUser: Button
    private lateinit var tvMessageBox: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = CodingChallengePresenter(this, CodingChallengeModel())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_view, container, false)
        etPageNo = view.findViewById(R.id.et_page_no)
        etDelete = view.findViewById(R.id.et_delete_user_id)
        etRetrieveUser = view.findViewById(R.id.et_user_id)
        btnLoadPageNo = view.findViewById<Button?>(R.id.btn_load_page_no).apply {
            setOnClickListener {
                presenter.retrievePage(etPageNo.text.toString())
                it.hideKeyboard()
            }
        }
        btnDeleteUser = view.findViewById<Button?>(R.id.btn_delete).apply {
            setOnClickListener {
                presenter.deleteUser(etDelete.text.toString())
                it.hideKeyboard()
            }
        }
        btnRetrieveUser = view.findViewById<Button?>(R.id.btn_retrieve_user).apply {
            setOnClickListener {
                presenter.retrieveUser(etRetrieveUser.text.toString())
                it.hideKeyboard()
            }
        }
        tvMessageBox = view.findViewById(R.id.tv_message_box)
        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.onViewLoaded()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDetached()
    }

    companion object {
        const val TAG = "HomeFragmentView"
        fun newInstance() = CodingChallengeFragmentView()

    }

    fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun showMessage(message: String) {
        val update = StringBuilder(tvMessageBox.text)
        update.append("\n")
        update.append(message)
        tvMessageBox.text = update.toString()
    }
}