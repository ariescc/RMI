package com.ariescc.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteMethod extends Remote {

    // 调用时候发生网络问题或者Server挂掉，抛出 RemoteException

    String sayHello() throws RemoteException;

    // 用户注册
    String Register(String username, String password) throws RemoteException;

    // 添加会议
    String Add(String userOneName, String password, String userTwoName, String startTime,
             String endTime, String title) throws RemoteException;

    // 查询会议
    String Query(String username, String password, String startTime, String endTime) throws RemoteException;

    // 删除会议：根据会议ID，删除由本用户创建的会议
    String Delete(String username, String password, String meetingId) throws RemoteException;

    // 清除会议：清除所有本用户创建的会议
    String Clear(String username, String password) throws RemoteException;

}
