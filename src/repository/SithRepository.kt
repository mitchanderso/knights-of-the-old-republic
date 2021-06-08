package com.ld.learning.repository

import com.ld.learning.domain.Sith
import com.mongodb.client.MongoClient
import org.litote.kmongo.*

class SithRepository {

    val mongoClient: MongoClient = KMongo.createClient()
    val mongoDb = mongoClient.getDatabase("sith")
    val collection = mongoDb.getCollection<Sith>()

    fun getSithByName(name: String) : Sith? = collection.findOne(Sith::name eq name)

}