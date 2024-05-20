package com.agsft.shield.dto.request;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

public class AvengersRequestDTO {
    @Valid
    @NotEmpty
    @NotNull
    @NotBlank
    private String name;

    @Valid
    @NotEmpty
    @NotNull
    @Positive
    private Long userId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "AvengersRequestDTO{" +
                "name='" + name + '\'' +
                ", userId=" + userId +
                '}';
    }
}
