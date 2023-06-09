package com.notes.notes.Repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notes.notes.Model.Notes;

public interface INotesRepository extends JpaRepository<Notes, Long>{
    
    public Collection<Notes> findByPatientId(Long patientId);
}
