package com.crealink.app.dto.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UsernameAvailabilityDto {

    private Boolean available;
}
