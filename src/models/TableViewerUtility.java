package models;

import java.sql.*;

public class TableViewerUtility {
    public static void main(String[] args) {
        DBAccess db = DBAccess.getInstance();
        showTableData(db, "users");
        showTableData(db, "restaurants");
    }

    public static void showTableData(DBAccess db, String tableName) {
        String sql = "SELECT * FROM " + tableName;

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            System.out.println("\n📋 Data from table: " + tableName);
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(metaData.getColumnName(i) + " | ");
            }
            System.out.println("\n" + "-".repeat(50));

            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getString(i) + " | ");
                }
                System.out.println();
            }

        } catch (SQLException e) {
            System.err.println("❌ Error fetching data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void showTableColumns(DBAccess db, String tableName) {
        String sql = "SELECT COLUMN_NAME, TYPE_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tableName.toUpperCase());
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("📋 Columns in '" + tableName + "' table:");
                while (rs.next()) {
                    System.out.println(rs.getString("COLUMN_NAME") + " | " + rs.getString("TYPE_NAME"));
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error fetching columns: " + e.getMessage());
            e.printStackTrace();
        }
    }
}