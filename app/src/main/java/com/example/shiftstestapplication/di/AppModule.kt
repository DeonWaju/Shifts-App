package com.example.shiftstestapplication.di

import com.example.shiftstestapplication.data.repository.ShiftsRepository
import com.example.shiftstestapplication.domain.implementation.AddShiftToListUsecaseImpl
import com.example.shiftstestapplication.domain.implementation.ShiftsUsecaseImpl
import com.example.shiftstestapplication.domain.usecase.AddShiftToListUsecase
import com.example.shiftstestapplication.domain.usecase.ShiftListUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
        shiftsRepository: ShiftsRepository
    ): ShiftListUsecase = ShiftsUsecaseImpl(shiftsRepository)

    @Provides
    @Singleton
    fun provideAddShiftToListImpl(
        shiftsRepository: ShiftsRepository
    ): AddShiftToListUsecase = AddShiftToListUsecaseImpl(shiftsRepository)

    @Provides
    @Singleton
    fun provideRepository(): ShiftsRepository = ShiftsRepository()

    @Provides
    @Singleton
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Default
}
