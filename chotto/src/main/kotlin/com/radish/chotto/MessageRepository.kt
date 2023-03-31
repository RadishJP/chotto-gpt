package com.radish.chotto

import org.springframework.data.mongodb.repository.MongoRepository

interface MessageRepository : MongoRepository<Message, String>