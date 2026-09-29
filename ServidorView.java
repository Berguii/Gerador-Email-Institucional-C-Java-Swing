import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ServidorView extends JFrame {
    private JTextArea areaLog;
    private DefaultListModel<String> modeloListaVisual;
    private JList<String> listaGrafica;

    public ServidorView() {
        setTitle("Servidor Acadêmico - Painel de Controle");
        setSize(650, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2, 10, 10));

        // Painel Esquerdo: Logs
        areaLog = new JTextArea();
        areaLog.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(areaLog);
        scrollLog.setBorder(BorderFactory.createTitledBorder("Logs do Servidor (Rede)"));

        // Painel Direito: Lista de Alunos
        modeloListaVisual = new DefaultListModel<>();
        listaGrafica = new JList<>(modeloListaVisual);
        JScrollPane scrollLista = new JScrollPane(listaGrafica);
        scrollLista.setBorder(BorderFactory.createTitledBorder("Alunos Cadastrados"));

        add(scrollLog);
        add(scrollLista);
    }

    public void adicionarLog(String mensagem) {
        SwingUtilities.invokeLater(() -> {
            areaLog.append(mensagem + "\n");
            areaLog.setCaretPosition(areaLog.getDocument().getLength());
        });
    }

    public void atualizarLista(List<Pessoa> lista) {
        SwingUtilities.invokeLater(() -> {
            modeloListaVisual.clear();
            for (Pessoa p : lista) {
                modeloListaVisual.addElement(p.getNome() + " | " + p.getEmail());
            }
        });
    }
}