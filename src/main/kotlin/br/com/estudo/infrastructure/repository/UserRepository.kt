package br.com.estudo.infrastructure.repository

import br.com.estudo.domain.User
import org.jetbrains.exposed.dao.LongIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import javax.sql.DataSource

internal object Users : LongIdTable(){
    val email : Column<String> = varchar("email", 200).uniqueIndex()
    val username : Column<String> = varchar("username", 50).uniqueIndex()
    val password : Column<String> = varchar("password", 50)
    val city : Column<String> = varchar("city", 50)
    val age : Column<Int> = integer("age")

    fun toDomain(row: ResultRow): User {
        return User(id = row[Users.id].value,
                    email = row[Users.email],
                    username = row[Users.username],
                    password = row[Users.password],
                    city = row[Users.city],
                    age = row[Users.age])
    }
}

class UserRepository (private val dataSource: DataSource){

    init {
        transaction(Database.Companion.connect(dataSource)) {
            SchemaUtils.create(Users)
        }
    }

    fun createUser(user : User): Long {
        return transaction(Database.connect(dataSource)){
            Users.insertAndGetId { row ->
                row[Users.email] = user.email
                row[Users.password] = user.password!!
                row[Users.username] = user.username!!
                row[Users.city] = user.city
                row[Users.age] = user.age
            }.value
        }
    }

    fun findById(id : Long) : User? {
        return transaction (Database.connect(dataSource)){
            Users.select { Users.id eq id }.map { Users.toDomain(it) }.firstOrNull()
        }
    }

    fun findAll() = transaction(Database.connect(dataSource)) {
        Users.selectAll().map { Users.toDomain(it) }.toCollection(mutableListOf())
    }

    fun findByEmail(email : String) : User? {
        return transaction(Database.connect(dataSource)) {
            Users.select{Users.email eq email}.map{ Users.toDomain(it) }.firstOrNull()
        }
    }

    fun delete(id : Long) = transaction(Database.connect(dataSource)) {
        Users.deleteWhere { Users.id eq id }
    }

    fun update(user: User, id: Long) = transaction(Database.connect(dataSource)) {
        Users.update({Users.id eq id}){ row ->
            row[email] = user.email
            row[city] = user.city
            row[password] = user.password
            row[age] = user.age
            row[username] = user.username
        }
    }

}