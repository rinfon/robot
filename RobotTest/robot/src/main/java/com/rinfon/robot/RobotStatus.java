package com.rinfon.robot;

import java.util.HashMap;

/**
 * Created by chenyufeng on 2017/8/18.
 */
public interface RobotStatus {
    void onFinishJobs(HashMap<String, Object> resultList);
}
