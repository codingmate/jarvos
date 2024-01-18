package codingmate.jarvos.quartz

import jakarta.annotation.PostConstruct
import org.quartz.*
import org.quartz.impl.StdSchedulerFactory
import org.quartz.spi.JobFactory
import org.quartz.spi.TriggerFiredBundle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.AutowireCapableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.quartz.SchedulerFactoryBean
import org.springframework.stereotype.Component

@Component
class QuartzSchedulerConfig {

    // Quartz 작업에 Bean 주입
    @Autowired
    private lateinit var beanFactory: AutowireCapableBeanFactory

    @Autowired
    private lateinit var schedulerFactoryBean: SchedulerFactory

    @Bean
    fun springBeanJobFactory(): JobFactory {
        return JobFactory { bundle, _ ->
            val job = bundle.jobDetail.jobClass.getDeclaredConstructor().newInstance()
            beanFactory.autowireBean(job)
            job
        }

        /*
        return object : JobFactory {
            override fun newJob(bundle: TriggerFiredBundle, scheduler: Scheduler): Job {
                val job = bundle.jobDetail.jobClass.getDeclaredConstructor().newInstance()
                beanFactory.autowireBean(job)
                return job
            }
        } 와 같은 코드임
         */
    }

    @Bean
    fun schedulerFactoryBean(): SchedulerFactoryBean {
        val factory = SchedulerFactoryBean()
        factory.setJobFactory(springBeanJobFactory())
        return factory
    }

    // Scheduler Setting
    @PostConstruct
    fun startScheduler() {

        val scheduler: Scheduler = StdSchedulerFactory.getDefaultScheduler()

        scheduleJob(scheduler, ParticularMatterNotificationJob::class.java, "particular", "0 0 8 * * ?")

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