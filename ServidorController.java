import java.io.*;
import java.net.*;

public class ServidorController {
    private ServidorView view;
    private ServidorModel model;

    public ServidorController(ServidorView view, ServidorModel model) {
        this.view = view;
        this.model = model;
    }

    public void iniciarServidor(int porta) {
        // Thread separada para a escuta de rede não travar a interface visual
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(porta)) {
                view.adicionarLog("Servidor TCP rodando na porta " + porta);

                while (true) {
                    Socket clienteSocket = serverSocket.accept();
                    view.adicionarLog("Novo cliente conectado: " + clienteSocket.getInetAddress().getHostAddress());
                    
                    // Dispara uma nova Thread para atender o cliente em paralelo
                    new Thread(new TrataCliente(clienteSocket)).start();
                }
            } catch (IOException e) {
                view.adicionarLog("Erro no servidor: " + e.getMessage());
            }
        }).start();
    }

    // Tarefa executada individualmente para cada cliente
    private class TrataCliente implements Runnable {
        private Socket socket;

        public TrataCliente(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())
            ) {
                // 1. Recebe o objeto Pessoa do cliente
                Pessoa pessoaRecebida = (Pessoa) in.readObject();
                view.adicionarLog("Dados recebidos: " + pessoaRecebida.getNome());

                // 2. Processa e-mail e duplicatas no Model
                Pessoa pessoaProcessada = model.processarCadastro(pessoaRecebida);

                // 3. Atualiza os Logs e a Lista na Tela
                view.adicionarLog("Cadastro finalizado: " + pessoaProcessada.getEmail());
                view.atualizarLista(model.getListaPessoas());

                // 4. Envia o objeto atualizado de volta ao cliente
                out.writeObject(pessoaProcessada);
                out.flush();

            } catch (Exception e) {
                view.adicionarLog("Conexão com cliente encerrada.");
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}