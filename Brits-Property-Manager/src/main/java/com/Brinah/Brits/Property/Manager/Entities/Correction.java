package com.Brinah.Brits.Property.Manager.Entities;

import com.Brinah.Brits.Property.Manager.Enums.CorrectionStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "corrections")
public class Correction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String fieldName;
    private String oldValue;
    private String newValue;
    private LocalDateTime correctionDate;

    @Enumerated(EnumType.STRING)
    private CorrectionStatus status;

    @ManyToOne
    @JoinColumn(name = "actor_id")
    private User actor;
}
