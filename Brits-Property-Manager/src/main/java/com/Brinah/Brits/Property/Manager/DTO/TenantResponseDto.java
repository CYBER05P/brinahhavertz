package com.Brinah.Brits.Property.Manager.DTO;


import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TenantResponseDto {

    private Long id;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String occupation;

    private String employerName;

    private String employerPhone;

    private String photo;

    private String idCopy;

    private String leaseDocument;

    private String emergencyContactName;

    private String emergencyContactPhone;

    private LocalDate dateJoined;

    private boolean active;

    private String leaseAgreementReference;
}

