import br.com.ses.controller.maquina.MaquinaController;
import br.com.ses.controller.maquina.dto.MaquinaRequestDTO;
import br.com.ses.repository.maquina.MaquinaRepository;
import br.com.ses.repository.maquina.MaquinaRepositoryImpl;
import br.com.ses.repository.maquina.dao.MaquinaDao;
import br.com.ses.repository.maquina.dao.MaquinaDaoImpl;
import br.com.ses.service.maquina.MaquinaService;

public class Main {
    public static void main(String[] args) {
        //  instanciar as dependências de baixo para cima
        MaquinaDao dao = new MaquinaDaoImpl();
        MaquinaRepository repository = new MaquinaRepositoryImpl(dao);
        MaquinaService service = new MaquinaService(repository);
        MaquinaController controller = new MaquinaController(service);

        // criar o DTO de teste
        MaquinaRequestDTO request = new MaquinaRequestDTO("Massey Ferguson 5M", false);

        try {
            // 3. Chamar o controller para persistir
            System.out.println("Tentando persistir a máquina...");
            controller.adicionar(request);
            System.out.println("Máquina persistida com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao persistir: " + e.getMessage());
            e.printStackTrace();
        }
    }
}