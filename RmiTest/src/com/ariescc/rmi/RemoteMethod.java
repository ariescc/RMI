package com.ariescc.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 创建远程方法接口实现类
 */
public class RemoteMethod extends UnicastRemoteObject implements IRemoteMethod {

    // 由于方法参数和返回值最终都将在网络上传输，所以必须是可序列化的。
    private static final long serialVersionUID = -271947229644133464L;

    public RemoteMethod() throws RemoteException {
        super();
    }

    @Override
    public String UseRemoteMethod(String name) throws RemoteException {
        return "Hello" + name;
    }
}
