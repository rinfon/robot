package com.rinfon.robot;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by chenyufeng on 2017/8/18.
 */
public class Group {
    private Robot mRobot;

    private LinkedHashMap<String, Job> mJobList = new LinkedHashMap<String, Job>();

    private HashMap<String, Object> mResultList = new HashMap<String, Object>();

    private byte[] lock = new byte[0];

    public Group(Robot robot) {
        this.mRobot = robot;
    }

    public Group add(final IJob iJob) {
        Job mJob = new Job(iJob);
        mJob.setJobStatus(new JobStatus() {
            @Override
            public void onFinish(Object result) {
                synchronized (lock) {
                    mResultList.put(String.valueOf(iJob.hashCode()), result);
                    if (mResultList.size() == mJobList.size()) {
//                    finish
                        mRobot.getRobotStatus().onFinishJobs(mResultList);
                    }
                }
            }
        });
        mJobList.put(String.valueOf(iJob.hashCode()), mJob);
        return this;
    }

    public Robot last(IJob job) {
        add(job);
        return mRobot;
    }

    public LinkedHashMap<String, Job> getJobList() {
        return mJobList;
    }
}
