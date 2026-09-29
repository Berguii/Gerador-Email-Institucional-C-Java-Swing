import javax.swing.*;
import java.awt.*;

public class ClienteView extends JFrame {
    // Entradas
    private JTextField txtNomeEntrada;
    private JTextField txtDataEntrada;
    private JButton btnEnviar;

    // Saídas (Bloqueadas para edição)
    private JTextField txtNomeSaida;
    private JTextField txtEmailSaida;
    private JTextField txtDataSaida;

    public ClienteView() {
        setTitle("Sistema Acadêmico - Cadastro de Aluno");
        setSize(420, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelMain = new JPanel(new GridLayout(13, 1, 5, 5));
        panelMain.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 1. Campos de Entrada
        panelMain.add(new JLabel("Nome Completo:"));
        txtNomeEntrada = new JTextField();
        panelMain.add(txtNomeEntrada);

        panelMain.add(new JLabel("Data de Nascimento (dd/MM/yyyy):"));
        txtDataEntrada = new JTextField();
        panelMain.add(txtDataEntrada);

        // 2. Botão
        btnEnviar = new JButton("Enviar Cadastro");
        panelMain.add(btnEnviar);

        panelMain.add(new JSeparator());

        // 3. Campos de Retorno Bloqueados
        panelMain.add(new JLabel("Nome Confirmado pelo Servidor:"));
        txtNomeSaida = new JTextField();
        txtNomeSaida.setEditable(false);
        panelMain.add(txtNomeSaida);

        panelMain.add(new JLabel("E-mail Gerado (@ufn.edu.br):"));
        txtEmailSaida = new JTextField();
        txtEmailSaida.setEditable(false);
        panelMain.add(txtEmailSaida);

        panelMain.add(new JLabel("Data de Nascimento Confirmada:"));
        txtDataSaida = new JTextField();
        txtDataSaida.setEditable(false);
        panelMain.add(txtDataSaida);

        add(panelMain);
    }

    // Getters dos elementos de entrada
    public String getNomeEntrada() { return txtNomeEntrada.getText().trim(); }
    public String getDataEntrada() { return txtDataEntrada.getText().trim(); }
    public JButton getBtnEnviar() { return btnEnviar; }

    // Atualização thread-safe dos campos bloqueados
    public void setDadosRetorno(String nome, String email, String data) {
        SwingUtilities.invokeLater(() -> {
            txtNomeSaida.setText(nome);
            txtEmailSaida.setText(email);
            txtDataSaida.setText(data);
        });
    }

    public void exibirMensagem(String mensagem) {
        SwingUtilities.invokeLater(() -> 
            JOptionPane.showMessageDialog(this, mensagem)
        );
    }
}