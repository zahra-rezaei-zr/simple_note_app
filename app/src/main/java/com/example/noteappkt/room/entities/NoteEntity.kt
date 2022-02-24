package com.example.noteappkt.room.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.noteappkt.model.Note

@Entity(tableName = "noteTable")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val uId:Int,
    val noteModel:Note

):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        TODO("noteModel")
    )


    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<NoteEntity> {
        override fun createFromParcel(parcel: Parcel): NoteEntity {
            return NoteEntity(parcel)
        }

        override fun newArray(size: Int): Array<NoteEntity?> {
            return arrayOfNulls(size)
        }
    }
}
