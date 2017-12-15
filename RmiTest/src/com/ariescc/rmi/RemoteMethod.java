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
    public void Register(String username, String password) throws RemoteException {

        try {
            saveDataXML.UserRegisterToXML(username, password);

            System.out.println("Successful!");

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Add(String userOneName, String password, String userTwoName,
                    String startTime, String endTime, String title) throws RemoteException {
        try {
            saveDataXML.MeetingCreateToXML(userOneName, password, userTwoName, startTime, endTime, title);

            System.out.println("Successful!");
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Query(String username, String password, String startTime,
                      String endTime) throws RemoteException {

    }

    @Override
    public void Delete(String username, String password, String meetingId) throws RemoteException {

    }

    @Override
    public void Clear(String username, String password) throws RemoteException {

    }


}
