package kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Posts_Chapter07 {
	public static void main(String[] args) {
		
		Connection con = null;
		PreparedStatement insertStatement = null;
		PreparedStatement selectStatement = null;
		
		String[][] postsList = {
				{"1003", "2023-02-08", "昨日の夜は徹夜でした・・", "13"},
				{"1002", "2023-02-08", "お疲れ様です！", "12"},
				{"1003", "2023-02-09", "今日も頑張ります！", "18"},
				{"1001", "2023-02-09", "無理は禁物ですよ！", "17"},
				{"1002", "2023-02-10", "明日から連休ですね！", "20"},
		};
		
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					"Sakm3546@"
			);
			
			System.out.println("データベース接続成功:" + con);
			
			String insertSql = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES (?, ?, ?, ?)";
			insertStatement = con.prepareStatement(insertSql);
			
			System.out.println("レコード追加を実行します");
			for (String[] post : postsList) {
				insertStatement.setInt(1, Integer.parseInt(post[0]));
				insertStatement.setString(2, post[1]);
				insertStatement.setString(3, post[2]);
				insertStatement.setInt(4, Integer.parseInt(post[3]));
				insertStatement.executeUpdate();
			}
			System.out.println(postsList.length + "件のレコードが追加されました");
			
			String selectSql = "SELECT posted_at, post_content, likes FROM posts WHERE user_id = 1002";
			selectStatement = con.prepareStatement(selectSql);
			
			System.out.println("ユーザーIDが1002のレコードを検索しました");
			ResultSet result = selectStatement.executeQuery();
			
			int rowNumber = 0;
			while (result.next()) {
				rowNumber++;
				String postedAt = result.getString("posted_at");
				String postContent = result.getString("post_content");
				int likes = result.getInt("likes");
				
				System.out.println(rowNumber + "件目：投稿日時=" + postedAt + "／投稿内容=" + postContent + "／いいね数=" + likes);
			}
		}catch (SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		}finally {
			if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException ignore) {
                }
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException ignore) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ignore) {
                }
            }
        }
    }
}