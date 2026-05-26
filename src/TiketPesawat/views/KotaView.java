package TiketPesawat.views;

import TiketPesawat.controller.KotaController;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
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

        // ── Form Card ──────────────────────────────────────────────
        JPanel formCard = new JPanel(null);
        formCard.setBackground(new Color(255, 255, 255, 200));
        formCard.setBorder(new CompoundBorder(
            new LineBorder(KHAKI, 1, true),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        formCard.setBounds(16, 12, 190, 140);
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
        kotaLabel.setBounds(10, 88, 170, 16);
        formCard.add(kotaLabel);

        kota = new JTextField();
        styleInputField(kota);
        kota.setBounds(10, 106, 170, 28);
        formCard.add(kota);

        // ── Buttons ────────────────────────────────────────────────
        tambahBtn  = primaryButton("Tambah");
        tambahBtn.setBounds(16, 162, 90, 32);
        content.add(tambahBtn);

        ubahBtn    = primaryButton("Ubah");
        ubahBtn.setBounds(114, 162, 90, 32);
        content.add(ubahBtn);

        hapusBtn   = dangerButton("Hapus");
        hapusBtn.setBounds(16, 202, 90, 32);
        content.add(hapusBtn);

        clearBtn   = secondaryButton("Clear");
        clearBtn.setBounds(114, 202, 90, 32);
        content.add(clearBtn);

        kembaliBtn = secondaryButton("⬅ Kembali");
        kembaliBtn.setBounds(16, 340, 190, 32);
        content.add(kembaliBtn);

        // ── Table ──────────────────────────────────────────────────
        kotaTabel = new JTable();
        styleTable(kotaTabel);

        // Custom cell renderer: tambah padding 8px kiri-kanan agar teks tidak terpotong
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                if (isSelected) {
                    setBackground(CAMEL);
                    setForeground(Color.WHITE);
                } else {
                    setBackground(row % 2 == 0 ? Color.WHITE : new Color(0xFAF7F4));
                    setForeground(ESPRESSO);
                }
                return this;
            }
        };
        kotaTabel.setDefaultRenderer(Object.class, cellRenderer);

        JScrollPane scroll = new JScrollPane(kotaTabel);
        styleScrollPane(scroll);
        scroll.setBounds(222, 12, 342, 356);
        content.add(scroll);

        root.add(content, BorderLayout.CENTER);

        // ── Events ─────────────────────────────────────────────────
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
                kotaTabel.clearSelection();
                kodeKota.setEnabled(true);
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
                    kodeKota.setEnabled(false);
                }
            }
        });

        setContentPane(root);
        pack();
        setLocationRelativeTo(null);
    }

    /** Dipanggil dari controller setelah setModel() */
    public void setupColumnWidths() {
        if (kotaTabel.getColumnCount() >= 2) {
            // Kode: lebar tetap 80px
            TableColumn kodeCol = kotaTabel.getColumnModel().getColumn(0);
            kodeCol.setMinWidth(80);
            kodeCol.setMaxWidth(80);
            kodeCol.setPreferredWidth(80);
            // Kota: isi sisa ruang (~262px)
            TableColumn kotaCol = kotaTabel.getColumnModel().getColumn(1);
            kotaCol.setPreferredWidth(250);
        }
    }

    public JTextField getKodeKota() { return kodeKota; }
    public JTextField getKota()     { return kota; }
    public JTable     getKotaTabel(){ return kotaTabel; }
    public void setKode(String v)   { kodeKota.setText(v); }
    public void setNama(String v)   { kota.setText(v); }
    public void clearFields()       { kodeKota.setText(""); kota.setText(""); }
}