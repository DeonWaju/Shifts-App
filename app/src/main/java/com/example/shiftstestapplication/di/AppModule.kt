package com.example.shiftstestapplication.di

import android.content.Context
import androidx.room.Room
import com.example.shiftstestapplication.data.db.ShiftsDao
import com.example.shiftstestapplication.data.db.ShiftsDatabase
import com.example.shiftstestapplication.data.repository.ShiftsRepository
import com.example.shiftstestapplication.domain.implementation.AddShiftToListUsecaseImpl
import com.example.shiftstestapplication.domain.implementation.ShiftsUsecaseImpl
import com.example.shiftstestapplication.domain.usecase.AddShiftToListUsecase
import com.example.shiftstestapplication.domain.usecase.ShiftListUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

/**
 * Created by Gideon Olarewaju on 29/11/2022.
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideShiftImpl(
        shiftsRepository: ShiftsRepository,
        ioDispatcher: CoroutineDispatcher
    ): ShiftListUsecase = ShiftsUsecaseImpl(shiftsRepository, ioDispatcher)

    @Provides
    @Singleton
    fun provideAddShiftToListImpl(
        shiftsRepository: ShiftsRepository,
        ioDispatcher: CoroutineDispatcher
    ): AddShiftToListUsecase = AddShiftToListUsecaseImpl(shiftsRepository, ioDispatcher)

    @Provides
    @Singleton
    fun provideShiftDatabase(@ApplicationContext context: Context): ShiftsDatabase {
        return Room.databaseBuilder(
            context,
            ShiftsDatabase::class.java,
            "RssReader"
        ).build()
    }

    @Provides
    fun provideShiftDao(shiftDatabase: ShiftsDatabase): ShiftsDao {
        return shiftDatabase.shiftsDao()
    }

    @Provides
    @Singleton
    fun provideRepository(shiftDatabase: ShiftsDatabase): ShiftsRepository =
        ShiftsRepository(shiftDatabase)


    @Provides
    @Singleton
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Default
}