package entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Getter @Setter
public class Candidato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo nome do candidato é obrigatório")
    private String nome;

    @ManyToMany(mappedBy = "candidatos")
    @JsonIgnoreProperties("candidatos")
    private List<Vaga> vagas;
}