package entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import jakarta.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
public class Vaga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo título da vaga é obrigatório")
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    @JsonIgnoreProperties("vagas")
    private Empresa empresa;

    @ManyToMany
    @JoinTable(
            name = "vaga_candidato",
            joinColumns = @JoinColumn(name = "vaga_id"),
            inverseJoinColumns = @JoinColumn(name = "candidato_id")
    )
    @JsonIgnoreProperties("vagas")
    private List<Candidato> candidatos;
}