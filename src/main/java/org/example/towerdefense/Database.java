package org.example.towerdefense;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database {
    private final String DB_URL;

    public Database(String DB_URL) {
        this.DB_URL = DB_URL;
    }

    public List<HashMap<String, Object>> readPlayers() {
        List<HashMap<String, Object>> playersList = new ArrayList<HashMap<String, Object>>();

        String sql = "SELECT player_name, win_quantity, lose_quantity FROM Players";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String player_name = rs.getString("player_name");
                int win_quantity = rs.getInt("win_quantity");
                int lose_quantity = rs.getInt("lose_quantity");

                HashMap<String, Object> tmpHashMap = new HashMap<>();
                tmpHashMap.put("name", player_name);
                tmpHashMap.put("wins", win_quantity);
                tmpHashMap.put("losses", lose_quantity);
                playersList.add(tmpHashMap);
            }

        } catch (SQLException e) {
            System.err.println("Error reading players: " + e.getMessage());
        }


        return playersList;
    }

    public void updatePlayer(String player_name, int plus_win_quantity, int plus_lose_quantity) {
        String sql = "SELECT win_quantity, lose_quantity FROM Players WHERE player_name='You'";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            int win_quantity = 0;
            int lose_quantity = 0;

            while (rs.next()) {
                win_quantity = rs.getInt("win_quantity");
                lose_quantity = rs.getInt("lose_quantity");
            }

            String sqlUpdate = "UPDATE Players SET win_quantity = ?, lose_quantity = ? WHERE player_name = 'You'";
            PreparedStatement pstmt = conn.prepareStatement(sqlUpdate);
            pstmt.setInt(1, plus_win_quantity + win_quantity);
            pstmt.setInt(2, lose_quantity + plus_lose_quantity);
            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}