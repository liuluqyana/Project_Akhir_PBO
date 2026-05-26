package TiketPesawat.model;

public class PesawatModel {
    // [OOP: Encapsulation] Menggunakan access modifier private (Data Hiding) agar atribut 
    // hanya bisa diakses dan diubah melalui perantara method getter & setter
    private String kodePesawat = "";
    private String pesawat = "";

    // [OOP: Encapsulation] Method Getter sebagai akses kontrol untuk membaca data dari variabel kodePesawat
    public String getKodePesawat() {
        return kodePesawat;
    }

    // [OOP: Encapsulation] Method Setter untuk mengubah isi variabel kodePesawat secara aman. 
    // Keyword 'this' digunakan untuk mempertegas kepemilikan variabel instans milik class ini
    public void setKodePesawat(String kodePesawat) {
        this.kodePesawat = kodePesawat;
    }

    // [OOP: Encapsulation] Method Getter sebagai akses kontrol untuk membaca data dari variabel pesawat
    public String getPesawat() {
        return pesawat;
    }

    // [OOP: Encapsulation] Method Setter untuk mengubah isi variabel pesawat secara terkontrol
    public void setPesawat(String pesawat) {
        this.pesawat = pesawat;
    }
}