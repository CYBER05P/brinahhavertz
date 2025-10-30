package com.Brinah.Brits.Property.Manager.Entities;

import com.Brinah.Brits.Property.Manager.Enums.PropertyStatus;
import com.Brinah.Brits.Property.Manager.Enums.PropertyType;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String propertyName;
    private String location;
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyType type;

    @Enumerated(EnumType.STRING)
    private PropertyStatus status;

    // Linked to the landlord (User)
    @ManyToOne
    @JoinColumn(name = "landlord_id")
    private User landlord;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Unit> units;
}
