package TiketPesawat.helper;

import java.sql.*;
import java.util.*;
import TiketPesawat.model.*;

public class DBPesawat extends DBHelper implements ICrud {

    @Override
    public String getNamaTable() {
        return "pesawat";
    }

    // ---- Implementasi ICrud ----

    @Override
    public boolean tambahData(String kode, String nilai) {
        return insertDataPesawat(kode, nilai);
    }

    @Override
    public boolean ubahData(String kode, String nilai) {
        // ubahData di ICrud hanya 2 param, jadi kodeLama = kodeBaru (kode tidak diubah)
        return updateDataPesawat(kode, kode, nilai);
    }

    @Override
    public boolean hapusData(String kode) {
        return deleteDataPesawat(kode);
    }

    // ---- Pesawat Feature ----

    public List<PesawatModel> getAllPesawat() {
        List<PesawatModel> list = new ArrayList<>();
        String query = "SELECT * FROM " + getNamaTable();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                PesawatModel data = new PesawatModel();
                data.setKodePesawat(rs.getString("kodePesawat"));
                data.setPesawat(rs.getString("pesawat"));
                list.add(data);
            }
            tutupResource();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertDataPesawat(String kode, String pesawat) {
        String query = "INSERT INTO " + getNamaTable() + " (kodePesawat, pesawat) VALUES (?, ?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, kode);
            pstmt.setString(2, pesawat);
            boolean result = pstmt.executeUpdate() > 0;
            pstmt.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteDataPesawat(String kode) {
    String queryJadwal = "DELETE FROM jadwal WHERE kodePesawat = ?";
    String queryPesawat = "DELETE FROM " + getNamaTable() + " WHERE kodePesawat = ?";
    try {
        // 1. Hapus data jadwal yang pakai kode ini dulu
        PreparedStatement pJadwal = conn.prepareStatement(queryJadwal);
        pJadwal.setString(1, kode);
        pJadwal.executeUpdate();
        pJadwal.close();

        // 2. Baru hapus pesawat
        PreparedStatement pPesawat = conn.prepareStatement(queryPesawat);
        pPesawat.setString(1, kode);
        boolean result = pPesawat.executeUpdate() > 0;
        pPesawat.close();
        return result;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

    public boolean updateDataPesawat(String kodeLama, String kodeBaru, String pesawat) {
    String queryPesawat = "UPDATE " + getNamaTable()
            + " SET kodePesawat = ?, pesawat = ? WHERE kodePesawat = ?";
    try {
        // Matikan sementara foreign key check
        conn.createStatement().execute("SET FOREIGN_KEY_CHECKS=0");

        // Update tabel pesawat
        PreparedStatement pPesawat = conn.prepareStatement(queryPesawat);
        pPesawat.setString(1, kodeBaru);
        pPesawat.setString(2, pesawat);
        pPesawat.setString(3, kodeLama);
        boolean result = pPesawat.executeUpdate() > 0;
        pPesawat.close();

        // Update referensi di tabel jadwal
        PreparedStatement pJadwal = conn.prepareStatement(
            "UPDATE jadwal SET kodePesawat = ? WHERE kodePesawat = ?");
        pJadwal.setString(1, kodeBaru);
        pJadwal.setString(2, kodeLama);
        pJadwal.executeUpdate();
        pJadwal.close();

        // Nyalakan kembali foreign key check
        conn.createStatement().execute("SET FOREIGN_KEY_CHECKS=1");

        return result;
    } catch (SQLException e) {
        // Pastikan FK check tetap nyala meski error
        try { conn.createStatement().execute("SET FOREIGN_KEY_CHECKS=1"); }
        catch (SQLException ex) { ex.printStackTrace(); }
        e.printStackTrace();
    }
    return false;
}
}