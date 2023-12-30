package com.southernsunrise.notesapp.data.repository

import com.southernsunrise.notesapp.data.dao.NoteDao
import com.southernsunrise.notesapp.data.entity.NoteEntity
import com.southernsunrise.notesapp.data.repository.INoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(private val noteDao: NoteDao) : INoteRepository {

    override suspend fun insertNotes(notes: List<NoteEntity>) {
        noteDao.insertNotes(notes)
    }

    override suspend fun insertNote(note: NoteEntity) {
        noteDao.insertNote(note)
    }

    override fun subscribeToAllNotes(): Flow<List<NoteEntity>> {
        return noteDao.subscribeToAllNotes()
    }

    override suspend fun getNotes(ids: List<Long>): List<NoteEntity> {
        return noteDao.getNotes(ids)
    }

    override fun subscribeToAllStarredNotes(): Flow<List<NoteEntity>> {
        return noteDao.subscribeToAllStarredNotes()
    }

    override suspend fun getNote(id: Long?): NoteEntity? {
        return noteDao.getNote(id)
    }

    override suspend fun deleteAll() {
        noteDao.deleteAll()
    }

    override suspend fun deleteNote(id: Long?) {
        noteDao.deleteNote(id)
    }

}
