package com.ariescc.rmi;

import com.ariescc.rmi.server.SaveDataXML;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * IRemoteMethod 实现类
 */
public class RemoteMethod extends UnicastRemoteObject implements IRemoteMethod {

    SaveDataXML saveDataXML = new SaveDataXML();

    public RemoteMethod() throws RemoteException {
        super();
    }

    @Override
    public String sayHello() throws RemoteException {
        return "hello, world";
    }

    @Override
    public String Register(String username, String password) throws RemoteException {

        try {

            System.out.println("Successful!");

            return saveDataXML.UserRegisterToXML(username, password);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return "REGISTER 远程方法实现错误";

    }

    @Override
    public String Add(String userOneName, String password, String userTwoName,
                    String startTime, String endTime, String title) throws RemoteException {
        try {

            return saveDataXML.MeetingCreateToXML(userOneName, password, userTwoName,
                    startTime, endTime, title);

        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        return "ADD 远程方法实现错误";

    }

    @Override
    public String Query(String username, String password, String startTime,
                      String endTime) throws RemoteException {

        try {
            return saveDataXML.QueryMeetingFromXML(username, password, startTime, endTime);
        }

        return "Query 远程方法实现错误";
    }

    @Override
    public String Delete(String username, String password, String meetingId) throws RemoteException {

        return "";
    }

    @Override
    public String Clear(String username, String password) throws RemoteException {

        return "";
    }


}
