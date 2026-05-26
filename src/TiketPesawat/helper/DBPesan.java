package TiketPesawat.helper;

import TiketPesawat.model.*;
import java.sql.*;
import java.util.*;

/**
 * DBPesan — mengelola fitur pemesanan tiket.
 *
 * [OOP: Inheritance] Mewarisi properti koneksi database dan fungsi utilitas dari superclass DBHelper.
 * [OOP: Polymorphism] Override getNamaTable() untuk menentukan target tabel secara dinamis pada runtime.
 */
public class DBPesan extends DBHelper {

    @Override
    public String getNamaTable() {
        return "jadwal";
    }

    // ---- Pesan Fitur ----

    // Mengambil daftar jadwal penerbangan berdasarkan filter rute kota asal dan tujuan
    public List<JadwalModel> getJadwal(String kodeKotaAwal, String kodeKotaTujuan) {
        List<JadwalModel> list = new ArrayList<>();
        String query = "SELECT * FROM " + getNamaTable()
                + " WHERE kodeKotaAwal = ? AND kodeKotaTujuan = ?";
        try {
            // [OOP: Encapsulation / Abstraction] Detail query SQL dibungkus aman menggunakan PreparedStatement
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, kodeKotaAwal);
            pstmt.setString(2, kodeKotaTujuan);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                // [OOP: Encapsulation] Data row database dibungkus ke dalam objek entitas melalui setter
                JadwalModel data = new JadwalModel();
                data.setKodeJadwal(rs.getString("kodeJadwal"));
                data.setKodePesawat(rs.getString("kodePesawat"));
                data.setKodeKotaAwal(rs.getString("kodeKotaAwal"));
                data.setKodeKotaTujuan(rs.getString("kodeKotaTujuan"));
                data.setJamKeberangkatan(rs.getString("jamKeberangkatan"));
                data.setJamKedatangan(rs.getString("jamKedatangan"));
                data.setHarga(rs.getString("harga"));
                data.setKursiTersedia(rs.getString("kursiTersedia"));
                data.setKursiDiambil(rs.getString("kursiDiambil"));
                data.setStatusKuota(rs.getString("statusKuota"));
                list.add(data);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Bagian Penting: Query kompleks INNER JOIN untuk menggabungkan relasi tabel jadwal, pesawat, dan kota (k1 & k2)
    public List<JadwalModel> getJadwalKodeJadwal(String kodeJadwal) {
        List<JadwalModel> list = new ArrayList<>();
        String query = "SELECT jadwal.*, pesawat.pesawat, k1.kota AS kotaAwal, k2.kota AS kotaTujuan "
                + "FROM jadwal "
                + "INNER JOIN pesawat ON pesawat.kodePesawat = jadwal.kodePesawat "
                + "INNER JOIN kota AS k1 ON k1.kodeKota = jadwal.kodeKotaAwal "
                + "INNER JOIN kota AS k2 ON k2.kodeKota = jadwal.kodeKotaTujuan "
                + "WHERE kodeJadwal = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, kodeJadwal);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                JadwalModel data = new JadwalModel();
                data.setKodeJadwal(rs.getString("kodeJadwal"));
                data.setPesawat(rs.getString("pesawat"));
                data.setKotaAwal(rs.getString("kotaAwal"));
                data.setKotaTujuan(rs.getString("kotaTujuan"));
                data.setJamKeberangkatan(rs.getString("jamKeberangkatan"));
                data.setJamKedatangan(rs.getString("jamKedatangan"));
                data.setHarga(rs.getString("harga"));
                data.setKursiTersedia(rs.getString("kursiTersedia"));
                data.setKursiDiambil(rs.getString("kursiDiambil"));
                data.setStatusKuota(rs.getString("statusKuota"));
                list.add(data);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Mengambil data seluruh kota untuk kebutuhan komponen Dropdown/Combo Box di View
    public List<PesanModel> getDataKota() {
        List<PesanModel> list = new ArrayList<>();
        String query = "SELECT * FROM kota";
        try {
            // Menggunakan properti statement (stmt) dan result set (rs) warisan dari superclass DBHelper
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                PesanModel data = new PesanModel();
                data.setKodeKota(rs.getString("kodeKota"));
                data.setKota(rs.getString("kota"));
                list.add(data);
            }
            tutupResource(); // Memanggil fungsi penutup resource warisan DBHelper
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Validasi data eksistensi rute penerbangan berdasarkan kota asal dan tujuan
    public boolean cekDataJadwal(String kodeKotaAwal, String kodeKotaTujuan) {
        String query = "SELECT COUNT(*) FROM " + getNamaTable()
                + " WHERE kodeKotaAwal = ? AND kodeKotaTujuan = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, kodeKotaAwal);
            pstmt.setString(2, kodeKotaTujuan);
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

    // Mengambil ID / Kode Jadwal penerbangan utama yang mengikat rute terpilih
    public String ambilKodeJadwal(String kodeKotaAwal, String kodeKotaTujuan) {
        String value = "";
        String query = "SELECT kodeJadwal FROM " + getNamaTable()
                + " WHERE kodeKotaAwal = ? AND kodeKotaTujuan = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, kodeKotaAwal);
            pstmt.setString(2, kodeKotaTujuan);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                value = rs.getString("kodeJadwal");
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
    }

    // Bagian Penting: Validasi logika bisnis untuk memastikan sisa kapasitas bangku pesawat masih mencukupi
    public boolean isKuotaPenuh(String kodeJadwal) {
        String query = "SELECT kursiTersedia, kursiDiambil FROM " + getNamaTable()
                + " WHERE kodeJadwal = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, kodeJadwal);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int tersedia = rs.getInt("kursiTersedia");
                int diambil  = rs.getInt("kursiDiambil");
                rs.close();
                pstmt.close();
                return diambil >= tersedia;   // Logika penentu status penuh/tidaknya kuota kursi
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Proses modifikasi data kolom akumulasi bangku pesawat yang telah dipesan oleh penumpang
    public boolean updateKursiDiambil(String kodeJadwal, int jumlahKursi) {
        String query = "UPDATE " + getNamaTable()
                + " SET kursiDiambil = kursiDiambil + ? WHERE kodeJadwal = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, jumlahKursi);
            pstmt.setString(2, kodeJadwal);
            boolean result = pstmt.executeUpdate() > 0;
            pstmt.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}