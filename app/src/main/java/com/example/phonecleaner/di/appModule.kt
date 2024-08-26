package com.example.phonecleaner.di

import com.example.phonecleaner.domain.repository.Repository
import com.example.phonecleaner.presentation.viewmodel.MainViewModel
import org.koin.dsl.module

val appModule = module {
    single { Repository() }
    single { MainViewModel(get()) }
}