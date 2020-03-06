package com.xxgc.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xxgc.model.Article;
import com.xxgc.model.Catalog;
import com.xxgc.model.Count;
import com.xxgc.utils.JDBCUtils;

public class ArticleDAO {
	private Connection conn=null;
	private PreparedStatement ps=null;
	private ResultSet rs=null;
	public int pagesize = 5;//һҳ��ʾ������
	private String nowtime=null;//δ��ʽ��ʱ��
	private String res=null;//ʱ���
	
	public String getRes() {
		return res;
	}
	
	public String getNowtime() {
		return nowtime;
	}

	public int getPagesize() {
		return pagesize;
	}


	public int setPagesize(int pagesize) {
		return this.pagesize = pagesize;
	}

	public ArticleDAO(){
		conn=new JDBCUtils().conn;
		
	}
	
	//ɾ������
	public boolean delete(Article article){
		String sql="delete from article where arid="+article.getArid();
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
	
	//ͨ��arid�����ǰ��������Ϣ
	public Article findbyarid(Article article){
		String sql="select * from article where arid="+article.getArid();
		try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next()){
				article.setArid(rs.getInt(1));
				article.setCaid(rs.getInt(2));
				article.setArnumber(rs.getString(3));
				article.setArtitle(rs.getString(4));
				article.setArimage(rs.getString(5));
				article.setArcontent(rs.getString(6));
				article.setAruser(rs.getString(7));
				article.setArtime(rs.getString(8));
				article.setArstate(rs.getString(9));
				article.setClick(rs.getInt(10));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return article;
	}
	
	
	//����֮���ҳ��ȫ������
	public ArrayList<Article> nameAll(String artitle){
		String sql="select * from article where artitle like '%"+artitle+"%'";
		ArrayList<Article> ArticleList=new ArrayList<Article>();
		try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				Article article=new Article();
				article.setArid(rs.getInt(1));
				article.setCaid(rs.getInt(2));
				article.setArnumber(rs.getString(3));
				article.setArtitle(rs.getString(4));
				article.setArimage(rs.getString(5));
				article.setArcontent(rs.getString(6));
				article.setAruser(rs.getString(7));
				article.setArtime(rs.getString(8));
				article.setArstate(rs.getString(9));
				ArticleList.add(article);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ArticleList;
	}
	
	//	�����������ķ�ҳ
	public ArrayList<Article> namepage(int startpage,String artitle){
		String sql="select * from article where artitle like '%"+artitle+"%' order by arnumber desc limit "+(startpage-1)*pagesize+","+pagesize+"";
		ArrayList<Article> ArticleList=new ArrayList<Article>();
		
		try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				Article article=new Article();
				article.setArid(rs.getInt(1));
				article.setCaid(rs.getInt(2));
				article.setArnumber(rs.getString(3));
				article.setArtitle(rs.getString(4));
				article.setArimage(rs.getString(5));
				article.setArcontent(rs.getString(6));
				article.setAruser(rs.getString(7));
				article.setArtime(rs.getString(8));
				article.setArstate(rs.getString(9));
				ArticleList.add(article);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ArticleList;
	}
	//����
	public boolean exnumber(String canumber, String canumberup){

		String sql="update article AS a JOIN article AS b ON(a.arnumber ='"+canumber+"' AND b.arnumber='"+canumberup+"') OR (a.arnumber='"+canumberup+"'  AND b.arnumber ='"+canumber+"')set b.arnumber=a.arnumber";
		try {
			ps = conn.prepareStatement(sql);
			int a=ps.executeUpdate();
			if(a>0&a<2){
				return true;
			}
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public String prenumber(String arnumber){

		String sql="select arnumber from article where (arnumber+0)>'"+arnumber+"' "+
					"order by arnumber+0 asc limit 1";
		try {
			ps = conn.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next()){
				return rs.getString(1);
			}
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * ȫ�����£����ڻ�ȡ����
	 * @return
	 */
	public ArrayList<Article> finaAll(){
		String sql="select * from article";
		ArrayList<Article> ArticleList=new ArrayList<Article>();
		try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				Article article=new Article();
				article.setArid(rs.getInt(1));
				article.setCaid(rs.getInt(2));
				article.setArnumber(rs.getString(3));
				article.setArtitle(rs.getString(4));
				article.setArimage(rs.getString(5));
				article.setArcontent(rs.getString(6));
				article.setAruser(rs.getString(7));
				article.setArtime(rs.getString(8));
				article.setArstate(rs.getString(9));
				ArticleList.add(article);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ArticleList;
	}
	
	

	
	/**
	 * 
	 * @param ȫ��ķ�ҳ
	 * @return
	 */
	
	public ArrayList<Article> page(int startpage){
		String sql="select * from article order by arnumber+0 desc limit "+(startpage-1)*pagesize+","+pagesize+"";
		ArrayList<Article> ArticleList=new ArrayList<Article>();
		
		try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				Article article=new Article();
				article.setArid(rs.getInt(1));
				article.setCaid(rs.getInt(2));
				article.setArnumber(rs.getString(3));
				article.setArtitle(rs.getString(4));
				article.setArimage(rs.getString(5));
				article.setArcontent(rs.getString(6));
				article.setAruser(rs.getString(7));
				article.setArtime(rs.getString(8));
				article.setArstate(rs.getString(9));
				ArticleList.add(article);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ArticleList;
	}
	
	//��ȡ���ҳ�����Ŀ�б�
	public ArrayList<Catalog> finacaid(int caid){
		String sql="select * from catalog where castate='1' and caid="+caid;
		ArrayList<Catalog> CatalogList=new ArrayList<Catalog>();
		try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				Catalog catalog=new Catalog();
				catalog.setCaid(rs.getInt(1));
				catalog.setCaname(rs.getString(2));
				catalog.setCanumber(rs.getString(3));
				catalog.setCastate(rs.getString(4));
				CatalogList.add(catalog);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CatalogList;
	}
	
	/**
	 * ��ѯ������caid�����õ���Ŀ�б����ڱ༭ʱѡ����Ŀ�������������Ŀ
	 */
	public ArrayList<Catalog> findcatalog(int caid){
		String sql="select * from catalog where castate='1' and caid <> "+caid;
		ArrayList<Catalog> CatalogList=new ArrayList<Catalog>();
		try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				Catalog catalog=new Catalog();
				catalog.setCaid(rs.getInt(1));
				catalog.setCaname(rs.getString(2));
				catalog.setCanumber(rs.getString(3));
				catalog.setCastate(rs.getString(4));
				CatalogList.add(catalog);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CatalogList;
	}
	
	/**
	 * ����������Ŀ�б����ڱ༭ʱѡ����Ŀ
	 */
	public ArrayList<Catalog> findcatalog(){
		String sql="select * from catalog where castate='1'";
		ArrayList<Catalog> CatalogList=new ArrayList<Catalog>();
		try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				Catalog catalog=new Catalog();
				catalog.setCaid(rs.getInt(1));
				catalog.setCaname(rs.getString(2));
				catalog.setCanumber(rs.getString(3));
				catalog.setCastate(rs.getString(4));
				CatalogList.add(catalog);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CatalogList;
	}
	/**
	 * 
	 * @param ��ѯ����
	 * @return
	 */
	public String number(){
		String sql="select MAX(arnumber+0) from article";
		ArrayList<Count> countList=new ArrayList<Count>();
		try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				return rs.getString(1);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	//�������
	public boolean add(Article article){
		String sql="INSERT INTO article (caid,arnumber,artitle,arimage,arcontent,aruser,artime,arstate) VALUES ("+article.getCaid()+",'"+article.getArnumber()+"','"+article.getArtitle()+"','"+article.getArimage()+"','"+article.getArcontent()+"','"+article.getAruser()+"','"+article.getArtime()+"','"+article.getArstate()+"')";
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
	
	//��������
	
	public boolean update(Article article){
		String sql="update article set caid="+article.getCaid()+",artitle='"+article.getArtitle()+"',arimage='"+article.getArimage()+"',arcontent='"+article.getArcontent()+"',aruser='"+article.getAruser()+"',artime='"+article.getArtime()+"',arstate='"+article.getArstate()+"' where arid="+article.getArid();
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
	
	public boolean click(int click,int arid){
		String sql="update article set click="+click+"  where arid="+arid;
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
	/* 
     * ��ʱ��ת��Ϊʱ���
     */    
    public String dateToStamp(String nowtimes){
    	
        SimpleDateFormat sdfh = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Date date;
		try {
			date = sdfh.parse(nowtimes);
			long ts = date.getTime();
	        nowtime = String.valueOf(ts);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nowtime;
    }
    
    /* 
     * ��ʱ���ת��Ϊʱ��
     */
    public String stampToDate(String artime){
    	SimpleDateFormat sdfh = new SimpleDateFormat("yyyy-MM-dd");
			long lt = new Long(artime);
	        Date date = new Date(lt);
	        String nowtimes = sdfh.format(date);
	        res=nowtimes;
			return res;
		} 

//	public String stampToData(String artime){
//		SimpleDateFormat sdfx = new SimpleDateFormat("yyyy/MM/dd'T'hh:mm:ss");
//			long lt = new Long(artime);
//	        Date date = new Date(lt);
//	        String nowtimes = sdfx.format(date);
//	        res=nowtimes;
//			return res;
//	} 
    
	//ȥ��html��ʽ

	public String removeHtmlTag(String htmlContent){
	    	if (htmlContent!=null&&htmlContent.length()>0) {
	    		//ȥ��html��ǩ
	    		htmlContent = htmlContent.replaceAll("\\s*",""); //ȥ���ո�
	    		String regEx_html="<[^>]+>";                      //HTML��ǩ������ʽ
	    		Pattern pattern = Pattern.compile(regEx_html);
	    		Matcher matcher = pattern.matcher(htmlContent);
	    		while (matcher.find()){
	    			htmlContent = htmlContent.replace(matcher.group(),"");
	    		}
	    		return htmlContent.replaceAll("&nbsp;","");
			}else {
				return " ";
			}
	}
	public String getEncode(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) { // �ж��ǲ���GB2312
				String s = encode;
				return "GB2312"; // �ǵĻ������ء�GB2312�������´���ͬ��
			}
		} catch (Exception exception) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) { // �ж��ǲ���ISO-8859-1
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) { // �ж��ǲ���UTF-8
				String s2 = encode;
				return "UTF-8";
			}
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) { // �ж��ǲ���GBK
				String s3 = encode;
				return s3;
			}
		} catch (Exception exception3) {
		}
		return "";
	}
	public ArrayList<Article> findbystate(int nowpage , int max ,String arstate ,String castate){
		String sql="select * from article where arstate ='"+
					arstate+"' and caid in (select caid from catalog where castate='"+
					castate+"') order by arnumber+0 desc limit "
					+(nowpage-1)*max+","+max;
		ArrayList<Article> ArticleList=new ArrayList<Article>();
		try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				Article article=new Article();
				article.setArid(rs.getInt(1));
				article.setCaid(rs.getInt(2));
				article.setArnumber(rs.getString(3));
				article.setArtitle(rs.getString(4));
				article.setArimage(rs.getString(5));
				article.setArcontent(rs.getString(6));
				article.setAruser(rs.getString(7));
				article.setArtime(rs.getString(8));
				article.setArstate(rs.getString(9));
				article.setClick(rs.getInt(10));
				ArticleList.add(article);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ArticleList;
	}
	public int count(){
		String sql="select count(*) from article";
		int a=0;
		try {
			ps = conn.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next()){
				a= rs.getInt(1);
			}
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
	}
}

