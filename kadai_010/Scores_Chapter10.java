package kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Scores_Chapter10 {
	public static void main(String[] args) {
		Connection con = null;
		Statement statement = null;
		
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					"Sakm3546@"
			);
			
			System.out.println("データベース接続成功:" + con);
			System.out.println("レコード更新を実行します");
			
			statement = con.createStatement();
			
			String updateSql = "UPDATE scores SET score_math = 95, score_english = 80 WHERE id = 5;";
			int rowCnt = statement.executeUpdate(updateSql);
			System.out.println( rowCnt + "件のレコードが更新されました");
			
			String selectSql = "SELECT id, name, score_math, score_english " +
					"FROM scores " +
					"ORDER BY score_math DESC, score_english DESC;";
			ResultSet resultSet = statement.executeQuery(selectSql);
			
			System.out.println("数学・英語の点数が高い順に並べ替えました");
			int count = 1;
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				int math = resultSet.getInt("score_math");
				int english = resultSet.getInt("score_english");
				System.out.println(count + "件目：生徒ID=" + id + "／氏名=" + name + "／数学=" + math + "／英語=" + english);
				count++;
			}
			
		} catch (SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		} finally {
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException ignore) {}
			}
			if(con != null) {
				try {
					con.close();
				} catch(SQLException ignore) {}
			}
		}
	}
}
