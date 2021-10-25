package com.shoaib.imagefilterapp.dependencyinjection

import com.shoaib.imagefilterapp.repositories.EditImageRepository
import com.shoaib.imagefilterapp.repositories.EditImageRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val repositoryViewModule = module {
    factory<EditImageRepository> { EditImageRepositoryImpl(androidContext())  }
}