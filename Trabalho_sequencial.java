import java.util.Random;


class Eleitor {
    private Urna urna_eletronica;
    int total_votos;
    Random random = new Random();

    // Construtor
    public Eleitor(int _total_votos, Urna urna) {
        total_votos = _total_votos;
        this.urna_eletronica = urna;
    }

    /*
     * votacao() simula uma votacao entre os candidatos, 
     * em que cada voto é aleatoriamente atribuido a um candidato.
    */
    public void votacao() {
        for (int i = 0; i < total_votos; i++) {
            int voto = random.nextInt(3);
            if (voto == 2) {
                urna_eletronica.votaKelmon();
            } else if (voto == 1) {
                urna_eletronica.votaMarcio();
            } else {
                urna_eletronica.votaRui();
            }
        }
    }
}


class Urna {
    // Contadores de votos
    int padreKelmon = 0;
    int Marcio = 0;
    int RuiCosta = 0;

    // Construtor
    public Urna(int _padreKelmon, int _Marcio, int _RuiCosta) {
        padreKelmon = _padreKelmon;
        Marcio = _Marcio;
        RuiCosta = _RuiCosta;
    }

    // Métodos para aumentar o número de votos de cada candidato
    public void votaKelmon() {
        padreKelmon++;
    }

    public void votaMarcio() {
        Marcio++;
    }

    public void votaRui() {
        RuiCosta++;
    }

    // apuracao() printa o número de votos individuais e retorna a soma dos votos para conferência dos resultados
    public int apuracao() {
        System.out.println("Votos - padre Kelmon: " + padreKelmon);
        System.out.println("Votos - Marcio: " + Marcio);
        System.out.println("Votos - Rui Costa: " + RuiCosta);
        return padreKelmon + Marcio + RuiCosta;
    }
}


/* ------------ Main ------------ */
public class Trabalho_sequencial {
    public static void main(String[] args) {
        Urna urna_eletronica = new Urna(0, 0, 0);
        Eleitor eleitores = new Eleitor(100, urna_eletronica); 

        eleitores.votacao();

        // Verificação da fraudabilidade da urna eletrônica
        int votos_totais = urna_eletronica.apuracao();
        if (votos_totais == 100) {
            System.out.println("Votação encerrada com sucesso!");
        } else {
            System.out.println("Foi confirmada a Fraude!"); // ladroes
        }
    }
}
