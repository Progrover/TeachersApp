package dev.platovco.mentorium.feature.chat.impl.data.api


import dev.platovco.mentorium.core.base.data.appwrite.AppwriteManager
import io.appwrite.services.Account
import io.appwrite.services.Databases
import io.appwrite.services.Functions

class ChatApi(
    private val functions: Functions,
    private val databases: Databases,
    private val account: Account,
) : AppwriteManager(functions, databases, account)