package library;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class users extends Database {     //Database 부모클래스를 상속.
	
	Connection con = null;
	Statement stmt = null;
	static String url = "jdbc:mysql://localhost:3306/madang?serverTimezone=Asia/Seoul";	//dbstudy스키마
	static String user = "madang";
	static String passwd = "madang";		//MySQL에 저장한 root 계정의 비밀번호를 적어주면 된다.
	String tableName = null;
	public void user() {
		users db = new users();
	    
		/* 데이터베이스 관련 코드는 try-catch문으로 예외 처리를 꼭 해주어야 한다. */
		try {
			//데이터베이스 연결
			Class.forName("com.mysql.cj.jdbc.Driver");
			db.con = DriverManager.getConnection(db.url, db.user, db.passwd);
			db.stmt = db.con.createStatement();
			
			
			
			while(true) {
			//메뉴 리스트
			System.out.println("===============================================================");
			System.out.println("[1] 회원 정보 조회");
			System.out.println("[2] 대여 목록 조회");
			System.out.println("[3] 회원 정보 수정");
			System.out.println("[4] 회원 탈퇴");
			System.out.println("[5] 도서 대여");
			System.out.println("[6] 도서 반납");
			System.out.println("[7] 종료");
			System.out.println("===============================================================");
			
			System.out.print(" 실행할 번호를 입력해주세요 >>  ");
			Scanner s = new Scanner(System.in);
			int num = s.nextInt();
			
			switch(num) {
				case 1 :
					db.viewuser();	//조회
					break;
				case 2 :
					db.status();	//대여 조회
					break;
				case 3 :
					db.updateuser();	//수정
					break;
				case 4 :
					db.deleteuser();	//삭제
					break;
				case 5 :
				    db.rentalbook();	//대여
				    break;
				case 6 :
					db.returnbook();	//반납
					break;
				case 7 :
					System.out.println("안녕히가세요 ₍˄·͈༝·͈˄₎ฅ"); //종료
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


void viewuser() {
	 try {
	        String id; 			//입력 받을 id를 저장하는 변수
	        Scanner s = new Scanner(System.in);			//키보드입력을 받기 위한 Scanner 객체를 생성합니다.
	        System.out.println("아이디를 입력해 주세요>>");
	        id = s.nextLine();
	        System.out.println("========================  회원 정보  ============================");
	        String listAll = "SELECT name, id, phone FROM users WHERE id=?";
	        //SQL 쿼리를 정의하는 문자열 listAll을 선언. 입력받는 id에 해당id의 name id phone를 조회.
	        try (PreparedStatement preparedStatement = con.prepareStatement(listAll)) {
	            preparedStatement.setString(1, id);	  //SQL 쿼리의 "?"에 입력받은 아이디를 매개변수로 설정합니다.
	             
	            ResultSet result1 = preparedStatement.executeQuery();	//SQL쿼리 실행 결과인ResultSet를 result1객체에 저장.

	            if (!result1.isBeforeFirst()) {
	            	//isBeforeFirst() 메소드는 boolean값을 반환
	            	//부정 연산자 !를 사용하여 result1이 첫 번째 행 이전에 위치해 있지 않다면을 의미합니다.
	            	// 조회 결과가 없을 경우, "해당 아이디의 회원이 존재하지 않습니다."를 출력.
	                System.out.println("해당 아이디의 회원이 존재하지 않습니다.");
	            } else {		//조회가 성공한경우 아래 출력.
	             while (result1.next()) {
	                    System.out.println("이름:" + result1.getString("name") + "    아이디:" + result1.getString("id")
	                            + "    전화번호:" + result1.getString("phone"));
	                    System.out.println("===============================================================");
	                }
	                 System.out.println("★회원 조회 성공★");
	            }
	        }
    } catch (Exception e) {
        System.out.println("회원 조회 실패 이유: " + e.toString());
    }
    }
void status() {
    try {
        String id;
        Scanner s = new Scanner(System.in);
        System.out.println("아이디를 입력해 주세요>>");
        id = s.nextLine();
        System.out.println("========================  대여도서 현황  =========================");
        String listAll = "SELECT * FROM users, bookinfo WHERE id=? and users.id = bookinfo.users_id";
        //SQL 쿼리를 정의하는 문자열 listAll을 선언합니다.
        //SELSCT ALL 모든 열을 선택.
        //users 테이블에서 특정 아이디와 일치하는 행을 선택합니다.
        //users 테이블의 id와 bookinfo 테이블의 users_id가 일치하는 경우만 선택합니다.
        try (PreparedStatement preparedStatement = con.prepareStatement(listAll)) {
            preparedStatement.setString(1, id);
            
            ResultSet result1 = preparedStatement.executeQuery();

            if (!result1.isBeforeFirst()) {
                System.out.println("해당 아이디에 대여 정보가 존재하지 않습니다.");
              //isBeforeFirst() 메소드는 boolean값을 반환
            	//부정 연산자 !를 사용하여 result1이 첫 번째 행 이전에 위치해 있지 않다면을 의미합니다.
            } else {
             while (result1.next()) {
                  
                    System.out.println("대여중인 도서번호 :" + result1.getString("bookno") + "       대여중인 도서명 :" + result1.getString("title"));
                    System.out.println("===============================================================");
                }
                 System.out.println("★대여 목록 조회 성공★");
            }
        }
    } catch (Exception e) {
        System.out.println("회원 조회 실패 이유: " + e.toString());
    }
    }

void updateuser() throws SQLException {
	System.out.println("수정하고 싶은 정보를 골라주세요");
	System.out.println("[1] id [2] pw [3] phone [4] name");
	Scanner s = new Scanner(System.in);
	System.out.print(">>>   ");
	int num = s.nextInt();
	s.nextLine();
	
	switch(num) {
	case 1 :
		System.out.println("변경 전 id를 입력하세요.");
		String id = s.nextLine();
		System.out.println("변경 후 id를 입력하세요.");
		String id2 = s.nextLine();
		String changeStr7 = "UPDATE users SET id = '" + id2 + "' WHERE id = '" + id + "'";
		stmt.executeUpdate(changeStr7);
		System.out.println("★정보 수정 완료★"); 		
		break;
	case 2 :
		System.out.println("변경 전 pw를 입력하세요.");
		String pw = s.nextLine();
		System.out.println("변경 후 pw을 입력하세요.");
		String pw1 = s.nextLine();
		String changeStr8 = "UPDATE users SET pw= '" + pw1 + "' WHERE pw= '" + pw + "'";

		stmt.executeUpdate(changeStr8);
		System.out.println("★정보 수정 완료★");
		break;
	case 3 :
		System.out.println("변경 전 phone을 입력하세요.");
		String phone = s.nextLine();
		System.out.println("변경 후 phone을 입력하세요.");
		String phone1 = s.nextLine();
		String changeStr9 = "UPDATE users SET phone= '" + phone1 + "' WHERE phone= '" + phone + "'";
		stmt.executeUpdate(changeStr9);	
		System.out.println("★정보 수정 완료★");
		break;
	case 4 :
		System.out.println("변경 전 이름을 입력하세요.");
		String name = s.nextLine();
		System.out.println("변경 후 이름을 입력하세요.");
		String name1 = s.nextLine();
		String changeStr10 = "UPDATE users SET name= '" + name1 + "' WHERE name= '" + name + "'";
		stmt.executeUpdate(changeStr10);	
		System.out.println("★정보 수정 완료★");
		break;
	}
}
void deleteuser() {
    try {
        String id;
        Scanner s = new Scanner(System.in);
        System.out.println("아이디를 입력해 주세요>>");
        id = s.nextLine();
        String del = "DELETE FROM users WHERE id=?";
        
        try (PreparedStatement preparedStatement = con.prepareStatement(del)) {
            preparedStatement.setString(1, id);
            
        int rowsAffected = preparedStatement.executeUpdate();
        if(rowsAffected == 0) {				//"만약 영향을 받은 행의 수가 0이면"이라는 조건을 나타냄.
        	System.out.println("가입된 회원이 아닙니다.");		//위 조건문이 참이면 행이 이미 0이기에 "가입된 회원이 아닙니다"출력.
        }else {
        	System.out.println("회원의 정보가 삭제되었습니다.");		//위 조건문이 거짓이면 영향받은 행의 수가 1 이상이기에 "회원의 정보가 삭제되었습니다"출력.
        }
        
            
        }
    } catch (Exception e) {
        System.out.println("데이터 삭제 실패 이유 : " + e.toString());
    }
}

		void rentalbook() {
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
							
				System.out.println("---------------------------------------------");
				System.out.println("★도여 대서를 원하시면 아이디와 도서번호를 입력해주세요★");
				System.out.println("---------------------------------------------");
			} catch(Exception e) {
				System.out.println("오류가 발생하였습니다. " + e.toString());
			}
			try  {
					String id;
            		Scanner sc = new Scanner(System.in);
	            		// 도서 목록 조회
            		    
	            		String bookslist = "SELECT bookno, title,' ' FROM books";
	            		// 도서 등록 번호 입력
	            		System.out.print("아이디를 입력하세요 :");
	            		id = sc.nextLine();
	            		System.out.print("도서번호를 입력하세요 : ");
	            		int num = sc.nextInt();
	            		// 도서 정보 조회 및 등록
	            		 if (login(id)) {
	            		String insert = "INSERT INTO bookinfo SELECT bookno, title, '"+id+"' FROM books WHERE bookno =" + num;
	            		int a =stmt.executeUpdate(insert);
	            		System.out.println( num + "번 도서를 대여하셨습니다.");	
	            		 }else {
	            			 System.out.println("등록된 아이디가 아닙니다");
	            		 }
			}catch(Exception e) {
				System.out.println("오류가 발생하였습니다. " + e.toString());
			}}

	    private static boolean login(String username) throws SQLException {
	    	
	    	
	        try (Connection connection = DriverManager.getConnection(url, user, passwd)) {
	            String ueserlogin = "SELECT * FROM users WHERE id = ?";
	            try (PreparedStatement preparedStatement = connection.prepareStatement(ueserlogin)) {
	                preparedStatement.setString(1, username);
	                try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                    return resultSet.next(); 
	                    
	                }
	            }
	        }
	    }
							
		
					
		void returnbook() {
		    try {
		        String id;
		        Scanner s = new Scanner(System.in);
		        System.out.println("아이디를 입력해 주세요>>");
		        id = s.nextLine();
		        System.out.println("========================  대여도서 현황  =========================");
		        String listAll = "SELECT * FROM users, bookinfo WHERE id=? and users.id = bookinfo.users_id";
		        
		        try (PreparedStatement preparedStatement = con.prepareStatement(listAll)) {
		            preparedStatement.setString(1, id);
		            
		            ResultSet result1 = preparedStatement.executeQuery();

		            if (!result1.isBeforeFirst()) {
		                System.out.println("해당 아이디에 대여 정보가 존재하지 않습니다.");

		            } else {
		             while (result1.next()) {
		                  
		                    System.out.println("대여중인 도서번호 :" + result1.getString("bookno") + "       대여중인 도서명 :" + result1.getString("title"));
		                    System.out.println("===============================================================");
		                }
		                 System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		            }
		        }
		    } catch (Exception e) {
		        System.out.println("회원 조회 실패 이유: " + e.toString());
		    }

		    try {      					
				System.out.println("");
				Scanner s = new Scanner(System.in);
				System.out.print("반납할 도서번호를 입력하세요>>");
				int bookno = s.nextInt();
				s.nextLine();
				String del = "DELETE FROM bookinfo where bookno='"+ bookno + "'";
				int a= stmt.executeUpdate(del);
				if(a==0) {
					System.out.println("----------------------------------------------------------");
					System.out.println("★빌린 내역이 없거나 빌린 도서번호와 입력하신번호가 일치하지 않습니다★");
					System.out.println("----------------------------------------------------------"); 
					}else {
						System.out.println("-------------------------");
						System.out.println("★반납을 완료 했습니다★");
						System.out.println("-------------------------");
					}}
				catch(Exception e) {
					System.out.println("데이터 삭제 실패이유 : " + e.toString());
		}
		
		}		
}

        
        
    

