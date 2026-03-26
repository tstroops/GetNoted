package com.example.getnoted.data

import com.example.getnoted.viewModel.Notebook
import com.example.getnoted.viewModel.Note
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from

object NotesRepository {

    suspend fun getNotebooksByUser(): List<Notebook> {
        /**
         * @return list of notebooks for the current user
         * retrieves the user ID
         * retrieves the notebooks from the supabase table based on user ID
         */
        val uuid = supabase.auth.currentUserOrNull()?.id ?: return emptyList()
        return supabase.from("notebooks").select {
            filter {
                eq(column = "user", value = uuid)
            }
        }.decodeList<Notebook>()
    }

    suspend fun getNotesByNotebook(notebookId: Long): List<Note> {
        /**
         * @param notebookId the id of the notebook
         * @return list of notes for the current notebook
         * retrieves all notes from the supabase table based on notebook ID
         */
        return supabase.from("notes").select {
            filter {
                eq(column = "notebook_id", value = notebookId)
            }
        }.decodeList<Note>()

    }

    suspend fun addNotebook(title: String) {
        /**
         * @param title the title of the notebook
         * @return none
         * retrieves the user ID
         * creates a new notebook with the given title and user ID
         * inserts a new notebook into the supabase table
         */
        val uuid = supabase.auth.currentUserOrNull()?.id ?: return
        supabase.from("notebooks").insert(
            Notebook(
                title = title,
                userId = uuid
            )
        )
    }

    suspend fun addNote(title: String, notebookId: Long) {
        /**
         * @param title the title of the notebook
         * @param notebookId the id of the notebook
         * @return none
         * retrieves the user ID
         * creates a new note with the given title and notebook ID
         * inserts a new note into the supabase table
         */
        supabase.from("notes").insert(
            Note(
                name = title,
                info = "",
                nbId = notebookId
            )
        )
    }


    suspend fun updateNote(columnToChange: String, newValue: String, noteId: Int) {
        /**
         * @param columnToChange the column to change
         * @param newValue the text for the notebook
         * @param noteId the id of the note
         * @return none
         * updates the note's text based on the note ID
         */
        supabase.from("notes").update(
            {
                set(columnToChange, newValue)
            }
        ) {
            filter {
                eq("id", noteId)
            }
        }
    }

    suspend fun deleteNotebook(notebookId: Int) {
        /**
         * @param notebookId the id of the notebook
         * @return none
         * deletes all notes associated with the notebook from the supabase table based on the notebook ID
         * deletes the notebook from the supabase table based on the notebook ID
         */
        supabase.from("notes").delete {
            filter {
                eq("notebook_id", notebookId)
            }
        }
        supabase.from("notebooks").delete {
            filter {
                eq("id", notebookId)
            }
        }
    }

    suspend fun deleteNote(noteId: Int) {
        /**
         * @param noteId the id of the note
         * @return none
         * deletes the note from the supabase table based on the note ID
         */
        supabase.from("notes").delete {
            filter {
                eq("id", noteId)
            }
        }
    }
}