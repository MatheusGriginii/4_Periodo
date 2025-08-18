package entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import entity.Vaga;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo nome é obrigatório")
    private String nome;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    @JsonIgnoreProperties("usuarios")
    @NotNull(message = "O campo endereco é obrigatório")
    private Endereco endereco;

    @ManyToMany(mappedBy = "usuarios")
    @JsonIgnoreProperties("usuarios")
    private List<Vaga> vagas;
}