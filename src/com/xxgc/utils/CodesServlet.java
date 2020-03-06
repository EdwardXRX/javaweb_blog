package com.xxgc.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CodesServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		BufferedImage bfi = new BufferedImage(80, 25, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = (Graphics2D) bfi.getGraphics();
		
		// ���ñ���ɫ
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, 80, 25);
		
		// ���ñ߿�
		graphics.setColor(Color.BLACK);
		graphics.drawRect(0, 0, 80 - 1, 25 - 1);
		
		String str = drawRandomNum(graphics);
		System.out.println(str);
		
		HttpSession session = request.getSession();
		session.setAttribute("code", str);
		
		drawPoint(bfi);
		drawInfluencedLine(graphics);
		
		response.setHeader("Expires", "-1");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		
		response.setHeader("content-type", "image/jpeg");
		
		ImageIO.write(bfi, "jpg", response.getOutputStream());
		}
		
		// ���ϵ�
		private void drawPoint(BufferedImage bfi) {
		int area = (int) (0.02 * 80 * 25);
		for (int i = 0; i < area; ++i) {
		    int x = (int) (Math.random() * 80);
		    int y = (int) (Math.random() * 25);
		    bfi.setRGB(x, y, (int) (Math.random() * 255));
		}
		}
		
		private void drawInfluencedLine(Graphics2D g) {
		//������֤���еĸ�����
		for (int i = 0; i < 3; i++) {
		    Random random = new Random();
		    //�����ȡ�����ߵ������յ�
		    int xstart = random.nextInt(80);
		    int ystart = random.nextInt(25);
		    int xend = random.nextInt(80);
		    int yend = random.nextInt(25);
		    g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
		    g.drawLine(xstart, ystart, xend, yend);
		}
		}
		
		// �����������ת��д��
		private String drawRandomNum(Graphics2D g) {
		char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
		Random random = new Random();
		int index;
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 4; i++) {
		    // ���ַ������л������ַ�
		    index = random.nextInt(chars.length);
		    String value = chars[index] + "";
		    // ��������ַ�����ɫ
		    g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
		    // �����ʽ
		    Font font = new Font(null, Font.BOLD, 20);
		    g.setFont(font);
		    //����-30~30֮�������
		    int dgree = random.nextInt(60) - 30;
		    //��ת
		    g.rotate(dgree * Math.PI / 180, i * 20 + 2, 23);
		    // д���ַ�
		    g.drawString(value, i * 20 + 2, 20);
		    // �ѻ�����ת��ȥ,���Ӱ���´εĻ���
		    g.rotate(-dgree * Math.PI / 180, i * 20 + 2, 23);
		    builder.append(value);
		}
		return builder.toString();
		}
		
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
