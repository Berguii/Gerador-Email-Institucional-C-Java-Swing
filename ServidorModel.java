import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServidorModel {
    // Lista protegida contra acessos simultâneos (Race Conditions)
    private final List<Pessoa> listaPessoas = Collections.synchronizedList(new ArrayList<>());

    public Pessoa processarCadastro(Pessoa pessoaRecebida) {
        synchronized (listaPessoas) {
            // 1. Gera o e-mail pela regra de negócio
            String emailGerado = gerarEmail(pessoaRecebida.getNome(), pessoaRecebida.getDataNascimento());
            pessoaRecebida.setEmail(emailGerado);

            // 2. Verifica se já existe na lista (mesmo nome e data de nascimento)
            for (Pessoa p : listaPessoas) {
                if (p.getNome().equalsIgnoreCase(pessoaRecebida.getNome()) && 
                    p.getDataNascimento().equals(pessoaRecebida.getDataNascimento())) {
                    return p; // Retorna o registro já existente
                }
            }

            // 3. Se não for duplicado, salva na lista
            listaPessoas.add(pessoaRecebida);
            return pessoaRecebida;
        }
    }

    private String gerarEmail(String nomeCompleto, String dataNascimento) {
        String[] partesNome = nomeCompleto.trim().toLowerCase().split("\\s+");
        String primeiroNome = partesNome[0];
        String ultimoSobrenome = partesNome[partesNome.length - 1];

        String[] partesData = dataNascimento.trim().split("/");
        String ano = partesData[2];

        return primeiroNome + "." + ultimoSobrenome + "." + ano + "@ufn.edu.br";
    }

    public List<Pessoa> getListaPessoas() {
        synchronized (listaPessoas) {
            return new ArrayList<>(listaPessoas);
        }
    }
}