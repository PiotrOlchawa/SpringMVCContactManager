package pl.somehost.contactmanager.messageclient;

import pl.somehost.contactmanager.domain.message.enums.MessageStatus;

public interface IMessageClient<T> {
    MessageStatus sendMessage(T e);
}
