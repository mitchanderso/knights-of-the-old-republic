package com.ld.learning.repository

import com.ld.learning.domain.Jedi
import com.ld.learning.domain.enum.JediRank
import com.mongodb.client.MongoClient
import org.litote.kmongo.*

class JediRepository {

    val mongoClient: MongoClient = KMongo.createClient()
    val mongoDb = mongoClient.getDatabase("jedi")
    val collection = mongoDb.getCollection<Jedi>()

    fun getJediByName(name: String) : Jedi? = collection.findOne(Jedi::name eq name)

}