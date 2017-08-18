package com.rinfon.robot;

/**
 * Created by chenyufeng on 2017/8/18.
 */


import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by rinfon on 17-8-17.
 */

public class Robot {

    private static Robot instance;

    private ExecutorService mPoolBox = Executors.newFixedThreadPool(5);

    private LinkedHashMap<String, Group> mGroupList = new LinkedHashMap<String, Group>();

    private RobotStatus mRobotStatus;

    public Robot() {
    }

    public static void init() {
        if (instance == null) {
            synchronized (Robot.class) {
                if (instance == null) {
                    instance = new Robot();
                }
            }
        }
    }

    public void setJobThreads(int number) {
        mPoolBox = Executors.newFixedThreadPool(number);
    }

    public static Robot getInstance() {
        return instance;
    }

    public Robot onFinish(RobotStatus robotStatus) {
        this.mRobotStatus = robotStatus;
        return this;
    }

    public RobotStatus getRobotStatus() {
        return mRobotStatus;
    }

    public Group group() {
        Group mGroup = new Group(this);
        mGroupList.put(String.valueOf(mGroup.hashCode()), mGroup);
        return mGroup;
    }


    public void start() {
        for (String key : mGroupList.keySet()) {
            for (String jobKey : mGroupList.get(key).getJobList().keySet()) {
                mPoolBox.execute(mGroupList.get(key).getJobList().get(jobKey));
            }
        }
    }
}






