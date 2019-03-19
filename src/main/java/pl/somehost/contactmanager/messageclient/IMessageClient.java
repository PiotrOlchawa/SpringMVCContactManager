package pl.somehost.contactmanager.messageclient;

import pl.somehost.contactmanager.domain.message.definitions.MessageStatus;

public interface IMessageClient<T> {
    MessageStatus sendMessage(T t);
}
