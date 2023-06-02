package com.notes.notes.Controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/notes")
public class NotesController {

    @Autowired
    private INotesRepository notesRepository;

    // posting notes
    @PostMapping("/add")
    public void addNotes(@RequestBody Notes notes) {
        notesRepository.save(notes);
    }

    // Getting notes with id

    @GetMapping("/{id}")
    public ResponseEntity<Notes> getNotes(@PathVariable Long id) {
        Optional<Notes> note =  notesRepository.findById(id);
        return ResponseEntity.ok().body(note.get());
    }

    // Getting all notes
    @GetMapping("/all")
    public Collection<Notes> getAllNotes() {
        return notesRepository.findAll();
    }

    // Delete a note with id
    @DeleteMapping("/{id}")
    public void deleteNotes(@PathVariable Long id) {
        notesRepository.deleteById(id);

    }


    //Update a note with id
    @PutMapping("/update/{id}")
    public String updateNote(@PathVariable Long id, @RequestBody Notes updateNote){
        Optional<Notes> note =  notesRepository.findById(id);
        Notes note2 = note.get();
        note2.setContent(updateNote.getContent());
        note2.setUpdated(updateNote.getUpdated());
        notesRepository.save(note2);

        return "Note has updated successfully";
    }
}
