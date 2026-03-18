package com.example.getnoted.data

import com.example.getnoted.viewModel.Notebook
import com.example.getnoted.viewModel.Note
import io.github.jan.supabase.postgrest.from

object NotesRepository {

    suspend fun getNotebooksByUser(userId: Int): List<Notebook> {

        return supabase.from("Notebooks").select {
            filter {
                eq(column = "user", value = userId)
            }
        }.decodeList<Notebook>()
    }

    suspend fun getNotesByNotebook(notebookId: Int): List<Note> {

        return supabase.from("Notes").select {
            filter {
                eq(column = "notebook_id", value = notebookId)
            }
        }.decodeList<Note>()

    }

    suspend fun addNotebook(notebook: Notebook) {

        supabase.from("Notebooks").insert(notebook)
    }

    suspend fun addNote(note: Note) {
        supabase.from("Notes").insert(note)
    }


    suspend fun updateNote(columnToChange: String, newValue: String, noteId: Int) {
        supabase.from("Notes").update(
            {
                set(columnToChange, newValue)
            }
        ) {
            filter {
                eq("id", noteId)
            }
        }.decodeSingleOrNull<Note>()
    }

    suspend fun updateNotebook(columnToChange: String, newValue: String, noteId: Int) {
        supabase.from("Notes").update(
            {
                set(columnToChange, newValue)
            }
        ) {
            filter {
                eq("id", noteId)
            }
        }.decodeSingleOrNull<Notebook>()
    }

    suspend fun deleteNotebook(notebookId: Int) {
        supabase.from("Notebooks").delete {
            filter {
                eq("id", notebookId)
            }
        }
        supabase.from("Notes").delete {
            filter {
                eq("notebook_id", notebookId)
            }
        }
    }

    suspend fun deleteNote(noteId: Int) {
        supabase.from("Notes").delete {
            filter {
                eq("id", noteId)
            }
        }
    }
}