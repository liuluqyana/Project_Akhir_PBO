package TiketPesawat.views;

import TiketPesawat.controller.PesawatController;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import static TiketPesawat.views.ThemeHelper.*;

public class PesawatView extends JFrame {

    PesawatController pc;

    private JTextField kodePesawat, pesawat;
    private JTable     pesawatTabel;
    private JButton    tambahBtn, ubahBtn, hapusBtn, clearBtn, kembaliBtn;

    public PesawatView(PesawatController c) {
        this.pc = c;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Kelola Pesawat");
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(LINEN);

        JPanel header = createHeaderPanel("✈  Halaman Pesawat", 620);
        root.add(header, BorderLayout.NORTH);

        JPanel content = new JPanel(null);
        content.setBackground(LINEN);
        content.setPreferredSize(new Dimension(620, 380));

        // Form card
        JPanel formCard = new JPanel(null);
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(new LineBorder(KHAKI, 1, true));
        formCard.setBounds(16, 12, 210, 150);
        content.add(formCard);

        JLabel titleForm = new JLabel("Data Pesawat");
        titleForm.setFont(new Font("Georgia", Font.BOLD, 14));
        titleForm.setForeground(ESPRESSO);
        titleForm.setBounds(10, 8, 190, 22);
        formCard.add(titleForm);

        JSeparator sep = new JSeparator();
        sep.setForeground(KHAKI);
        sep.setBounds(10, 30, 190, 2);
        formCard.add(sep);

        JLabel kodeLabel = new JLabel("Kode Pesawat");
        styleLabel(kodeLabel);
        kodeLabel.setBounds(10, 38, 190, 16);
        formCard.add(kodeLabel);

        kodePesawat = new JTextField();
        styleInputField(kodePesawat);
        kodePesawat.setBounds(10, 56, 190, 28);
        formCard.add(kodePesawat);

        JLabel maskapaiLabel = new JLabel("Nama Maskapai");
        styleLabel(maskapaiLabel);
        maskapaiLabel.setBounds(10, 90, 190, 16);
        formCard.add(maskapaiLabel);

        pesawat = new JTextField();
        styleInputField(pesawat);
        pesawat.setBounds(10, 108, 190, 28);
        formCard.add(pesawat);

        // Buttons
        tambahBtn = primaryButton("Tambah");
        tambahBtn.setBounds(16, 172, 100, 32);
        content.add(tambahBtn);

        ubahBtn = primaryButton("Ubah");
        ubahBtn.setBounds(124, 172, 100, 32);
        content.add(ubahBtn);

        hapusBtn = dangerButton("Hapus");
        hapusBtn.setBounds(16, 212, 100, 32);
        content.add(hapusBtn);

        clearBtn = secondaryButton("Clear");
        clearBtn.setBounds(124, 212, 100, 32);
        content.add(clearBtn);

        kembaliBtn = secondaryButton("⬅ Kembali");
        kembaliBtn.setBounds(16, 340, 210, 32);
        content.add(kembaliBtn);

        // Table
        pesawatTabel = new JTable();
        styleTable(pesawatTabel);
        JScrollPane scroll = new JScrollPane(pesawatTabel);
        styleScrollPane(scroll);
        scroll.setBounds(240, 12, 366, 356);
        content.add(scroll);

        root.add(content, BorderLayout.CENTER);

        // ── Events (nama method disesuaikan dengan PesawatController) ──

        tambahBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // addData(String kode, String pesawat)
                pc.addData(kodePesawat.getText(), pesawat.getText());
            }
        });

        ubahBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = pesawatTabel.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null,
                        "Pilih baris di tabel terlebih dahulu!", 
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // updateData(int row, String kode, String pesawat)
                pc.updateData(row, kodePesawat.getText(), pesawat.getText());
            }
        });

        hapusBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = pesawatTabel.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null,
                        "Pilih baris di tabel terlebih dahulu!", 
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // hapusData(int row)
                pc.hapusData(row);
            }
        });

        clearBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // clear field manual, tidak ada clearForm() di controller
                kodePesawat.setText("");
                pesawat.setText("");
                pesawatTabel.clearSelection();
            }
        });

        kembaliBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                pc.keAdmin();
            }
        });

        pesawatTabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = pesawatTabel.getSelectedRow();
                if (row != -1) {
                    // isi field dari data tabel yang diklik
                    kodePesawat.setText(pesawatTabel.getValueAt(row, 0).toString());
                    pesawat.setText(pesawatTabel.getValueAt(row, 1).toString());
                }
            }
        });

        setContentPane(root);
        pack();
        setLocationRelativeTo(null);
    }

    public JTextField getKodePesawat() { return kodePesawat; }
    public JTextField getPesawat()     { return pesawat; }
    public JTable     getPesawatTabel(){ return pesawatTabel; }
    public void setKode(String v)      { kodePesawat.setText(v); }
    public void setNama(String v)      { pesawat.setText(v); }
    public void clearFields()          { kodePesawat.setText(""); pesawat.setText(""); }
}