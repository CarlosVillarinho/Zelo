package br.com.carlosvillarinho.zelo.network

import br.com.carlosvillarinho.zelo.model.Relato
import retrofit2.http.GET

interface RelatoService {
    @GET("posts")
    suspend fun getRelatos(): List<Relato>
}