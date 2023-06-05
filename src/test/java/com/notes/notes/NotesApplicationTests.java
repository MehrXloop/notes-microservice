package com.notes.notes;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notes.notes.Controller.NotesController;
import com.notes.notes.Model.Notes;
import com.notes.notes.Repository.INotesRepository;

@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@SpringBootTest
class NotesApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Mock
	private INotesRepository notesRepository;

	@InjectMocks
	private NotesController notesController;

	private JacksonTester<Notes> jsonNote;
	private JacksonTester<List<Notes>> jsonNotes;

	@BeforeEach
	public void setUp() {
		JacksonTester.initFields(this, new ObjectMapper());
		mvc = MockMvcBuilders.standaloneSetup(notesController).build();
	}

	// Test1: posting a note

	@Test
	public void canPostANote() throws Exception {
		Notes note = new Notes(1L, 1L, 1L, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()),
				"Lorem ipsum dolor sit amet, consectetur");

		when(notesRepository.save(note)).thenReturn((note));
		mvc.perform(MockMvcRequestBuilders
				.post("/notes/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonNote.write(note).getJson()))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	// Test2: Getting a note with id
	@Test
	public void canGetANote() throws Exception {
		Notes note1 = new Notes(1L, 1L, 1L, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()),
				"Lorem ipsum dolor sit amet, consectetur");
		when(notesRepository.findById(1L)).thenReturn(Optional.of(note1));

		mvc.perform(MockMvcRequestBuilders.get("/notes/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(jsonNote.write(note1).getJson()));
	}

	// Test3: Getting a list of notes
	@Test
	public void canGetAllNotes() throws Exception {
		Notes note1 = new Notes(1L, 1L, 1L, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()),
				"Lorem ipsum dolor sit amet, consectetur");
		Notes note2 = new Notes(2L, 2L, 2L, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()),
				"Lorem ipsum dolor ");
		List<Notes> noteList = new ArrayList<>();
		noteList.add(note1);
		noteList.add(note2);

		when(notesRepository.findAll()).thenReturn(noteList);
		mvc.perform(MockMvcRequestBuilders
				.get("/notes/all")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(jsonNotes.write(noteList).getJson()));
	}

	// Test4: delete a note with id
	@Test
	public void canDeleteANote() throws Exception {
		Long noteId = 1l;
		doNothing().when(notesRepository).deleteById(noteId);
		mvc.perform(MockMvcRequestBuilders
				.delete("/notes/1"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	// Test5: update a note with id
	@Test
	public void canUpdateANote() throws Exception {
		Notes updatenote = new Notes(1L, 1L, 1L, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()),
				"Lorem ipsum dolor sit amet, consectetur");
		when(notesRepository.save(updatenote)).thenReturn((updatenote));
		mvc.perform(MockMvcRequestBuilders
				.put("/notes/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonNote.write(updatenote).getJson()))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
