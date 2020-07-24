package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Mutter;

public class MutterDAO {
  // データベース接続に使用する情報
    String DATABASE_NAME = "docotsubu";
    String PROPATIES = "?characterEncoding=UTF-8&serverTimezone=JST";
    String URL = "jdbc:mySQL://localhost/" + DATABASE_NAME+PROPATIES;
    //DB接続用・ユーザ定数
    String USER = "root";
    String PASS = "14555334";

  public List<Mutter> findAll() {
    List<Mutter> mutterList = new ArrayList<Mutter>();

    // データベース接続
    try {
        //MySQL に接続する
        Class.forName("com.mysql.cj.jdbc.Driver");
        //データベースに接続
        Connection conn = DriverManager.getConnection(URL, USER, PASS);

        // SELECT文を準備
        String sql = "SELECT ID,NAME,TEXT FROM MUTTER ORDER BY ID DESC";
        PreparedStatement pStmt = conn.prepareStatement(sql);

        // SELECTを実行し、結果表（ResultSet）を取得
        ResultSet rs = pStmt.executeQuery();

      // SELECT文の結果をArrayListに格納
      while (rs.next()) {
        int id = rs.getInt("ID");
        String userName = rs.getString("NAME");
        String text = rs.getString("TEXT");
        Mutter mutter = new Mutter(id, userName, text);
        mutterList.add(mutter);
      }
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        return null;
    }
    return mutterList;
  }
  public boolean create(Mutter mutter) {
    // データベース接続
	  try {
	        //MySQL に接続する
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        //データベースに接続
	        Connection conn = DriverManager.getConnection(URL, USER, PASS);
	        // データベースに対する処理
//	        System.out.println("データベースに接続に成功");

      // INSERT文の準備(idは自動連番なので指定しなくてよい）
      String sql = "INSERT INTO MUTTER(NAME, TEXT) VALUES(?, ?)";
      PreparedStatement pStmt = conn.prepareStatement(sql);
      // INSERT文中の「?」に使用する値を設定しSQLを完成
      pStmt.setString(1, mutter.getUserName());
      pStmt.setString(2, mutter.getText());

      // INSERT文を実行
      int result = pStmt.executeUpdate();

      if (result != 1) {
        return false;
      }
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        return false;
    }
    return true;
  }
}