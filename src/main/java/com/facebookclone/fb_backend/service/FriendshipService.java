package com.facebookclone.fb_backend.service;

import com.facebookclone.fb_backend.entity.Friendship;
import com.facebookclone.fb_backend.repository.FriendshipRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;

    public FriendshipService(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    public Friendship createFriendship(Friendship friendship) {
        return friendshipRepository.save(friendship);
    }

    public void deleteFriendship(Long id) {
        friendshipRepository.deleteById(id);
    }

    public List<Friendship> getFriendRequestsReceived(Long receiverId) {
        return friendshipRepository.findByReceiverIdAndStatus(receiverId, Friendship.Status.pending);
    }

    public List<Friendship> getFriends(Long userId) {
        return friendshipRepository.findByRequesterIdAndStatus(userId, Friendship.Status.accepted);
    }

    // Hàm này lấy người dùng được gửi lời mời kết bạn và đã chấp nhận
    public List<Friendship> getAcceptedFriends(Long userId) {
        return friendshipRepository.findByReceiverIdAndStatus(userId, Friendship.Status.accepted);
    }
}