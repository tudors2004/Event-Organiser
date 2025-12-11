package org.example.eventorganiser.DTOs;

import lombok.*;
import org.example.eventorganiser.Models.InvitationStatus;
import org.example.eventorganiser.Models.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuestsDto {
    private User user;
    private InvitationStatus status;
}
