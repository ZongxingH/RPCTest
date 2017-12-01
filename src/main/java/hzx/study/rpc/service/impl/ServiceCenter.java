package hzx.study.rpc.service.impl;

import hzx.study.rpc.service.svc.Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Introduction:  [Simple Introduction of the java documents]
 * Package Name:  hzx.study.rpc.service.impl
 * Project Name:  hzx.study.rpc
 * Author:  ZongxingH
 * Email： zongxingh@163.com
 * Tel: 157*****778
 * Create Time:  2017/12/1 17:01
 */
public class ServiceCenter implements Server {

    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static final HashMap<String,Class> serviceRegistry = new HashMap<String, Class>();

    private static boolean isRunning = false;

    private static int port;

    public ServiceCenter(int port){
        this.port = port;
    }

    public void stop() {
        isRunning = false;
        executor.shutdown();
    }

    public void start() throws IOException {

        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(port));
        System.out.println("start server");
        try {
            while (true){
                executor.execute(new ServiceTask(server.accept(),serviceRegistry));
            }
        }finally {
            server.close();
        }
    }

    public void register(Class intf, Class impl) {

        serviceRegistry.put(intf.getName(),impl);

    }

    public boolean isRunning() {
        return isRunning;
    }

    public int getPort() {
        return port;
    }
}
