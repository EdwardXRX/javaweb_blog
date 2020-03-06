package com.xxgc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xxgc.dao.CatalogDAO;
import com.xxgc.model.Catalog;
import com.xxgc.model.Count;

public class CatalogServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String type=request.getParameter("type");
		if(type.endsWith("cataloglist")){
			cataloglist(request, response);
		}else if(type.endsWith("findall")){
			findall(request, response);
		}else if(type.endsWith("catalogadd")){
			catalogadd(request, response);
		}else if(type.endsWith("count")){
			count(request, response);
		}else if(type.endsWith("del")){
			del(request, response);
		}else if(type.endsWith("delupdate")){
			delupdate(request, response);
		}else if(type.endsWith("update")){
			update(request, response);
		}else if(type.endsWith("updata")){
			updata(request, response);
		}else if(type.endsWith("canumberup")){
			canumberup(request, response);
		}
		else if(type.endsWith("search")){
			search(request, response);
		}
	}
	public void search(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		CatalogDAO catalogDAO=new CatalogDAO();
		String caname=request.getParameter("caname");
		ArrayList<Catalog> catalogname=catalogDAO.finacaname(caname);
		for(Catalog p:catalogname){
			if(p.getCastate().endsWith("1")){
				p.setCastate("����");
			}
			if(p.getCastate().endsWith("2")){
				p.setCastate("δ����");
			}
		}
		if(catalogname.size()<=0){
			out.flush();
		    out.println("<script>");
		    out.println("alert('���޴���Ŀ��');");
		    out.println("history.back();");
		    out.println("</script>");
		}
		request.setAttribute("catalogname", catalogname);
		String path="/background/catalogfind.jsp";
		request.getRequestDispatcher(path).forward(request, response);
	}
	
	//����
	public void canumberup(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String canumber=request.getParameter("canumber");
		CatalogDAO catalogDAO=new CatalogDAO();
		String canumbers=catalogDAO.prenumber(canumber);
		
		catalogDAO.catalogup(canumber, canumbers);
		String path="CatalogServlet?type=cataloglist&page=1";
		request.getRequestDispatcher(path).forward(request, response);
	}
	
	
	//�༭����
	public void updata(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		CatalogDAO catalogDAO=new CatalogDAO();
		Catalog catalogupata=new Catalog();
		String caids=request.getParameter("caid");
		int caid=Integer.parseInt(caids);
		String caname=request.getParameter("caname");
		String castate=request.getParameter("castate");
		String path="background/cataloglist.jsp";		
		if(caname!=""){
			catalogupata.setCaname(caname);
			catalogupata.setCastate(castate);
			catalogupata.setCaid(caid);
			if(catalogDAO.update(catalogupata)){
				path="CatalogServlet?type=cataloglist&page=1";
			}
		}else{
			out.flush();
			out.println("<script>");
			out.println("alert('��������Ŀ����');");
			out.println("history.back();");
			out.println("</script>");	
		}
		request.getRequestDispatcher(path).forward(request, response);
		
	}
	//�༭��ת
	public void update(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		int caid=Integer.parseInt(request.getParameter("caid"));
		CatalogDAO catalogDAO=new CatalogDAO();
		ArrayList<Catalog> catalogAll=catalogDAO.finabycaid(caid);
		Catalog catalogs=catalogAll.get(0);
		request.setAttribute("catalogs", catalogs);
		String path="background/catalogupdate.jsp";
		request.getRequestDispatcher(path).forward(request, response);
	}
	
	//ɾ������canumber ���࣬����Ҫ��
	public void delupdate(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
	//ɾ��
		int canumber=Integer.parseInt(request.getParameter("canumber"));
		System.out.println(canumber);
		CatalogDAO catalogDAO=new CatalogDAO();
		catalogDAO.deleteupdate(canumber);
		request.getRequestDispatcher("CatalogServlet?type=cataloglist&page=1").forward(request, response);
	}
	//ɾ��del
	public void del(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
	//ɾ��
		int id=Integer.parseInt(request.getParameter("id"));
		CatalogDAO catalogDAO=new CatalogDAO();
		Catalog catalogdel=new Catalog();
		catalogdel.setCaid(id);
		catalogDAO.delete(catalogdel);
		request.getRequestDispatcher("CatalogServlet?type=delupdate").forward(request, response);
	}
	
	
	//number count
	public void count(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		CatalogDAO catalogDAO=new CatalogDAO();
		ArrayList<Count> count=catalogDAO.count();
		request.setAttribute("count", count.get(0).getCounts());
		request.getRequestDispatcher("background/catalog_info.jsp").forward(request, response);
	}
	
	
	//�����Ŀ
	public void catalogadd(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String caname=request.getParameter("caname");
		String count=request.getParameter("count");
		String castate=request.getParameter("castate");
		String path="background/catalog_info.jsp";
		CatalogDAO catalogDAO=new CatalogDAO();
		Catalog catalogadd=new Catalog();
		if(caname!=""){
			catalogadd.setCaname(caname);
			catalogadd.setCanumber(count);
			catalogadd.setCastate(castate);
			if(catalogDAO.insert(catalogadd)){
				path="CatalogServlet?type=cataloglist&page=1";
			}
		}else{
			out.flush();
			out.println("<script>");
			out.println("alert('��������Ŀ����');");
			out.println("history.back();");
			out.println("</script>");	
		}
		request.getRequestDispatcher(path).forward(request, response);
	}
	//findall
	public void findall(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		CatalogDAO catalogDAO=new CatalogDAO();
		ArrayList<Catalog> catalogList=catalogDAO.finaAll();
		
		request.setAttribute("catalogList", catalogList);
		request.getRequestDispatcher("background/cataloglist.jsp").forward(request, response);
	}
	
	//page
	public void cataloglist(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		CatalogDAO catalogDAO=new CatalogDAO();
		int page=Integer.parseInt(request.getParameter("page"));
		int startpage=page;//ҳ��
		//int pagesize=catalogDAO.setPagesize(5);//��ʾ����
		String path="CatalogServlet?type=findall";
		ArrayList<Catalog> pageList=catalogDAO.page(startpage);
		for(Catalog p:pageList){
			if(p.getCastate().endsWith("1")){
				p.setCastate("����");
			}
			if(p.getCastate().endsWith("2")){
				p.setCastate("δ����");
			}
		}
		request.setAttribute("pageList", pageList);
		request.setAttribute("startpage", startpage);
		request.setAttribute("pagesize", catalogDAO.getPagesize());
		request.getRequestDispatcher(path).forward(request, response);
	}

}
