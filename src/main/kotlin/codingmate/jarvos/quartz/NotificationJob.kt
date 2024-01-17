package codingmate.jarvos.quartz

import org.quartz.Job
import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.quartz.JobExecutionContext
import org.quartz.Scheduler

import org.quartz.SchedulerFactory
import org.quartz.SimpleScheduleBuilder
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.quartz.impl.StdSchedulerFactory

class ParticularMatterNotificationJob: Job {
    override fun execute(p0: JobExecutionContext?) {
        TODO("Not yet implemented")
    }
}

class QuartzShcedulerConfig {
    fun startScheduler() {
        val schedulerFactory: SchedulerFactory = StdSchedulerFactory()
        val scheduler: Scheduler = schedulerFactory.scheduler

        val jobDetail: JobDetail = JobBuilder.newJob(ParticularMatterNotificationJob::class.java)
            .withIdentity("particularMatterNotificationJob")
            .build()

        val trigger: Trigger = TriggerBuilder.newTrigger()
            .withIdentity("particularMatterNotificationTrigger")
            .startNow()
            .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(10)
                .repeatForever())
            .build()

        scheduler.scheduleJob(jobDetail, trigger)

        scheduler.start()
    }
}