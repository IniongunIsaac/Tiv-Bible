package com.iniongun.tivbible.common.utils.rxScheduler

import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler

/**
 * Created by Isaac Iniongun on 2019-07-18.
 * For TivBible project.
 */
class TestSchedulerProvider : SchedulerProvider {

    override fun ui(): Scheduler = TestScheduler()

    override fun computation(): Scheduler = TestScheduler()

    override fun io(): Scheduler = TestScheduler()
}