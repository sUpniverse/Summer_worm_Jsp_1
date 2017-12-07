package chat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class chatDAO {
	
	private Connection con;
	
	public chatDAO() {
		try {
			String dbUrl = "jdbc:mysql://localhost:3306/Anonymouschat";
			String dbId = "root";
			String dbPw = "22345335";
			
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(dbUrl, dbId, dbPw);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<chat> getChatList(String nowTime) {
		ArrayList<chat> chatList = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String query = "SELECT * from CHAT Where chatTime > ? ORDER BY chatTime";
			
		try {
			pstmt  = con.prepareStatement(query);
			pstmt.setString(1, nowTime);
			rs = pstmt.executeQuery();
			chatList = new ArrayList<chat>();
			while(rs.next()) {
				chat chat = new chat();
				chat.setChatId(rs.getInt(1));
				chat.setChatName(rs.getString(2));
				chat.setChatContent(rs.getString(3).replaceAll(" ", "&nbsp").replaceAll("<", "&lt").replaceAll(">", "&gt").replaceAll("\n", "<br>"));
				chat.setChatTime(rs.getString(4).substring(0, 19));
				chatList.add(chat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		
		return chatList;
	}
	
	
	public ArrayList<chat> getChatListByRecent(int number) {
		ArrayList<chat> chatList = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String query = "SELECT * from CHAT Where chatId > (Select MAX(chatId) - ? FROM CHAT) ORDER BY chatTime";
			
		try {
			pstmt  = con.prepareStatement(query);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();
			chatList = new ArrayList<chat>();
			while(rs.next()) {
				chat chat = new chat();
				chat.setChatId(rs.getInt(1));
				chat.setChatName(rs.getString(2));
				chat.setChatContent(rs.getString(3).replaceAll(" ", "&nbsp").replaceAll("<", "&lt").replaceAll(">", "&gt").replaceAll("\n", "<br>"));
				chat.setChatTime(rs.getString(4).substring(0, 19));
				chatList.add(chat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		
		return chatList;
	}
	
	public ArrayList<chat> getChatListByRecent(String chatId) {
		ArrayList<chat> chatList = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String query = "SELECT * from CHAT Where chatId > ? ORDER BY chatTime";
			
		try {
			pstmt  = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(chatId));
			rs = pstmt.executeQuery();
			chatList = new ArrayList<chat>();
			while(rs.next()) {
				chat chat = new chat();
				chat.setChatId(rs.getInt(1));
				chat.setChatName(rs.getString(2));
				chat.setChatContent(rs.getString(3).replaceAll(" ", "&nbsp").replaceAll("<", "&lt").replaceAll(">", "&gt").replaceAll("\n", "<br>"));
				chat.setChatTime(rs.getString(4).substring(0, 19));
				chatList.add(chat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		
		return chatList;
	}
	

	public int submit(String chatName, String chatContent) {
		PreparedStatement pstmt = null;		
		String query = "Insert into CHAT values(NULL,?,?,now() )";
			
		try {
			pstmt  = con.prepareStatement(query);
			pstmt.setString(1, chatName);
			pstmt.setString(2, chatContent);
			return pstmt.executeUpdate();			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {				
				if(pstmt != null) pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		return -1; // DB¿À·ù
	}
}
