package co.utp.aves.di

import co.utp.aves.data.BirdRepository
import co.utp.aves.domain.interfaces.IBirdRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
interface IBirdRepositoryModule {

    @Binds
    fun bind(repository: BirdRepository): IBirdRepository
}