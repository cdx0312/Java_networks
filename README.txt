Java支持两种通信
    基于流的通信----TCP----无损的，可靠的
    基于包的通信----UDP----不可靠的

客户端/服务端
    服务器端套接字----ServerSocket
        ServerSocket serverSocket = new ServerSocket(port);   //创建服务器套接字
        Socket socket = serverSocket.accept()                 //监听连接
    客户端套接字----Socket
        Socket socket = new Socket(serverName, port)          //创建客户端套接字
    套接字：两台主机之间逻辑连接的端点，可以用来发送和接收数据

InetAddress类
    服务器程序可以使用InetAddress类来获得客户端的IP地址和主机名字等信息