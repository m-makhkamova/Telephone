package uz.itschool.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import uz.itschool.dataclass.Contact

class DBHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "ContactDatabase"
        const val TABLE_CONTACTS = "ContactTable"
        const val ID = "id"
        const val NAME = "name"
        const val SURNAME = "surname"
        const val NUMBER = "phone_number"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE " + TABLE_CONTACTS + "("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT," + NAME +" TEXT," + SURNAME +" TEXT," + NUMBER + " TEXT" + ")"
        db?.execSQL(query)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    fun addContact(contact:Contact):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, contact.name)
        if(contact.surname != null){
            contentValues.put(SURNAME, contact.surname)}
        contentValues.put(NUMBER, contact.phone)

        val success = db.insert(TABLE_CONTACTS, null, contentValues)

        return success
    }

    fun deleteContact(contact: Contact):Int{
        val db = this.writableDatabase
        val success = db.delete(TABLE_CONTACTS, "id="+contact.id, null)
        db.close()
        return success
    }

    fun updateContact(contact: Contact):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, contact.name)
        if(contact.surname != null){
            contentValues.put(SURNAME, contact.surname)}
        contentValues.put(NUMBER, contact.phone)

        val success = db.update(TABLE_CONTACTS, contentValues, "id="+contact.id, null)

        return success
    }

    @SuppressLint("Range")
    fun viewContact():MutableList<Contact>{
        val contactsList:MutableList<Contact> = mutableListOf()
        val selectQuery = "SELECT * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch(e: SQLiteException){
            db.execSQL(selectQuery)
            return  mutableListOf()
        }

        var contactId:Int
        var contactName: String
        var contactSurname: String
        var contactNumber: String
        if(cursor.moveToFirst()){
            do{
                contactId = cursor.getInt(cursor.getColumnIndex(ID))
                contactName = cursor.getString(cursor.getColumnIndex(NAME))
                contactSurname = cursor.getString(cursor.getColumnIndex(SURNAME))
                contactNumber = cursor.getString(cursor.getColumnIndex(NUMBER))
                val emp = Contact(id = contactId, name = contactName, surname = contactSurname, phone = contactNumber)
                contactsList.add(emp)
            } while (cursor.moveToNext())
        }
        return contactsList
    }
}