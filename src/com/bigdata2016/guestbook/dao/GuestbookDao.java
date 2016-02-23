package com.bigdata2016.guestbook.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bigdata2016.guestbook.vo.GuestbookVo;

public class GuestbookDao {
	
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
   
   public void insert(GuestbookVo vo){
      try{
         //1.드라이버 로딩
      Class.forName("oracle.jdbc.driver.OracleDriver");
      
      //2. connection 얻기
      String url = "jdbc:oracle:thin:@localhost:1521:TestDB";
      conn = DriverManager.getConnection(url, "webdev", "webdev");
      
      //3. Statement 준비
      String sql = "insert into guestbook values(guestbook_seq.nextval, ?, ?, ?, sysdate)";
      pstmt = conn.prepareStatement(sql);
      
      //4. binding
      pstmt.setString(1, vo.getName());
      pstmt.setString(2, vo.getPassword());
      pstmt.setString(3, vo.getMessage());
      
      //5. query 실행
      pstmt.executeUpdate();      
      
      }catch(ClassNotFoundException ex){
         System.out.println("jdbc 드라이버를 찾을 수 없습니다."+ex);
      }catch(SQLException ex ) {
         System.out.println("sql error" + ex);
      }finally {
    	//6. 자원 정리 
          try {
        	  if(!pstmt.isClosed()) {
        		  pstmt.close();
        	  }
        	  if(!conn.isClosed()) {
        		  conn.close();
        	  }			
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			System.out.println("sql error" + ex);
		}          
      }
   }
   
   public List<GuestbookVo> getList() {
	   
	   List<GuestbookVo> result = new ArrayList<GuestbookVo>();
	   
	   try{
	         //1.드라이버 로딩
	      Class.forName("oracle.jdbc.driver.OracleDriver");
	      
	      //2. connection 얻기
	      String url = "jdbc:oracle:thin:@localhost:1521:TestDB";
	      conn = DriverManager.getConnection(url, "webdev", "webdev");
	      
	      //3. Statement 준비	      
	      stmt = conn.createStatement();
	      
	      //5. query 실행
	      String sql = "select no, name, message, to_char(reg_date, 'YYYY-MM-DD HH:MI:SS')" +
	    		  		"from guestbook order by reg_date desc";
	      stmt.executeUpdate(sql);
	      
	      rs = stmt.executeQuery(sql);
	      while(rs.next()) {
	    	  Long no = rs.getLong(1);
	    	  String name = rs.getString(2);
	    	  String message = rs.getString(3);
	    	  String regDate = rs.getString(4);
	    	  
	    	  GuestbookVo vo = new GuestbookVo();
	    	  vo.setNo(no);
	    	  vo.setName(name);
	    	  vo.setMessage(message);
	    	  vo.setRegDate(regDate);
	    	  
	    	  result.add(vo);
	      }
	      
	      }catch(ClassNotFoundException ex){
	         System.out.println("jdbc 드라이버를 찾을 수 없습니다."+ex);
	      }catch(SQLException ex ) {
	         System.out.println("sql error" + ex);
	      }finally {
	    	//6. 자원 정리 
	          try {
	        	  if(!rs.isClosed()) {
	        		  rs.close();
	        	  }
	        	  if(!stmt.isClosed()) {
	        		  stmt.close();
	        	  }
	        	  if(!conn.isClosed()) {
	        		  conn.close();
	        	  }			
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				System.out.println("sql error" + ex);
			}          
	      } 
	   
      return result;
   }

}