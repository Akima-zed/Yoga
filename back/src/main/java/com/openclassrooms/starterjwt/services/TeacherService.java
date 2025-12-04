package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    /**
     * Service Spring pour gérer les enseignants (Teacher).
     *
     * <p>Expose les opérations de lecture sur les entités
     * {@link com.openclassrooms.starterjwt.models.Teacher} via
     * {@link TeacherRepository}.</p>
     *
     * <p>Exceptions:
     * <ul>
     *   <li>{@link NotFoundException} lorsqu'un enseignant recherché est introuvable</li>
     * </ul>
     * </p>
     */

    private final TeacherRepository teacherRepository;

    /**
     * Constructeur du service.
     *
     * @param teacherRepository repository pour les enseignants (non null)
     */
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    /**
     * Récupère un enseignant par son identifiant.
     *
     * @param id identifiant de l'enseignant (non null)
     * @return l'enseignant correspondant (non null)
     * @throws NotFoundException si aucun enseignant n'est trouvé pour cet id
     */
    public @NonNull Teacher findById(@NonNull Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Enseignant introuvable avec id: " + id));
    }

    /**
     * Retourne la liste de tous les enseignants.
     *
     * @return liste non nulle des enseignants
     */
    public @NonNull List<Teacher> findAll() {
        return teacherRepository.findAll();
    }
}
