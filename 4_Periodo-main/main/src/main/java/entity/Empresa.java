package entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Getter @Setter
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo nome da empresa é obrigatório")
    private String nome;

    @OneToMany(mappedBy = "empresa")
    @JsonIgnoreProperties("empresa")
    private List<Vaga> vagas;
}