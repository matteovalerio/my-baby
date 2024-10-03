package com.matteo.mybaby2.koin

import com.matteo.mybaby2.modules.babies.BabyViewModel
import com.matteo.mybaby2.modules.babies.repositories.IBabyRepository
import com.matteo.mybaby2.modules.babies.repositories.MockedBabyRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::MockedBabyRepository) {bind<IBabyRepository>()}
    viewModelOf(::BabyViewModel)
}