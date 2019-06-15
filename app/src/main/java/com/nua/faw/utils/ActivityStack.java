package com.nua.faw.utils;

import android.app.Activity;

import java.util.Stack;

public class ActivityStack {

    private static Stack<Activity> activityStack;
    private static ActivityStack instance;

    private ActivityStack() {
    };

    public static ActivityStack getActivityManager() {
        if (instance == null) {
            instance = new ActivityStack();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }


    public void removeActivity(Activity activity) {
        if (activityStack == null) {
            return;
        }
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    public void removeActivity(Class<?> cls) {
        if (activityStack == null) {
            return;
        }
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                removeActivity(activity);
            }
        }
    }

    public void finishAllActivity() {
        if (activityStack == null) {
            return;
        }
        for(Activity activity:activityStack){
            activity.finish();
        }
        activityStack.clear();
    }

    public void AppExit() {
        try {
            finishAllActivity();
            System.exit(0);

        } catch (Exception e) {	}
    }
}
