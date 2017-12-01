package hzx.study.rpc.service.svc;

import java.io.IOException;

/**
 * Introduction:  [Simple Introduction of the java documents]
 * Package Name:  hzx.study.rpc.service.svc
 * Project Name:  hzx.study.rpc
 * Author:  ZongxingH
 * Email： zongxingh@163.com
 * Tel: 157*****778
 * Create Time:  2017/12/1 17:00
 */
public interface Server {
    public void stop();

    public void start() throws IOException;

    public void register(Class intf, Class impl);

    public boolean isRunning();

    public int getPort();
}
