package com.rinfon.robottest;

import android.app.Application;

import com.rinfon.robot.Robot;

/**
 * Created by chenyufeng on 2017/8/18.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Robot.init();
//        set threads number
        Robot.getInstance().setJobThreads(5);
    }
}
