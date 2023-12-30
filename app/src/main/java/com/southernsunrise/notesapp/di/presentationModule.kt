@file:Suppress("RemoveExplicitTypeArguments")

package com.southernsunrise.notesapp.di

import com.southernsunrise.notesapp.data.repository.IDrawingRepository
import com.southernsunrise.notesapp.data.repository.INoteRepository
import com.southernsunrise.notesapp.ui.fragments.draw.draw.DrawViewModel
import com.southernsunrise.notesapp.ui.fragments.draw.createDrawing.CreateDrawingViewModel
import com.southernsunrise.notesappbottomnav.fragments.draw.drawDetails.DrawingDetailsViewModel
import com.southernsunrise.notesapp.ui.fragments.notes.createNote.CreateNoteViewModel
import com.southernsunrise.notesapp.ui.fragments.notes.noteDetails.NoteDetailsViewModel
import com.southernsunrise.notesappbottomnav.fragments.notes.notes.NotesViewModel
import com.southernsunrise.notesapp.ui.fragments.starreds.StarredViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel { NotesViewModel(noteRepository = get<INoteRepository>()) }
    viewModel { NoteDetailsViewModel(noteRepository = get<INoteRepository>()) }
    viewModel { CreateNoteViewModel(noteRepository = get<INoteRepository>()) }
    viewModel { StarredViewModel(noteRepository = get<INoteRepository>()) }
    viewModel { DrawViewModel(drawingRepository = get<IDrawingRepository>()) }
    viewModel { CreateDrawingViewModel(drawingRepository = get<IDrawingRepository>()) }
    viewModel { DrawingDetailsViewModel(drawingRepository = get<IDrawingRepository>()) }
//   factory<NoteReminderWorker> { NoteReminderWorker(NotesApplication.applicationContext(), ) }
}
