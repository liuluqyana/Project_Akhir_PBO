package TiketPesawat.model;

/**
 * Class Admin — extends User.
 * INHERITANCE: mewarisi semua field dan method dari User.
 * POLYMORPHISM: override getHakAkses() dan getWelcomeMessage().
 * Hak akses: Kelola Kota, Maskapai, Jadwal, Lihat Pemesanan.
 */
public class Admin extends User {

    // [OOP: Inheritance] Constructor menggunakan keyword 'super' untuk memicu instansiasi field milik superclass (User)
    public Admin(int idUser, String nama, String email, String username, String password) {
        super(idUser, nama, email, username, password, "admin");
    }

    // [OOP: Polymorphism] Overriding method 'getHakAkses' untuk memberikan representasi hak akses khusus Admin
    @Override
    public String getHakAkses() {
        return "Kelola Kota, Kelola Maskapai, Kelola Jadwal, Lihat Pemesanan";
    }

    // [OOP: Polymorphism] Overriding method 'getWelcomeMessage' untuk menghasilkan sapaan teks login yang spesifik bagi Admin
    @Override
    public String getWelcomeMessage() {
        return "Selamat datang, Admin " + getNama() + "!";
    }

    // [OOP: Polymorphism] Overriding method 'getInfo' untuk menyusun ringkasan informasi identitas objek Admin
    @Override
    public String getInfo() {
        return "Admin: " + getNama() + " | Akses: " + getHakAkses();
    }
}