package br.com.estudo.service

import br.com.estudo.domain.User
import br.com.estudo.domain.UserResult
import br.com.estudo.infrastructure.repository.UserRepository
import java.lang.Exception

class UserService(private val userRepository: UserRepository) {

    fun create(user: User) = try {
        UserResult.Success(userRepository.createUser(user))
    } catch (e : Exception){
        UserResult.Error(
            message = "Erro ao inserir dado na tabela",
            cause = e
        )
    }



    fun delete(userId: String) = userRepository.delete(userId.toLong())


    fun getAllUsers() = userRepository.findAll()

    fun getUser(userId: String) = userRepository.findById(userId.toLong())

    fun updateUser(user: User, id: String) = userRepository.update(user, id.toLong())

}