package com.app.websocketsample.scene

import androidx.recyclerview.widget.LinearLayoutManager
import com.app.websocketsample.R
import com.app.websocketsample.core.extension.*
import com.app.websocketsample.core.mvvm.BaseActivity
import com.app.websocketsample.data.entity.Mock
import com.app.websocketsample.databinding.ActivityMainBinding
import com.app.websocketsample.databinding.LayoutItemMockBinding
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.minimize.android.rxrecycleradapter.RxDataSource
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_main.*

typealias Inputs = MainViewModel.Inputs
typealias MockViewModel = MainViewModel.MockViewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private val rxDataSource = RxDataSource<LayoutItemMockBinding, Mock>(R.layout.layout_item_mock, listOf())

    override fun layoutId() = R.layout.activity_main

    override fun instantiateViewModel(): MainViewModel = with(viewModelFactory)

    override fun attachView() = viewModel.attachView(lifecycle)

    override fun setupView() {
        setRecyclerView()
    }

    override fun bindViewModel() {
        val messageText = RxTextView.textChanges(editText).map { it.toString() }
        val sendButtonTap = RxView.clicks(imageButton).map { Unit }

        val inputs = Inputs(messageText, sendButtonTap)
        val outputs = viewModel.makeOutputFrom(inputs)

        outputs.mocks
            .subscribe {
                updateRecyclerView(it)
            }.addTo(disposeBag)

        outputs.isLoading
            .subscribe {
                if (it ) progressBar.show() else progressBar.hide()
            }.addTo(disposeBag)

        outputs.error
            .subscribe {
                toast(it.toString())
            }.addTo(disposeBag)

        outputs.clearEditText
            .subscribe {
                editText.text.clear()
            }.addTo(disposeBag)

        outputs.updateList
            .subscribe {
                updateRecyclerView(it)
            }.addTo(disposeBag)
    }

    private fun updateRecyclerView(mocks : List<Mock>) {
        runOnUiThread { rxDataSource.updateDataSet(mocks).updateAdapter() }
    }

    private fun setRecyclerView(){
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        rxDataSource
            .asObservable()
            .subscribe {
                val binding = it.viewDataBinding ?: return@subscribe
                it.item?.let{ i ->
                    binding.viewModel = MockViewModel(i.id.toString(), i.name.ignoreNull())
                }
            }.addTo(disposeBag)

        rxDataSource.bindRecyclerView(recyclerView)
    }
}
