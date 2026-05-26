package TiketPesawat.model;

/**
 * Class Penumpang — extends User.
 * INHERITANCE: mewarisi semua field dan method dari User.
 * POLYMORPHISM: override getHakAkses() dan getWelcomeMessage().
 * Hak akses: Lihat Jadwal, Pesan Tiket.
 */
public class Penumpang extends User {

    // [OOP: Inheritance] Constructor sub-class yang memanggil constructor super-class (User) menggunakan kata kunci 'super'
    public Penumpang(int idUser, String nama, String email, String username, String password) {
        super(idUser, nama, email, username, password, "penumpang");
    }

    // [OOP: Polymorphism] Overriding method 'getHakAkses' untuk mengembalikan hak akses spesifik level Penumpang
    @Override
    public String getHakAkses() {
        return "Lihat Jadwal, Pesan Tiket";
    }

    // [OOP: Polymorphism] Overriding method 'getWelcomeMessage' untuk menampilkan pesan selamat datang yang personal bagi Penumpang
    @Override
    public String getWelcomeMessage() {
        return "Selamat datang, " + getNama() + "! Silakan pesan tiket Anda.";
    }

    // [OOP: Polymorphism] Overriding method 'getInfo' untuk menyusun format ringkas kartu informasi data Penumpang
    @Override
    public String getInfo() {
        return "Penumpang: " + getNama() + " | Akses: " + getHakAkses();
    }
}