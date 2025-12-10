package com.openclassrooms.starterjwt.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.time.LocalDateTime;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@Data
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of = {"id", "email", "lastName", "firstName", "password", "admin"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Size(max = 50)
    @Email
    @Column(nullable = false)
    private String email;

    
    @Size(max = 20)
    @Column(nullable = false)
    private String lastName;

    
    @Size(max = 20)
    @Column(nullable = false)
    private String firstName;

    
    @Size(max = 120)
    @Column(nullable = false)
    private String password;

    
    private boolean admin;

        /**
         * Méthode utilitaire pour vérifier si l'utilisateur est administrateur et a un email d'entreprise.
         * Un utilisateur est considéré comme "superAdmin" si admin=true et email se termine par "@entreprise.com".
         */
        public boolean isSuperAdmin() {
            if (!admin) return false;
            if (email == null) return false;
            return email.endsWith("@entreprise.com");
        }

    @CreatedDate
    @Column(updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

}
