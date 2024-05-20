package com.agsft.shield.dto.request;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class MissionRequestDTO {
    @Valid
    @NotEmpty
    @NotNull
    @NotBlank
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MissionRequestDTO{" +
                ", name='" + name + '\'' +
                '}';
    }
}
