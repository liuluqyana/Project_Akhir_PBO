package TiketPesawat.views;

import TiketPesawat.controller.PemesananController;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import static TiketPesawat.views.ThemeHelper.*;

public class PemesananView extends JFrame {

    PemesananController pc;
    private JTable  tabelPemesanan;
    private JButton refreshBtn, kembaliBtn;

    public PemesananView(PemesananController c) {
        this.pc = c;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Data Pemesanan Tiket");
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(LINEN);

        JPanel header = createHeaderPanel("📋  Daftar Pemesanan Tiket", 800);
        root.add(header, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(0, 12));
        content.setBackground(LINEN);
        content.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        tabelPemesanan = new JTable();
        tabelPemesanan.setAutoCreateRowSorter(true);
        styleTable(tabelPemesanan);
        JScrollPane scroll = new JScrollPane(tabelPemesanan);
        styleScrollPane(scroll);
        content.add(scroll, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnPanel.setBackground(LINEN);

        refreshBtn  = primaryButton("🔄 Refresh");
        kembaliBtn  = dangerButton("⬅ Kembali");

        refreshBtn.addActionListener(e -> pc.refreshTable());
        kembaliBtn.addActionListener(e -> pc.keAdmin());

        btnPanel.add(refreshBtn);
        btnPanel.add(kembaliBtn);
        content.add(btnPanel, BorderLayout.SOUTH);

        root.add(content, BorderLayout.CENTER);

        setContentPane(root);
        setSize(800, 480);
        setMinimumSize(new Dimension(800, 450));
        setLocationRelativeTo(null);
    }

    public JTable getTabelPemesanan() { return tabelPemesanan; }
}
