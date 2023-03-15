import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * DB에 데이터 넣고 조회하는 클래스
 * 
 * @author yblee
 * @since 2023.03.15
 * 
 */
public class DB {
    /**
     * DB에 데이터 넣는 함수
     * 
     * @author yblee
     * @since 2023.03.15
     * 
     */
    public void insert(String id, String pwd) {
        // JDBC를 Load 해준다.
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (
                // conncection 정보 설정
                Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/jdbc_test", "root",
                        "0694");
                // 실행할 Query 작성
                PreparedStatement stmt = con.prepareStatement("INSERT INTO user(id, password) VALUES (?, ?)");) {
            // parameter 설정
            stmt.setString(1, id);
            stmt.setString(2, pwd);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * DB 데이터 조회 함수
     * 
     * @author yblee
     * @since 2023.03.15
     * 
     */
    public void findAll() {
        // JDBC를 Load 해준다.
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (
                // conncection 정보 설정
                Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/jdbc_test", "root",
                        "0694");
                // 실행할 Query 작성
                PreparedStatement stmt = con.prepareStatement("SELECT * FROM user");
                // Query 실행
                ResultSet rs = stmt.executeQuery();) {
            // Query 결과 확인
            while (rs.next()) {
                System.out.println(String.format("id: %s, password: %s", rs.getString("id"), rs.getString("password")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * (non-javadoc)
     * 
     * @author yblee
     * @since 2023.03.15
     * 
     */
    public static void main(String[] args) {
        DB jdbc = new DB();
        System.out.println("ID와 PWD 입력하세요");
        Scanner scan = new Scanner(System.in);
        String id = scan.next();
        String pwd = scan.next();

        System.out.println("데이터 삽입 전");
        jdbc.findAll(); // 데이터 넣기 전
        jdbc.insert(id, pwd); // 데이터 삽입

        System.out.println("데이터 삽입 후");
        jdbc.findAll(); // 데이터 넣은 후

        scan.close();
    }
}
