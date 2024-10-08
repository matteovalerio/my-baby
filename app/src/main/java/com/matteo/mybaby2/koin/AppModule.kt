package com.matteo.mybaby2.koin

import com.matteo.mybaby2.modules.activities.ActivitiesViewModel
import com.matteo.mybaby2.modules.breastfeedings.BreastFeedingViewModel
import com.matteo.mybaby2.modules.activities.repositories.IActivityRepository
import com.matteo.mybaby2.modules.activities.repositories.MockedActivityRepository
import com.matteo.mybaby2.modules.babies.BabyViewModel
import com.matteo.mybaby2.modules.babies.repositories.IBabyRepository
import com.matteo.mybaby2.modules.babies.repositories.MockedBabyRepository
import com.matteo.mybaby2.modules.poopings.PoopViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    // mocked
    singleOf(::MockedBabyRepository) { bind<IBabyRepository>() }
    singleOf(::MockedActivityRepository) { bind<IActivityRepository>() }

    // view models
    viewModelOf(::BabyViewModel)
    viewModelOf(::ActivitiesViewModel)
    viewModelOf(::BreastFeedingViewModel)
    viewModelOf(::PoopViewModel)
}