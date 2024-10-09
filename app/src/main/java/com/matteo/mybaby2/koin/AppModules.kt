package com.matteo.mybaby2.koin

import com.matteo.mybaby2.modules.activities.ActivitiesViewModel
import com.matteo.mybaby2.modules.breastfeedings.BreastFeedingViewModel
import android.app.Application
import androidx.room.Room
import com.matteo.mybaby2.db.AppDatabase
import com.matteo.mybaby2.modules.activities.repositories.IActivityRepository
import com.matteo.mybaby2.modules.activities.repositories.MockedActivityRepository
import com.matteo.mybaby2.modules.babies.BabyViewModel
import com.matteo.mybaby2.modules.babies.repositories.IBabyRepository
import com.matteo.mybaby2.modules.babies.repositories.MockedBabyRepository
import com.matteo.mybaby2.modules.breastfeedings.daos.BreastFeedingDao
import com.matteo.mybaby2.modules.poopings.PoopViewModel
import com.matteo.mybaby2.modules.poopings.daos.PoopingDao
import com.matteo.mybaby2.modules.poopings.repositories.IPoopingRepository
import com.matteo.mybaby2.modules.poopings.repositories.PoopingRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    // mocked
    singleOf(::MockedBabyRepository) { bind<IBabyRepository>() }
    singleOf(::MockedActivityRepository) { bind<IActivityRepository>() }
    singleOf(::PoopingRepository) { bind<IPoopingRepository>() }

    // view models
    viewModelOf(::BabyViewModel)
    viewModelOf(::ActivitiesViewModel)
    viewModelOf(::BreastFeedingViewModel)
    viewModelOf(::PoopViewModel)
}

val databaseModule = module {
    single { provideDb(get()) }
    single { providePoopingDao(get()) }
    single { provideBreastFeedingDao(get()) }
}

fun provideDb(application: Application) {
    Room.databaseBuilder(application, AppDatabase::class.java, "mybaby2.db")
        .fallbackToDestructiveMigration().build()
}

fun providePoopingDao(appDatabase: AppDatabase): PoopingDao = appDatabase.poopingDao()
fun provideBreastFeedingDao(appDatabase: AppDatabase): BreastFeedingDao = appDatabase.breastFeedingDao()