package com.example.getnoted.data

import com.example.getnoted.viewModel.Notebook
import com.example.getnoted.viewModel.Note
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from

object NotesRepository {

    suspend fun getNotebooksByUser(): List<Notebook> {
        val uuid = supabase.auth.currentUserOrNull()?.id ?: return emptyList()
        return supabase.from("notebooks").select {
            filter {
                eq(column = "user", value = uuid)
            }
        }.decodeList<Notebook>()
    }

    suspend fun getNotesByNotebook(notebookId: Long): List<Note> {

        return supabase.from("notes").select {
            filter {
                eq(column = "notebook_id", value = notebookId)
            }
        }.decodeList<Note>()

    }

    suspend fun addNotebook(title: String) {
        val uuid = supabase.auth.currentUserOrNull()?.id ?: return
        supabase.from("notebooks").insert(
            Notebook(
                title = title,
                userId = uuid
            )
        )
    }

    suspend fun addNote(title: String, notebookId: Long) {
        supabase.from("notes").insert(
            Note(
                name = title,
                info = "",
                nbId = notebookId
            )
        )
    }


    suspend fun updateNote(columnToChange: String, newValue: String, noteId: Int) {
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
        supabase.from("notes").delete {
            filter {
                eq("id", noteId)
            }
        }
    }
}