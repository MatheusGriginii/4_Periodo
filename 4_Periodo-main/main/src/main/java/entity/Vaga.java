package entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Getter @Setter
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