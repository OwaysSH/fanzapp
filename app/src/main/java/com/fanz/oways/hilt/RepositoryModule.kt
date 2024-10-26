package com.fanz.oways.hilt

import android.content.Context
import com.fanz.oways.repos.clubs.ClubsRepositoryImpl
import com.fanz.oways.repos.clubs.IClubsRepository
import com.fanz.oways.repos.players.IPlayersRepository
import com.fanz.oways.repos.players.PlayersRepositoryImpl
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Singleton
    @Provides
    fun provideIplayersRepository(
        @ApplicationContext context: Context,
        @Named("players_database_reference") playersReference: DatabaseReference
    ): IPlayersRepository {
        return PlayersRepositoryImpl(
            context = context,
            service = playersReference
        )
    }

    @Singleton
    @Provides
    fun provideIClubsRepository(
        @ApplicationContext context: Context,
        @Named("clubs_database_reference") clubsReference: DatabaseReference,
    ): IClubsRepository {
        return ClubsRepositoryImpl(
            context = context,
            service = clubsReference
        )
    }

    @Provides
    @Singleton
    @Named("root_database_reference")
    fun provideDatabaseReference(): DatabaseReference {
        return FirebaseDatabase.getInstance().reference.child("fanz")
    }
    @Provides
    @Singleton
    @Named("clubs_database_reference")
    fun provideClubsReference(@Named("root_database_reference") database: DatabaseReference): DatabaseReference {
        return database.child("clubs")
    }

    @Provides
    @Singleton
    @Named("players_database_reference")
    fun providePlayersReference(@Named("root_database_reference") database: DatabaseReference): DatabaseReference {
        return database.child("players")
    }
}