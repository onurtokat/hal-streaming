// 
// Decompiled by Procyon v0.5.30
// 

package com.motive.mas.configuration.service.api;

import java.util.concurrent.Delayed;
import org.slf4j.LoggerFactory;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.DelayQueue;
import org.slf4j.Logger;

public class TimeDelayedNodeListener
{
    private static final Logger log;
    private long notificationDelay;
    public static final long QUEUE_TIMEOUT = 10000L;
    private DelayQueue<DelayElement> queue;
    private boolean working;
    private String pathName;
    private NodeListener nodeListener;
    private DelayElement currentDelayElement;
    
    public TimeDelayedNodeListener(final String pathName, final NodeListener nodeListener, final long notificationDelay) {
        this.queue = new DelayQueue<DelayElement>();
        this.working = true;
        this.pathName = pathName;
        this.nodeListener = nodeListener;
        this.notificationDelay = notificationDelay;
    }
    
    public void start() {
        final Thread thread = new Thread(() -> {
            while (this.working) {
                try {
                    if (this.queue.poll(10000L, TimeUnit.MILLISECONDS) != null) {
                        this.nodeListener.callBack(this.pathName);
                    }
                    else {
                        continue;
                    }
                }
                catch (InterruptedException e) {
                    TimeDelayedNodeListener.log.error(e.getMessage(), e);
                }
            }
            return;
        });
        thread.start();
    }
    
    public synchronized void insertEvent(final TreeCacheEvent treeCacheEvent) {
        if (this.currentDelayElement != null) {
            this.queue.remove(this.currentDelayElement);
        }
        this.currentDelayElement = new DelayElement(treeCacheEvent, this.notificationDelay);
        this.queue.put(this.currentDelayElement);
    }
    
    public void stop() {
        TimeDelayedNodeListener.log.debug("TimeDelayedNodeListener stop() thread...");
        this.working = false;
    }
    
    static {
        log = LoggerFactory.getLogger(TimeDelayedNodeListener.class);
    }
    
    private class DelayElement implements Delayed
    {
        private TreeCacheEvent element;
        private long expiryTime;
        
        public DelayElement(final TreeCacheEvent element, final long delay) {
            this.element = element;
            this.expiryTime = System.currentTimeMillis() + delay;
        }
        
        @Override
        public long getDelay(final TimeUnit timeUnit) {
            final long diff = this.expiryTime - System.currentTimeMillis();
            return timeUnit.convert(diff, TimeUnit.MILLISECONDS);
        }
        
        @Override
        public int compareTo(final Delayed o) {
            if (this.expiryTime < ((DelayElement)o).expiryTime) {
                return -1;
            }
            if (this.expiryTime > ((DelayElement)o).expiryTime) {
                return 1;
            }
            return 0;
        }
        
        @Override
        public String toString() {
            return this.element + ": " + this.expiryTime;
        }
    }
}
