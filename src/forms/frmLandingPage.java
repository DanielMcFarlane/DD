package forms;

import javax.swing.*;

public class frmLandingPage extends JFrame {
    private JButton btnRegister;
    private JButton btnLogin;
    private JPanel pnlContent;

    public frmLandingPage(){
        super("Dine Dash");
        setContentPane(pnlContent);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 800);
        setLocationRelativeTo(null);
        setVisible(true);

        btnRegister.addActionListener(_ -> {
            new frmRegister();
            dispose();
        });

        btnLogin.addActionListener(_ -> {
            new frmLogin();
            dispose();
        });
    }
}