package com.diagno.vision.webapps.diagnovision.dto;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "image")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    @JoinColumn(name="userEmail", referencedColumnName = "userEmail")
    private User user;

    private String imageDescription;
    @NonNull
    private LocalDateTime uploadDate;
    @NonNull
    private LocalDateTime creationDate;
}
