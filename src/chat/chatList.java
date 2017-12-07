package chat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ChatSubmit
 */
@WebServlet("/chatList")
public class chatList extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String listType = request.getParameter("listType");
		if(listType == null || listType.equals("")) response.getWriter().write("");
		 else if(listType.equals("today")) 	response.getWriter().write(getToday());
		 else if(listType.equals("ten")) 	response.getWriter().write(getTen());
		 else {
			 try {
				Integer.parseInt(listType);
				response.getWriter().write(getId(listType));
			} catch (Exception e) {
				response.getWriter().write("");
			}
		 }
		
	}
	
	public String getToday() {	
		StringBuffer result = new StringBuffer("");	
		result.append("{\"result\":[");
		chatDAO chatDAO = new chatDAO();
		ArrayList<chat> chatlist = chatDAO.getChatList(new SimpleDateFormat("yyyy-mm-dd").format(new Date()));
		for(int i = 0; i < chatlist.size(); i++) {
			result.append("[{\"value\": \"" + chatlist.get(i).getChatName() + "\"},");
			result.append("{\"value\": \"" + chatlist.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + chatlist.get(i).getChatTime() + "\"}]");
			if(i != chatlist.size() - 1) result.append(",");
		}
		result.append("],\"last\":\"" + chatlist.get(chatlist.size() - 1).getChatId() + "\"}");
		return result.toString();	
		
	}
	
	public String getTen() {	
		StringBuffer result = new StringBuffer("");	
		result.append("{\"result\":[");
		chatDAO chatDAO = new chatDAO();
		ArrayList<chat> chatlist = chatDAO.getChatListByRecent(10);
		for(int i = 0; i < chatlist.size(); i++) {
			result.append("[{\"value\": \"" + chatlist.get(i).getChatName() + "\"},");
			result.append("{\"value\": \"" + chatlist.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + chatlist.get(i).getChatTime() + "\"}]");
			if(i != chatlist.size() - 1) result.append(",");
		}
		result.append("],\"last\":\"" + chatlist.get(chatlist.size() - 1).getChatId() + "\"}");
		return result.toString();	
		
	}
	
	public String getId(String chatId) {	
		StringBuffer result = new StringBuffer("");	
		result.append("{\"result\":[");
		chatDAO chatDAO = new chatDAO();
		ArrayList<chat> chatlist = chatDAO.getChatListByRecent(chatId);
		for(int i = 0; i < chatlist.size(); i++) {
			result.append("[{\"value\": \"" + chatlist.get(i).getChatName() + "\"},");
			result.append("{\"value\": \"" + chatlist.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + chatlist.get(i).getChatTime() + "\"}]");
			if(i != chatlist.size() - 1) result.append(",");
		}
		result.append("],\"last\":\"" + chatlist.get(chatlist.size() - 1).getChatId() + "\"}");
		return result.toString();	
		
	}
	
}
