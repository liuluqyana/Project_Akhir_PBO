package TiketPesawat.helper;

import TiketPesawat.model.KotaModel;
import java.sql.*;
import java.util.*;

/**
 * DBKota — mengelola data kota.
 *
 * Extends DBHelper    → Inheritance (mewarisi koneksi DB).
 * Implements ICrud    → Abstraction & Polymorphism
 *                       (kelas ini memenuhi kontrak interface ICrud).
 */
public class DBKota extends DBHelper implements ICrud {

    /** Polymorphism: override method abstract dari superclass DBHelper. */
    @Override
    public String getNamaTable() {
        return "kota";
    }

    // ---- Implementasi ICrud (Abstraction + Polymorphism) ----

    @Override
    public boolean tambahData(String kode, String nilai) {
        return insertDataKota(kode, nilai);
    }

    @Override
    public boolean ubahData(String kode, String nilai) {
    return updateDataKota(kode, kode, nilai);
}

    @Override
    public boolean hapusData(String kode) {
        return deleteDataKota(kode);
    }

    // ---- Kota Feature ----

    public List<KotaModel> getAllKota() {
        List<KotaModel> list = new ArrayList<>();
        String query = "SELECT * FROM " + getNamaTable();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                KotaModel data = new KotaModel();
                data.setKodeKota(rs.getString("kodeKota"));
                data.setKota(rs.getString("kota"));
                list.add(data);
            }
            tutupResource();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertDataKota(String kode, String kota) {
        String query = "INSERT INTO " + getNamaTable() + " (kodeKota, kota) VALUES (?, ?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, kode);
            pstmt.setString(2, kota);
            boolean result = pstmt.executeUpdate() > 0;
            pstmt.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteDataKota(String kode) {
        String query = "DELETE FROM " + getNamaTable() + " WHERE kodeKota = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, kode);
            boolean result = pstmt.executeUpdate() > 0;
            pstmt.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateDataKota(String kodeLama, String kodeBaru, String kota) {
    String query = "UPDATE " + getNamaTable()
            + " SET kodeKota = ?, kota = ? WHERE kodeKota = ?";
    try {
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, kodeBaru);
        pstmt.setString(2, kota);
        pstmt.setString(3, kodeLama);
        boolean result = pstmt.executeUpdate() > 0;
        pstmt.close();
        return result;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
    }
}
