package library;

import java.sql.DriverManager;

import java.util.Scanner;

public class Index extends users{
	
	
	
	public static void main(String[] args) {
		
		Database db = new Database();

		try {
			//드라이버 찾고 실패 시 에러메시지
			System.out.println("DB 연결 준비...");
			Class.forName("com.mysql.cj.jdbc.Driver");
			db.con = DriverManager.getConnection(db.url, db.user, db.passwd);   //데이터베이스 연결 메소드
			System.out.println("DB 연결 성공");
			db.stmt = db.con.createStatement();  //데이터베이스로 SQL문을 보내기 위한 SQLServerStatement 개체 생성
			
			//접속 모드 선택
			System.out.println("1. 관리자 모드");
			System.out.println("2. 사용자 모드");
			System.out.print("접속 모드를 선택하세요. >> ");
			Scanner sc = new Scanner(System.in);
			int mode = sc.nextInt();
			
			switch(mode) {
			case 1 :
				Admin.main(args);    //관리자 모드 실행
				break;
			case 2 :
				login.main(args);//유저 로그인 실행
				break;
			}
			
			
		} catch(Exception e) {
			System.out.println(e.toString());
		}
		
		
		
		
		}
		

}
