package com.ariescc.rmi.server;

import com.ariescc.rmi.IRemoteMethod;
import com.ariescc.rmi.RemoteMethod;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * 创建 Server 并把对象注册到端口
 */
public class RmiServer {

    public static void main(String[] args) {
        try {
            // 实例化远程对象，同时导出该对象
            IRemoteMethod remoteMethod = new RemoteMethod();

            // 获得本地 RMI 注册表对象
            Registry registry = LocateRegistry.createRegistry(8081);

            // 在注册表中绑定远程对象
            registry.bind("hello", remoteMethod);

            // 通告服务器端已准备好
            System.out.println("System ready!");

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            System.out.println("在向注册表中绑定时出现了问题，名字已被绑定了!"
                    + e.getMessage());
            e.printStackTrace();
        }
    }

}
