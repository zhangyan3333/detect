package com.bjj.detect;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.net.*;

@SpringBootTest
public class UdpTest {

	@Test
	public void test() throws IOException {
		// 设置UDP服务器监听的端口

		int port = 9250;



		// 创建DatagramSocket对象来监听端口

		try (DatagramSocket serverSocket = new DatagramSocket(port)) {

			System.out.println("UDP服务器启动，监听端口：" + port);



			// 创建一个缓冲区来存储接收到的数据

			byte[] buffer = new byte[1024];



			// 创建一个空的DatagramPacket来接收数据

			DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);



			// 循环接收数据

			while (true) {

				// 接收数据报

				serverSocket.receive(receivePacket);



				// 转换接收到的数据为字符串

				String message = new String(receivePacket.getData(), 0, receivePacket.getLength());

				System.out.println("接收到数据：" + message);



				// 可以在这里添加处理接收到的数据的逻辑



				// 如果需要，可以发送回复

				// 示例：向发送方发送“收到”作为回复

				// 注意：实际使用中，你可能需要记录发送方的地址和端口

				String reply = "收到";

				byte[] replyBytes = reply.getBytes();

				DatagramPacket sendPacket = new DatagramPacket(replyBytes, replyBytes.length,

						receivePacket.getAddress(), receivePacket.getPort());

				serverSocket.send(sendPacket);



				// 重置DatagramPacket，以便再次接收数据

				receivePacket.setLength(buffer.length);

			}

		} catch (SocketException e) {

			System.err.println("无法打开端口 " + port + "：" + e.getMessage());

		} catch (IOException e) {

			System.err.println("IO异常：" + e.getMessage());

		}
	}
}
