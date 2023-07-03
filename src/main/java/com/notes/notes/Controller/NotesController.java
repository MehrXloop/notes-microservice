package com.notes.notes.Controller;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notes.notes.Model.Notes;
import com.notes.notes.Repository.INotesRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/notes")
public class NotesController {

    @Autowired
    private INotesRepository notesRepository;

    // Posting notes
    @PostMapping("/add")
    public Notes addNotes(@RequestBody Notes notes) {
        return notesRepository.save(notes);
    }

    // Getting notes by ID
    @GetMapping("/{id}")
    public ResponseEntity<Notes> getNotes(@PathVariable Long id) {
        try {
            Optional<Notes> note = notesRepository.findById(id);
            if (note.isPresent()) {
                return ResponseEntity.ok().body(note.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Getting notes by patient ID
    @GetMapping("/notesByPatientId/{patientId}")
    public ResponseEntity<Collection<Notes>> getNotesByPatientId(@PathVariable Long patientId) {
        try {
            Collection<Notes> notes = notesRepository.findByPatientId(patientId);
            return ResponseEntity.ok().body(notes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Getting all notes
    @GetMapping("/all")
    public ResponseEntity<Collection<Notes>> getAllNotes() {
        try {
            Collection<Notes> notes = notesRepository.findAll();
            return ResponseEntity.ok().body(notes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Delete a note by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotes(@PathVariable Long id) {
        try {
            notesRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Update a note by ID
    @PutMapping("/update")
    public ResponseEntity<String> updateNote(@RequestBody Notes updateNote) {
        try {
            Optional<Notes> note = notesRepository.findById(updateNote.getId());

            if (note.isPresent()) {
                Notes note2 = note.get();
                note2.setContent(updateNote.getContent());
                note2.setUpdated(updateNote.getUpdated());
                notesRepository.save(note2);
                return ResponseEntity.ok("Note has been updated successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
     
    //deleting all notes
    @DeleteMapping("/deleteall")
    public void deleteAllNotes(){
        notesRepository.deleteAll();
    }

}
