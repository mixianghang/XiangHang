package com.run.crawler.weibo.frontier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.run.crawler.weibo.entity.WeiboPage;

public class TelnetListener extends TimerTask {

	Logger log = Logger.getLogger(this.getClass());

	Frontier frontier;

	public TelnetListener(Frontier frontier) {
		this.frontier = frontier;
	}

	private int port = 7777;

	@Override
	public void run() {
		try {
			log.info("TelnetListener ����");
			ServerSocket serverScocket = new ServerSocket(port);
			while (true) {
				log.info("�����˿�     : " + port);
				Socket socket = serverScocket.accept();
				/**
				 * ��ʱ����30S
				 */
				socket.setSoTimeout(30*1000);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream(),
						true);

				String cmond = "";
				try {
					while ((cmond = in.readLine()) != null) {
						cmond = cmond.toLowerCase().trim();
						log.info("cmond : " + cmond);
						if ("quit".equals(cmond)) {
							log.info("quit ..... ");
							if (null != in) {
								in.close();
							}
							if (null != out) {
								out.close();
							}
							break;
						} else if ("getall readylist".equals(cmond)) {
							List<WeiboPage> list = frontier.lookReadyList();
							fillOut(list, out);
						} else if ("getall disablelist".equals(cmond)) {
							List<WeiboPage> list = frontier.lookDisableList();
							fillOut(list, out);
						} else {
							out.append("��������������(�����ִ�Сд):").append("\r\n");
							out.append("1��quit(�˳�)").append("\r\n");
							out.append("2��getall readylist(�鿴readyList�е�����)")
									.append("\r\n");
							out.append(
									"3��getall disablelist(�鿴disableList�е�����)")
									.append("\r\n");
						}
						out.flush();
					}
				} catch (IOException ie) {
					log.info("���յķ������˳�........");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void fillOut(List<WeiboPage> list, PrintWriter out) {
		for (WeiboPage weiboPage : list) {
			out.append(weiboPage.toString()).append("\r\n");
			out.println("-----------------------------------------");
		}
	}

	public static void main(String[] args) {
		new TelnetListener(null).run();
	}

}
