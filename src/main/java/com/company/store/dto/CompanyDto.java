package com.company.store.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CompanyDto {
    private Long companyId;
    private String name;
    private String description;
}
