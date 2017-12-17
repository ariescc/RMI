package com.ariescc.rmi.server;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveDataXML {

    String userFile = "User.xml";

    String meetingFile = "Meeting.xml";

    /**
     * 创建会议写入XML中，首先判断 Meeting.xml 文件是否存在
     * @param userOne 创建者
     * @param password 创建者密码
     * @param userTwo 参与者
     * @param startTime 会议开始时间
     * @param endTime 会议结束时间
     * @param title 会议名称
     * @return
     * @throws TransformerException
     * @throws ParserConfigurationException
     */
    public String MeetingCreateToXML(String userOne, String password, String userTwo,
                                   String startTime, String endTime, String title)
            throws TransformerException, ParserConfigurationException {

        File file = new File(meetingFile);
        // 如果 MeetingTable.xml 不存在
        if(!file.exists()) {
            initMeetingXML();
        }

        return updateMeetingTable(userOne, password, userTwo, startTime, endTime, title);

    }

    /**
     * 添加新建会议数据，修改 Meeting.xml 并保存数据
     * @param userOne 创建者
     * @param password 创建者密码
     * @param userTwo 参与者
     * @param startTime 会议开始时间
     * @param endTime 会议结束时间
     * @param title 会议名称
     * @return
     */
    private String updateMeetingTable(String userOne, String password, String userTwo,
                                    String startTime, String endTime, String title) {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        // 判断会议是否能够成功创建
        if (judgeMeetingCreateOrNot(userOne, password, userTwo, startTime, endTime)) {

        }

        try {

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(meetingFile));

            Element root1 = doc.getDocumentElement();

            // 创建一个会议的元素结点
            Element eltMeetObj = doc.createElement("MeetObj");
            Element eltUserOne = doc.createElement("User1");
            Element eltPassword = doc.createElement("Password");
            Element eltUserTwo = doc.createElement("User2");
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
            eltPassword.appendChild(txtPassword);
            eltUserTwo.appendChild(txtUserTwo);
            eltStartTime.appendChild(txtStartTime);
            eltEndTime.appendChild(txtEndTime);
            eltTitle.appendChild(txtTitle);

            // 将 User1 和 User2 和 StartTime 和 EndTime 添加为 Meeting 的子节点
            eltMeetObj.appendChild(eltUserOne);
            eltMeetObj.appendChild(eltPassword);
            eltMeetObj.appendChild(eltUserTwo);
            eltMeetObj.appendChild(eltStartTime);
            eltMeetObj.appendChild(eltEndTime);
            eltMeetObj.appendChild(eltTitle);

            root1.appendChild(eltMeetObj);

            // 创建 Meeting.XML 文件
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();

            // 将 document 对象转化成 XML 文件
            tf.transform(new DOMSource(doc), new StreamResult(new File(meetingFile)));

            tf.setOutputProperty(OutputKeys.INDENT, "yes");

            String response = "会议创建成功！";
            return response;


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return "程序错误";

    }

    /**
     *
     * 判断创建者用户名和密码是否正确
     *
     * 判断两个用户是否有其他会议，有其他会议的话是否有时间冲突
     *
     * @param userOne 创建者用户名
     * @param password 创建者密码
     * @param userTwo 第二个用户的用户名
     * @param startTime 会议开始时间
     * @param endTime 会议结束时间
     * @return
     */
    private boolean judgeMeetingCreateOrNot(String userOne, String password,
                                            String userTwo, String startTime, String endTime) {

        // 判断创建者是否合法，参与者是否合法
        boolean isUserAvailable = judgeMeetingUserAvailable(userOne, password, userTwo);

        // 判断会议时间是否与参与者其他会议有冲突
        boolean isTimeAvailable = judgeMeetingTimeAvailable(userOne, userTwo, startTime, endTime);

        // 如果两者均合法
        if (isUserAvailable && isTimeAvailable) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断会议时间是否与参会者的其他会议时间冲突
     * @param startTime 会议开始时间
     * @param endTime 会议结束时间
     * @return
     */
    private boolean judgeMeetingTimeAvailable(String userOne, String userTwo, String startTime, String endTime) {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(userFile));

//            System.out.println(doc == null);

//            System.out.println("doc: " + doc);

            Element root = doc.getDocumentElement();

            // 获得所有 MeetingObj 子节点
            NodeList childNodes = root.getChildNodes();

            for (int i = 0; i < childNodes.getLength(); i++) {

                Node curNode = childNodes.item(i);

                String userone = "";
                String usertwo = "";
                String st = "";
                String ed = "";

                if ("MeetingObj".equals(curNode.getNodeName())) {

                    // 获取 MeetingObj 下的所有子节点
                    NodeList nodeDetail = curNode.getChildNodes();

                    for (int j = 0; j < nodeDetail.getLength(); j++) {

                        Node detail = nodeDetail.item(j);


                        if ("User1".equals(detail.getNodeName())) {
                            if (userOne.equals(detail.getTextContent())) {
                                userone = detail.getTextContent();
                            }
                        } else if ("User2".equals(detail.getNodeName())) {
                            if (userTwo.equals(detail.getTextContent())) {
                                usertwo = detail.getTextContent();
                            }
                        } else if ("StartTime".equals(detail.getNodeName())) {
                            st = detail.getTextContent();
                        } else if ("EndTime".equals(detail.getNodeName())) {
                            ed = detail.getTextContent();
                        }

                    }

                    // 如果 userone 或者 usertwo 不为空，则判断时间是否冲突，冲突则结束
                    if (!userone.equals("") || !usertwo.equals("")) {

                        // 时间比较
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                        // 新建会议的开始时间
                        Date beginTimeCreate = sdf.parse(startTime);

                        // 已存在的会议的开始时间
                        Date beginTimeExist = sdf.parse(st);

                        // 新建会议的结束时间
                        Date endTimeCreate = sdf.parse(endTime);

                        // 已存在的会议的结束时间
                        Date endTimeExist = sdf.parse(ed);

                        // 新建会议开始时间 < 已存在会议结束时间
                        // 新建会议结束时间 > 已存在会议的开始时间
                        // 会议时间冲突，返回 false
                        if (beginTimeCreate.before(endTimeExist)
                                && beginTimeExist.before(endTimeCreate)) {
                            return false;
                        }

                    }

                }
            }

            // 所有的 Meeting 均不存在冲突
            return true;

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("程序错误！");

        return false;

    }

    /**
     * 判断创建者身份是否合法（用户名、密码正确）
     * 判断参与者用户名是否有效
     * @param userOne 创建者用户名
     * @param password 创建者密码
     * @param userTwo 参与者用户名
     * @return
     */
    private boolean judgeMeetingUserAvailable(String userOne, String password, String userTwo) {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(userFile));

//            System.out.println(doc == null);

//            System.out.println("doc: " + doc);

            Element root = doc.getDocumentElement();

            // 判断创建者账号密码正确
            boolean isUserAvailable = judgeUserAvailable(userOne, password);

            // 判断参与者账号有效
            boolean isParticipantAvailable = judgeUserNameAvaliable(userTwo);

            if (isUserAvailable && isParticipantAvailable) {
                return true;
            } else {
                return false;
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        System.out.println("程序错误!");

        return false;

    }

    /**
     * 判断用户用户名密码是否正确
     * @param userOne 用户名
     * @param password 密码
     * @return
     */
    private boolean judgeUserAvailable(String userOne, String password) {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(userFile));

//            System.out.println(doc == null);

//            System.out.println("doc: " + doc);

            Element root = doc.getDocumentElement();

            NodeList childNodes = root.getChildNodes();

            boolean isUserName = false;
            boolean isPassword = false;

            for (int i = 0; i < childNodes.getLength(); i++) {

                Node curNode = childNodes.item(i);

                if ("UserObj".equals(curNode.getNodeName())) {

                    NodeList nodeDetail = curNode.getChildNodes();

                    for (int j = 0; j < nodeDetail.getLength(); j++) {
                        Node detail = nodeDetail.item(j);

                        if ("UserName".equals(detail.getNodeName())) {
                            if (userOne.equals(detail.getTextContent())) {
                                isUserName = true;
                            }
                        } else if ("Password".equals(detail.getNodeName())) {
                            if (password.equals(detail.getTextContent())) {
                                isPassword = true;
                            }
                        }
                    }

                }

            }

            if (isUserName && isPassword) {
                return true;
            } else {
                return false;
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        System.out.println("程序错误！");

        return false;

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

        // 创建 Meeting.XML 文件
        TransformerFactory tff = TransformerFactory.newInstance();
        Transformer tf = tff.newTransformer();

        // 将 document 对象转化成 XML 文件
        tf.transform(new DOMSource(document), new StreamResult(new File(meetingFile)));

        tf.setOutputProperty(OutputKeys.INDENT, "yes");


    }

    /**
     * 注册新用户
     * 检查该用户名是否已存在，如果不存在创建成功
     * @param username 用户名
     * @param password 密码
     * @throws ParserConfigurationException
     * @throws TransformerException
     */
    public String UserRegisterToXML(String username, String password)
            throws ParserConfigurationException, TransformerException {

        File file = new File(userFile);

//        System.out.println(file.exists());

        // 如果 UserTable.xml 不存在
        if(!file.exists()) {
            // 初始化 User.xml 结构和根节点
            initUserXML();
        }

        // 添加一组新的用户节点
        return updateUserTable(username, password);

    }

    /**
     * 向 UserTable 中插入一条新数据
     * @param username 注册用户名
     * @param password 用户密码
     */
    private String updateUserTable(String username, String password) {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        // 判断该用户名是否可注册
        if(!judgeUserNameAvaliable(username)) {

            String response = "此用户名已经被注册！";

            return response;

        }

        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(userFile));

//            System.out.println(doc == null);

//            System.out.println("doc: " + doc);

            Element root = doc.getDocumentElement();

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

            root.appendChild(eltUser);

            System.out.println(doc);

            // 创建 Meeting.XML 文件
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();

            // 将 document 对象转化成 XML 文件
            tf.transform(new DOMSource(doc), new StreamResult(new File(userFile)));

            tf.setOutputProperty(OutputKeys.INDENT, "yes");

            String response = "注册成功！";

            return response;

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return "程序错误！";

    }

    /**
     * 判断用户名是否可注册
     * @param username 客户端输入的用户名
     * @return
     */
    private boolean judgeUserNameAvaliable(String username) {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(userFile));

//            System.out.println(doc == null);

//            System.out.println("doc: " + doc);

            Element root = doc.getDocumentElement();

            // 获取所有子节点 UserObj
            NodeList childNodes = root.getChildNodes();

            // 遍历所有的 UserObj
            for(int i = 0; i < childNodes.getLength(); i++) {

                // 获得每个位置的节点
                Node curNode = childNodes.item(i);

                if ("UserObj".equals(curNode.getNodeName())) {

                    // 获取 UserObj 下的子节点
                    NodeList nodeDetail = curNode.getChildNodes();

                    // 遍历 UserObj 下的子节点
                    for (int j = 0; j < nodeDetail.getLength(); j++) {

                        // 获取 UserObj 下的每一个子节点
                        Node detail = nodeDetail.item(j);

                        // 用户名
                        if ("UserName".equals(detail.getNodeName())) {
                            if (username.equals(detail.getTextContent())) {

                                System.out.println("用户名已经被注册，不可注册！");

                                return false;
                            }
                        }

                    }
                }
            }

            System.out.println("用户名未在数据表内出现，可以注册！");

            return true;

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        System.out.println("程序异常");

        return false;

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


    public String QueryMeetingFromXML(String username, String password, String startTime, String endTime) {

        // 判断要查询的用户的用户名和密码是否正确
        if (!judgeUserAvailable(username, password)) {
            return "检查欲查询的用户名和密码是否正确！";
        }

        // 用户检查正确，查询该用户所有的会议

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(userFile));

            Element root = doc.getDocumentElement();


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }
}
