package com.rinfon.robot;

/**
 * Created by chenyufeng on 2017/8/18.
 */
public class Job implements Runnable {

    private JobStatus jobStatus;

    private IJob job;

    public Job(IJob job) {
        this.job = job;
    }

    @Override
    public void run() {
        Object result = job.job();
        jobStatus.onFinish(result);

    }

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }
}
