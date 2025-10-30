package com.Brinah.Brits.Property.Manager.DTO;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TenantRequestDto {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Occupation is required")
    private String occupation;

    private String employerName;

    private String employerPhone;

    private String photo; // Uploaded file path or URL

    private String idCopy; // Uploaded file path or URL

    private String leaseDocument; // Optional document link

    private String emergencyContactName;

    private String emergencyContactPhone;

    private LocalDate dateJoined;

    private boolean active = true;

    private Long leaseAgreementId;
}

