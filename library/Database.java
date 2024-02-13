package library;

import java.io.*;

import java.sql.*;   //SQL 관련 클래스는 모두 java.sql.*에 포함
import java.util.ArrayList;
import java.util.Scanner;

public class Database {
	
//	1. Driver 클래스 로드
//	2. DB 연결을 위한 Connection 객체 생성
//	3. SQL을 담는 Statement 또는 PrepareStatement 객체 생성
//	4. SQL 실행
//	5. 리소스 정리
	
//	접속변수 초기화
	Connection con = null;   //Connection 변수 선언
	Statement stmt = null;
	String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul";  
	String user ="madang";
	String passwd = "madang";
	String tableName = null;
	
	String adminPw = "sajotuna";
	
	
	public boolean equals(String adminPw) {
		if(adminPw == null) {
			return false;
		}
		String temp = (String)adminPw;
		return adminPw.equals(temp);
	}
			
	void insertDB() {			//sql문 : insert into values()를 이용해서 books라는 테이블에 정보를 입력
		try {
			Scanner sc = new Scanner(System.in);
			System.out.print("등록 할 도서번호를 입력하세요 >>  ");
			int bookno = sc.nextInt();
			sc.nextLine();
			System.out.print("등록 할 도서명을 입력하세요 >>  ");
			String bookname = sc.nextLine();
			System.out.print("등록 할 작가명을 입력하세요 >>  ");
			String author = sc.nextLine();
			System.out.print("등록 할 도서의 출판사를 입력하세요 >>  ");
			String publisher = sc.nextLine();
			String insertStr = " insert into books values(" + bookno + ", '" + bookname + "' , '" + author + "' , '" + publisher + "' ) ";
			stmt.execute(insertStr);
			System.out.println("-------------------------");
			System.out.println("★도서 등록이 완료 되었습니다★");
			System.out.println("-------------------------");
		} catch(Exception e) {
			System.out.println("오류가 발생하였습니다. " + e.toString());
		}
	}
	
	void deleteDB() {	
		//sql문 : DELETE FROM 을 이용해서 books라는 테이블에 정보를 삭제
		try {
			System.out.println("======================================  도서 조회 목록  ==============================================");
			String booklist = "SELECT * FROM books";
			ResultSet result = stmt.executeQuery(booklist);
			while(result.next()) {
				System.out.print("도서 번호 : " + result.getString("bookno") + "\t" + "도서 제목 : " + result.getString("title")
				 + "\t" + "작 가 명 : " + result.getString("author") + "\t" + "출 판 사 : " 
				 + result.getString("publisher") +  "\n");
System.out.println("===================================================================================================");
			}
			
			String dBookno;
			Scanner s = new Scanner(System.in);
			System.out.print("정보를 지우고 싶은 도서 번호를 입력하세요 >>  ");
			dBookno = s.nextLine();
			String del = "DELETE FROM books where bookno='"+ dBookno + "'";
			int a= stmt.executeUpdate(del);
			if(a==0) {
			System.out.println("-------------------------");
			System.out.println("★없는 도서 번호 입니다★");
			System.out.println("-------------------------"); 
			}else {
				System.out.println("-------------------------");
				System.out.println("★삭제 완료 했습니다★");
				System.out.println("-------------------------");
			}}
			catch(Exception e) {
			System.out.println("오류가 발생하였습니다. " + e.toString());
		}
	}
	
		void updateDB() {                                           	//switch case 을 이용해 원하는 수정목록을 선택후  
							                                        // while문을 통해 목록을 보여줘서 쉽게 목록을 보고 수정할 목록을 고를수있도록 하였다.
			try {
				Database db = new Database();
				Scanner sc = new Scanner(System.in);
				
				System.out.println("아래 메뉴에서 번호를 선택하세요. ");
				System.out.println("[1] 도서번호 수정");
				System.out.println("[2] 도서제목 수정");
				System.out.println("[3] 작 가 수정");
				System.out.println("[4] 출판사 수정");
				System.out.println("[5] 수정 취소");
				System.out.print("메뉴 선택 >>  ");
				int inputUpdate = sc.nextInt();
				sc.nextLine();
				
				switch(inputUpdate) {
				case 1:
					try {
						System.out.println("======================================  도서 조회 목록  ==============================================");
						String booklist = "SELECT * FROM books";
						ResultSet result = stmt.executeQuery(booklist);
						while(result.next()) {
							System.out.print("도서 번호 : " + result.getString("bookno") + "\t" + "도서 제목 : " + result.getString("title")
							 + "\t" + "작 가 명 : " + result.getString("author") + "\t" + "출 판 사 : " 
							 + result.getString("publisher") +  "\n");
			System.out.println("===================================================================================================");
						}
						
						System.out.println("");
					System.out.print("수정 하고싶은 도서번호를 입력하세요 >>  ");
					int bookno2 = sc.nextInt();
					System.out.print("새롭게 변경 된 도서번호를 입력하세요 >>  ");
					int bookno1 = sc.nextInt();
					String updateBno = "UPDATE books SET bookno = " + bookno1 + " WHERE bookno = " + bookno2;
					stmt.executeUpdate(updateBno);
					System.out.println("--------------------");
					System.out.println("★도서번호 수정 완료★");
					System.out.println("--------------------");
					break;
					}
					catch(Exception e) {
						System.out.println(e.toString());
						}
					
				case 2:
					try {
						System.out.println("======================================  도서 조회 목록  ==============================================");
						String booklist = "SELECT * FROM books";
						ResultSet result = stmt.executeQuery(booklist);
						while(result.next()) {
							System.out.print("도서 번호 : " + result.getString("bookno") + "\t" + "도서 제목 : " + result.getString("title")
							 + "\t" + "작 가 명 : " + result.getString("author") + "\t" + "출 판 사 : " 
							 + result.getString("publisher") +  "\n");
			System.out.println("===================================================================================================");
						}
						
						System.out.println("");
					System.out.print("수정 하고싶은 도서번호를 입력하세요 >>  ");
					int title = sc.nextInt();
					sc.nextLine();
					System.out.print("새롭게 변경 된 도서제목을 입력하세요 >>  ");
					String title1 = sc.nextLine();
					String updateBtitle = "UPDATE books SET title= '" + title1 + "' WHERE bookno= '" + title + "'";
					stmt.executeUpdate(updateBtitle);
					System.out.println("--------------------");
					System.out.println("★도서제목 수정 완료★");
					System.out.println("--------------------");
					break;
				}
				catch(Exception e) {
					System.out.println(e.toString());
					}
				case 3: 
					try {
						System.out.println("======================================  도서 조회 목록  ==============================================");
						String booklist = "SELECT * FROM books";
						ResultSet result = stmt.executeQuery(booklist);
						while(result.next()) {
							System.out.print("도서 번호 : " + result.getString("bookno") + "\t" + "도서 제목 : " + result.getString("title")
							 + "\t" + "작 가 명 : " + result.getString("author") + "\t" + "출 판 사 : " 
							 + result.getString("publisher") +  "\n");
			System.out.println("===================================================================================================");
						}
						
						System.out.println("");
					System.out.print("수정 하고싶은 도서번호를 입력하세요 >>  ");
					int author = sc.nextInt();
					sc.nextLine();
					System.out.print("새롭게 변경 된 작가명을 입력하세요 >>  ");
					String author1 = sc.nextLine();
					String updateAuthor = "UPDATE books SET author= '" + author1 + "' WHERE bookno= '" + author + "'";
					stmt.executeUpdate(updateAuthor);	
					System.out.println("--------------------");
					System.out.println("★작가명 수정 완료★");
					System.out.println("--------------------");
					break;
					}
					catch(Exception e) {
						System.out.println(e.toString());
						}
				case 4:
					try {
						System.out.println("======================================  도서 조회 목록  ==============================================");
						String booklist = "SELECT * FROM books";
						ResultSet result = stmt.executeQuery(booklist);
						while(result.next()) {
							System.out.print("도서 번호 : " + result.getString("bookno") + "\t" + "도서 제목 : " + result.getString("title")
							 + "\t" + "작 가 명 : " + result.getString("author") + "\t" + "출 판 사 : " 
							 + result.getString("publisher") +  "\n");
			System.out.println("===================================================================================================");
						}
						
						System.out.println("");
					System.out.print("수정 하고싶은 도서번호를 입력하세요 >>  ");
					int publisher = sc.nextInt();
					sc.nextLine();
					System.out.print("새롭게 변경 된  출판사를 입력하세요 >>  ");
					String publisher1 = sc.nextLine();
					String updatePub = "UPDATE books SET publisher= '" + publisher1 + "' WHERE bookno= '" + publisher + "'";
					stmt.executeUpdate(updatePub);	
					System.out.println("--------------------");
					System.out.println("★출판사 수정 완료★");
					System.out.println("--------------------");
					break;
				}
				catch(Exception e) {
					System.out.println(e.toString());
					}
				case 5: 
					System.out.println("수정을 취소하겠습니다.");
					break;
				}} catch(Exception e) {
			System.out.println("오류가 발생하였습니다. " + e.toString());
		}
	}
	
	void selectDB() {	//sql 문법 SElECT FROM 을 사용하여 도서에 있는 모든 도서 목록가져오고
						//while 문을 통해 보기좋게 나열 하였다.
		try {
			System.out.println("======================================  도서 조회 목록  ==============================================");
			String listAll = "SELECT * FROM books";
			ResultSet result1 = stmt.executeQuery(listAll);
			// int cnt1 = 0;
			while(result1.next()) {
				System.out.print("도서 번호 : " + result1.getString("bookno") + "\t" + "도서 제목 : " + result1.getString("title")
								 + "\t" + "작 가 명 : " + result1.getString("author") + "\t" + "출 판 사 : " 
								 + result1.getString("publisher") +  "\n");
				System.out.println("===================================================================================================");
				// cnt1++;
			}
						
			System.out.println("--------------------");
			System.out.println("★도서 목록 조회 성공★");
			System.out.println("--------------------");
		} catch(Exception e) {
			System.out.println("오류가 발생하였습니다. " + e.toString());
		}
	}
	
	void quit() {
		System.exit(0);
	}
	
}
		
		
	
