package com.iniongun.tivbible.common.utils.rxScheduler

import io.reactivex.Scheduler

/**
 * Created by Isaac Iniongun on 2019-07-18.
 * For TivBible project.
 */
interface SchedulerProvider {

    fun ui(): Scheduler

    fun computation(): Scheduler
    
    fun io(): Scheduler

}