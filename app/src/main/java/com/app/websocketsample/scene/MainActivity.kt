package com.app.websocketsample.scene

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.websocketsample.R
import com.app.websocketsample.core.extension.ignoreNull
import com.app.websocketsample.core.extension.with
import com.app.websocketsample.core.mvvm.BaseActivity
import com.app.websocketsample.data.entity.Mock
import com.app.websocketsample.databinding.ActivityMainBinding
import com.app.websocketsample.databinding.LayoutItemMockBinding
import com.jakewharton.rxbinding2.view.RxView
import com.minimize.android.rxrecycleradapter.RxDataSource
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_main.*

typealias MockViewModel = MainViewModel.MockViewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private val rxDataSource = RxDataSource<LayoutItemMockBinding, Mock>(R.layout.layout_item_mock, listOf())

    override fun layoutId() = R.layout.activity_main

    override fun instantiateViewModel(): MainViewModel = with(viewModelFactory)

    override fun attachView() = viewModel.attachView(lifecycle)

    override fun setupView() {
        setRecyclerView()

        RxView.clicks(imageButton).subscribe {
            viewModel.messageSubject.onNext(editText.text.toString())
            editText.text.clear()
        }.addTo(disposeBag)
    }

    override fun bindViewModel() {
        viewModel.isLoading.observe(this, Observer {
            progressBar.visibility = if (it ) View.VISIBLE else View.GONE
        })

        viewModel.mocks.observe(this, Observer { updateRecyclerView(it) })
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
