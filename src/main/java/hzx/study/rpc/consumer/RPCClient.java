package hzx.study.rpc.consumer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Introduction:  [Simple Introduction of the java documents]
 * Package Name:  hzx.study.rpc.consumer
 * Project Name:  hzx.study.rpc
 * Author:  ZongxingH
 * Email： zongxingh@163.com
 * Tel: 157*****778
 * Create Time:  2017/12/1 17:31
 */
public class RPCClient<T> {

    public static <T> T getRemoteProxy(final Class<?> intf, final InetSocketAddress addr){

        // 1.将本地的接口调用转换成JDK的动态代理，在动态代理中实现接口的远程调用
        return (T) Proxy.newProxyInstance(intf.getClassLoader(),new Class<?>[]{intf}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = null;
                ObjectOutputStream objOut = null;
                ObjectInputStream objIn = null;
                try{
                    // 2.创建Socket客户端，根据指定地址连接远程服务提供者
                    socket = new Socket();
                    socket.connect(addr);

                    // 3.将远程服务调用所需的接口类、方法名、参数列表等编码后发送给服务提供者
                    objOut = new ObjectOutputStream(socket.getOutputStream());
                    objOut.writeUTF(intf.getName());
                    objOut.writeUTF(method.getName());
                    objOut.writeObject(method.getParameterTypes());
                    objOut.writeObject(args);

                    // 4.同步阻塞等待服务器返回应答，获取应答后返回
                    objIn = new ObjectInputStream(socket.getInputStream());
                    return objIn.readObject();
                }finally {
                    if (socket != null) socket.close();
                    if (objOut != null) objOut.close();
                    if (objOut != null) objOut.close();
                }
            }
        });

    }
}
