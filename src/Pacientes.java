import java.io.IOException;

public class Pacientes {
    private String nome;
    private String tratamento;
    private String celular;
    private String genero;
    private String faixaEtaria;
    private int idade;

    public Pacientes(String nome, String tratamento, String celular, String genero, String faixaEtaria, int idade){
        this.nome = nome;
        this.tratamento = tratamento;
        this.celular = celular;
        this.genero = genero;
        this.faixaEtaria = faixaEtaria;
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getTratamento(){
        return tratamento;
    }

    public void setTratamento(String tratamento){
        this.tratamento = tratamento;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        String validacaoCel = "^\\(?[1-9]{2}\\)? ?(?:[2-8]|9[0-9])[0-9]{3}\\-?[0-9]{4}$\n";

        if (celular.matches(validacaoCel)){
            this.celular = celular;
        }
        else{
            System.out.println("Número de Celular inválido.");
        }
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFaixaEtaria() {
        return faixaEtaria;
    }

    public void setFaixaEtaria(String faixaEtaria) {
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
        } else if (getIdade() > 0){
            faixaEtaria = "Infantil";
        }
        this.faixaEtaria = faixaEtaria;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        try {
            if (idade > 0 && idade < 130) {
                this.idade = idade;
            }
            else {
                System.out.println("Idade inválida");
            }
        } catch (ClassCastException e){
            e.printStackTrace();
        }
    }
}
