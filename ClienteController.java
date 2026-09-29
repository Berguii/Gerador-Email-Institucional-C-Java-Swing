import java.io.*;
import java.net.Socket;

public class ClienteController {
    private ClienteView view;
    private final String HOST = "localhost";
    private final int PORTA = 12345;

    public ClienteController(ClienteView view) {
        this.view = view;
        
        // Associa a ação do clique no botão "Enviar"
        this.view.getBtnEnviar().addActionListener(e -> enviarCadastro());
    }

    private void enviarCadastro() {
        String nome = view.getNomeEntrada();
        String data = view.getDataEntrada();

        // Validação simples dos campos
        if (nome.isEmpty() || data.isEmpty()) {
            view.exibirMensagem("Por favor, preencha todos os campos antes de enviar.");
            return;
        }

        // Garanta formato básico de data antes de enviar
        if (!data.matches("\\d{2}/\\d{2}/\\d{4}")) {
            view.exibirMensagem("Formato de data inválido! Use dd/MM/yyyy (ex: 15/05/2002).");
            return;
        }

        Pessoa novaPessoa = new Pessoa(nome, data);

        // Operação de rede rodando em Thread paralela (não trava o Swing)
        new Thread(() -> {
            try (
                Socket socket = new Socket(HOST, PORTA);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
            ) {
                // 1. Envia o objeto Pessoa para o servidor
                out.writeObject(novaPessoa);
                out.flush();

                // 2. Recebe o objeto Pessoa processado de volta
                Pessoa pessoaRetornada = (Pessoa) in.readObject();

                // 3. Preenche os campos bloqueados na tela
                view.setDadosRetorno(
                    pessoaRetornada.getNome(),
                    pessoaRetornada.getEmail(),
                    pessoaRetornada.getDataNascimento()
                );

            } catch (Exception ex) {
                view.exibirMensagem("Erro de Conexão: O servidor está offline ou inacessível.");
            }
        }).start();
    }
}
