package codingmate.jarvos.quartz

import codingmate.jarvos.quartz.job.AirQualityDataFetchJob
import codingmate.jarvos.quartz.job.ParticularMatterNotificationJob
import jakarta.annotation.PostConstruct
import org.quartz.*

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.quartz.SchedulerFactoryBean

@Configuration
class QuartzSchedulerConfig {

    @Autowired
    private lateinit var schedulerFactoryBean: SchedulerFactoryBean

    @PostConstruct
    fun startScheduler() {

        val scheduler: Scheduler = schedulerFactoryBean.`object`!!

        //scheduleJob(scheduler, ParticularMatterNotificationJob::class.java, "particularMatterNotification", "0 0 7 * * ?")
        scheduleJob(scheduler, AirQualityDataFetchJob::class.java, "airQualityDataFetch", "* 30 0/1 * * ?")
        scheduleJob(scheduler, ParticularMatterNotificationJob::class.java, "particularMatterNotification", "0/10 * * * * ?")

        scheduler.start()
    }
    private fun <T : Job> scheduleJob(scheduler: Scheduler, jobClass: Class<T>, name: String, cronExpression: String) {
        val jobDetail = JobBuilder.newJob(jobClass)
            .withIdentity("${name}NotificationJob")
            .build()

        val trigger = TriggerBuilder.newTrigger()
            .withIdentity("${name}NotificationTrigger")
            .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
            .build()

        scheduler.scheduleJob(jobDetail, trigger)
    }
}
