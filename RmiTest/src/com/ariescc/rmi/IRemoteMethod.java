package com.ariescc.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 创建远程方法接口，该接口继承自 Remote 接口
 */
public interface IRemoteMethod extends Remote {

    public String UseRemoteMethod(String name) throws RemoteException;

}
