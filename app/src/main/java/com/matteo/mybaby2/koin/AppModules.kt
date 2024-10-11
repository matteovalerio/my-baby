package com.matteo.mybaby2.koin

import android.content.Context
import androidx.room.Room
import com.matteo.mybaby2.db.AppDatabase
import com.matteo.mybaby2.modules.breastfeedings.BreastFeedingViewModel
import com.matteo.mybaby2.modules.breastfeedings.daos.BreastFeedingDao
import com.matteo.mybaby2.modules.breastfeedings.repositories.BreastFeedingRepository
import com.matteo.mybaby2.modules.breastfeedings.repositories.IBreastFeedingRepository
import com.matteo.mybaby2.modules.poopings.PoopViewModel
import com.matteo.mybaby2.modules.poopings.daos.PoopingDao
import com.matteo.mybaby2.modules.poopings.repositories.IPoopingRepository
import com.matteo.mybaby2.modules.poopings.repositories.PoopingRepository
import com.matteo.mybaby2.modules.statistics.StatisticsViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    // repositories
    singleOf(::PoopingRepository) { bind<IPoopingRepository>() }
    singleOf(::BreastFeedingRepository) { bind<IBreastFeedingRepository>() }

    // view models
    viewModelOf(::BreastFeedingViewModel)
    viewModelOf(::PoopViewModel)
    viewModelOf(::StatisticsViewModel)
}

val databaseModule = module {
    single { provideDb(get()) }
    single { providePoopingDao(get()) }
    single { provideBreastFeedingDao(get()) }
}

fun provideDb(context: Context): AppDatabase {
    return Room.databaseBuilder(context, AppDatabase::class.java, "mybaby2.db")
        .fallbackToDestructiveMigration().build()
}

fun providePoopingDao(appDatabase: AppDatabase): PoopingDao = appDatabase.poopingDao()
fun provideBreastFeedingDao(appDatabase: AppDatabase): BreastFeedingDao =
    appDatabase.breastFeedingDao()