package com.shoaib.imagefilterapp.dependencyinjection

import com.shoaib.imagefilterapp.viewmodels.EditImageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { EditImageViewModel(editImageRepository = get()) }
}