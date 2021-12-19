package com.sonic.agent.tests.ios;

import com.alibaba.fastjson.JSONObject;
import com.sonic.agent.automation.AndroidStepHandler;
import com.sonic.agent.automation.IOSStepHandler;
import com.sonic.agent.tests.android.AndroidTestTaskBootThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class IOSRunStepThread extends Thread {

    private final Logger log = LoggerFactory.getLogger(IOSRunStepThread.class);

    public final static String IOS_RUN_STEP_TASK_PRE = "ios-run-step-task-%s-%s-%s";

    private final IOSTestTaskBootThread iosTestTaskBootThread;

    public IOSRunStepThread(IOSTestTaskBootThread iosTestTaskBootThread) {
        this.iosTestTaskBootThread = iosTestTaskBootThread;

        this.setDaemon(true);
        this.setName(iosTestTaskBootThread.formatThreadName(IOS_RUN_STEP_TASK_PRE));
    }

    public IOSTestTaskBootThread getIosTestTaskBootThread() {
        return iosTestTaskBootThread;
    }

    @Override
    public void run() {
        JSONObject jsonObject = iosTestTaskBootThread.getJsonObject();
        IOSStepHandler iosStepHandler = iosTestTaskBootThread.getIosStepHandler();
        List<JSONObject> steps = jsonObject.getJSONArray("steps").toJavaList(JSONObject.class);

        for (JSONObject step : steps) {
            try {
                iosStepHandler.runStep(step);
            } catch (Throwable e) {
                break;
            }
        }

    }
}