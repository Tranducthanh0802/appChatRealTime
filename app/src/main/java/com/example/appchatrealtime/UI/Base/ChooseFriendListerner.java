package com.example.appchatrealtime.UI.Base;

import com.example.appchatrealtime.service.model.Friend;

public interface ChooseFriendListerner {
    void addFriend(Friend friend);
    void removeFriend(Friend friend);
}
