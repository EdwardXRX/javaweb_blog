package com.xxgc.test;

import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.sql.Connection;

public class ConnectTest {

	public static void main(String[] args) {
		String Driver="com.mysql.jdbc.Driver";
		String url="jdbc:mysql://localhost:3306/blog";
		String username="root";
		String passwd="123456";
		
		//�������ݿ�
		
		try {
			Class.forName(Driver).newInstance();//��������
			//�������ݿ⣬ͨ����ַ���û���������
			Connection coon=DriverManager.getConnection(url, username, passwd);
			System.out.println("���ݿ����ӳɹ�");
			//����SQL���
			String sql="delete from manager where managerid=1";
			PreparedStatement ps=coon.prepareStatement(sql);
			//�õ����
			int a=ps.executeUpdate();
			if(a>0){
				System.out.println("ɾ���ɹ�");
			}else{
				System.out.println("ɾ��ʧ��");
			}
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("���ݿ����ʧ��");
		}
	}

}
/*String sql="select * from manager where managerid=1";
			PreparedStatement ps=coon.prepareStatement(sql);
			//�õ����
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				System.out.println("managerid:"+rs.getInt(1));
				System.out.println("managername:"+rs.getString(2));
				System.out.println("passwd:"+rs.getString(3));
				System.out.println("mstate:"+rs.getString(4));
			}*/