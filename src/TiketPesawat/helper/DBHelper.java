package TiketPesawat.helper;

import java.sql.*;

/**
 * Abstract base class untuk semua kelas DB.
 * Menerapkan konsep OOP:
 *  - Abstraction   : kelas ini abstract, dan punya method abstract getNamaTable()
 *  - Inheritance   : semua kelas DB (DBAuth, DBKota, dst) mewarisi kelas ini
 *  - Encapsulation : koneksi dikelola di satu tempat, field protected
 */
public abstract class DBHelper {

    private static final String DB_URL  = "jdbc:mysql://localhost/pesawat";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    protected Connection conn;
    protected Statement  stmt;
    protected ResultSet  rs;

    /** Constructor: membuka koneksi — subclass tidak perlu menulis ulang (Inheritance). */
    public DBHelper() {
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (SQLException e) {
            System.err.println("Gagal koneksi ke database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Method abstract — setiap subclass WAJIB mengimplementasikan (Abstraction).
     * Polymorphism: setiap subclass mengembalikan nama tabelnya masing-masing.
     * @return nama tabel yang dikelola subclass ini
     */
    public abstract String getNamaTable();

    /** Menutup ResultSet dan Statement dengan aman. */
    protected void tutupResource() {
        try {
            if (rs   != null && !rs.isClosed())   rs.close();
            if (stmt != null && !stmt.isClosed()) stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        try { return conn != null && !conn.isClosed(); }
        catch (SQLException e) { return false; }
    }
}
