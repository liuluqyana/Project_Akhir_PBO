package TiketPesawat.helper;

/**
 * Interface ICrud — mendefinisikan kontrak operasi CRUD dasar.
 *
 * Ini adalah bentuk Abstraction: interface hanya mendefinisikan "apa"
 * yang harus dilakukan, bukan "bagaimana" melakukannya.
 *
 * Polymorphism: kelas yang mengimplementasikan interface ini dapat
 * diperlakukan secara seragam melalui tipe ICrud.
 */
public interface ICrud {

    /**
     * Menambahkan data baru ke tabel.
     * @param kode  kode/primary key
     * @param nilai nilai/nama dari data
     * @return true jika berhasil, false jika gagal
     */
    boolean tambahData(String kode, String nilai);

    /**
     * Mengubah data yang sudah ada.
     * @param kode  kode yang ingin diubah
     * @param nilai nilai baru
     * @return true jika berhasil
     */
    boolean ubahData(String kode, String nilai);

    /**
     * Menghapus data berdasarkan kode.
     * @param kode kode yang ingin dihapus
     * @return true jika berhasil
     */
    boolean hapusData(String kode);
}
