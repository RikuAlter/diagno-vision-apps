package com.diagno.vision.webapps.diagnovision.dto;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

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
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private User user;

    private String imageDescription;
    @NonNull
    private Date uploadDate;
    @NonNull
    private Date creationDate;

    private boolean markedForDeletion;
}
