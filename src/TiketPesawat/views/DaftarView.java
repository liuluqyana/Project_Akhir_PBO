package TiketPesawat.views;

import TiketPesawat.controller.DaftarController;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.*;

public class DaftarView extends JFrame {

    private static final Color LINEN    = new Color(0xF5, 0xF1, 0xEA);
    private static final Color KHAKI    = new Color(0xD7, 0xC9, 0xB8);
    private static final Color CAMEL    = new Color(0xB2, 0x96, 0x7D);
    private static final Color COCOA    = new Color(0x7D, 0x5A, 0x44);
    private static final Color ESPRESSO = new Color(0x4A, 0x34, 0x2A);

    DaftarController dc;
    private JTextField     nama, email, username;
    private JPasswordField password;
    private JButton        daftarBtn, kembaliBtn;

    public DaftarView(DaftarController c) {
        this.dc = c;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tiket Pesawat - Daftar Akun");
        setResizable(false);

        JPanel root = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setPaint(new GradientPaint(0, 0, LINEN, 0, getHeight(), KHAKI));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        root.setPreferredSize(new Dimension(420, 580));

        JPanel topBar = new JPanel();
        topBar.setBackground(ESPRESSO);
        topBar.setBounds(0, 0, 420, 6);
        root.add(topBar);

        JPanel card = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 210));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20));
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBounds(40, 50, 340, 480);
        root.add(card);

        JLabel iconLabel = new JLabel("👤", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        iconLabel.setForeground(COCOA);
        iconLabel.setBounds(0, 16, 340, 46);
        card.add(iconLabel);

        JLabel title = new JLabel("Buat Akun Baru", SwingConstants.CENTER);
        title.setFont(new Font("Georgia", Font.BOLD, 20));
        title.setForeground(ESPRESSO);
        title.setBounds(0, 64, 340, 28);
        card.add(title);

        JLabel subtitle = new JLabel("Isi data diri Anda di bawah ini", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        subtitle.setForeground(CAMEL);
        subtitle.setBounds(0, 90, 340, 18);
        card.add(subtitle);

        int y = 120;
        nama     = addField(card, "Nama Lengkap", y); y += 66;
        email    = addField(card, "Email",         y); y += 66;
        username = addField(card, "Username",       y); y += 66;

        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        passLabel.setForeground(ESPRESSO);
        passLabel.setBounds(40, y, 260, 18);
        card.add(passLabel);
        password = new JPasswordField();
        styleField(password, y + 20);
        card.add(password);
        y += 66;

        daftarBtn = createPrimaryButton("Daftar Sekarang");
        daftarBtn.setBounds(40, y, 260, 40);
        daftarBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dc.daftarUser(nama.getText(), email.getText(), username.getText(), password.getText());
            }
        });
        card.add(daftarBtn);

        kembaliBtn = createSecondaryButton("Sudah punya akun? Masuk");
        kembaliBtn.setBounds(40, y + 48, 260, 36);
        kembaliBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { dc.keMasuk(); }
        });
        card.add(kembaliBtn);

        setContentPane(root);
        pack();
        setLocationRelativeTo(null);
    }

    private JTextField addField(JPanel card, String labelText, int y) {
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lbl.setForeground(ESPRESSO);
        lbl.setBounds(40, y, 260, 18);
        card.add(lbl);
        JTextField tf = new JTextField();
        styleField(tf, y + 20);
        card.add(tf);
        return tf;
    }

    private void styleField(JComponent tf, int y) {
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tf.setBackground(LINEN);
        tf.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(KHAKI, 1, true),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        tf.setBounds(40, y, 260, 36);
        if (tf instanceof JTextField) ((JTextField) tf).setForeground(ESPRESSO);
        if (tf instanceof JPasswordField) ((JPasswordField) tf).setForeground(ESPRESSO);
    }

    private JButton createPrimaryButton(String text) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isPressed() ? ESPRESSO : getModel().isRollover() ? CAMEL : COCOA);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton createSecondaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setForeground(COCOA);
        btn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(CAMEL, 1, true),
            BorderFactory.createEmptyBorder(4, 10, 4, 10)));
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
