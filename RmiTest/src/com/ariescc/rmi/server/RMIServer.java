package com.ariescc.rmi.server;

import com.ariescc.rmi.RemoteMethod;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {

    public static void main(String[] args) {
        try {

            String m_strName = "TheRMIExample";

            System.out.println("Server: Registering RMIExampleImpl as \"" + m_strName +"\"");

            // 在端口 5678 声明一个注册表
            Registry registry = LocateRegistry.createRegistry(5678);

            // 创建 RemoteMethod 实体
            RemoteMethod rmi = new RemoteMethod();

            String StrName = "rmi://localhost:5678/TheRMIExample";

            // 将 StrName 和 RemoteMethod 实体一起发到注册表中（StrName 在注册表中唯一
            Naming.rebind(StrName, rmi);

            System.out.println("Server: Ready...");

        } catch (RemoteException e) {
            System.out.println("Server: Failed to register RMIExampleImpl: " + e);
            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.out.println("Server: Failed to register RMIExampleImpl: " + e);
            e.printStackTrace();
        }
    }

}
