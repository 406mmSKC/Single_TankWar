package cn.edu.ncepu.sa.Model;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
    public ExecutorService executorService;
    private final int maxThreadNum=4;
    public  ThreadPool(){
        executorService= Executors.newFixedThreadPool(maxThreadNum);
    }
}
