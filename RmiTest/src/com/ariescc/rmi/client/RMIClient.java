package com.ariescc.rmi.client;

import com.ariescc.rmi.IRemoteMethod;
import com.ariescc.rmi.Method;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiClient {

    public RmiClient() {}

    public static void main(String[] args) {

        if (args.length < 7) {
            System.out.println("Usage: command don't exist.");
            System.exit(1);
        }

        if (args.length == 7 && args[4].equals(Method.register)) {
            handleRegister(args);
        } else if (args.length == 11 && args[4].equals(Method.add)) {
            handleAdd(args);
        } else if (args.length == 9 && args[4].equals(Method.query)) {
            handleQuery(args);
        } else if (args.length == 8 && args[4].equals(Method.delete)) {
            handleDelete(args);
        } else if (args.length == 7 && args[4].equals(Method.clear)) {
            handleDelete(args);
        } else {
            System.out.println("Illegal command. Please check your input!");
            System.exit(1);
        }


    }

    private static void handleDelete(String[] args) {

        try {

            // 获得运行 RmiRegister 服务的主机上的注册表
            Registry registry = LocateRegistry.getRegistry(args[2], Integer.parseInt(args[3]));

            // 查询并获得远程对象的存根
            IRemoteMethod stub = (IRemoteMethod) registry.lookup("hello");

            // 像在使用本地方法一样调用远程方法
            String response = stub.sayHello();

            System.out.println("Response: " + response);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }

    private static void handleQuery(String[] args) {

        try {

            // 获得运行 RmiRegister 服务的主机上的注册表
            Registry registry = LocateRegistry.getRegistry(args[2], Integer.parseInt(args[3]));

            // 查询并获得远程对象的存根
            IRemoteMethod stub = (IRemoteMethod) registry.lookup("hello");

            // 像在使用本地方法一样调用远程方法
            String response = stub.sayHello();

            System.out.println("Response: " + response);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }

    private static void handleAdd(String[] args) {

        try {

            // 获得运行 RmiRegister 服务的主机上的注册表
            Registry registry = LocateRegistry.getRegistry(args[2], Integer.parseInt(args[3]));

            // 查询并获得远程对象的存根
            IRemoteMethod stub = (IRemoteMethod) registry.lookup("hello");

            // 像在使用本地方法一样调用远程方法
            stub.Add(args[5], args[6], args[7], args[8], args[9], args[10]);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }

    private static void handleRegister(String[] args) {

        try {

            // 获得运行 RmiRegister 服务的主机上的注册表
            Registry registry = LocateRegistry.getRegistry(args[2], Integer.parseInt(args[3]));

            // 查询并获得远程对象的存根
            IRemoteMethod stub = (IRemoteMethod) registry.lookup("hello");

            // 像在使用本地方法一样调用远程方法
            stub.Register(args[5], args[6]);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }


}
