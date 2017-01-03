package com.coreservices.utils;

import org.apache.log4j.Logger;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by janet on 1/3/17.
 */
public class RpcLogger implements Runnable{
    private static Logger logger = Logger.getLogger(RpcLogger.class.getSimpleName());
    private final BlockingQueue<RpcLogTemplate> queue;

    public RpcLogger(){
        this.queue = new ArrayBlockingQueue<RpcLogTemplate>(100);
    }
    public void addLog(RpcLogTemplate logTemplate) {
       this.queue.add(logTemplate);
    }

    @Override
    public void run() {
        try{
            RpcLogTemplate log = queue.take();
            //Log to remote host here
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
