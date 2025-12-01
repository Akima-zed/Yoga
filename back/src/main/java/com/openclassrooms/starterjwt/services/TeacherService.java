package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour la gestion des enseignants.
 * Toutes les opérations CRUD de lecture sont centralisées ici.
 */
@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    /**
     * Récupère un enseignant par son ID.
     *
     * @param id identifiant de l'enseignant
     * @return enseignant correspondant
     * @throws NotFoundException si l'enseignant n'existe pas
     */
    public Teacher findById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Enseignant introuvable avec id: " + id));
    }

    /**
     * Récupère tous les enseignants.
     *
     * @return liste de tous les enseignants
     */
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }
}
