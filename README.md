1 
׼����
yum install 
build-essential openssl openssl-devel unixODBC unixODBC-devel 
make gcc gcc-c++ kernel-devel m4 ncurses-devel tk tc xz
2 
Դ������أ�����װ��ʽ����˳��
wget www.rabbitmq.com/releases/erlang/erlang-18.3-1.el7.centos.x86_64.rpm
wget http://repo.iotti.biz/CentOS/7/x86_64/socat-1.7.3.2-5.el7.lux.x86_64.rpm
http://www.rabbitmq.com/releases/rabbitmq-server/v3.6.5/rabbitmq-server-3.6.5-1.noarch.rpm

����һ�ְ�װ��ʽ�������ƣ��Ƽ���
rpm -ivh erlang-18.3-1.el7.centos.x86_64.rpm
rpm -ivh socat-1.7.3.2-5.el7.lux.x86_64.rpm
rpm -ivh rabbitmq-server-3.6.5-1.noarch.rpm

3 (һ��������̿��Ժ���)
���� 
vim /etc/hosts 
�Լ� 
/etc/hostname  (Linux
����ǽ
)
3 
�����ļ���
vim /usr/lib/rabbitmq/lib/rabbitmq_server-3.6.5/ebin/rabbit.app
�����޸����롢���õȵȣ����磺
loopback_users 
�е� 
<<"guest">>,
ֻ����
guest
����������ֹͣ��
���� 
rabbitmq-server start &
ֹͣ 
rabbitmqctl app_stop
�鿴�Ƿ�����
lsof -i:5672
4 
��������
rabbitmq-plugins enable rabbitmq_management
5 
���ʵ�ַ��
http://192.168.11.81:15672/
http://106.12.13.238:15672
�û��������붼��guest1111
