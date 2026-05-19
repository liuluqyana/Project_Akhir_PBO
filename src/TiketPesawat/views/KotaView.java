package TiketPesawat.views;

import TiketPesawat.controller.KotaController;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import static TiketPesawat.views.ThemeHelper.*;

public class KotaView extends JFrame {

    KotaController kc;

    private JTextField kodeKota, kota;
    private JTable     kotaTabel;
    private JButton    tambahBtn, ubahBtn, hapusBtn, clearBtn, kembaliBtn;

    public KotaView(KotaController c) {
        this.kc = c;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Kelola Kota");
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(LINEN);

        JPanel header = createHeaderPanel("🏙  Halaman Kota", 580);
        root.add(header, BorderLayout.NORTH);

        JPanel content = new JPanel(null);
        content.setBackground(LINEN);
        content.setPreferredSize(new Dimension(580, 380));

        JPanel formCard = new JPanel(null);
        formCard.setBackground(new Color(255, 255, 255, 200));
        formCard.setBorder(new CompoundBorder(
            new LineBorder(KHAKI, 1, true),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        formCard.setBounds(16, 12, 190, 130);
        content.add(formCard);

        JLabel titleForm = new JLabel("Data Kota");
        styleLabel(titleForm);
        titleForm.setFont(new Font("Georgia", Font.BOLD, 14));
        titleForm.setForeground(ESPRESSO);
        titleForm.setBounds(10, 6, 170, 22);
        formCard.add(titleForm);

        JLabel kodeLabel = new JLabel("Kode Kota");
        styleLabel(kodeLabel);
        kodeLabel.setBounds(10, 34, 170, 16);
        formCard.add(kodeLabel);

        kodeKota = new JTextField();
        styleInputField(kodeKota);
        kodeKota.setBounds(10, 52, 170, 28);
        formCard.add(kodeKota);

        JLabel kotaLabel = new JLabel("Nama Kota");
        styleLabel(kotaLabel);
        kotaLabel.setBounds(10, 86, 170, 16);
        formCard.add(kotaLabel);

        kota = new JTextField();
        styleInputField(kota);
        kota.setBounds(10, 104, 170, 28);
        formCard.add(kota);

        tambahBtn = primaryButton("Tambah");   tambahBtn.setBounds(16, 152, 90, 32);   content.add(tambahBtn);
        ubahBtn   = primaryButton("Ubah");     ubahBtn.setBounds(114, 152, 90, 32);    content.add(ubahBtn);
        hapusBtn  = dangerButton("Hapus");     hapusBtn.setBounds(16, 192, 90, 32);    content.add(hapusBtn);
        clearBtn  = secondaryButton("Clear");  clearBtn.setBounds(114, 192, 90, 32);   content.add(clearBtn);
        kembaliBtn= secondaryButton("⬅ Kembali"); kembaliBtn.setBounds(16, 340, 190, 32); content.add(kembaliBtn);

        kotaTabel = new JTable();
        styleTable(kotaTabel);
        JScrollPane scroll = new JScrollPane(kotaTabel);
        styleScrollPane(scroll);
        scroll.setBounds(222, 12, 342, 356);
        content.add(scroll);

        root.add(content, BorderLayout.CENTER);

        tambahBtn.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseClicked(java.awt.event.MouseEvent e) {
        kc.addData(kodeKota.getText(), kota.getText());
    }
});
ubahBtn.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseClicked(java.awt.event.MouseEvent e) {
        int row = kotaTabel.getSelectedRow();
        kc.updateData(row, kodeKota.getText(), kota.getText());
    }
});
hapusBtn.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseClicked(java.awt.event.MouseEvent e) {
        kc.hapusData(kotaTabel.getSelectedRow());
    }
});
clearBtn.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseClicked(java.awt.event.MouseEvent e) {
        kodeKota.setText("");
        kota.setText("");
    }
});
kembaliBtn.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseClicked(java.awt.event.MouseEvent e) {
        kc.keAdmin();
    }
});
kotaTabel.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseClicked(java.awt.event.MouseEvent e) {
        int row = kotaTabel.getSelectedRow();
        if (row != -1) {
            kodeKota.setText(kotaTabel.getValueAt(row, 0).toString());
            kota.setText(kotaTabel.getValueAt(row, 1).toString());
        }
    }
});

        setContentPane(root);
        pack();
        setLocationRelativeTo(null);
    }

    public JTextField getKodeKota() { return kodeKota; }
    public JTextField getKota()     { return kota; }
    public JTable     getKotaTabel(){ return kotaTabel; }
    public void setKode(String v)   { kodeKota.setText(v); }
    public void setNama(String v)   { kota.setText(v); }
    public void clearFields()       { kodeKota.setText(""); kota.setText(""); }
}
