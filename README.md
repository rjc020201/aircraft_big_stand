# 飞机大战作业代码

这个是我面向对象编程的课的作业，经过非常挫折的和斟酌之后将代码改成这个样子，分享出来仅供学习借鉴，请不要随意照抄代码

### 使用的设计模式:sunny:
使用了的设计模式有工厂模式，单例模式，数据访问对象模式，策略模式，模板模式，观察者模式

### 缺点:sob:
代码运行速度并不快，因为添加了背景音乐的功能，但是关于线程方面的知识还非常欠缺，以及并没有添加JUnit的测试，同时自己做的测试也不多，所以有bug也非常非常非常非常非常正常，同时我的变量命名也非常非常烂，导致很多长变量且不知所云的变量出现，还有注释也非常少，总之见谅

### 注意点:stuck_out_tongue_winking_eye:
我用了两种不同的方法实现数据持久化，分别是[jdbc](https://github.com/rjc020201/aircraft_big_stand/blob/master/src/edu/hitsz/database/MySQLConect.java)和[文件读写](https://github.com/rjc020201/aircraft_big_stand/blob/master/src/edu/hitsz/database/TagDaoImplementByFile.java)，如果你的系统并未安装8.0以上版本mysql同时没有mysql的jar包那么就无法测试jdbc的方法，文件读写将生成三个数据文件来保存数据，当然如果生成文件有问题我还写了一个非持久化存储数据的[类](https://github.com/rjc020201/aircraft_big_stand/blob/master/src/edu/hitsz/database/TagDaoImplement1.java)，只要你将代码中对应的使用了TagDao的对象的位置修改为相应的类就可以了，最后一种方法无法保存游戏记录，每次运行都会刷新游戏记录

### 欢迎及轻喷:bomb:
欢迎pr和star，有bug欢迎指正，good luck!!!:kissing_heart: