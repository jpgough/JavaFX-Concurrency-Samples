/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.examples;

import java.util.concurrent.*;

/**
 * This class acts as a proxy to the existing Future object
 * 
 * @author boxcat
 */
public class FutureManager implements Future<String> {

    private final ScheduledExecutorService srv = Executors.newScheduledThreadPool(3);

    private FutureTask<String> current = null;
    
    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return current.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return current.isCancelled();
    }

    @Override
    public boolean isDone() {
        return current.isDone();
    }

    @Override
    public String get() throws InterruptedException, ExecutionException {
        return current.get();
    }

    @Override
    public String get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return current.get(timeout, unit);
    }
    
    /**
     * Creates a new Future object and sets it running on the threadpool
     * @param waitTime
     * @param message 
     */
    public synchronized void createFuture(final int waitTime, final String message) {
        if (current != null) current.cancel(true); 
        current = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(waitTime * 1000);
                return message;
            }
            
        });
        srv.schedule(current, 500, TimeUnit.MILLISECONDS);
    }
    
}
