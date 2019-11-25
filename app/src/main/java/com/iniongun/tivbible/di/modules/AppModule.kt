package com.iniongun.tivbible.di.modules

import android.content.Context
import com.google.gson.GsonBuilder
import com.iniongun.tivbible.TivBibleApplication
import com.iniongun.tivbible.di.scopes.AppScope
import com.iniongun.tivbible.utils.AppConstants
import dagger.Module
import dagger.Provides

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

@Module
class AppModule {

    @Provides
    @AppScope
    fun provideContext(tivBibleApp: TivBibleApplication) = tivBibleApp

    @Provides
    @AppScope
    fun providesGson() = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create()

    @Provides
    @AppScope
    fun providesSharedPreference(context: Context) = context.getSharedPreferences(
        AppConstants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

}