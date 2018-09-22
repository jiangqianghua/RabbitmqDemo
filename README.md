1 
准备：
yum install 
build-essential openssl openssl-devel unixODBC unixODBC-devel 
make gcc gcc-c++ kernel-devel m4 ncurses-devel tk tc xz
2 
源码包下载：（安装方式如下顺序）
wget www.rabbitmq.com/releases/erlang/erlang-18.3-1.el7.centos.x86_64.rpm
wget http://repo.iotti.biz/CentOS/7/x86_64/socat-1.7.3.2-5.el7.lux.x86_64.rpm
http://www.rabbitmq.com/releases/rabbitmq-server/v3.6.5/rabbitmq-server-3.6.5-1.noarch.rpm

另外一种安装方式，二进制（推荐）
rpm -ivh erlang-18.3-1.el7.centos.x86_64.rpm
rpm -ivh socat-1.7.3.2-5.el7.lux.x86_64.rpm
rpm -ivh rabbitmq-server-3.6.5-1.noarch.rpm

3 (一般这个过程可以忽略)
配置 
vim /etc/hosts 
以及 
/etc/hostname  (Linux
防火墙
)
3 
配置文件：
vim /usr/lib/rabbitmq/lib/rabbitmq_server-3.6.5/ebin/rabbit.app
比如修改密码、配置等等，例如：
loopback_users 
中的 
<<"guest">>,
只保留
guest
服务启动和停止：
启动 
rabbitmq-server start &
停止 
rabbitmqctl app_stop
查看是否启动
lsof -i:5672
4 
管理插件：
rabbitmq-plugins enable rabbitmq_management
5 
访问地址：
http://192.168.11.81:15672/
http://106.12.13.238:15672
用户名和密码都是guest1111
