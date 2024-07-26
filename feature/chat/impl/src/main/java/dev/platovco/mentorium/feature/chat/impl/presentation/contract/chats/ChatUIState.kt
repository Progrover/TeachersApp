package dev.platovco.mentorium.feature.chat.impl.presentation.contract.chats

import dev.platovco.mentorium.core.base.presentation.mvi.UIState
import dev.platovco.mentorium.feature.chat.impl.domain.model.ChatItem

data class ChatUIState(
    val messages: List<ChatItem> = listOf(
        ChatItem(
            chatId = "1",
            title = "My Bratan",
            lastMessage = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            isLastMessageRidden = false,
            picture = "https://static.foto.ru/foto/images/photos/000/001/913/1913742_image_preview.jpg?1705172183",
        ),
        ChatItem(
            chatId = "2",
            title = "Hehehe some guy",
            lastMessage = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            isLastMessageRidden = false,
            picture = "https://i01.fotocdn.net/s128/21a22af88cca51f3/public_pin_l/2903894132.jpg",
        ),
        ChatItem(
            chatId = "3",
            title = "Some girl",
            lastMessage = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            isLastMessageRidden = true,
            picture = "https://a.d-cd.net/3ef2861s-1920.jpg",
        ),
        ChatItem(
            chatId = "4",
            title = "Another girl",
            lastMessage = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            isLastMessageRidden = true,
            picture = "https://sun9-70.userapi.com/impf/c830708/v830708555/d95ac/qq0LO6Zl_qk.jpg?size=542x600&quality=96&sign=966bb1e68841f33893deee6df4e2dcd7&c_uniq_tag=rcfyOgIEmcaOJzTvTf8cE776_Uz4W_Jn8N3AGuOmjec&type=album",
        ),
        ChatItem(
            chatId = "5",
            title = "Sime person",
            lastMessage = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            isLastMessageRidden = true,
            picture = "https://data.1freewallpapers.com/detail/nick-offerman-in-parks-and-recreation.jpg",
        ),
    ),
) : UIState