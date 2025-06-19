import java.util.Random;

class Eleitor {
    private Urna urna_eletronica;
    int total_votos;
    Random random = new Random();

    public Eleitor(int _total_votos, Urna urna) {
        total_votos = _total_votos;
        this.urna_eletronica = urna;
    }

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
    int padreKelmon = 0;
    int Marcio = 0;
    int RuiCosta = 0;

    public Urna(int _padreKelmon, int _Marcio, int _RuiCosta) {
        padreKelmon = _padreKelmon;
        Marcio = _Marcio;
        RuiCosta = _RuiCosta;
    }

    public void votaKelmon() {
        padreKelmon++;
    }

    public void votaMarcio() {
        Marcio++;
    }

    public void votaRui() {
        RuiCosta++;
    }

    public int apuracao() {
        System.out.println("Votos - padre Kelmon: " + padreKelmon);
        System.out.println("Votos - Marcio: " + Marcio);
        System.out.println("Votos - Rui Costa: " + RuiCosta);
        return padreKelmon + Marcio + RuiCosta;
    }
}


public class Trabalho {
    public static void main(String[] args) {
        Urna urna_eletronica = new Urna(0, 0, 0);
        Eleitor eleitores = new Eleitor(100, urna_eletronica); 

        eleitores.votacao();

        int votos_totais = urna_eletronica.apuracao();
        if (votos_totais == 100) {
            System.out.println("Votação encerrada com sucesso!");
        } else {
            System.out.println("Foi confirmada a Fraude!");
        }
    }
}
