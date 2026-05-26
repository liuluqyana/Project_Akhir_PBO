package TiketPesawat.helper;

import TiketPesawat.model.*;
import java.sql.*;

/**
 * DBAuth — mengelola autentikasi user.
 * INHERITANCE: extends DBHelper (mewarisi koneksi DB).
 * POLYMORPHISM: override getNamaTable().
 * DIPERBAIKI: tambah kolom role, ambilDataUser() mengembalikan Admin atau Penumpang.
 */
public class DBAuth extends DBHelper {

    @Override
    public String getNamaTable() { return "users"; }

    public boolean cekLogin(String username, String password) {
        String query = "SELECT * FROM " + getNamaTable() + " WHERE username = ? AND password = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            boolean ok = rs.next();
            rs.close(); pstmt.close();
            return ok;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    /**
     * Ambil data user setelah login — return Admin atau Penumpang (POLYMORPHISM).
     * Tipe object ditentukan saat runtime berdasarkan kolom 'role' di database.
     */
    public User ambilDataUser(String username, String password) {
        String query = "SELECT * FROM " + getNamaTable() + " WHERE username = ? AND password = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int    id    = rs.getInt("id_user");
                String nama  = rs.getString("nama");
                String email = rs.getString("email");
                String uname = rs.getString("username");
                String pass  = rs.getString("password");
                String role  = rs.getString("role");
                rs.close(); pstmt.close();
                // Polymorphism: tipe object berbeda sesuai role
                if ("admin".equalsIgnoreCase(role)) {
                    return new Admin(id, nama, email, uname, pass);
                } else {
                    return new Penumpang(id, nama, email, uname, pass);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public String ambilNama(String username, String password) {
        String nama = "";
        String query = "SELECT nama FROM " + getNamaTable() + " WHERE username = ? AND password = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            if (rs.next()) nama = rs.getString("nama");
            rs.close(); pstmt.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return nama;
    }

    /** Registrasi — role otomatis = 'penumpang'. Admin hanya bisa dibuat manual di DB. */
    public boolean daftarUser(String nama, String email, String username, String password) {
        String query = "INSERT INTO " + getNamaTable()
                + " (nama, email, username, password, role) VALUES (?, ?, ?, ?, 'penumpang')";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, nama);
            pstmt.setString(2, email);
            pstmt.setString(3, username);
            pstmt.setString(4, password);
            boolean ok = pstmt.executeUpdate() > 0;
            pstmt.close();
            return ok;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}
