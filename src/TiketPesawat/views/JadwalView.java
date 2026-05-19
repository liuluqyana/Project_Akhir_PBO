package TiketPesawat.views;

import TiketPesawat.controller.JadwalController;
import TiketPesawat.model.JadwalModel;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import static TiketPesawat.views.ThemeHelper.*;

public class JadwalView extends JFrame {

    JadwalController jc;

    private JTextField        kodeJadwal, jamKeberangkatan, jamKedatangan, harga,
                              kursiTersedia, kursiDiambil, statusKuota;
    private JComboBox<String> kodeMaskapai, kotaAwal, kotaTujuan;
    private JTable            jadwalTabel;
    private JButton           tambahBtn, ubahBtn, hapusBtn, cleatBtn, kembaliBtn;

    public JadwalView(JadwalController c) {
        this.jc = c;
        initComponents();
        kodeMaskapai.removeAllItems();
        kotaAwal.removeAllItems();
        kotaTujuan.removeAllItems();
        loadDataPesawat();
        loadDataKota();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Jadwal Penerbangan");
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(LINEN);

        JPanel header = createHeaderPanel("📅  Jadwal Penerbangan", 920);
        root.add(header, BorderLayout.NORTH);

        JPanel content = new JPanel(null);
        content.setBackground(LINEN);
        content.setPreferredSize(new Dimension(920, 520)); // tinggi content diperbesar

        // ── Form Card ──────────────────────────────────────────────
        JPanel formCard = new JPanel(null);
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(new LineBorder(KHAKI, 1, true));
        formCard.setBounds(10, 8, 330, 508); // <-- diperbesar dari 430 ke 508
        content.add(formCard);

        JLabel formTitle = new JLabel("Data Jadwal");
        formTitle.setFont(new Font("Georgia", Font.BOLD, 14));
        formTitle.setForeground(ESPRESSO);
        formTitle.setBounds(12, 10, 306, 20);
        formCard.add(formTitle);

        JSeparator sep = new JSeparator();
        sep.setForeground(KHAKI);
        sep.setBounds(12, 32, 306, 2);
        formCard.add(sep);

        // Fields
        kodeJadwal       = addField(formCard, "Kode Jadwal",       10,  40, 306);
        kodeMaskapai     = addCombo(formCard, "Kode Maskapai",     10, 100, 306);
        kotaAwal         = addCombo(formCard, "Kota Asal",         10, 160, 145);
        kotaTujuan       = addCombo(formCard, "Kota Tujuan",      165, 160, 145);
        jamKeberangkatan = addField(formCard, "Jam Keberangkatan", 10, 220, 145);
        jamKedatangan    = addField(formCard, "Jam Kedatangan",   165, 220, 145);
        harga            = addField(formCard, "Harga",             10, 280, 145);
        kursiTersedia    = addField(formCard, "Kursi Tersedia",   165, 280, 145);
        kursiDiambil     = addField(formCard, "Kursi Diambil",    10, 340, 145);
        statusKuota      = addField(formCard, "Status Kuota",     165, 340, 145);

        // Buttons CRUD
        tambahBtn = primaryButton("Tambah");
        tambahBtn.setBounds(10, 400, 148, 32);
        formCard.add(tambahBtn);

        ubahBtn = primaryButton("Ubah");
        ubahBtn.setBounds(168, 400, 148, 32);
        formCard.add(ubahBtn);

        hapusBtn = dangerButton("Hapus");
        hapusBtn.setBounds(10, 440, 148, 32);
        formCard.add(hapusBtn);

        cleatBtn = secondaryButton("Clear");
        cleatBtn.setBounds(168, 440, 148, 32);
        formCard.add(cleatBtn);

        // Tombol Kembali -- sekarang muat karena formCard lebih tinggi
        kembaliBtn = secondaryButton("⬅  Kembali ke Menu Admin");
        kembaliBtn.setBounds(10, 482, 306, 16); // y=482, masih dalam formCard 508
        formCard.add(kembaliBtn);

        // ── Table ──────────────────────────────────────────────────
        jadwalTabel = new JTable();
        styleTable(jadwalTabel);
        JScrollPane scroll = new JScrollPane(jadwalTabel);
        styleScrollPane(scroll);
        scroll.setBounds(352, 8, 554, 504); // sesuaikan dengan tinggi baru
        content.add(scroll);

        root.add(content, BorderLayout.CENTER);

        // ── Events ─────────────────────────────────────────────────
        tambahBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                jc.addData(
                    kodeJadwal.getText(),
                    String.valueOf(kodeMaskapai.getSelectedItem()),
                    String.valueOf(kotaAwal.getSelectedItem()),
                    String.valueOf(kotaTujuan.getSelectedItem()),
                    jamKeberangkatan.getText(), jamKedatangan.getText(),
                    harga.getText(), kursiTersedia.getText(), kursiDiambil.getText()
                );
            }
        });

        ubahBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                jc.updateData(
                    jadwalTabel.getSelectedRow(),
                    kodeJadwal.getText(),
                    String.valueOf(kodeMaskapai.getSelectedItem()),
                    String.valueOf(kotaAwal.getSelectedItem()),
                    String.valueOf(kotaTujuan.getSelectedItem()),
                    jamKeberangkatan.getText(), jamKedatangan.getText(),
                    harga.getText(), kursiTersedia.getText(), kursiDiambil.getText()
                );
                kodeJadwal.setEnabled(false);
            }
        });

        hapusBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                jc.hapusData(jadwalTabel.getSelectedRow());
            }
        });

        cleatBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                kodeJadwal.setText("");
                harga.setText("");
                jamKeberangkatan.setText("");
                jamKedatangan.setText("");
                kodeJadwal.setEnabled(true);
            }
        });

        // Event kembali ke admin
        kembaliBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                jc.keAdmin();
            }
        });

        jadwalTabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = jadwalTabel.getSelectedRow();
                kodeJadwal.setText(jadwalTabel.getValueAt(row, 0).toString());
                kodeJadwal.setEnabled(false);

                String b = jadwalTabel.getValueAt(row, 1).toString();
                for (int i = 0; i < kodeMaskapai.getItemCount(); i++) {
                    String z = kodeMaskapai.getItemAt(i);
                    if (z.substring(0, 4).equals(b)) { kodeMaskapai.setSelectedIndex(i); break; }
                }

                String c = jadwalTabel.getValueAt(row, 2).toString();
                for (int i = 0; i < kotaAwal.getItemCount(); i++) {
                    String z = kotaAwal.getItemAt(i);
                    if (z != null && z.length() >= 4 && z.substring(0, 4).equals(c)) { kotaAwal.setSelectedIndex(i); break; }
                }

                String d = jadwalTabel.getValueAt(row, 3).toString();
                for (int i = 0; i < kotaTujuan.getItemCount(); i++) {
                    String z = kotaTujuan.getItemAt(i);
                    if (z != null && z.length() >= 4 && z.substring(0, 4).equals(d)) { kotaTujuan.setSelectedIndex(i); break; }
                }

                jamKeberangkatan.setText(jadwalTabel.getValueAt(row, 4).toString());
                jamKedatangan.setText(jadwalTabel.getValueAt(row, 5).toString());
                harga.setText(jadwalTabel.getValueAt(row, 6).toString());
                kursiTersedia.setText(jadwalTabel.getValueAt(row, 7).toString());
                kursiDiambil.setText(jadwalTabel.getValueAt(row, 8).toString());
                statusKuota.setText(jadwalTabel.getValueAt(row, 9).toString());
                statusKuota.setEnabled(false);
            }
        });

        setContentPane(root);
        pack();
        setLocationRelativeTo(null);
    }

    // ── Helpers ────────────────────────────────────────────────────
    private JTextField addField(JPanel p, String label, int x, int y, int w) {
        JLabel lbl = new JLabel(label);
        styleLabel(lbl);
        lbl.setBounds(x, y, w, 16);
        p.add(lbl);
        JTextField tf = new JTextField();
        styleInputField(tf);
        tf.setBounds(x, y + 18, w, 28);
        p.add(tf);
        return tf;
    }

    private JComboBox<String> addCombo(JPanel p, String label, int x, int y, int w) {
        JLabel lbl = new JLabel(label);
        styleLabel(lbl);
        lbl.setBounds(x, y, w, 16);
        p.add(lbl);
        JComboBox<String> cb = new JComboBox<>();
        cb.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        cb.setBackground(LINEN);
        cb.setForeground(ESPRESSO);
        cb.setBounds(x, y + 18, w, 28);
        p.add(cb);
        return cb;
    }

    public JTable getJadwalTabel() { return jadwalTabel; }

    private void loadDataPesawat() {
        java.util.List<JadwalModel> data = jc.loadDataPesawat();
        for (JadwalModel m : data)
            kodeMaskapai.addItem(m.getKodePesawat() + " - " + m.getPesawat());
    }

    private void loadDataKota() {
        java.util.List<JadwalModel> data = jc.loadDataKota();
        for (JadwalModel m : data) {
            kotaAwal.addItem(m.getKodeKota() + " - " + m.getKota());
            kotaTujuan.addItem(m.getKodeKota() + " - " + m.getKota());
        }
    }
}