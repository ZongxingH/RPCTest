package hzx.study.rpc.service.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;

/**
 * Introduction:  [Simple Introduction of the java documents]
 * Package Name:  hzx.study.rpc.service.impl
 * Project Name:  hzx.study.rpc
 * Author:  ZongxingH
 * Email： zongxingh@163.com
 * Tel: 157*****778
 * Create Time:  2017/12/1 17:13
 */
public class ServiceTask implements Runnable {
    private Socket client = null;
    private static HashMap<String,Class> serviceRegistry = null;


    public ServiceTask(Socket client,HashMap<String,Class> serviceRegistry) {
        this.client = client;
        this.serviceRegistry = serviceRegistry;
    }
    public void run() {

        ObjectInputStream objIn = null;
        ObjectOutputStream objOut = null;
        try{
            objIn = new ObjectInputStream(client.getInputStream());
            String serviceName = objIn.readUTF();
            String methodName = objIn.readUTF();
            Class<?>[] parameterTypes = (Class<?>[]) objIn.readObject();
            Object[] args = (Object[]) objIn.readObject();
            Class serviceClass = serviceRegistry.get(serviceName);
            if (null == serviceClass){
                throw new ClassNotFoundException(serviceName + "not found");
            }
            Method method = serviceClass.getMethod(methodName,parameterTypes);
            Object results = method.invoke(serviceClass.newInstance(),args);

            objOut = new ObjectOutputStream(client.getOutputStream());
            objOut.writeObject(results);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (objOut != null){
               try{
                   objOut.close();
               }catch (IOException e){
                   e.printStackTrace();
               }
            }
            if (objIn != null){
                try {
                    objIn.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (client != null){
                try{
                    client.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
