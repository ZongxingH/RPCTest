package hzx.study.rpc;

import hzx.study.rpc.consumer.RPCClient;
import hzx.study.rpc.provider.impl.HelloServiceImpl;
import hzx.study.rpc.provider.svc.HelloService;
import hzx.study.rpc.service.impl.ServiceCenter;
import hzx.study.rpc.service.svc.Server;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Introduction:  [Simple Introduction of the java documents]
 * Package Name:  hzx.study.rpc
 * Project Name:  hzx.study.rpc
 * Author:  ZongxingH
 * Email： zongxingh@163.com
 * Tel: 157*****778
 * Create Time:  2017/12/1 17:48
 */
public class RPCBootstrap {

    public static void main(String[] args) throws IOException{
        new Thread(new Runnable() {
            public void run() {
                try{
                    Server server = new ServiceCenter(9090);
                    server.register(HelloService.class, HelloServiceImpl.class);
                    server.start();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
        HelloService service = RPCClient.getRemoteProxy(HelloService.class,new InetSocketAddress("localhost",9090));
        System.out.println(service.sayHi("rpc test..."));
    }
}
