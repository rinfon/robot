package com.rinfon.robottest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.rinfon.robot.IJob;
import com.rinfon.robot.Robot;
import com.rinfon.robot.RobotStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final IJob job = new IJob() {
            @Override
            public Object job() {
                Log.i(TAG, "job 1 start sleep 0s");
                return "finish job 1";
            }
        };

        Robot.getInstance().group()
                .add(job)
                .add(new IJob() {
                    @Override
                    public Object job() {
                        Log.i(TAG, "job 2 start sleep 2s");
                        try {
                            Thread.sleep(2000);
                            Log.i(TAG, "job 2 finish sleep");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            return new JSONObject("{'name':'job 2'}");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                })
                .add(new IJob() {
                    @Override
                    public Object job() {
                        Log.i(TAG, "job 3 start sleep 3s");
                        try {
                            Thread.sleep(3000);
                            Log.i(TAG, "job 3 finish sleep");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return 3;
                    }
                })
                .last(new IJob() {
                    @Override
                    public Object job() {
                        Log.i(TAG, "job 4 start sleep 4s");
                        try {
                            Thread.sleep(4000);
                            Log.i(TAG, "job 4 finish sleep");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return "finish job 4";
                    }
                })
                .onFinish(new RobotStatus() {
                    @Override
                    public void onFinishJobs(HashMap<String, Object> resultList) {
                        Log.i(TAG, "all finish");
//                        result maybe null,add try catch
                        String job1result = String.valueOf(resultList.get(String.valueOf(job.hashCode())));
//                        you can get another result by this way
                        Log.i(TAG, "job1result" + job1result);
                    }
                }).start();
    }
}
