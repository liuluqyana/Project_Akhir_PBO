package TiketPesawat.helper;

import TiketPesawat.model.KotaModel;
import java.sql.*;
import java.util.*;

/**
 * DBKota — mengelola data kota.
 *
 * [OOP: Inheritance] Mewarisi koneksi DB dan utility resource dari superclass DBHelper.
 * [OOP: Abstraction & Polymorphism] Mengimplementasikan kontrak interface ICrud untuk standardisasi operasi CRUD.
 */
public class DBKota extends DBHelper implements ICrud {

    /** [OOP: Polymorphism] Override method abstract dari superclass DBHelper untuk menentukan target nama tabel database. */
    @Override
    public String getNamaTable() {
        return "kota";
    }

    // ---- Implementasi ICrud (Abstraction + Polymorphism) ----

    // [OOP: Polymorphism / Abstraction] Mengisi realisasi method interface ICrud untuk menambahkan data
    @Override
    public boolean tambahData(String kode, String nilai) {
        return insertDataKota(kode, nilai);
    }

    // [OOP: Polymorphism / Abstraction] Mengisi realisasi method interface ICrud untuk mengubah data
    @Override
    public boolean ubahData(String kode, String nilai) {
        return updateDataKota(kode, kode, nilai);
    }

    // [OOP: Polymorphism / Abstraction] Mengisi realisasi method interface ICrud untuk menghapus data
    @Override
    public boolean hapusData(String kode) {
        return deleteDataKota(kode);
    }

    // ---- Kota Feature ----

    // Mengambil seluruh baris record data kota dari database ke dalam Collection List
    public List<KotaModel> getAllKota() {
        List<KotaModel> list = new ArrayList<>();
        String query = "SELECT * FROM " + getNamaTable();
        try {
            // Menggunakan properti instans (stmt, conn, rs) yang diwarisi dari DBHelper
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                // [OOP: Encapsulation] Data dibungkus ke dalam objek model melalui method setter
                KotaModel data = new KotaModel();
                data.setKodeKota(rs.getString("kodeKota"));
                data.setKota(rs.getString("kota"));
                list.add(data);
            }
            tutupResource(); // Memanggil method utilitas penutup koneksi milik superclass DBHelper
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Proses penambahan entitas kota baru ke database menggunakan PreparedStatement (Aman dari SQL Injection)
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

    // Proses penghapusan data kota berdasarkan Primary Key kodeKota
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

    // Proses pembaruan record kode atau nama kota berdasarkan parameter kunci utama lama
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