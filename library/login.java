package library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class login extends users {

	Connection con = null;
	Statement stmt = null;
	static String url = "jdbc:mysql://localhost:3306/madang?serverTimezone=Asia/Seoul";	//dbstudy스키마
	static String user = "madang";
	static String passwd = "madang";		//MySQL에 저장한 root 계정의 비밀번호를 적어주면 된다.
	String tableName = null;
	public static void main(String[] args) {
		login db = new login();
	    
		/* 데이터베이스 관련 코드는 try-catch문으로 예외 처리를 꼭 해주어야 한다. */
		try {
			//데이터베이스 연결
			Class.forName("com.mysql.cj.jdbc.Driver");
			db.con = DriverManager.getConnection(db.url, db.user, db.passwd);
			db.stmt = db.con.createStatement();
			
			
			
			while(true) {
			//메뉴 리스트
			System.out.println("=================");
			System.out.println("[1] 로그인");
			System.out.println("[2] 회원 등록");
			System.out.println("[3] 종료");
			System.out.println("=================");
			
			System.out.print(" 실행할 번호를 입력해주세요 >> ");
			Scanner s = new Scanner(System.in);
			int num = s.nextInt();
			
			switch(num) {
				case 1 :
					db.login();	
					break;
				case 2 :
					db.insertuser();	
					break;
				case 3 :
					System.out.println("안녕히가세요 ₍˄·͈༝·͈˄₎ฅ ");	
					return;
				
			}
		  }
		} catch(Exception e) {
			System.out.println(e.toString());
		} finally {
			try {
				db.stmt.close();
				db.con.close();				
			} catch(Exception e) {
				System.out.println(e.toString());
			}
		}
	
	}
	void login() {
        try {
        	String id,pw;
        	Scanner s = new Scanner(System.in);
            System.out.println("아이디를 입력해 주십시오 >>  ");
            id = s.nextLine();
            System.out.print("패스워드를 입력해 주십시오 >>  ");
            pw = s.nextLine();

            if (login(id, pw)) {
                System.out.println("로그인 성공!");
                users us = new users();
				us.user();
                
                
                
            } else {
                System.out.println("로그인 실패. 아이디 또는 패스워드가 틀립니다.");
            }
        } catch (SQLException e) {
            System.out.println("로그인 실패! 오류내용: " + e.getMessage());
        }
    }

    private static boolean login(String username, String password) throws SQLException {
    	
    	
        try (Connection connection = DriverManager.getConnection(url, user, passwd)) {
            String ueserlogin = "SELECT * FROM users WHERE id = ? AND pw = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(ueserlogin)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next(); 
                    
                }
            }
        }
    }


void insertuser() {
	try {
		String name,id,pw,phone;
		Scanner s = new Scanner(System.in);
		System.out.print("이름을 입력하세요 >>");
		name = s.nextLine();
		System.out.print("아이디를 입력하세요 >>");
		id = s.nextLine();
		System.out.print("비밀번호를 입력하세요 >>");
		pw = s.nextLine();
		System.out.print("전화번호를 입력하세요 >>");
		phone = s.nextLine();
		String insertStr = "insert into users values('" + name + "', '" + id + "','" + pw + "','" + phone + "')";
		stmt.executeUpdate(insertStr);
		System.out.println("회원 등록 완료!");
		
	} catch(Exception e) {
		System.out.println("회원 등록 실패 이유 : " + e.toString());
	}
}

}

