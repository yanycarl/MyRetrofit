package com.example.myretrofit;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Dispatcher {

    private int maxRunning = 3;
    @SuppressLint("CI_NotAllowInvokeExecutorsMethods")
    private ExecutorService mCacheExcutor = Executors.newCachedThreadPool();

    private ArrayDeque<Call.AsyncCall> readyAsyncCalls = new ArrayDeque<>();
    private ArrayDeque<Call.AsyncCall> executableCalls = new ArrayDeque<>();
    private ArrayDeque<Call.AsyncCall> runningAsyncCalls = new ArrayDeque<>();

    public  void enqueue(Call.AsyncCall asyncCall){
        readyAsyncCalls.add(asyncCall);
        promoteAndExecute();
    }

    private void promoteAndExecute(){
        Iterator<Call.AsyncCall> iterator = readyAsyncCalls.iterator();
        while (iterator.hasNext()) {
            synchronized (this){
                if (getRunningSize() > maxRunning) {
                    break;
                }
            }

            Call.AsyncCall asyncCall = iterator.next();
            executableCalls.add(asyncCall);
            readyAsyncCalls.remove(asyncCall);
        }

        Iterator<Call.AsyncCall> iterator2 = executableCalls.iterator();
        while (iterator2.hasNext()) {
            Call.AsyncCall asyncCall = iterator2.next();
            asyncCall.executeOn(mCacheExcutor);
            runningAsyncCalls.add(asyncCall);
            executableCalls.remove(asyncCall);
        }
    }

    public void finished(Call.AsyncCall call){
        runningAsyncCalls.remove(call);
        promoteAndExecute();
    }

    private  int getRunningSize(){
        return executableCalls.size();
    }
}

