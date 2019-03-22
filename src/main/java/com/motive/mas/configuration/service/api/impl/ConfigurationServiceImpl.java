// 
// Decompiled by Procyon v0.5.30
// 

package com.motive.mas.configuration.service.api.impl;

import org.slf4j.LoggerFactory;
import javax.annotation.PreDestroy;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import com.motive.mas.configuration.service.api.NodeListener;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import java.util.Iterator;
import java.util.Set;
import org.apache.curator.framework.api.transaction.CuratorTransactionFinal;
import org.apache.curator.framework.api.transaction.CuratorTransaction;
import java.util.Collection;
import org.apache.curator.framework.api.transaction.CuratorTransactionBridge;
import java.util.Collections;
import java.util.HashSet;
import org.apache.zookeeper.KeeperException;
import org.apache.curator.framework.recipes.cache.ChildData;
import java.util.HashMap;
import com.motive.mas.configuration.service.api.Node;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.curator.RetryPolicy;
import java.util.ArrayList;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCache;
import com.motive.mas.configuration.service.api.TimeDelayedNodeListener;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Value;
import com.motive.mas.configuration.service.api.ConfigurationService;

public class ConfigurationServiceImpl implements ConfigurationService
{
    @Value("${zookeeper.connectionString}")
    private String zkConnectionString;
    @Value("${zookeeper.namespace}")
    private String zkNamespace;
    private String zkUser;
    private String zkPass;
    private static final int WAIT_BEFORE_CLOSE = 11000;
    private static final int RETRY_COUNT = 3;
    private static final int WAIT_BEFORE_RETRY = 1000;
    private static final String CACHE_ROOT = "/";
    private static final String PATH_SEPARATOR = "/";
    private static final Pattern NAME_SPACE_PATTERN;
    private static final long NOTIFICATION_DELAY = 30000L;
    private List<TimeDelayedNodeListener> timeDelayedNodeListeners;
    private TreeCache cache;
    private boolean initialized;
    private CuratorFramework client;
    private static final Logger log;
    
    public ConfigurationServiceImpl() {
        this.initialized = false;
    }
    
    private TreeCache tryInitCache(final CuratorFramework curatorClient) throws Exception {
        TreeCache theCache = new TreeCache(curatorClient, "/");
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        theCache.getListenable().addListener((client1, event) -> {
            if (event.getType().equals((Object)TreeCacheEvent.Type.INITIALIZED)) {
                countDownLatch.countDown();
            }
        });
        theCache.start();
        if (!countDownLatch.await(30L, TimeUnit.SECONDS)) {
            ConfigurationServiceImpl.log.error("Configuration service cache could not initialize");
            theCache.close();
            theCache = null;
        }
        return theCache;
    }
    
    private boolean initCache(final CuratorFramework curatorClient) throws Exception {
        int retry = 0;
        this.cache = null;
        while (null == this.cache && retry < 3) {
            ConfigurationServiceImpl.log.info("initCache:" + Integer.toString(retry + 1));
            this.cache = this.tryInitCache(curatorClient);
            ++retry;
        }
        return null != this.cache;
    }
    
    @PostConstruct
    public void init() throws Exception {
        if (this.initialized) {
            ConfigurationServiceImpl.log.info("Configuration service was already initialized");
            return;
        }
        this.initialized = true;
        ConfigurationServiceImpl.log.info("Starting ConfigurationServiceImpl");
        final RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        final String namespace = this.convert2Path(this.zkNamespace);
        (this.client = CuratorFrameworkFactory.builder().connectString(this.zkConnectionString).retryPolicy(retryPolicy).namespace(namespace).build()).start();
        this.client.blockUntilConnected();
        ConfigurationServiceImpl.log.info("client:" + this.client.toString());
        if (!this.initCache(this.client)) {
            ConfigurationServiceImpl.log.error("ConfigurationServiceImpl could not initialize cache! - client state:" + this.client.getState() + " client namespace:" + this.client.getNamespace() + " client zookeeper:" + this.client.getZookeeperClient().getCurrentConnectionString() + " client data:" + this.client.getData() + " client:" + this.client);
        }
        this.timeDelayedNodeListeners = new ArrayList<TimeDelayedNodeListener>();
        ConfigurationServiceImpl.log.info("ConfigurationServiceImpl started.");
    }
    
    @Override
    public Map<String, Node> getAllNodes(final String parentPath) {
        final Map<String, Node> nodeMap = new HashMap<String, Node>();
        this.getAllNodes(parentPath, nodeMap);
        return nodeMap;
    }
    
    public void getAllNodes(String parentPath, final Map<String, Node> nodeMap) {
        if (parentPath.length() > 1) {
            parentPath = parentPath.replaceAll("/$", "");
        }
        final Map<String, ChildData> currentChildren = (Map<String, ChildData>)this.cache.getCurrentChildren(parentPath);
        if (currentChildren != null) {
            currentChildren.values().stream().forEach(node -> {
                nodeMap.put(node.getPath(), new Node(node.getPath(), node.getData()));
                if (node.getStat() != null && node.getStat().getNumChildren() > 0) {
                    this.getAllNodes(node.getPath(), nodeMap);
                }
            });
        }
    }
    
    @Override
    public void set(final String key, final byte[] value) throws Exception {
        try {
            this.client.setData().forPath(key, value);
        }
        catch (KeeperException.NoNodeException e) {
            this.client.create().creatingParentsIfNeeded().forPath(key, value);
        }
    }
    
    @Override
    public void remove(final String path) throws Exception {
        this.client.delete().guaranteed().forPath(path);
    }
    
    @Override
    public void atomicBatchUpdate(final Map<String, byte[]> recordMap, final String deletePath) throws Exception {
        final CuratorTransaction curatorTransaction = this.client.inTransaction();
        CuratorTransactionFinal transactionFinal = null;
        final Set<String> set = new HashSet<String>();
        for (final String path : recordMap.keySet()) {
            final String[] split = path.split("/");
            String currentPath = "";
            for (final String subPath : split) {
                if (!subPath.isEmpty()) {
                    currentPath = currentPath + "/" + subPath;
                    set.add(currentPath);
                }
            }
        }
        final List<String> nonExist = new ArrayList<String>();
        for (final String path2 : set) {
            if (this.client.checkExists().forPath(path2) == null) {
                nonExist.add(path2);
            }
        }
        final Set<String> paths = new HashSet<String>();
        Collections.sort(nonExist);
        for (final String path3 : nonExist) {
            if (transactionFinal != null) {
                transactionFinal = transactionFinal.create().forPath(path3, new byte[0]).and();
                paths.add(path3);
            }
            else {
                transactionFinal = curatorTransaction.create().forPath(path3, new byte[0]).and();
                paths.add(path3);
            }
        }
        transactionFinal = this.resetNodesValues(curatorTransaction, transactionFinal, set);
        for (final Map.Entry<String, byte[]> e : recordMap.entrySet()) {
            if (transactionFinal != null) {
                transactionFinal = transactionFinal.setData().forPath(e.getKey(), e.getValue()).and();
                paths.add(e.getKey());
            }
            else {
                transactionFinal = curatorTransaction.setData().forPath(e.getKey(), e.getValue()).and();
                paths.add(e.getKey());
            }
        }
        if (deletePath != null) {
            final Map<String, Node> deleteNodes = this.getAllNodes(deletePath);
            final List<String> list = new ArrayList<String>(deleteNodes.size());
            list.addAll(deleteNodes.keySet());
            Collections.sort(list);
            Collections.reverse(list);
            for (final String path4 : list) {
                if (paths.stream().filter(p -> p.startsWith(path4)).count() == 0L) {
                    if (transactionFinal != null) {
                        transactionFinal = transactionFinal.delete().forPath(path4).and();
                    }
                    else {
                        transactionFinal = curatorTransaction.delete().forPath(path4).and();
                    }
                }
            }
        }
        if (transactionFinal != null) {
            transactionFinal.commit();
        }
    }
    
    private CuratorTransactionFinal resetNodesValues(final CuratorTransaction curatorTransaction, CuratorTransactionFinal transactionFinal, final Set<String> paths) throws Exception {
        for (final String node : paths) {
            if (transactionFinal != null) {
                transactionFinal = transactionFinal.setData().forPath(node, new byte[0]).and();
            }
            else {
                transactionFinal = curatorTransaction.setData().forPath(node, new byte[0]).and();
            }
        }
        return transactionFinal;
    }
    
    @Override
    public void atomicBatchUpdate(final Map<String, byte[]> recordMap) throws Exception {
        this.atomicBatchUpdate(recordMap, null);
    }
    
    @Override
    public byte[] get(final String key) {
        final ChildData currentData = this.cache.getCurrentData(key);
        return (byte[])((currentData != null) ? currentData.getData() : null);
    }
    
    public void addListenerToTreeCache(final TreeCacheListener treeCacheListener) {
        this.cache.getListenable().addListener(treeCacheListener);
    }
    
    private String convert2Path(final String appName) {
        this.validateAppName(appName);
        return appName.replaceAll("\\.", "/");
    }
    
    private void validateAppName(final String appName) {
        if (appName == null) {
            throw new IllegalArgumentException("Null namespace name is not allowed.");
        }
        if (!appName.isEmpty() && !ConfigurationServiceImpl.NAME_SPACE_PATTERN.matcher(appName).matches()) {
            throw new IllegalArgumentException("Invalid namespace name: " + appName);
        }
    }
    
    @Override
    public void addListenerToPath(final String path, final NodeListener listener) {
        this.addListenerToPath(path, listener, 30000L);
    }
    
    public void addListenerToPath(final String path, final NodeListener listener, final long notificationDelay) {
        final TimeDelayedNodeListener timeDelayedNodeListener = new TimeDelayedNodeListener(path, listener, notificationDelay);
        this.cache.getListenable().addListener(new TreeCacheListener() {
            public void childEvent(final CuratorFramework client, final TreeCacheEvent event) throws Exception {
                if (event.getData() != null && this.check(event)) {
                    timeDelayedNodeListener.insertEvent(event);
                }
            }
            
            private boolean check(final TreeCacheEvent event) {
                return event.getData().getPath().startsWith(path);
            }
        });
        this.timeDelayedNodeListeners.add(timeDelayedNodeListener);
        timeDelayedNodeListener.start();
    }
    
    @PreDestroy
    public void close() {
        ConfigurationServiceImpl.log.debug("Closing ConfigurationServiceImpl...cache...");
        this.cache.close();
        ConfigurationServiceImpl.log.debug("Closing ConfigurationServiceImpl...client...");
        try {
            this.client.close();
            ConfigurationServiceImpl.log.debug("Closing ConfigurationServiceImpl...connected clients...");
            this.timeDelayedNodeListeners.forEach(TimeDelayedNodeListener::stop);
            ConfigurationServiceImpl.log.debug("Will sleep for " + Integer.toString(11000) + " milisec...");
            Thread.sleep(11000L);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setZkConnectionString(final String conn) {
        this.zkConnectionString = conn;
    }
    
    public String getZkConnectionString() {
        return this.zkConnectionString;
    }
    
    public String getZkNamespace() {
        return this.zkNamespace;
    }
    
    public void setZkNamespace(final String zkNamespace) {
        this.zkNamespace = zkNamespace;
    }
    
    public String getZkUser() {
        return this.zkUser;
    }
    
    public void setZkUser(final String zkUser) {
        this.zkUser = zkUser;
    }
    
    public String getZkPass() {
        return this.zkPass;
    }
    
    public void setZkPass(final String zkPass) {
        this.zkPass = zkPass;
    }
    
    @Override
    public void unregisterAllListeners() {
        this.timeDelayedNodeListeners.forEach(TimeDelayedNodeListener::stop);
    }
    
    static {
        NAME_SPACE_PATTERN = Pattern.compile("^[\\w]+(\\.\\w+)*$");
        log = LoggerFactory.getLogger(ConfigurationServiceImpl.class);
    }
}
