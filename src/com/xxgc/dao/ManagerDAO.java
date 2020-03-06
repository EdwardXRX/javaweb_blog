package com.xxgc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.xxgc.model.*;
import com.xxgc.utils.JDBCUtils;

public class ManagerDAO {

	private Connection conn=null;
	private PreparedStatement ps=null;
	private ResultSet rs=null;
	public int pagesize=5;//һҳ��ʾ������

	public ManagerDAO(){
		conn=new JDBCUtils().conn;
		
	}
	
	
	public int getPagesize() {
		return pagesize;
	}


	public int setPagesize(int pagesize) {
		return this.pagesize = pagesize;
	}


	/**
	 * 
	 * @param �������ݿ�
	 * @return
	 */
	
	public Manager User(Manager manager){
		//�������ݿ�
		//ʵ����users
		//��дsql���
		String sql="select * from manager where managername='"+manager.getManagername()+"'and passwd='"+manager.getPasswd()+"'";
		Manager newman=new Manager();
		//Ԥ����sql
		try {
			ps=conn.prepareStatement(sql);
			//�õ����
			 rs=ps.executeQuery();
			//������
			if(rs.next()){
				newman.setManagerid(rs.getInt(1));
				newman.setManagername(rs.getString(2));
				newman.setPasswd(rs.getString(3));
				newman.setMatate(rs.getString(4));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newman;
	}
	
	public Manager findname(String username){
		Manager manager=new Manager();
		String sql="select * from manager where managername like '"+username+"'";
		try {
			ps=conn.prepareStatement(sql);
			 rs=ps.executeQuery();
			if(rs.next()){
				manager.setManagerid(rs.getInt(1));
				manager.setManagername(rs.getString(2));
				manager.setPasswd(rs.getString(3));
				manager.setMatate(rs.getString(4));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return manager;
	}
	public ArrayList<Manager> finaname(String username){
		String sql="select * from manager where managername like '%"+username+"%'";
		ArrayList<Manager> managerList=new ArrayList<Manager>();
		
		try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				Manager manager=new Manager();
				manager.setManagerid(rs.getInt(1));
				manager.setManagername(rs.getString(2));
				manager.setPasswd(rs.getString(3));
				manager.setMatate(rs.getString(4));
				managerList.add(manager);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return managerList;
	}
	
	//��ѯδɾ������Ա
	public ArrayList<Manager> finaAll(){
		String sql="select * from manager where matate='1' or matate='2'";
		ArrayList<Manager> managerList=new ArrayList<Manager>();
		
		try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				Manager manager=new Manager();
				manager.setManagerid(rs.getInt(1));
				manager.setManagername(rs.getString(2));
				manager.setPasswd(rs.getString(3));
				manager.setMatate(rs.getString(4));
				managerList.add(manager);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return managerList;
	}

	
	//��ѯɾ���Ĺ���Ա
	public ArrayList<Manager> recycled(){
		String sql="select * from manager where matate=3";
		ArrayList<Manager> managerList=new ArrayList<Manager>();
		
		try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				Manager manager=new Manager();
				manager.setManagerid(rs.getInt(1));
				manager.setManagername(rs.getString(2));
				manager.setPasswd(rs.getString(3));
				manager.setMatate(rs.getString(4));
				managerList.add(manager);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return managerList;
	}
	
	//��ӹ���Ա
	public boolean insert(Manager manager){

		String sql="insert into manager(managername,passwd,matate) values('"+
				manager.getManagername()+"','"+
				manager.getPasswd()+"','"+
				manager.getMatate()+"')";
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

	
	public boolean update(Manager manager){
		//��dateת����������ʽ
		String sql="update manager set passwd='"+manager.getPasswd()+"',matate='"+manager.getMatate()+"' where managerid="+manager.getManagerid();
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
	
	//αɾ��
	public boolean recycled(Manager manager){
		//��dateת����������ʽ
		String sql="update manager set matate='3' where managerid="+manager.getManagerid();
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
	
	//�ָ�
	public boolean recovery(Manager manager){
		//��dateת����������ʽ
		String sql="update manager set matate='"+manager.getMatate()+"' where managerid='"+manager.getManagerid()+"'";
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
	
	public boolean delete(Manager manager){
		String sql="delete from manager where managerid="+manager.getManagerid();
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
	
	public Manager findbyid(int id){
		Manager manager=new Manager();
		
		String sql="select * from manager where managerid="+id;
		try {
			ps=conn.prepareStatement(sql);

			 rs=ps.executeQuery();
			if(rs.next()){
				manager.setManagerid(rs.getInt(1));
				manager.setManagername(rs.getString(2));
				manager.setPasswd(rs.getString(3));
				manager.setMatate(rs.getString(4));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return manager;
		
	}
	
	/**
	 * 
	 * @param ����Ա�б��ҳ
	 * @param 
	 * @return ArrayList<Manager>
	 */
	
	
	public ArrayList<Manager> page(int startpage){
		String sql="select * from manager where matate='1' or matate='2' limit "+(startpage-1)*pagesize+","+pagesize+" ";
		ArrayList<Manager> managerList=new ArrayList<Manager>();
		
		try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				Manager manager=new Manager();
				manager.setManagerid(rs.getInt(1));
				manager.setManagername(rs.getString(2));
				manager.setPasswd(rs.getString(3));
				manager.setMatate(rs.getString(4));
				managerList.add(manager);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return managerList;
	}	
	
	/**
	 * 
	 * @param ����վ�б��ҳ
	 * @param pagesize
	 * @return
	 */
	public ArrayList<Manager> Recpage(int startpage){
		String sql="select * from manager where matate='3' limit "+(startpage-1)*pagesize+","+pagesize+" ";
		ArrayList<Manager> managerList=new ArrayList<Manager>();
		
		try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				Manager manager=new Manager();
				manager.setManagerid(rs.getInt(1));
				manager.setManagername(rs.getString(2));
				manager.setPasswd(rs.getString(3));
				manager.setMatate(rs.getString(4));
				managerList.add(manager);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return managerList;
	}	
	//ip
	public String getRequestRealIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip != null && ip.contains(",")) {
			ip = ip.split(",")[0];
		}

		if (!checkIp(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (!checkIp(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (!checkIp(ip)) {
			ip = request.getHeader("X-Real-IP");
		}

		if (!checkIp(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	private static boolean checkIp(String ip) {
		if (ip == null || ip.length() == 0 || "unkown".equalsIgnoreCase(ip) ) {
			return false;
		}
		return true;
	}
	
	//���ͷ��
	public boolean maimage(String maimage,String managername){
		String sql="update manager set maimage='"+maimage+"' where managername='"+managername+"'";
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
	public String getimage(String managername){
		String sql="select maimage from manager where managername='"+managername+"'";
		Manager manager = new Manager();
		try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				manager.setMaimage(rs.getString(1));
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return manager.getMaimage();
		
	}
	

	
}
