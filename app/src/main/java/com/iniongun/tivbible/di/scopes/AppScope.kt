package com.iniongun.tivbible.di.scopes

import javax.inject.Scope

/**
 * Created by Isaac Iniongun on 2019-11-25
 * For Tiv Bible project
 */

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
@Scope
annotation class AppScope