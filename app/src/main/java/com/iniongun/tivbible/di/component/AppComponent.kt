package com.iniongun.tivbible.di.component

import com.iniongun.tivbible.TivBibleApplication
import com.iniongun.tivbible.di.modules.AppModule
import com.iniongun.tivbible.di.modules.RepositoryModule
import com.iniongun.tivbible.di.modules.RoomModule
import com.iniongun.tivbible.di.scopes.AppScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        RoomModule::class,
        RepositoryModule::class
    ]
)
@AppScope
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(tivBibleApp: TivBibleApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun bindAppInstance(tivBibleApp: TivBibleApplication): Builder

        fun buildAppComponent(): AppComponent

    }

}