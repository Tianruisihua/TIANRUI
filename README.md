# TIANRUI
+这是一个结合单片机功能的应用程序，主要通过无线网络进行通讯，接下来废话少说，讲一下我的类
+ wifiAdmin类：这是一个工具类，可以看到一开始初始化的无线网络连接管理器等功能，然后可以直接在主程序应用其方法了，
+ wifiuser类：这是一个用户信息类，存储着一些连接无线网络的信息，本人主要用它来界面与界面之间传输数据。
+主要活动类：主程序，上面有个搜索附近无线网络，然后列陈出来，供人选择，
+ ConnectActivity类：在主程序选择的无线网络，接下来来到了这个连接的界面，开了一个线程，通讯完马上删除无线网络
