package com.sist.controller;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.action.SistAction;

/**
 * Servlet implementation class SistController
 */
@WebServlet("*.do")
public class SistController extends HttpServlet {
	
	HashMap<String , SistAction> map;

	private static final long serialVersionUID = 1L;
       
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		//super.init(config);
		map = new HashMap<String, SistAction>();
		String path = config.getServletContext().getRealPath("WEB-INF"); //config가 필요한 건 sist.properties가 들어간 파일의 실경로를 알기 위해서임
		try {
			FileReader fr = new FileReader(path+"/sist.properties");
			Properties prop = new Properties();
			prop.load(fr);
			Iterator iter = prop.keySet().iterator();
			while(iter.hasNext()) {
				String key = (String)iter.next();
				String clsName = (String)prop.get(key);
				SistAction obj = (SistAction)Class.forName(clsName).newInstance();
				map.put(key, obj);
			}
			fr.close();
		} catch (Exception e) {
			System.out.println("예외 발생:"+e.getMessage());
		}
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SistController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String cmd = uri.substring(uri.indexOf("/",1)+1);
		SistAction action = map.get(cmd);
		String viewPage = action.pro(request, response);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}

}
