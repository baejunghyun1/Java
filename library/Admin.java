package library;

import java.io.*;

import java.sql.*;   //SQL 관련 클래스는 모두 java.sql.*에 포함
import java.util.*;

public class Admin extends Database {

	
	
	
	public static void main(String[] args) {
		
		Database db = new Database();
		
		
		//관리자 모드 접속번호 확인
		System.out.print("관리자 모드 접속번호를 입력하세요. >> ");
		Scanner sc = new Scanner(System.in);
		String adminPW = sc.nextLine();
		
		if (adminPW.equals(db.adminPw)) {
				System.out.println("접속 성공하였습니다.");
		} else {
			System.out.println("접속번호를 확인해주세요.");
			System.exit(0);
		}
	
		
		try {
			System.out.println("DB 연결 준비...");
			Class.forName("com.mysql.cj.jdbc.Driver");
			db.con = DriverManager.getConnection(db.url, db.user, db.passwd);   //데이터베이스 연결 메소드
			System.out.println("DB 연결 성공");
			db.stmt = db.con.createStatement();   //try catch 문 내부에 써야 함
			
			
			while(true) {
				//메뉴 리스트
				System.out.println("아래 메뉴에서 번호를 선택하세요.");
				System.out.println("[1] 도서 정보 추가");
				System.out.println("[2] 도서 삭제");
				System.out.println("[3] 도서 정보 변경");
				System.out.println("[4] 도서 목록*정보 조회");
				System.out.println("[5] 프로그램 종료");
					
				System.out.print("메뉴 선택 >> ");
				Scanner sc2 = new Scanner(System.in);
				int input = sc2.nextInt();
				
				//메뉴 실행 메소드를 만들고 실행 (switch case 문에 집어넣기)
				switch(input) {
					case 1: 
						db.insertDB();
						break;
					case 2: 
						db.deleteDB();
						break;
					case 3: 
						db.updateDB();
						break;
					case 4: 
						db.selectDB();
						break;		
					case 5: 
						System.out.println("프로그램이 종료됩니다.");
						db.quit();
						break;	
				}
			}
			
		} catch(Exception e) {
			System.out.println(e.toString());
		}
			
			
		
		
	
	

	}
	
}		
