package TiketPesawat.helper;

import TiketPesawat.model.JadwalModel;
import java.sql.*;
import java.util.*;

/**
 * DBJadwal — mengelola data jadwal penerbangan.
 *
 * Extends DBHelper → Inheritance (mewarisi koneksi DB dari superclass).
 * Override getNamaTable() → Polymorphism.
 */
public class DBJadwal extends DBHelper {

    @Override
    public String getNamaTable() {
        return "jadwal";
    }

    // ---- Jadwal Feature ----

    public List<JadwalModel> getDataPesawat() {
        List<JadwalModel> list = new ArrayList<>();
        String query = "SELECT * FROM pesawat";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                JadwalModel data = new JadwalModel();
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

    public List<JadwalModel> getDataKota() {
        List<JadwalModel> list = new ArrayList<>();
        String query = "SELECT * FROM kota";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                JadwalModel data = new JadwalModel();
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

    public List<JadwalModel> getAllJadwal() {
        List<JadwalModel> list = new ArrayList<>();
        String query = "SELECT * FROM " + getNamaTable();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                JadwalModel data = new JadwalModel();
                data.setKodeJadwal(rs.getString("kodeJadwal"));
                data.setKodePesawat(rs.getString("kodePesawat"));
                data.setKodeKotaAwal(rs.getString("kodeKotaAwal"));
                data.setKodeKotaTujuan(rs.getString("kodeKotaTujuan"));
                data.setJamKeberangkatan(rs.getString("jamKeberangkatan"));
                data.setJamKedatangan(rs.getString("jamKedatangan"));
                data.setHarga(rs.getString("harga"));
                data.setKursiDiambil(rs.getString("kursiDiambil"));
                data.setKursiTersedia(rs.getString("kursiTersedia"));
                data.setStatusKuota(rs.getString("statusKuota"));
                list.add(data);
            }
            tutupResource();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertDataJadwal(String kode, String pesawat, String kotaAwal,
            String kotaTujuan, String jamKeb, String jamKed,
            String harga, String kursiTersedia, String kursiDiambil) {
        String query = "INSERT INTO " + getNamaTable()
                + " (kodeJadwal, jamKeberangkatan, jamKedatangan, harga,"
                + " kodePesawat, kodeKotaAwal, kodeKotaTujuan, kursiTersedia, kursiDiambil)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, kode);
            pstmt.setString(2, jamKeb);
            pstmt.setString(3, jamKed);
            pstmt.setString(4, harga);
            pstmt.setString(5, pesawat);
            pstmt.setString(6, kotaAwal);
            pstmt.setString(7, kotaTujuan);
            pstmt.setString(8, kursiTersedia);
            pstmt.setString(9, kursiDiambil);
            boolean result = pstmt.executeUpdate() > 0;
            pstmt.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteDataJadwal(String kode) {
        String query = "DELETE FROM " + getNamaTable() + " WHERE kodeJadwal = ?";
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

    public boolean updateDataJadwal(String kode, String pesawat, String kotaAwal,
            String kotaTujuan, String jamKeb, String jamKed,
            String harga, String kursiTersedia, String kursiDiambil) {
        String query = "UPDATE " + getNamaTable()
                + " SET kodePesawat=?, kodeKotaAwal=?, kodeKotaTujuan=?,"
                + " jamKeberangkatan=?, jamKedatangan=?, harga=?,"
                + " kursiTersedia=?, kursiDiambil=?"
                + " WHERE kodeJadwal=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, pesawat);
            pstmt.setString(2, kotaAwal);
            pstmt.setString(3, kotaTujuan);
            pstmt.setString(4, jamKeb);
            pstmt.setString(5, jamKed);
            pstmt.setString(6, harga);
            pstmt.setString(7, kursiTersedia);
            pstmt.setString(8, kursiDiambil);
            pstmt.setString(9, kode);
            boolean result = pstmt.executeUpdate() > 0;
            pstmt.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isKodeJadwalExists(String kodeJadwal) {
        String query = "SELECT COUNT(*) FROM " + getNamaTable() + " WHERE kodeJadwal = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, kodeJadwal);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                boolean exists = rs.getInt(1) > 0;
                rs.close();
                pstmt.close();
                return exists;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
