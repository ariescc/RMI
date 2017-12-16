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

        System.out.println(args.length + " " + args[4] + " " + args[4].equals(Method.REGISTER.toString()) + " " + Method.REGISTER);

        if (args.length < 7) {
            System.out.println("Usage: command don't exist.");
            System.exit(1);
        }

        if (args.length == 7 && args[4].equals(Method.REGISTER.toString())) {
            handleRegister(args);
        } else if (args.length == 11 && args[4].equals(Method.ADD.toString())) {
            handleAdd(args);
        } else if (args.length == 9 && args[4].equals(Method.QUERY.toString())) {
            handleQuery(args);
        } else if (args.length == 8 && args[4].equals(Method.DELETE.toString())) {
            handleDelete(args);
        } else if (args.length == 7 && args[4].equals(Method.CLEAR.toString())) {
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

            System.out.println(args[2] + " " + Integer.parseInt(args[3]));

            // 获得运行 RmiRegister 服务的主机上的注册表
            Registry registry = LocateRegistry.getRegistry(args[2], Integer.parseInt(args[3]));

            System.out.println(registry);

            // 查询并获得远程对象的存根
            IRemoteMethod stub = (IRemoteMethod) registry.lookup("hello");

            System.out.println(stub);

            System.out.println(args[5] + " " + args[6]);

            // 像在使用本地方法一样调用远程方法
            stub.Register(args[5], args[6]);

            System.out.println("bingo");

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }


}
