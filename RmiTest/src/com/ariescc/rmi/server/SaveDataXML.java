package com.ariescc.rmi.server;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class SaveDataXML {

    String userFile = "User.xml";

    String meetingFile = "Meeting.xml";

    public void MeetingCreateToXML(String userOne, String password, String userTwo,
                                   String startTime, String endTime, String title)
            throws TransformerException, ParserConfigurationException {

        File file = new File(meetingFile);
        // 如果 MeetingTable.xml 不存在
        if(!file.exists()) {
            initMeetingXML();
        }

        updateMeetingTable(userOne, password, userTwo, startTime, endTime, title);

    }

    private void updateMeetingTable(String userOne, String password, String userTwo,
                                    String startTime, String endTime, String title) {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(userFile);

            // 创建一个会议的元素结点
            Element eltMeetObj = doc.createElement("MeetObj");
            Element eltUserOne = doc.createElement("User_1");
            Element eltpassword = doc.createElement("Password");
            Element eltUserTwo = doc.createElement("User_2");
            Element eltStartTime = doc.createElement("StartTime");
            Element eltEndTime = doc.createElement("EndTime");
            Element eltTitle = doc.createElement("Title");

            // 创建会议信息的文本结点
            Text txtUserOne = doc.createTextNode(userOne);
            Text txtPassword = doc.createTextNode(password);
            Text txtUserTwo = doc.createTextNode(userTwo);
            Text txtStartTime = doc.createTextNode(startTime);
            Text txtEndTime = doc.createTextNode(endTime);
            Text txtTitle = doc.createTextNode(title);

            // 将文本结点添加为对应元素结点的子节点
            eltUserOne.appendChild(txtUserOne);
            eltpassword.appendChild(txtPassword);
            eltUserTwo.appendChild(txtUserTwo);
            eltStartTime.appendChild(txtStartTime);
            eltEndTime.appendChild(txtEndTime);
            eltTitle.appendChild(txtTitle);

            // 将 User1 和 User2 和 StartTime 和 EndTime 添加为 Meeting 的子节点
            eltMeetObj.appendChild(eltUserOne);
            eltMeetObj.appendChild(eltpassword);
            eltMeetObj.appendChild(eltUserTwo);
            eltMeetObj.appendChild(eltStartTime);
            eltMeetObj.appendChild(eltEndTime);
            eltMeetObj.appendChild(eltTitle);


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    /**
     * 初始化 Meeting.xml
     * @throws ParserConfigurationException
     * @throws TransformerException
     */
    private void initMeetingXML() throws ParserConfigurationException, TransformerException {

        // 准备工作
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        // 获取一个 Document 对象
        Document document = db.newDocument();

        // 创建根元素
        Element meeting = document.createElement("Meeting");

        // 将根元素 UserTable 添加到 document 中
        document.appendChild(meeting);

        // 创建 UserTable.XML 文件
        TransformerFactory tff = TransformerFactory.newInstance();
        Transformer tf = tff.newTransformer();

        // 将 document 对象转化成 XML 文件
        tf.transform(new DOMSource(document), new StreamResult(new File("Meeting.xml")));

        tf.setOutputProperty(OutputKeys.INDENT, "yes");


    }

    /**
     * 注册新用户
     * 检查该用户名是否已存在，如果不存在创建成功
     * @param username
     * @param password
     * @throws ParserConfigurationException
     * @throws TransformerException
     */
    public void UserRegisterToXML(String username, String password) throws ParserConfigurationException, TransformerException {

        File file = new File(userFile);
        // 如果 UserTable.xml 不存在
        if(!file.exists()) {
            initUserXML();
        }

        updateUserTable(username, password);


    }

    /**
     * 向 UserTable 中插入一条新数据
     * @param username 注册用户名
     * @param password 用户密码
     */
    private void updateUserTable(String username, String password) {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(userFile);

            // 创建一个用户的元素结点
            Element eltUser = doc.createElement("UserObj");
            Element eltUserName = doc.createElement("UserName");
            Element eltPassword = doc.createElement("Password");

            // 创建用户信息的文本结点
            Text txtUserName = doc.createTextNode(username);
            Text txtPassword = doc.createTextNode(password);

            // 将文本结点添加为对应元素结点的子节点
            eltUserName.appendChild(txtUserName);
            eltPassword.appendChild(txtPassword);

            // 将 UserName 和 Password 添加为 User 的子节点
            eltUser.appendChild(eltUserName);
            eltUser.appendChild(eltPassword);


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 初始化 DocumentBuilder 对象
     */
    private void initUserXML() throws ParserConfigurationException, TransformerException {

        // 准备工作
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        // 获取一个 Document 对象
        Document document = db.newDocument();

        // 创建根元素
        Element user = document.createElement("User");

        // 将根元素 UserTable 添加到 document 中
        document.appendChild(user);

        // 创建 UserTable.XML 文件
        TransformerFactory tff = TransformerFactory.newInstance();
        Transformer tf = tff.newTransformer();

        // 将 document 对象转化成 XML 文件
        tf.transform(new DOMSource(document), new StreamResult(new File("User.xml")));

        tf.setOutputProperty(OutputKeys.INDENT, "yes");

    }

}
