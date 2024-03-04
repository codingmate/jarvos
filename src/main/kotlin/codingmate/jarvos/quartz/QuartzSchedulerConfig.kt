package codingmate.jarvos.quartz

import codingmate.jarvos.quartz.job.AirQualityDataFetchJob
import codingmate.jarvos.quartz.job.ParticularMatterNotificationJob
import codingmate.jarvos.quartz.job.ParticularMatterReCheckJob
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

        // 대기 오염도 api를 사용하여 db에 적재
        scheduleJob(scheduler, AirQualityDataFetchJob::class.java, "airQualityDataFetch", "0 30 0/1 * * ?")

        // 미세먼지 알림 ( 7시 )
        scheduleJob(scheduler, ParticularMatterNotificationJob::class.java, "particularMatterNotification", "0 0/5 7 * * ?")
        scheduleJob(scheduler, ParticularMatterReCheckJob::class.java, "particularMatterReCheckJob", "0 0/10 8-19 * * ?")

        // test
//        scheduleJob(scheduler, AirQualityDataFetchJob::class.java, "airQualityDataFetch", "0/10 * * * * ?")
//        scheduleJob(scheduler, ParticularMatterNotificationJob::class.java, "particularMatterNotification", "0/15 * * * * ?")
//        scheduleJob(scheduler, ParticularMatterReCheckJob::class.java, "particularMatterReCheckJob", "0/10 * * * * ?")

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
