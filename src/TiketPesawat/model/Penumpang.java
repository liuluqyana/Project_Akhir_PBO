package TiketPesawat.model;

/**
 * Class Penumpang — extends User.
 * INHERITANCE: mewarisi semua field dan method dari User.
 * POLYMORPHISM: override getHakAkses() dan getWelcomeMessage().
 * Hak akses: Lihat Jadwal, Pesan Tiket.
 */
public class Penumpang extends User {

    public Penumpang(int idUser, String nama, String email, String username, String password) {
        super(idUser, nama, email, username, password, "penumpang");
    }

    @Override
    public String getHakAkses() {
        return "Lihat Jadwal, Pesan Tiket";
    }

    @Override
    public String getWelcomeMessage() {
        return "Selamat datang, " + getNama() + "! Silakan pesan tiket Anda.";
    }

    @Override
    public String getInfo() {
        return "Penumpang: " + getNama() + " | Akses: " + getHakAkses();
    }
}
