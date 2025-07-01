import java.util.Random;
import java.util.concurrent.Semaphore;
import java.lang.Runnable;



// A classe Eleitor é a que conterá a Thread
class Eleitor implements Runnable{
    private Urna urna_eletronica;
    private int total_votos;
    private Random random = new Random();

    // Construtor
    public Eleitor(int _total_votos, Urna urna) {
        total_votos = _total_votos;
        this.urna_eletronica = urna;
    }

    /*
     * votacao() foi substituido por run() 
     * run() é o metodo que executará a thread
     * 
     * run() simula uma votacao entre os candidatos, 
     * em que cada voto é aleatoriamente atribuido a um candidato.
    */
    @Override
    public void run() {
        String nomeThread = Thread.currentThread().getName();
        System.out.println(nomeThread + " iniciou a votação...");

        for (int i = 0; i < total_votos; i++) {
            int voto = random.nextInt(3);
            
            try {
                // Adquire a permissão ANTES de modificar a urna
                urna_eletronica.semaforo.acquire();

                // --- Seção Crítica ---
                if (voto == 2) {
                    urna_eletronica.votaKelmon();
                } else if (voto == 1) {
                    urna_eletronica.votaMarcio();
                } else {
                    urna_eletronica.votaRui();
                }
                // --- Fim da Seção Crítica ---

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                // Libera a permissão DEPOIS de modificar, para outra thread poder usar.
                // O bloco finally garante que a liberação ocorrerá mesmo se uma exceção acontecer.
                urna_eletronica.semaforo.release();
            }
        }
        System.out.println(nomeThread + " terminou a votação.");
    }

}


class Urna {
    // Contadores de votos
    int padreKelmon = 0;
    int Marcio = 0;
    int RuiCosta = 0;
    Semaphore semaforo = new Semaphore(1);

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
public class Trabalho_protege_urna {
    public static void main(String[] args) throws InterruptedException{

        int total_votos_esperado = 20000000;

        Urna urna_eletronica = new Urna(0, 0, 0);
        Eleitor Eleitor1 = new Eleitor(total_votos_esperado, urna_eletronica);
        Eleitor Eleitor2 = new Eleitor(total_votos_esperado, urna_eletronica);
        Eleitor Eleitor3 = new Eleitor(total_votos_esperado, urna_eletronica);

        Thread t1 = new Thread(Eleitor1);
        Thread t2 = new Thread(Eleitor2);
        Thread t3 = new Thread(Eleitor3);

        System.out.println("Iniciando votação");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        // Verificação da fraudabilidade da urna eletrônica
        int votos_totais = urna_eletronica.apuracao();
        System.out.println("Total de votos esperado: " + 3*total_votos_esperado);
        System.out.println("Total de votos obtido: " + votos_totais);
        if (votos_totais == 3*total_votos_esperado) {
            System.out.println("Votação encerrada com sucesso!");
        } else {
            System.out.println("Foi confirmada a Fraude!"); // ladroes
        }
    }
}
