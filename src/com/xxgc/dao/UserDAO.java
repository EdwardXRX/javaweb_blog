package com.xxgc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.xxgc.model.Manager;
import com.xxgc.model.User;
import com.xxgc.utils.JDBCUtils;

public class UserDAO {

	private Connection conn=null;
	private PreparedStatement ps=null;
	private ResultSet rs=null;
	public int pagesize=5;//һҳ��ʾ������

	public UserDAO(){
		conn=new JDBCUtils().conn;
	}
	
	public int getPagesize() {
		return pagesize;
	}

	public int setPagesize(int pagesize) {
		return this.pagesize = pagesize;
	}
	
	public User User(User user){
		//�������ݿ�
		//ʵ����users
		//��дsql���
		String sql="select * from user where username='"+user.getUsername()+"'and passwd='"+user.getPasswd()+"'";
		User newman=new User();
		//Ԥ����sql
		try {
			ps=conn.prepareStatement(sql);
			//�õ����
			 rs=ps.executeQuery();
			//������
			if(rs.next()){
				newman.setUserid(rs.getInt(1));
				newman.setUsername(rs.getString(2));
				newman.setPasswd(rs.getString(3));
				newman.setUserstate(rs.getString(4));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newman;
	}
	
	public User findname(String username){
		User user=new User();
		String sql="select * from user where username like '"+username+"'";
		try {
			ps=conn.prepareStatement(sql);
			 rs=ps.executeQuery();
			if(rs.next()){
				user.setUserid(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPasswd(rs.getString(3));
				user.setUserstate(rs.getString(4));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	public boolean insert(User user){

		String sql="insert into user(username,passwd,userstate) values('"+
				user.getUsername()+"','"+
				user.getPasswd()+"','"+
				user.getUserstate()+"')";
		try {
			ps = conn.prepareStatement(sql);
			int a=ps.executeUpdate();
			if(a>0){
				ps.close();
				return true;
			}
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public String getimage(String username){
		String sql="select userimage from user where username='"+username+"'";
		User user=new User();
		try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				user.setUserimage(rs.getString(1));
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user.getUserimage();
		
	}
	public String getimage(int userid){
		String sql="select userimage from user where userid='"+userid+"'";
		User user=new User();
		try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				user.setUserimage(rs.getString(1));
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user.getUserimage();
		
	}
	public int findbyname(String name){
		String sql="select userid from user where username='"+name+"'";
		User user = new User();
		try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next()){
				user.setUserid(rs.getInt(1));
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user.getUserid();
	}
	public ArrayList<User> page(int startpage){
		String sql="select * from user where userstate='1' or userstate='2' limit "+(startpage-1)*pagesize+","+pagesize+" ";
		ArrayList<User> userList=new ArrayList<User>();
		
		try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				User user=new User();
				user.setUserid(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPasswd(rs.getString(3));
				user.setUserstate(rs.getString(4));
				userList.add(user);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userList;
	}
	
	public ArrayList<User> finaAll(){
		String sql="select * from user where userstate='1' or userstate='2'";
		ArrayList<User> userList=new ArrayList<User>();
		
		try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				User user=new User();
				user.setUserid(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPasswd(rs.getString(3));
				user.setUserstate(rs.getString(4));
				userList.add(user);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userList;
	}
	
	public boolean delete(User user){
		String sql="delete from user where userid="+user.getUserid();
		try {
			ps = conn.prepareStatement(sql);
			int a=ps.executeUpdate();
			if(a>0){
				return true;
			}
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	//���ͷ��
	public boolean maimage(String maimage,int id){
		String sql="update user set userimage='"+maimage+"' where userid='"+id+"'";
		try {
			ps = conn.prepareStatement(sql);
			int a=ps.executeUpdate();
			if(a>0){
				return true;
			}
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public User findbyid(int id){
		User user=new User();
		
		String sql="select * from user where userid="+id;
		try {
			ps=conn.prepareStatement(sql);

			 rs=ps.executeQuery();
			if(rs.next()){
				user.setUserid(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPasswd(rs.getString(3));
				user.setUserstate(rs.getString(4));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
		
	}
	
	public boolean update(User user){
		//��dateת����������ʽ
		String sql="update user set passwd='"+user.getPasswd()+"',userstate='"+user.getUserstate()+"' where userid="+user.getUserid();
		try {
			ps = conn.prepareStatement(sql);
			int a=ps.executeUpdate();
			if(a>0){
				return true;
			}
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
}
