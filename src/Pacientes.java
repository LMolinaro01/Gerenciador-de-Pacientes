import java.io.IOException;

public class Pacientes {
    private String nome;
    private String tratamento;
    private String celular;
    private String genero;
    private String faixaEtaria;
    private int idade;
    private String fisioResponsavel;
    private String frequencia;

    public Pacientes(String nome, String tratamento, String celular, String genero, int idade, String fisioResponsavel, String frequencia){
        this.nome = nome;
        this.tratamento = tratamento;
        this.celular = celular;
        this.genero = genero;
        this.idade = idade;
        this.fisioResponsavel = fisioResponsavel;
        this.frequencia = frequencia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome){
        try{
            if (!nome.equals(null) && !nome.isEmpty()){
                if (nome.matches(".*[;,'\\-+\\.].*")){
                    System.out.println("O nome não pode conter caracteres especiais.");
                }
                else {
                    this.nome = nome;
                }
            }else {
                System.out.println("O nome não pode estar vazio.");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getTratamento(){
        return tratamento;
    }

    public void setTratamento(String tratamento){
        try {
            if (tratamento.equals("Acupuntura") && getIdade() < 5){
                System.out.println("Tratamento não indicado para esta idade.");
            }
            else{
                this.tratamento = tratamento;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        String validacaoCel = "^\\(?[1-9]{2}\\)? ?(?:[2-8]|9[0-9])[0-9]{3}\\-?[0-9]{4}$\n";
        try {
            if (celular.matches(validacaoCel)){
                this.celular = celular;
            }
            else{
                System.out.println("Número de Celular inválido.");
            }
        } catch (Exception e){
            System.out.println("Houve um Erro na validação do Celular.");
            e.printStackTrace();
        }
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        try{
            if (genero.matches(".*[;,'\\-+\\.].*")){
                System.out.println("O Gênero não pode conter caracteres especiais.");
            }
            else {
                this.genero = genero;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getFaixaEtaria() {
        return faixaEtaria;
    }

    public void setFaixaEtaria(String faixaEtaria) {
        try {
            if (getIdade() >= 65){
                faixaEtaria = "Idoso";
            } else if (getIdade() >= 25) {
                faixaEtaria = "Adulto";
            } else if (getIdade() >= 18) {
                faixaEtaria = "Jovem Adulto";
            } else if (getIdade() >= 15) {
                faixaEtaria = "Adolescente";
            } else if (getIdade() >= 11){
                faixaEtaria = "Pré Adolescente";
            } else if (getIdade() > 5){
                faixaEtaria = "Criança";
            } else if (getIdade() < 5) {
                faixaEtaria = "Criança Pequena";
            }
            this.faixaEtaria = faixaEtaria;
        } catch (Exception e){
            System.out.println("Houve um Erro no cálculo da Faixa Etária.");
            e.printStackTrace();
        }
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        try {
            if (idade > 0 && idade < 120) {
                this.idade = idade;
            }
            else {
                System.out.println("Idade inválida");
            }
        } catch (ClassCastException e){
            System.out.println("Houve um Erro na validação da Idade.");
            e.printStackTrace();
        }
    }

    public String getFisioResponsavel(){
        return fisioResponsavel;
    }

    public void setFisioResponsavel(){
        try{
            if (!nome.equals(null) && !nome.isEmpty()){
                if (nome.matches(".*[;,'\\-+\\.].*")){
                    System.out.println("O Nome do Profissional Responsável não pode conter caracteres especiais.");
                }
                else {
                    this.nome = nome;
                }
            }else {
                System.out.println("O Nome do Profissional Responsável não pode estar vazio.");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getFrequencia(){
        return frequencia;
    }

    public void setFrequencia(){
        try{
            this.frequencia = frequencia;
        } catch (Exception e){
            System.out.println("A frequência semanal só pode ser Número (Do tipo inteiro).");
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return "\nInformações do Paciente: \n\n-Nome: " + getNome() + "\n-Idade: " + getIdade() + "\n-Gênero: " + getGenero() + "\n-Celular: " + getCelular() + "\nTratamento: " +getTratamento() + "\nFaixa Etária: " + getFaixaEtaria();
    }
}
