package hzx.study.rpc.provider.impl;

import hzx.study.rpc.provider.svc.HelloService;

/**
 * Introduction:  [Simple Introduction of the java documents]
 * Package Name:  hzx.study.rpc.provider.impl
 * Project Name:  hzx.study.rpc
 * Author:  ZongxingH
 * Email： zongxingh@163.com
 * Tel: 157*****778
 * Create Time:  2017/12/1 16:56
 */
public class HelloServiceImpl implements HelloService {
    public String sayHi(String whatSay) {
        return new StringBuilder("Hi,").append(whatSay).toString();
    }
}
