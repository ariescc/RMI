# Socket-RMI

**基于 RMI 实现分布式议程服务，并使用 XML 文本保存相关数据。**

**议程共享服务包括以下功能：用户注册、添加会议、查询会议、删除会议、清除会议；**

#### 用户注册
新用户注册需要填写用户名和密码，如果用户名已经存在，则注册失败并打印提示信息，否则注册成功并打印成功提示。使用如下命令进行用户注册：
java [clientname] [servername] [portnumber] register [username] [password]
#### 添加会议
已注册的用户可以添加会议。会议必须涉及到两个已注册的用户，一个只涉及单个用户的会议无法被创建。会议的信息包括开始时间、结束时间、会议标题、会议参与者。当一个会议被添加之后，它必须出现在创建会议的用户和参加会议的用户的议程中。如果一个用户的新会议与已经存在的会议出现时间重叠，则无法创建会议。最终，用户收到会议添加结果的提示。使用如下命令进行会议的添加：
java [clientname] [servername] [portnumber] add [username] [password] [otherusername] [start] [end] [title]
#### 查询会议
已注册的用户通过给出特定时间区间（给出开始时间和结束时间）在议程中查询到所有符合条件的会议记录。返回的结果列表按时间排序。在列表中，包括开始时间、结束时间、会议标题、会议参与者等信息。使用如下命令进行会议的查询：
java [clientname] [servername] [portnumber] query [username] [password] [start] [end]
#### 删除会议
已注册的用户可以根据会议的信息删除由该用户创建的会议。使用如下命令进行删除会议操作：
java [clientname] [servername] [portnumber] delete [username] [password] [meetingid]
#### 清除会议
已注册的用户可以清除所有由该用户创建的会议。使用如下命令进行清除操作：
java [clientname] [servername] [portnumber] clear [username] [password]
