package TiketPesawat.views;

import TiketPesawat.controller.MasukController;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.*;

public class MasukView extends JFrame {

    // ── Color Palette ──────────────────────────────────────────────
    private static final Color LINEN    = new Color(0xF5, 0xF1, 0xEA);
    private static final Color KHAKI    = new Color(0xD7, 0xC9, 0xB8);
    private static final Color CAMEL    = new Color(0xB2, 0x96, 0x7D);
    private static final Color COCOA    = new Color(0x7D, 0x5A, 0x44);
    private static final Color ESPRESSO = new Color(0x4A, 0x34, 0x2A);

    MasukController mc;
    private JTextField     username;
    private JPasswordField password;
    private JButton        masukBtn, daftarBtn, keluarBtn;

    public MasukView(MasukController c) {
        this.mc = c;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tiket Pesawat - Masuk");
        setResizable(false);

        JPanel root = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, LINEN, 0, getHeight(), KHAKI));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        root.setPreferredSize(new Dimension(420, 520));

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
        card.setBounds(40, 60, 340, 400);
        root.add(card);

        JLabel iconLabel = new JLabel("✈", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 42));
        iconLabel.setForeground(COCOA);
        iconLabel.setBounds(0, 20, 340, 50);
        card.add(iconLabel);

        JLabel title = new JLabel("Selamat Datang", SwingConstants.CENTER);
        title.setFont(new Font("Georgia", Font.BOLD, 22));
        title.setForeground(ESPRESSO);
        title.setBounds(0, 72, 340, 30);
        card.add(title);

        JLabel subtitle = new JLabel("Tiket Pesawat Indonesia", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(CAMEL);
        subtitle.setBounds(0, 100, 340, 20);
        card.add(subtitle);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        usernameLabel.setForeground(ESPRESSO);
        usernameLabel.setBounds(40, 140, 260, 18);
        card.add(usernameLabel);

        username = new JTextField();
        username.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        username.setForeground(ESPRESSO);
        username.setBackground(LINEN);
        username.setCaretColor(COCOA);
        username.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(KHAKI, 1, true),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        username.setBounds(40, 160, 260, 36);
        card.add(username);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        passwordLabel.setForeground(ESPRESSO);
        passwordLabel.setBounds(40, 208, 260, 18);
        card.add(passwordLabel);

        password = new JPasswordField();
        password.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        password.setForeground(ESPRESSO);
        password.setBackground(LINEN);
        password.setCaretColor(COCOA);
        password.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(KHAKI, 1, true),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        password.setBounds(40, 228, 260, 36);
        card.add(password);

        masukBtn = createPrimaryButton("Masuk");
        masukBtn.setBounds(40, 284, 260, 40);
        masukBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { mc.cekLogin(username.getText(), password.getText()); }
        });
        card.add(masukBtn);

        daftarBtn = createSecondaryButton("Belum punya akun? Daftar");
        daftarBtn.setBounds(40, 332, 260, 36);
        daftarBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { mc.keDaftar(); }
        });
        card.add(daftarBtn);

        keluarBtn = new JButton("✕");
        keluarBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        keluarBtn.setForeground(CAMEL);
        keluarBtn.setContentAreaFilled(false);
        keluarBtn.setBorderPainted(false);
        keluarBtn.setFocusPainted(false);
        keluarBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        keluarBtn.setBounds(370, 14, 36, 28);
        keluarBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { mc.hilangPage(); }
        });
        root.add(keluarBtn);

        JLabel footer = new JLabel("© 2025 Tiket Pesawat", SwingConstants.CENTER);
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        footer.setForeground(COCOA);
        footer.setBounds(0, 488, 420, 20);
        root.add(footer);

        setContentPane(root);
        pack();
        setLocationRelativeTo(null);
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
