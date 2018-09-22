package com.jqh.rabbitmqdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jqh.Utils;
import com.jqh.rabbitmqproducer.entity.Order;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.UUID;

//https://blog.csdn.net/csdnzouqi/article/details/79043248
public class MainActivity extends AppCompatActivity {

    private ConnectionFactory factory = new ConnectionFactory();

    private ConnectionFactory factory1 = new ConnectionFactory();//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // 发送消息
    public void sendMq(View view){
        setUpConnectFactory(factory);
        publisToAMPQ();
    }

    // 接受消息
    public void receiverMq(View view){
        setUpConnectFactory(factory1);
        factory1.setAutomaticRecoveryEnabled(true);// 设置链接恢复
        subscribe(incomingMessageHandler);
    }

    private void setUpConnectFactory(ConnectionFactory factory){
        factory.setHost("106.12.13.238");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
    }
    // 投递消息
    private void publisToAMPQ(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Connection connection = factory.newConnection();
                        Channel ch = connection.createChannel();
                        ch.confirmSelect();
                        while (true){
                            Order order = new Order();
                            order.setId("android  order id");
                            order.setName("android消息");
                            order.setMessageId(UUID.randomUUID().toString());
                            ch.basicPublish("order-exchange","order.abcd",null, Utils.toByteArray(order));
                            ch.waitForConfirmsOrDie();
                            break;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;

                }
            }
        }).start();
    }

    private Connection connection_rec ;
    private void subscribe(final Handler handler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(connection_rec != null)
                        connection_rec.close();
                    connection_rec = factory1.newConnection();
                    Channel channel = connection_rec.createChannel();
                    channel.basicQos(1);//  处理完一条，再接受下一条
                    channel.exchangeDeclare("order-exchange","topic",true);
                    AMQP.Queue.DeclareOk q = channel.queueDeclare("order-queue",true,false,false,null);
                    //AMQP.Queue.DeclareOk q = channel.queueDeclare();
                    channel.queueBind(q.getQueue(),"order-exchange","order.#"); // 绑定交换和key
                    Consumer consumer = new DefaultConsumer(channel){
                        @Override
                        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                            super.handleDelivery(consumerTag, envelope, properties, body);
                            Order order = (Order) Utils.toObject(body);
                            Message message = handler.obtainMessage();
                            message.obj = order ;
                            handler.sendMessage(message);
                        }
                    };
                    channel.basicConsume(q.getQueue(),true,consumer);

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();
    }



    // 处理handler发送的消息，然后进行操作（在主线程）
    private Handler incomingMessageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Order order = (Order) msg.obj;
            Toast.makeText(MainActivity.this,order.getName(),Toast.LENGTH_SHORT).show();
        }

    };

}
